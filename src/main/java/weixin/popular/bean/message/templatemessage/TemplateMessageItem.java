package weixin.popular.bean.message.templatemessage;

public class TemplateMessageItem{
	public TemplateMessageItem() {
	}

	public TemplateMessageItem(String value) {
		this.value = value;
	}
	public TemplateMessageItem(String value, String color) {
		this.value = value;
		if (color != null) {
			this.color = color;
		}
	}
	
	
	/**
	 * 模板内容文本
	 */
	private String value;
	/**
	 * 模板内容字体颜色，不填默认为黑色
	 */
	private String color;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}


}
