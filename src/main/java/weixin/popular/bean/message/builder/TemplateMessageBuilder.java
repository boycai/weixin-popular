package weixin.popular.bean.message.builder;

import java.util.LinkedHashMap;

import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.message.templatemessage.TemplateMessageMiniProgram;

public class TemplateMessageBuilder {

	/**
	 * 模版消息 - 跳转url 
	 * <br>
	 * message.data参数可以通过 message.setDataItem(key, text, color) 方法设置.
	 * 
	 * @param touser
	 * @param templateId
	 * @param data
	 * @param url 非必填
	 * @return
	 */
	public static TemplateMessage build(String touser, String templateId, LinkedHashMap<String, TemplateMessageItem> data, String url) {
		return build(touser, templateId, data, url, null, null);
	}
	
	/**
	 * 模版消息 - 跳转小程序
	 * <br>
	 * message.data参数可以通过 message.setDataItem(key, text, color) 方法设置.
	 * 
	 * @param touser
	 * @param templateId
	 * @param data
	 * @param miniprogram_appid 非必填
	 * @param miniprogram_pagepath 非必填
	 * @return
	 */
	public static TemplateMessage build(String touser, String templateId, LinkedHashMap<String, TemplateMessageItem> data, String miniprogram_appid, String miniprogram_pagepath) {
		return build(touser, templateId, data, null, miniprogram_appid, miniprogram_pagepath);
	}
	
	/**
	 * 模版消息 - 优先跳转小程序，客户端不支持打开小程序时，跳url
	 * <br>
	 * message.data参数可以通过 message.setDataItem(key, text, color) 方法设置.
	 * 
	 * @param touser
	 * @param templateId
	 * @param data
	 * @param url
	 * @param miniprogram_appid
	 * @param miniprogram_pagepath
	 * @return
	 */
	public static TemplateMessage build(String touser, String templateId, LinkedHashMap<String, TemplateMessageItem> data, String url, String miniprogram_appid, String miniprogram_pagepath) {
		TemplateMessage message = new TemplateMessage();
		message.setTouser(touser);
		message.setTemplate_id(templateId);
		if (data != null) {
			message.setData(data);
		}
		// message.setDataItem("first", "再见！", "#173177");
		if (url != null) {
			message.setUrl(url);
		}
		if (miniprogram_appid != null) {
			message.setMiniprogram(new TemplateMessageMiniProgram(miniprogram_appid, miniprogram_pagepath));
		}
		return message;
	}
	
	/**
	 * 
	 * @param touser 用户openid
	 * @param templateId
	 * @param jsonData 【json格式字符串: LinkedHashMap<String, TemplateMessageItem> data --> json 】
	 * @param url 非必填
	 * @return
	 */
	public static String buildToJson(String touser, String templateId, String jsonData, String url) {
		return buildToJson(touser, templateId, jsonData, url, null, null);
	}
	
	/**
	 * 
	 * @param touser 用户openid
	 * @param templateId
	 * @param jsonData 【json格式字符串: LinkedHashMap<String, TemplateMessageItem> data --> json 】
	 * @param miniprogram_appid 小程序appid
	 * @param miniprogram_pagepath 非必填。所需跳转到小程序的具体页面路径
	 * @return
	 */
	public static String buildToJson(String touser, String templateId, String jsonData, String miniprogram_appid, String miniprogram_pagepath) {
		return buildToJson(touser, templateId, jsonData, null, miniprogram_appid, miniprogram_pagepath);
	}
	
	
	/**
	 * 
	 * @param touser 用户openid
	 * @param templateId
	 * @param jsonData 【json格式字符串: LinkedHashMap<String, TemplateMessageItem> data --> json 】
	 * @param url
	 * @param miniprogram_appid 小程序appid
	 * @param miniprogram_pagepath 非必填。所需跳转到小程序的具体页面路径
	 * @return
	 */
	public static String buildToJson(String touser, String templateId, String jsonData, String url, String miniprogram_appid, String miniprogram_pagepath) {
		//"miniprogram":{"appid":"appid12345","pagepath":"index?foo=bar"}
		StringBuilder builder = new StringBuilder();
		builder.append("{\"touser\":\"").append(touser)
		.append("\",\"template_id\":\"").append(templateId)
		.append("\",\"data\":").append(jsonData).append("\"");
		
		if(url != null) {
			builder.append(",\"url\":\"").append(url).append("\"");
		}
		if(miniprogram_appid != null) {
			builder.append(",\"miniprogram\":{\"appid\":\"").append(miniprogram_appid).append("\"");
			if(miniprogram_pagepath != null) {
				builder.append(",\"pagepath\":\"").append(miniprogram_pagepath).append("\"");
			}
			builder.append("}");
		}
		builder.append("}");
		return builder.toString();
	}
	
	
	
	/**
	 * {"touser":"OPENID","template_id":"...","url":"...","data":{"first":{"value":"恭喜你购买成功！","color":"#173177"},"keyword1":{"value":"巧克力","color":"#173177"},"keyword2":{"value":"39.8元","color":"#173177"},"remark":{"value":"欢迎再次购买！","color":"#173177"}}}
	 * @param touser
	 * @param templateId
	 * @param jsonData
	 * @param url
	 * @return
	 */
	@Deprecated
	public static String templateMessageJson(String touser, String templateId, String jsonData, String url) {
		if(url == null) {
			return "{\"touser\":\""+touser+"\",\"template_id\":\""+templateId+"\",\"data\":"+jsonData+"}";
		}
		return "{\"touser\":\""+touser+"\",\"template_id\":\""+templateId+"\",\"data\":"+jsonData+",\"url\":\""+url+"\"}";
	}
	@Deprecated
	public static String templateMessageMiniprogramJson(String touser, String miniprogram_appid, String miniprogram_pagepath, String templateId, String jsonData, String url) {
		//"miniprogram":{"appid":"appid12345","pagepath":"index?foo=bar"}
		if(url == null) {
			return "{\"touser\":\""+touser+"\",\"miniprogram\":{\"appid\":\""+miniprogram_appid+"\",\"pagepath\":\""+miniprogram_pagepath+"\"},\"template_id\":\""+templateId+"\",\"data\":"+jsonData+"}";
		}
		if(miniprogram_pagepath == null) {
			return "{\"touser\":\""+touser+"\",\"miniprogram\":{\"appid\":\""+miniprogram_appid+"\"},\"template_id\":\""+templateId+"\",\"data\":"+jsonData+",\"url\":\""+url+"\"}";
		}
		return "{\"touser\":\""+touser+"\",\"miniprogram\":{\"appid\":\""+miniprogram_appid+"\",\"pagepath\":\""+miniprogram_pagepath+"\"},\"template_id\":\""+templateId+"\",\"data\":"+jsonData+",\"url\":\""+url+"\"}";
	}
	
}
