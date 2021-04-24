package weixin.popular.support.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.boycai.base.Utils;
import com.boycai.base.util.ClassPathUtils;

/**
 * @description:
 * @author: 蔡林
 * @date: 2019年1月2日 下午3:59:48
 * @version: V1.0
 */
@SuppressWarnings("unchecked")
public class WechatConfigLoader {
	// wxmp 微信公众号
	// wxcp 微信企业号 corporation
	// wxop 微信开发平台
	// wxma 微信小程序

	public static InputStream loadWechatConfig() {
		/*InputStream ins = null;
		String profile = SystemConfig.getSpringRunMode();
		if (Utils.notEmpty(profile)) {
			ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(String.format("config-%s/%s", profile, "wechat-config.yml"));
		}
		if (ins == null) {
			ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/wechat-config.yml");
		}*/
		InputStream ins = ClassPathUtils.loadConfig("wechat-config.yml");
		if (ins == null) {
			System.out.println("cannot found [classpath:config/wechat-config.yml]");
		}
		return ins;
	}

	/**
	 * 微信小程序
	 * 
	 * @return
	 */
	public static List<WxMaConfig> wxmaConfigs() {
		List<WxMaConfig> configs = new ArrayList<WxMaConfig>();

		InputStream ins = loadWechatConfig();
		if (ins == null) {
			return configs;
		}

		Yaml yaml = new Yaml();
		Map<String, Object> root = (Map<String, Object>) yaml.load(ins);
		// System.out.println("wxconfig:\n" + root);
		Map<String, Object> wxmaMap = (Map<String, Object>) root.get("wxma");

		return parseWxmaConfigs(wxmaMap);
	}

	public static List<WxMaConfig> parseWxmaConfigs(Map<String, Object> wxmaMap) {
		List<WxMaConfig> configs = new ArrayList<WxMaConfig>();
		if (wxmaMap != null) {
			List<Map<String, Object>> configMapList = new ArrayList<Map<String, Object>>();
			Object configsObj = wxmaMap.get("configs");
			if (configsObj instanceof List) {
				configMapList.addAll((List<Map<String, Object>>) configsObj);
			} else if (configsObj instanceof Map) {
				configMapList.add((Map<String, Object>) configsObj);
			}

			for (Map<String, Object> configMap : configMapList) {
				WxMaConfig config = new WxMaConfig();
				config.setAppId(toStr(configMap.get("appId")));
				config.setSecret(toStr(configMap.get("secret")));
				// config.setToken(toStr(configMap.get("token")));
				// config.setAesKey(toStr(configMap.get("aesKey")));
				// payConfig
				Map<String, Object> payConfigMap = (Map<String, Object>) configMap.get("pay");
				if (payConfigMap != null) {
					WxPayConfig payConfig = new WxPayConfig();
					payConfig.setAppId(toStr(payConfigMap.getOrDefault("appId", config.getAppId())));
					payConfig.setMchId(toStr(payConfigMap.get("mchId")));
					payConfig.setMchKey(toStr(payConfigMap.get("mchKey")));
					payConfig.setKeyPath(toStr(payConfigMap.get("keyPath")));
					payConfig.setSubAppId(toStr(payConfigMap.get("subAppId")));
					payConfig.setSubMchId(toStr(payConfigMap.get("subMchId")));
					config.setPayConfig(payConfig);
				}
				configs.add(config);
			}
		}
		return configs;
	}

	/**
	 * 微信公众号
	 * 
	 * @return
	 */
	public static List<WxMpConfig> wxmpConfigs() {
		List<WxMpConfig> configs = new ArrayList<WxMpConfig>();

		InputStream ins = loadWechatConfig();
		if (ins == null) {
			return configs;
		}

		Yaml yaml = new Yaml();
		Map<String, Object> root = (Map<String, Object>) yaml.load(ins);
		// System.out.println("wxconfig:\n" + root);
		Map<String, Object> wxmpMap = (Map<String, Object>) root.get("wxmp");

		return parseWxmpConfigs(wxmpMap);
	}

	public static List<WxMpConfig> parseWxmpConfigs(Map<String, Object> wxmpMap) {
		List<WxMpConfig> configs = new ArrayList<WxMpConfig>();
		if (wxmpMap != null) {
			List<Map<String, Object>> configMapList = new ArrayList<Map<String, Object>>();
			Object configsObj = wxmpMap.get("configs");
			if (configsObj instanceof List) {
				configMapList.addAll((List<Map<String, Object>>) configsObj);
			} else if (configsObj instanceof Map) {
				configMapList.add((Map<String, Object>) configsObj);
			}

			for (Map<String, Object> configMap : configMapList) {
				WxMpConfig config = new WxMpConfig();
				config.setAppId(toStr(configMap.get("appId")));
				config.setSecret(toStr(configMap.get("secret")));
				config.setToken(toStr(configMap.get("token")));
				config.setAesKey(toStr(configMap.get("aesKey")));
				// payConfig
				Map<String, Object> payConfigMap = (Map<String, Object>) configMap.get("pay");
				if (payConfigMap != null) {
					WxPayConfig payConfig = new WxPayConfig();
					payConfig.setAppId(toStr(payConfigMap.getOrDefault("appId", config.getAppId())));
					payConfig.setMchId(toStr(payConfigMap.get("mchId")));
					payConfig.setMchKey(toStr(payConfigMap.get("mchKey")));
					payConfig.setKeyPath(toStr(payConfigMap.get("keyPath")));
					payConfig.setSubAppId(toStr(payConfigMap.get("subAppId")));
					payConfig.setSubMchId(toStr(payConfigMap.get("subMchId")));
					config.setPayConfig(payConfig);
				}
				configs.add(config);
			}
		}
		return configs;
	}

