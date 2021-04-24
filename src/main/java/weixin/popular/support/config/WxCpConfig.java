package weixin.popular.support.config;

/**
 * 企业号/企业微信 <br>
 * 企业ID: corpId === appId
 * 
 * @description:
 * @author: 蔡林
 * @date: 2019年1月7日 下午4:46:24
 * @version: V1.0
 */
public class WxCpConfig extends WxAppConfig {
	// private String corpId; // 企业ID === this.appId
	private Integer agentId; // 应用ID
	private String corpAgentId; // 自己组装的企业应用ID. 格式：corpId + '$' + agentId
	private String token;
	private String aesKey;

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getCorpAgentId() {
		if (this.corpAgentId == null) {
			// 自动组装
			this.corpAgentId = this.appId + "$" + this.agentId;
		}
		return this.corpAgentId;
	}

	public void setCorpAgentId(String corpAgentId) {
		this.corpAgentId = corpAgentId;
	}

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