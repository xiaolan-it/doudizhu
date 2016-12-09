/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package com.xiaolan.netty.v3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 
 *
 * @author wangshiyan
 * @date 2016年10月13日 下午3:49:33
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private static final Logger logger = Logger.getLogger(WebSocketFrameHandler.class);
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private ParameterVO parameterVO;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception { // (1)
		Channel channel = ctx.channel();
		String msgTxt = msg.text();
		logger.info(msgTxt);
		parameterVO = null;
		String channelId = channel.id().asLongText();
		logger.info("channelId:" + channelId);
		try {
			parameterVO = JSONObject.parseObject(msgTxt, ParameterVO.class);
			String type = parameterVO.getType();

			if (Constant.ONLINE.equals(type)) {// 获取在线用户
				channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(OnlineUserList.getAll())));
			} else if (Constant.REGISTER.equals(type)) {// 用户注册
				UserVO userVO = parameterVO.getUserVO();
				userVO.setRegisterTime(System.currentTimeMillis());
				userVO.setChannelId(channelId);// 连接通道的ID赋给用户
				OnlineUserList.add(userVO);// 放到在线列表
				Map<String, Object> returnMap = new HashMap<>();
				returnMap.put("onlineList", OnlineUserList.getAll());
				returnMap.put("channel_id", channelId);
				channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(returnMap)));// 默认把在线用户和自己的channelId推给用户
				// 通知所有用户有用户注册上线
				parameterVO.setType("up");
				for (Channel channel2 : channels) {
					if (channel2 != channel) {
						channel2.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(parameterVO)));
					}
				}
				logger.info("用户注册：" + msgTxt);
			} else {// 发消息给对应用户
				MsgVO msgVO = parameterVO.getMsgVO();
				msgVO.setCreateTime(System.currentTimeMillis());
				if (0 == msgVO.getTo()) {// 群发
					for (Channel channel2 : channels) {
						if (channel != channel2)
							channel2.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(parameterVO)));
					}
				} else {// 私聊
					String to_channel_id = msgVO.getTo_channel_id();
					String channelId2 = null;
					Channel toChannel = null; // 接收消息对象的通道
					for (Channel channel2 : channels) {
						channelId2 = channel2.id().asLongText();
						if (to_channel_id.equals(channelId2)) {
							toChannel = channel2;
						}
					}
					if (null == toChannel) {// 对方用户不在线
						channel.writeAndFlush(
								new TextWebSocketFrame(JSONObject.toJSONString(new ReturnMsgVO("N", "对方不在线！"))));
					} else {
						toChannel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(parameterVO)));
					}
				}

			}
			logger.info("[来着客户端]：" + msgTxt);
		} catch (Exception e) {
			logger.error(e);
			try {
				channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(new ReturnMsgVO("N", "消息格式错误！"))));
			} catch (Exception e2) {
				logger.error("error" + e);
			}

		}

		// for (Channel channel2 : channels) {
		// if (channel2 != channel) {
		// channel2.writeAndFlush(new TextWebSocketFrame("[" +
		// channel.remoteAddress() + "]" + msg.text()));
		// } else {
		//
		// channel2.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text()));
		// }
		// }
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(OnlineUserList.getAll())));// 推送在线用户给所有用户
		}
		channels.add(ctx.channel());
		logger.info("Client:" + incoming.remoteAddress() + "加入");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		Channel incoming = ctx.channel();
		// for (Channel channel : channels) {
		// channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " +
		// incoming.remoteAddress() + " 离开"));
		// }
		logger.info("Client:" + incoming.remoteAddress() + "离开");
		leave(incoming);
	}

	/**
	 * 离线 清除在线用户
	 * 
	 * @param ctx
	 */
	public void leave(Channel channel) {
		String channelId = channel.id().asLongText();// id
		// 清除在线用户
		channels.remove(channel);
		List<UserVO> listUsers = OnlineUserList.getAll();
		for (int i = 0; i < listUsers.size(); i++) {
			if (channelId.equals(listUsers.get(i).getChannelId())) {
				listUsers.remove(i);
				break;
			}
		}

		// 通知所有用户有用户下线
		for (Channel channel2 : channels) {
			if (channel2 != channel) {
				channel2.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(channelId)));
			}
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
		logger.info("Client:" + incoming.remoteAddress() + "在线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel channel = ctx.channel();
		logger.info("Client:" + channel.remoteAddress() + "掉线");
		leave(channel);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		logger.error("Client:" + incoming.remoteAddress() + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

}