	/**
	 * 微信企业号
	 * 
	 * @return
	 */
	public static List<WxCpConfig> wxcpConfigs() {
		List<WxCpConfig> configs = new ArrayList<WxCpConfig>();
		InputStream ins = loadWechatConfig();
		if (ins == null) {
			return configs;
		}
		Yaml yaml = new Yaml();
		Map<String, Object> root = (Map<String, Object>) yaml.load(ins);
		// System.out.println("wxconfig:\n" + root);
		Map<String, Object> wxcpMap = (Map<String, Object>) root.get("wxcp");

		return parseWxcpConfigs(wxcpMap);
	}

	public static List<WxCpConfig> parseWxcpConfigs(Map<String, Object> wxcpMap) {
		List<WxCpConfig> configs = new ArrayList<WxCpConfig>();
		if (wxcpMap != null) {
			List<Map<String, Object>> configMapList = new ArrayList<Map<String, Object>>();
			Object configsObj = wxcpMap.get("configs");
			if (configsObj instanceof List) {
				configMapList.addAll((List<Map<String, Object>>) configsObj);
			} else if (configsObj instanceof Map) {
				configMapList.add((Map<String, Object>) configsObj);
			}

			for (Map<String, Object> configMap : configMapList) {
				WxCpConfig config = new WxCpConfig();
				config.setAppId(toStr(configMap.get("appId")));// 等同于corpId：企业ID
				config.setSecret(toStr(configMap.get("secret")));
				config.setToken(toStr(configMap.get("token")));
				config.setAesKey(toStr(configMap.get("aesKey")));
				// 企业微信应用ID
				config.setAgentId(toInt(configMap.get("agentId")));
				if (Utils.notEmpty(config.getAppId()) && Utils.notEmpty(config.getAgentId())) {
					config.setCorpAgentId(config.getAppId() + "$" + config.getAgentId());
				}
				config.getAppId();

				// payConfig
				Map<String, Object> payConfigMap = (Map<String, Object>) configMap.get("pay");
				if (payConfigMap != null) {
					WxPayConfig payConfig = new WxPayConfig();
					payConfig.setAppId(toStr(payConfigMap.getOrDefault("appId", config.getAppId())));
					payConfig.setMchId(toStr(payConfigMap.get("mchId")));
					payConfig.setMchKey(toStr(payConfigMap.get("mchKey")));
					payConfig.setKeyPath(toStr(payConfigMap.get("keyPath")));
					payConfig.setSubAppId(toStr(payConfigMap.get("subAppId")));
					payConfig.setSubMchId(toStr(payConfigMap.get("subMchId")));
					config.setPayConfig(payConfig);
				}
				configs.add(config);
			}
		}
		return configs;
	}

	/**
	 * 开放平台
	 * 
	 * @return
	 */
	public static List<WxOpConfig> wxopConfigs() {
		List<WxOpConfig> configs = new ArrayList<WxOpConfig>();

		InputStream ins = loadWechatConfig();
		if (ins == null) {
			return configs;
		}

		Yaml yaml = new Yaml();
		Map<String, Object> root = (Map<String, Object>) yaml.load(ins);
		// System.out.println("wxconfig:\n" + root);
		Map<String, Object> wxopMap = (Map<String, Object>) root.get("wxop");

		return parseWxopConfigs(wxopMap);
	}

	public static List<WxOpConfig> parseWxopConfigs(Map<String, Object> wxopMap) {
		List<WxOpConfig> configs = new ArrayList<WxOpConfig>();
		if (wxopMap != null) {
			List<Map<String, Object>> configMapList = new ArrayList<Map<String, Object>>();
			Object configsObj = wxopMap.get("configs");
			if (configsObj instanceof List) {
				configMapList.addAll((List<Map<String, Object>>) configsObj);
			} else if (configsObj instanceof Map) {
				configMapList.add((Map<String, Object>) configsObj);
			}

			for (Map<String, Object> configMap : configMapList) {
				WxOpConfig config = new WxOpConfig();
				config.setAppId(toStr(configMap.get("appId")));
				config.setSecret(toStr(configMap.get("secret")));
				// config.setToken(toStr(configMap.get("token")));
				// config.setAesKey(toStr(configMap.get("aesKey")));
				// payConfig
				Map<String, Object> payConfigMap = (Map<String, Object>) configMap.get("pay");
				if (payConfigMap != null) {
					WxPayConfig payConfig = new WxPayConfig();
					payConfig.setAppId(toStr(payConfigMap.getOrDefault("appId", config.getAppId())));
					payConfig.setMchId(toStr(payConfigMap.get("mchId")));
					payConfig.setMchKey(toStr(payConfigMap.get("mchKey")));
					payConfig.setKeyPath(toStr(payConfigMap.get("keyPath")));
					payConfig.setSubAppId(toStr(payConfigMap.get("subAppId")));
					payConfig.setSubMchId(toStr(payConfigMap.get("subMchId")));
					config.setPayConfig(payConfig);
				}
				configs.add(config);
			}
		}
		return configs;
	}

	private static String toStr(Object value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

	private static Integer toInt(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		String str = value.toString();
		if (!str.isEmpty()) {
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// public static void main(String[] args) {
	// }

}
