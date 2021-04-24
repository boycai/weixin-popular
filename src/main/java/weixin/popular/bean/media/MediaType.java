package weixin.popular.bean.media;

public enum MediaType {
	image {

		@Override
		public String fileSuffix() {
			return "jpg";
		}

		@Override
		public String uploadType() {
			return "image";
		}
	},voice_mp3 {

		@Override
		public String fileSuffix() {
			return "mp3";
		}

		@Override
		public String uploadType() {
			return "voice";
		}
	},voice_amr {

		@Override
		public String fileSuffix() {
			return "amr";
		}

		@Override
		public String uploadType() {
			return "voice";
		}
	},video {
		@Override
		public String fileSuffix() {
			return "mp4";
		}

		@Override
		public String uploadType() {
			return "video";
		}
	},thumb {
		@Override
		public String fileSuffix() {
			return "jpg";
		}

		@Override
		public String uploadType() {
			return "thumb";
		}
	};

	public abstract String fileSuffix();

	/**
	 * 上传类型
	 * @return uploadType
	 */
	public abstract String uploadType();

	
	/**
	 * 解析MediaType
	 * 
	 * @param type  eg: image,voice_mp3,...
	 * @return
	 */
	public static MediaType parse(String type) {
		if (type != null) {
			MediaType[] mediaTypes = values();
			for (MediaType mediaType : mediaTypes) {
				if (type.equals(mediaType.name())) {
					return mediaType;
				}
			}
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		MediaType mediaType = MediaType.parse("image");
//		System.out.println(mediaType);
//		mediaType = MediaType.parse("voice_mp3");
//		System.out.println(mediaType);
//	}
	
}
