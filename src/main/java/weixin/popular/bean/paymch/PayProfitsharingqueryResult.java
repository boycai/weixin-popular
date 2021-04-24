package weixin.popular.bean.paymch;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class PayProfitsharingqueryResult extends MchBase {

	private String transaction_id;

	private String out_order_no;

	private String order_id;

	private String status;

	private String close_reason;
	
	private Integer amount;//分账金额  分账完结的分账金额，单位为分， 仅当查询分账完结的执行结果时，存在本字段
	private String description;//分账描述  分账完结的原因描述，仅当查询分账完结的执行结果时，存在本字段

	@XmlJavaTypeAdapter(value = ReceiverQuery.JsonXmlAdapter.class)
	private List<ReceiverQuery> receivers;

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_order_no() {
		return out_order_no;
	}

	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClose_reason() {
		return close_reason;
	}

	public void setClose_reason(String close_reason) {
		this.close_reason = close_reason;
	}

	public List<ReceiverQuery> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<ReceiverQuery> receivers) {
		this.receivers = receivers;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
