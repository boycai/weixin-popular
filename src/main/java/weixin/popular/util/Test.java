package weixin.popular.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		//String[] jsApiList = null;
		String[] jsApiList = {"chooseWXPay", "onMenuShareTimeline", "onMenuShareAppMessage"};
		String jsapiTicket = "ticketxxx";
		boolean debug = false;
		String appId = "appidxxx";
		String url = "https://www.abc.com/index";
		
		String json = JsUtil.generateConfigJson(jsapiTicket, debug, appId, url, jsApiList);
		System.out.println(json);
		String str = JsUtil.generateConfigStr(jsapiTicket, debug, appId, url, jsApiList);
		System.out.println(str);
		System.out.println(WxJsonUtil.toJSONString(WxJsonUtil.parseObject(str, LinkedHashMap.class)));
	}

}
