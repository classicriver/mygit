/**   
*/
package com.ptae.extra.login;

/**
 * @Description: TODO
 * @author xiesc
 * @date 2017年12月28日
 * @version V1.0  
 */
public class PlatformFactory {

	private static final String QQ = "QQ";
	private static final String WINXIN = "WX";

	public static Platform getPlatformInstance(String type) {
		return PlatformInstance.INSTANCE.getInstance(type);
	}

	private enum PlatformInstance {
		INSTANCE;
		private QQPlatform qqInstance;
		private WXPlatform wxInstance;

		private PlatformInstance() {
			qqInstance = new QQPlatform();
			wxInstance = new WXPlatform();
		}

		public Platform getInstance(String type) {
			if(QQ.equals(type.toUpperCase())) {
				return qqInstance;
			}else if(WINXIN.equals(type.toUpperCase())) {
				return wxInstance;
			}
			return null;
		}
	}

}
