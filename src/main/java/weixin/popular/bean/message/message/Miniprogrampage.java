package weixin.popular.bean.message.message;

public class Miniprogrampage {
		public Miniprogrampage() {
		}
		public Miniprogrampage(String title, String appid, String pagepath, String thumb_media_id) {
			this.title = title;
			this.appid = appid;
			this.pagepath = pagepath;
			this.thumb_media_id = thumb_media_id;
		}
		
		/**
		 * 小程序卡片的标题 - 非必填
		 */
		private String title;
		/**
		 * 小程序的appid，要求小程序的appid需要与公众号有关联关系
		 */
		private String appid;
		/**
		 * 小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar
		 */
		private String pagepath;
		/**
		 * 缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416
		 */
		private String thumb_media_id;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getPagepath() {
			return pagepath;
		}

		public void setPagepath(String pagepath) {
			this.pagepath = pagepath;
		}

		public String getThumb_media_id() {
			return thumb_media_id;
		}

		public void setThumb_media_id(String thumb_media_id) {
			this.thumb_media_id = thumb_media_id;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

}