/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package test;

/**
 * 
 *
 * @author wangshiyan
 * @date 2016年10月31日 上午10:52:33
 */
public class Test {
	public static void main(String[] args) {

		long millis = System.currentTimeMillis();
		for (int i = 0; i < 29999999; i++) {
			firstLetterToUpper("s121fdfs" + i);

		}
		System.out.println((System.currentTimeMillis() - millis) / 1000 + "秒");
	}

	/**
	 * 1.用了8秒
	 * 
	 * @param str
	 *            <a href="http://my.oschina.net/u/556800" class="referer"
	 *            target="_blank">@return</a>
	 */
	public static String firstLetterToUpper(String str) {
		char[] array = str.toCharArray();
		array[0] -= 32;
		return String.valueOf(array);
	}

	/**
	 * 2.用了10秒
	 * 
	 * @param str
	 *            <a href="http://my.oschina.net/u/556800" class="referer"
	 *            target="_blank">@return</a>
	 */
	public static String lcy_firstLetterToUpper(String str) {
		return String.valueOf(str.charAt(0)).concat(str.substring(1));
	}

	/**
	 * 3.用了11秒
	 * 
	 * @param str
	 *            <a href="http://my.oschina.net/u/556800" class="referer"
	 *            target="_blank">@return</a>
	 */
	public static String letterToUpper(String str) {
		Character c = Character.toUpperCase(str.charAt(0));
		return c.toString().concat(str.substring(1));
	}
}
