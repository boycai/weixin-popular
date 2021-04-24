package weixin.popular.bean.message.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 发送图文消息（点击跳转到外链）<br>
 * 图文消息条数限制在1条以内，注意，如果图文数超过1，则将会返回错误码45008。
 *
 * @author LiYi
 */
public class NewsMessage extends Message {

    public NewsMessage() {
    	super.setMsgtype("news");
    }

    public NewsMessage(String touser, List<WxArticle> wxArticleList) {
        super(touser, "news");
        this.news = new News();
        this.news.setArticles(wxArticleList);
    }
    
    /**
     * @since 2.8.26
     * @param touser
     * @param wxArticle
     */
    public NewsMessage(String touser, WxArticle wxArticle) {
    	super(touser, "news");
    	this.news = new News();
    	this.news.setArticles(Collections.singletonList(wxArticle));
    }
    
    private News news;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public static class News {

        private List<WxArticle> articles;

        public List<WxArticle> getArticles() {
            return articles;
        }
        public void setArticles(List<WxArticle> wxArticleList) {
            this.articles = wxArticleList;
        }
        public void addArticle(WxArticle wxArticle) {
        	if(this.articles == null) {
        		this.articles = new ArrayList<WxArticle>();
        	}
        	this.articles.add(wxArticle);
        }
    }

}
