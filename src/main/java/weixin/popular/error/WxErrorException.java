package weixin.popular.error;

import weixin.popular.bean.BaseResult;

public class WxErrorException extends Exception {
	private static final long serialVersionUID = 6339191832610004067L;

	private BaseResult error;

	public WxErrorException(BaseResult error) {
		super(error != null ? error.toString() : "error");
		this.error = error;
	}

	public WxErrorException(BaseResult error, Throwable cause) {
		super(error != null ? error.toString() : "error", cause);
		this.error = error;
	}

	public BaseResult getError() {
		return this.error;
	}

}
