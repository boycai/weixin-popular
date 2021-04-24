package weixin.popular.bean.paymch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class PayBankResult extends MchBase {

	private String payment_no;// 微信企业付款单号

	private Integer cmms_amt;// 手续费金额（分）

	public String getPayment_no() {
		return payment_no;
	}

	public void setPayment_no(String payment_no) {
		this.payment_no = payment_no;
	}

	public Integer getCmms_amt() {
		return cmms_amt;
	}

	public void setCmms_amt(Integer cmms_amt) {
		this.cmms_amt = cmms_amt;
	}

}
