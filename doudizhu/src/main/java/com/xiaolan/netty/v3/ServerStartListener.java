package com.xiaolan.netty.v3;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * socket服务启动
 *
 */
public class ServerStartListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(ServerStartListener.class);

	/**
	 * Default constructor.
	 */
	public ServerStartListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {

		new Thread(new Runnable() {
			public void run() {
				try {
					new WebsocketChatServer(8888).run();
				} catch (Exception e) {
					logger.error("服务启动发生异常：" + e);
				}
			}
		}).start();

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
