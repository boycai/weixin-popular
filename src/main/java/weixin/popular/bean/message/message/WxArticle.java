package weixin.popular.bean.message.message;

public class WxArticle {
	public String title;
	public String description;
	public String url;
	public String picurl;

	public WxArticle() {
	}

	public WxArticle(String title, String description, String url, String picurl) {
		this.title = title;
		this.description = description;
		this.url = url;
		this.picurl = picurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

}