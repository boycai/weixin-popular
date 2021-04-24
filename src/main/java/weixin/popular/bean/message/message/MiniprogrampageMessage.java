package weixin.popular.bean.message.message;

/**
 * 小程序卡片
 * 
 * @serial 2.8.26
 * @author LiYi
 */
public class MiniprogrampageMessage extends Message {
	public MiniprogrampageMessage() {
	}

	public MiniprogrampageMessage(String touser) {
		super(touser, "miniprogrampage");
	}

	public MiniprogrampageMessage(String touser, Miniprogrampage miniprogrampage) {
		this(touser);
		this.miniprogrampage = miniprogrampage;
	}

	private Miniprogrampage miniprogrampage;

	public Miniprogrampage getMiniprogrampage() {
		return miniprogrampage;
	}

	public void setMiniprogrampage(Miniprogrampage miniprogrampage) {
		this.miniprogrampage = miniprogrampage;
	}

}
