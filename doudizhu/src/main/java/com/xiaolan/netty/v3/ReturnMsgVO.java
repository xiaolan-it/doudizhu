/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package com.xiaolan.netty.v3;

/**
 * 返回信息
 * 
 *
 * @author wangshiyan
 * @date 2016年10月13日 下午5:32:48
 */
public class ReturnMsgVO {
	/**
	 * code ,Y:成功，N：失败
	 */
	private String code;
	/**
	 * 信息
	 */
	private String msg;

	/**
	 * @param code
	 * @param msg
	 */
	public ReturnMsgVO(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

}
