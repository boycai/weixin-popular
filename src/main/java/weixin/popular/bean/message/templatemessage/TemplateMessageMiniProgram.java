package weixin.popular.bean.message.templatemessage;

/**
 * 发送的微信模板消息、客服消息中带有跳转小程序的信息
 *
 * @author dave.wu
 * @version 2018/1/9
 */
public class TemplateMessageMiniProgram {
	public TemplateMessageMiniProgram() {
	}
	public TemplateMessageMiniProgram(String appid) {
		this.appid = appid;
	}
	public TemplateMessageMiniProgram(String appid, String pagepath) {
		this.appid = appid;
		if (pagepath != null) {
			this.pagepath = pagepath;
		}
	}
	
	/**
	 * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
	 */
    private String appid;
    /**
     * 非必填。所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏
     */
    private String pagepath;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }
}
