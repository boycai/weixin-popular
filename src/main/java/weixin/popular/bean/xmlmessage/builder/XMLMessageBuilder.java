package weixin.popular.bean.xmlmessage.builder;

import java.util.List;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.message.message.WxArticle;
import weixin.popular.bean.xmlmessage.XMLImageMessage;
import weixin.popular.bean.xmlmessage.XMLMusicMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.bean.xmlmessage.XMLTransferCustomerServiceMessage;
import weixin.popular.bean.xmlmessage.XMLVideoMessage;
import weixin.popular.bean.xmlmessage.XMLVoiceMessage;

public class XMLMessageBuilder {

	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static XMLTextMessage TEXT(String toUserName, String fromUserName, String content) {
		return new XMLTextMessage(toUserName, fromUserName, content);
	}

	/**
	 * 
	 * @param message
	 * @param content
	 * @return
	 */
	public static XMLTextMessage TEXT(EventMessage message, String content) {
		return new XMLTextMessage(message.getFromUserName(), message.getToUserName(), content);
	}

	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param wxArticle
	 * @return
	 */
	public static XMLNewsMessage NEWS(String toUserName, String fromUserName, WxArticle wxArticle) {
		return new XMLNewsMessage(toUserName, fromUserName, wxArticle);
	}

	/**
	 * 
	 * @param message
	 * @param wxArticle
	 * @return
	 */
	public static XMLNewsMessage NEWS(EventMessage message, WxArticle wxArticle) {
		return new XMLNewsMessage(message.getFromUserName(), message.getToUserName(), wxArticle);
	}

	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param wxArticleList
	 * @return
	 */
	public static XMLNewsMessage NEWS(String toUserName, String fromUserName, List<WxArticle> wxArticleList) {
		return new XMLNewsMessage(toUserName, fromUserName, wxArticleList);
	}

	/**
	 * 
	 * @param message
	 * @param wxArticleList
	 * @return
	 */
	public static XMLNewsMessage NEWS(EventMessage message, List<WxArticle> wxArticleList) {
		return new XMLNewsMessage(message.getFromUserName(), message.getToUserName(), wxArticleList);
	}

	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param mediaId
	 * @return
	 */
	public static XMLImageMessage IMAGE(String toUserName, String fromUserName, String mediaId) {
		return new XMLImageMessage(toUserName, fromUserName, mediaId);
	}

	/**
	 * 
	 * @param message
	 * @param mediaId
	 * @return
	 */
	public static XMLImageMessage IMAGE(EventMessage message, String mediaId) {
		return new XMLImageMessage(message.getFromUserName(), message.getToUserName(), mediaId);
	}

	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param title
	 * @param description
	 * @param musicUrl
	 * @param hQMusicUrl
	 * @param thumbMediaId
	 * @return
	 */
	public static XMLMusicMessage MUSIC(String toUserName, String fromUserName, String title, String description, String musicUrl, String hQMusicUrl,
			String thumbMediaId) {
		return new XMLMusicMessage(toUserName, fromUserName, title, description, musicUrl, hQMusicUrl, thumbMediaId);
	}

	/**
	 * 
	 * @param message
	 * @param title
	 * @param description
	 * @param musicUrl
	 * @param hQMusicUrl
	 * @param thumbMediaId
	 * @return
	 */
	public static XMLMusicMessage MUSIC(EventMessage message, String title, String description, String musicUrl, String hQMusicUrl, String thumbMediaId) {
		return new XMLMusicMessage(message.getFromUserName(), message.getToUserName(), title, description, musicUrl, hQMusicUrl, thumbMediaId);
	}

	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param mediaId
	 * @param title
	 * @param description
	 * @return
	 */
	public static XMLVideoMessage VIDEO(String toUserName, String fromUserName, String mediaId, String title, String description) {
		return new XMLVideoMessage(toUserName, fromUserName, mediaId, title, description);
	}

	/**
	 * 
	 * @param message
	 * @param mediaId
	 * @param title
	 * @param description
	 * @return
	 */
	public static XMLVideoMessage VIDEO(EventMessage message, String mediaId, String title, String description) {
		return new XMLVideoMessage(message.getFromUserName(), message.getToUserName(), mediaId, title, description);
	}

	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param mediaId
	 * @return
	 */
	public static XMLVoiceMessage VOICE(String toUserName, String fromUserName, String mediaId) {
		return new XMLVoiceMessage(toUserName, fromUserName, mediaId);
	}

	/**
	 * 
	 * @param message
	 * @param mediaId
	 * @return
	 */
	public static XMLVoiceMessage VOICE(EventMessage message, String mediaId) {
		return new XMLVoiceMessage(message.getFromUserName(), message.getToUserName(), mediaId);
	}

	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param kfAccount
	 * @return
	 */
	public static XMLTransferCustomerServiceMessage TRANSFER_CUSTOMER_SERVICE(String toUserName, String fromUserName, String kfAccount) {
		return new XMLTransferCustomerServiceMessage(toUserName, fromUserName, kfAccount);
	}

	/**
	 * 
	 * @param message
	 * @param kfAccount
	 * @return
	 */
	public static XMLTransferCustomerServiceMessage TRANSFER_CUSTOMER_SERVICE(EventMessage message, String kfAccount) {
		return new XMLTransferCustomerServiceMessage(message.getFromUserName(), message.getToUserName(), kfAccount);
	}

}
