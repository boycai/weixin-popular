package weixin.popular.support.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.boycai.base.Utils;
import com.boycai.base.util.StringUtils;

public class WxPayConfig {
	/**
	 * http请求连接超时时间.
	 */
	private int httpConnectionTimeout = 5000;
	/**
	 * http请求数据读取等待时间.
	 */
	private int httpTimeout = 10000;
	/**
	 * 公众号appid.
	 */
	private String appId;
	/**
	 * 商户号.
	 */
	private String mchId;
	/**
	 * 商户密钥.
	 */
	private String mchKey;
	/**
	 * p12证书文件的绝对路径或者以classpath:开头的类路径.
	 */
	private String keyPath;
	/**
	 * 服务商模式下的子商户公众账号ID.
	 */
	private String subAppId;// 服务商模式下的子商户公众账号ID
	/**
	 * 服务商模式下的子商户号.
	 */
	private String subMchId;// 服务商模式下的子商户号
	/**
	 * 微信支付异步回掉地址，通知url必须为直接可访问的url，不能携带参数.
	 */
	private String notifyUrl;
	/**
	 * p12证书文件内容的字节数组.
	 */
	private byte[] keyContent;
	/**
	 * 微信支付是否使用仿真测试环境. 默认不使用
	 */
	private boolean useSandboxEnv = false;
	/**
	 * 签名方式. 有两种HMAC_SHA256 和MD5
	 *
	 * @see com.github.binarywang.wxpay.constant.WxPayConstants.SignType
	 */
	private String signType;

	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getMchKey() {
		return mchKey;
	}
	public void setMchKey(String mchKey) {
		this.mchKey = mchKey;
	}
	public String getKeyPath() {
		return keyPath;
	}
	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}
	public String getSubAppId() {
		return subAppId;
	}
	public void setSubAppId(String subAppId) {
		this.subAppId = subAppId;
	}
	public String getSubMchId() {
		return subMchId;
	}
	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}
	
	public byte[] getKeyContent() {
		return keyContent;
	}
	public void setKeyContent(byte[] keyContent) {
		this.keyContent = keyContent;
	}
	
	
	public InputStream loadKeyPath() {
		if (Utils.isBlank(this.getMchId())) {
			throw new RuntimeException("请确保商户号mchId已设置");
		}
		InputStream inputStream;
		if (this.keyContent != null) {
			inputStream = new ByteArrayInputStream(this.keyContent);
		} else {
			if (Utils.isBlank(this.getKeyPath())) {
				throw new RuntimeException("请确保证书文件地址keyPath已配置");
			}
			
			final String prefix = "classpath:";
			String fileHasProblemMsg = "证书文件【" + this.getKeyPath() + "】有问题，请核实！";
			String fileNotFoundMsg = "证书文件【" + this.getKeyPath() + "】不存在，请核实！";
			if (this.getKeyPath().startsWith(prefix)) {
				String path = StringUtils.removeFirst(this.getKeyPath(), prefix);
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
				inputStream = WxPayConfig.class.getResourceAsStream(path);
				if (inputStream == null) {
					throw new RuntimeException(fileNotFoundMsg);
				}
			} else {
				try {
					File file = new File(this.getKeyPath());
					if (!file.exists()) {
						throw new RuntimeException(fileNotFoundMsg);
					}
					inputStream = new FileInputStream(file);
				} catch (IOException e) {
					throw new RuntimeException(fileHasProblemMsg, e);
				}
			}
		}
		
	    /*try {
	      KeyStore keystore = KeyStore.getInstance("PKCS12");
	      char[] partnerId2charArray = this.getMchId().toCharArray();
	      keystore.load(inputStream, partnerId2charArray);
	      this.sslContext = SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build();
	      return this.sslContext;
	    } catch (Exception e) {
	      throw new WxPayException("证书文件有问题，请核实！", e);
	    } finally {
	      IOUtils.closeQuietly(inputStream);
	    }*/
		return inputStream;
	}
	
}
