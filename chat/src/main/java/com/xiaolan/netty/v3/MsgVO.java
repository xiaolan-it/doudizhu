/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package com.xiaolan.netty.v3;

/**
 * 消息对象
 * 
 * @author wangshiyan
 * @date 2016年10月11日 下午3:28:04
 */
public class MsgVO {
	/**
	 * 0:群发,1:私聊
	 */
	private int to;
	/**
	 * 消息类型<br>
	 * txt：文本消息<br>
	 * voice：语音消息<br>
	 * video：视频<br>
	 */

	private String type;

	/**
	 * 发给那个用户ID
	 */
	private String to_user_id;
	/**
	 * 发给那个用户 连接id
	 */
	private String to_channel_id;
	/**
	 * 发给那个用户名称
	 */
	private String to_user_name;
	/**
	 * 那个用户id发的消息
	 */
	private String from_user_id;
	/**
	 * 那个用户名称发的消息
	 */
	private String from_user_name;
	/**
	 * 消息内容
	 */
	private String msg;
	/**
	 * 消息时间
	 */
	private long createTime;
	/**
	 * 用户头像
	 */
	private String userImg;

	/**
	 * @return the to
	 */
	public int getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(int to) {
		this.to = to;
	}

	/**
	 * @return the userImg
	 */
	public String getUserImg() {
		return userImg;
	}

	/**
	 * @param userImg
	 *            the userImg to set
	 */
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	/**
	 * @return the createTime
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the to_channel_id
	 */
	public String getTo_channel_id() {
		return to_channel_id;
	}

	/**
	 * @param to_channel_id
	 *            the to_channel_id to set
	 */
	public void setTo_channel_id(String to_channel_id) {
		this.to_channel_id = to_channel_id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the to_user_name
	 */
	public String getTo_user_name() {
		return to_user_name;
	}

	/**
	 * @param to_user_name
	 *            the to_user_name to set
	 */
	public void setTo_user_name(String to_user_name) {
		this.to_user_name = to_user_name;
	}

	/**
	 * @return the from_user_name
	 */
	public String getFrom_user_name() {
		return from_user_name;
	}

	/**
	 * @param from_user_name
	 *            the from_user_name to set
	 */
	public void setFrom_user_name(String from_user_name) {
		this.from_user_name = from_user_name;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the to_user_id
	 */
	public String getTo_user_id() {
		return to_user_id;
	}

	/**
	 * @param to_user_id
	 *            the to_user_id to set
	 */
	public void setTo_user_id(String to_user_id) {
		this.to_user_id = to_user_id;
	}

	/**
	 * @return the from_user_id
	 */
	public String getFrom_user_id() {
		return from_user_id;
	}

	/**
	 * @param from_user_id
	 *            the from_user_id to set
	 */
	public void setFrom_user_id(String from_user_id) {
		this.from_user_id = from_user_id;
	}

}
