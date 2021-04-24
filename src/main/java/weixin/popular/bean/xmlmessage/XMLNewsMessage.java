package weixin.popular.bean.xmlmessage;

import java.util.Collections;
import java.util.List;

import weixin.popular.bean.message.message.Message;
import weixin.popular.bean.message.message.NewsMessage;
import weixin.popular.bean.message.message.WxArticle;

public class XMLNewsMessage extends XMLMessage {

	private static final long serialVersionUID = -3297287142409782906L;

	private List<WxArticle> articles;

	/**
	 * @param toUserName
	 *            toUserName
	 * @param fromUserName
	 *            fromUserName
	 * @param wxArticleList
	 *            wxArticleList
	 */
	public XMLNewsMessage(String toUserName, String fromUserName, List<WxArticle> wxArticleList) {
		super(toUserName, fromUserName, "news");
		this.articles = wxArticleList;
	}
	
	/**
	 * @since 2.8.26           
	 * @param toUserName
	 *            toUserName
	 * @param fromUserName
	 *            fromUserName
	 * @param wxArticle
	 *            wxArticle
	 */
	public XMLNewsMessage(String toUserName, String fromUserName, WxArticle wxArticle) {
		super(toUserName, fromUserName, "news");
		this.articles = Collections.singletonList(wxArticle);
	}

	@Override
	public String subXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<ArticleCount>" + articles.size() + "</ArticleCount>");
		sb.append("<Articles>");
		for (WxArticle a : articles) {
			sb.append("<item>");
			sb.append("<Title><![CDATA[" + (a.title == null ? "" : a.title) + "]]></Title>");
			sb.append("<Description><![CDATA[" + (a.description == null ? "" : a.description) + "]]></Description>");
			sb.append("<PicUrl><![CDATA[" + (a.picurl == null ? "" : a.picurl) + "]]></PicUrl>");
			sb.append("<Url><![CDATA[" + (a.url == null ? "" : a.url) + "]]></Url>");
			sb.append("</item>");
		}
		sb.append("</Articles>");
		return sb.toString();
	}

	@Override
	public Message convert() {
		return new NewsMessage(toUserName, articles);
	}

}
