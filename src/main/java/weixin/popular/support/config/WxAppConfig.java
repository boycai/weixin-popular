package weixin.popular.support.config;

public class WxAppConfig {
	protected String appId;
	protected String secret;
//	protected String token;
//	protected String aesKey;
	protected WxPayConfig payConfig;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public WxPayConfig getPayConfig() {
		return payConfig;
	}
	public void setPayConfig(WxPayConfig payConfig) {
		this.payConfig = payConfig;
	}
	
}
