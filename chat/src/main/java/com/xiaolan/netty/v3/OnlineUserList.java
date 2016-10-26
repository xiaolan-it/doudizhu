/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package com.xiaolan.netty.v3;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author wangshiyan
 * @date 2016年10月11日 下午3:44:36
 */
public class OnlineUserList {
	private static List<UserVO> listUsers = new ArrayList<UserVO>();

	/**
	 * 添加在线用户
	 * 
	 * @param userVO
	 */
	public static void add(UserVO userVO) {
		listUsers.add(userVO);
	}

	/**
	 * 移除在现用户
	 * 
	 * @param userId
	 *            用户id
	 */
	public static void remove(String userId) {
		for (int i = 0, size = listUsers.size(); i < size; i++) {
			if (userId == listUsers.get(i).getUserId())
				listUsers.remove(i);
		}
	}

	/**
	 * 获取所有在线用户
	 * 
	 * @return
	 */
	public static List<UserVO> getAll() {
		return listUsers;
	}

}
