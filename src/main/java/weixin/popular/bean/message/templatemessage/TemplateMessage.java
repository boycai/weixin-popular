package weixin.popular.bean.message.templatemessage;

import java.util.LinkedHashMap;

/**
 * 模版消息
 * 
 * 注：url和miniprogram都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。开发者可根据实际需要选择其中一种跳转方式即可。当用户的微信客户端版本不支持跳小程序时，将会跳转至url。
 * @author Cailin
 *
 */
public class TemplateMessage {
	public TemplateMessage() {
	}
	/**
	 * data 参数通过 putDataItem 方法设置
	 * 
	 * @param touser
	 * @param templateId
	 * @param url
	 */
	public TemplateMessage(String touser, String templateId, LinkedHashMap<String, TemplateMessageItem> data, String url) {
		this.touser = touser;
		this.template_id = templateId;
		this.data = data;
		this.url = url;
	}
	/**
	 * data 参数通过 putDataItem 方法设置
	 * 
	 * @param touser
	 * @param templateId
	 * @param url
	 * @param miniprogram_appid
	 * @param miniprogram_pagepath
	 */
	public TemplateMessage(String touser, String templateId, LinkedHashMap<String, TemplateMessageItem> data, String url, String miniprogram_appid, String miniprogram_pagepath) {
		this.touser = touser;
		this.template_id = templateId;
		this.data = data;
		this.url = url;
		this.miniprogram = new TemplateMessageMiniProgram(miniprogram_appid, url);
	}

	private String touser;
	private String template_id;
	private LinkedHashMap<String, TemplateMessageItem> data;
	
	/**
	 * 跳小程序所需数据，不需跳小程序可不用传该数据
	 */
	private TemplateMessageMiniProgram miniprogram;
	/**
	 * 模板跳转链接（海外帐号没有跳转能力）
	 */
	private String url;
	
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LinkedHashMap<String, TemplateMessageItem> getData() {
		return data;
	}

	public void setData(LinkedHashMap<String, TemplateMessageItem> data) {
		this.data = data;
	}

	public TemplateMessageMiniProgram getMiniprogram() {
	    return miniprogram;
	}

	public void setMiniprogram(TemplateMessageMiniProgram miniprogram) {
		this.miniprogram = miniprogram;
	}
	
	/**
	 * @param key
	 * @param value
	 * @return 
	 */
	public TemplateMessage setDataItem(String key, String value) {
		return setDataItem(key, value, null);
	}
	/**
	 * @param key
	 * @param value
	 * @param color
	 * @return 
	 */
	public TemplateMessage setDataItem(String key, String value, String color) {
		if (this.data == null) {
			this.data = new LinkedHashMap<String, TemplateMessageItem>();
		}
		this.data.put(key, new TemplateMessageItem(value, color));
		return this;
	}
	
	
}
