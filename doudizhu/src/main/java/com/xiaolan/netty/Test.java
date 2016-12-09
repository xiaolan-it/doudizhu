/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package com.xiaolan.netty;

import com.alibaba.fastjson.JSONObject;
import com.xiaolan.netty.v3.MsgVO;

/**
 * 
 *
 * @author wangshiyan
 * @date 2016年10月11日 下午3:11:47
 */
public class Test {
	private volatile int m = 0;

	public static void main(String[] args) {
		MsgVO msgVO = new MsgVO();
		System.out.println(JSONObject.toJSONString(msgVO));
	}
}
