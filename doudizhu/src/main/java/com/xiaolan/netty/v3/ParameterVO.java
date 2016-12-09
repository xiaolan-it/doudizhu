/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package com.xiaolan.netty.v3;

/**
 * 
 *
 * @author wangshiyan
 * @date 2016年10月13日 下午6:12:06
 */
public class ParameterVO {
	/**
	 * 1、online：获取在线用户<br>
	 * 2、register：注册<br>
	 * 3、msg:发送消息 <br>
	 * 4、up:用户上线
	 */
	private String type;
	/**
	 * 用户信息
	 */
	private UserVO userVO;
	/**
	 * 消息
	 */
	private MsgVO msgVO;

	/**
	 * @return the userVO
	 */
	public UserVO getUserVO() {
		return userVO;
	}

	/**
	 * @param userVO
	 *            the userVO to set
	 */
	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
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
	 * @return the msgVO
	 */
	public MsgVO getMsgVO() {
		return msgVO;
	}

	/**
	 * @param msgVO
	 *            the msgVO to set
	 */
	public void setMsgVO(MsgVO msgVO) {
		this.msgVO = msgVO;
	}

}
