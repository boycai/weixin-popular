package weixin.popular.support.config;

/**
 * @description: 公众号（包括订阅号和服务号）
 * @author: 蔡林
 * @date: 2019年1月7日 下午4:46:42
 * @version: V1.0
 */
public class WxMpConfig extends WxAppConfig {
	private String token;
	private String aesKey;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAesKey() {
		return aesKey;
	}
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
}