package weixin.popular.bean.message.builder;

import java.util.List;

import weixin.popular.bean.message.message.ImageMessage;
import weixin.popular.bean.message.message.Miniprogrampage;
import weixin.popular.bean.message.message.MiniprogrampageMessage;
import weixin.popular.bean.message.message.MusicMessage;
import weixin.popular.bean.message.message.NewsMessage;
import weixin.popular.bean.message.message.TextMessage;
import weixin.popular.bean.message.message.VideoMessage;
import weixin.popular.bean.message.message.VoiceMessage;
import weixin.popular.bean.message.message.WxArticle;
import weixin.popular.bean.message.message.WxcardMessage;
import weixin.popular.bean.message.message.MusicMessage.Music;
import weixin.popular.bean.message.message.VideoMessage.Video;

/**
 * @description: 客服消息
 * @author: 蔡林
 * @date: 2019年1月6日 上午12:25:36
 * @version: V1.0
 */
public class MessageBuilder {

	public static NewsMessage NEWS(String touser, List<WxArticle> articles) {
		return new NewsMessage(touser, articles);
	}
	public static NewsMessage NEWS(String touser, WxArticle article) {
		return new NewsMessage(touser, article);
	}
	public static TextMessage TEXT(String touser, String content) {
		return new TextMessage(touser, content);
	}
	public static ImageMessage IMAGE(String touser, String mediaId) {
        return new ImageMessage(touser, mediaId);
    }
	public static MusicMessage MUSIC(String touser, Music music) {
        return new MusicMessage(touser, music);
    }
	public static VideoMessage VIDEO(String touser, Video video) {
        return new VideoMessage(touser, video);
    }
	public static VoiceMessage VOICE(String touser, String mediaId) {
        return new VoiceMessage(touser, mediaId);
    }
	public static WxcardMessage WXCARD(String touser, String card_id) {
        return new WxcardMessage(touser, card_id);
    }
	public static MiniprogrampageMessage MINIPROGRAM_PAGE(String touser, Miniprogrampage miniprogrampage) {
		return new MiniprogrampageMessage(touser, miniprogrampage);
	}
	
	
	/**
	 * NewMessage JSON
	 * 
	 * @param touser
	 * @param json_articles  必须是数组JSON. （ps: String json_articles = JsonUtils.toJson(wxArticleList);）
	 * @return
	 */
	public static String buildNewsToJson(String touser, String json_articles) {
		// {"touser":"OPENID","msgtype":"news","news":{"articles":[{"title":"Happy Day","description":"Is Really A Happy Day","url":"URL","picurl":"PIC_URL"}]}}
		return "{\"touser\":\"" + touser + "\",\"msgtype\":\"news\",\"news\":{\"articles\":" + json_articles + "}}";
	}
	
	/**
	 * TextMessage JSON
	 * 
	 * @param touser
	 * @param json_content 必须是字符串JSON，非普通json
	 * ps: String contentJson = JsonUtils.toJson(contentStr);
	 * @return
	 */
	public static String buildTextToJson(String touser, String json_content) {
		// {"touser":"openid...","msgtype":"text","text":{"content":"content..."}}
		//"<a href=\"http://www.baidu.com\">hello:rose::rose::rose::rose:word</a>\n"
		//return "{\"touser\":\""+touser+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+contentJson+"\"}}";
		return "{\"touser\":\""+touser+"\",\"msgtype\":\"text\",\"text\":{\"content\":"+json_content+"}}";
	}
	
	@Deprecated
	public static String newsMessageJson(String touser, String articlesJson) {
		return buildNewsToJson(touser, articlesJson);
	}
	@Deprecated
	public static String textMessageJson(String touser, String json_content) {
		return buildTextToJson(touser, json_content);
	}
	
}
