package weixin.popular.support;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import weixin.popular.client.LocalHttpClient;
import weixin.popular.support.config.WechatConfigLoader;
import weixin.popular.support.config.WxCpConfig;
import weixin.popular.support.config.WxMaConfig;
import weixin.popular.support.config.WxMpConfig;
import weixin.popular.support.config.WxOpConfig;
import weixin.popular.support.config.WxPayConfig;

import com.boycai.base.Utils;
import com.boycai.lang.NonNull;

/**
 * @description:
 * @author: 蔡林
 * @date: 2019年1月7日 上午12:57:42
 * @version: V1.0
 */
public class WechatConfig {
    private static List<WxMaConfig> maConfigs = null;// 微信小程序 miniapp
    private static List<WxMpConfig> mpConfigs = null;// 公众号（包括订阅号和服务号）
    private static List<WxOpConfig> opConfigs = null;// 微信开放平台
    private static List<WxCpConfig> cpConfigs = null;// 企业号/企业微信

    private static final Map<String, WxMaConfig> maConfigMap = new HashMap<String, WxMaConfig>();
    private static final Map<String, WxMpConfig> mpConfigMap = new HashMap<String, WxMpConfig>();
    private static final Map<String, WxOpConfig> opConfigMap = new HashMap<String, WxOpConfig>();
    private static final Map<String, WxCpConfig> cpConfigMap = new HashMap<String, WxCpConfig>();

    private static boolean inited;

    // static {
    // init();
    // }

    private static Map<String, WxMaConfig> getMaConfigMap() {
        if (!inited) {
            init(false);
        }
        return maConfigMap;
    }

    private static Map<String, WxMpConfig> getMpConfigMap() {
        if (!inited) {
            init(false);
        }
        return mpConfigMap;
    }

    private static Map<String, WxOpConfig> getOpConfigMap() {
        if (!inited) {
            init(false);
        }
        return opConfigMap;
    }

    private static Map<String, WxCpConfig> getCpConfigMap() {
        if (!inited) {
            init(false);
        }
        return cpConfigMap;
    }

    public static void init() {
        init(true);
    }

    @SuppressWarnings("unchecked")
    private static synchronized void init(boolean force) {
        if (force == false) {
            if (inited) {
                return;
            }
        }
        System.out.println("WechatConfig.init: wechat-config.yml start");
        InputStream ins = WechatConfigLoader.loadWechatConfig();
        if (ins == null) {
            System.out.println("WechatConfig.init: InputStream is null");
            return;
        }
        Yaml yaml = new Yaml();
        Map<String, Object> root = (Map<String, Object>) yaml.load(ins);
        // System.out.println("wxconfig:\n" + root);
        if (root == null) {
            System.out.println("WechatConfig.init: InputStream load result is null");
            return;
        }
        List<WxMaConfig> maConfigs = WechatConfigLoader.parseWxmaConfigs((Map<String, Object>) root.get("wxma"));
        List<WxMpConfig> mpConfigs = WechatConfigLoader.parseWxmpConfigs((Map<String, Object>) root.get("wxmp"));
        List<WxOpConfig> opConfigs = WechatConfigLoader.parseWxopConfigs((Map<String, Object>) root.get("wxop"));
        List<WxCpConfig> cpConfigs = WechatConfigLoader.parseWxcpConfigs((Map<String, Object>) root.get("wxcp"));

        for (WxMaConfig config : maConfigs) {
            setMaConfig(config);
            WxPayConfig payConfig = config.getPayConfig();
            if (payConfig != null && Utils.notEmpty(payConfig.getMchId()) && Utils.notEmpty(payConfig.getKeyPath())) {
                LocalHttpClient.initMchKeyStore(payConfig.getMchId(), payConfig.loadKeyPath());
            }
        }
        for (WxMpConfig config : mpConfigs) {
            setMpConfig(config);
            WxPayConfig payConfig = config.getPayConfig();
            if (payConfig != null && Utils.notEmpty(payConfig.getMchId()) && Utils.notEmpty(payConfig.getKeyPath())) {
                LocalHttpClient.initMchKeyStore(payConfig.getMchId(), payConfig.loadKeyPath());
            }
        }
        for (WxOpConfig config : opConfigs) {
            setOpConfig(config);
            WxPayConfig payConfig = config.getPayConfig();
            if (payConfig != null && Utils.notEmpty(payConfig.getMchId()) && Utils.notEmpty(payConfig.getKeyPath())) {
                LocalHttpClient.initMchKeyStore(payConfig.getMchId(), payConfig.loadKeyPath());
            }
        }
        for (WxCpConfig config : cpConfigs) {
            setCpConfig(config);
            WxPayConfig payConfig = config.getPayConfig();
            if (payConfig != null && Utils.notEmpty(payConfig.getMchId()) && Utils.notEmpty(payConfig.getKeyPath())) {
                LocalHttpClient.initMchKeyStore(payConfig.getMchId(), payConfig.loadKeyPath());
            }
        }
        inited = true;
        System.out.println("WechatConfig.init: success");
    }

    @NonNull
    public static List<WxMaConfig> getMaConfigs() {
        // List<Object> result =
        // maConfigMap.values().stream().collect(Collectors.toList());
        // List<WxMaConfig> result = new
        // ArrayList<WxMaConfig>(maConfigMap.values());
        if (maConfigs == null) {
            maConfigs = new ArrayList<WxMaConfig>(getMaConfigMap().values());
        }
        return maConfigs;
    }

    @NonNull
    public static List<WxMpConfig> getMpConfigs() {
        if (mpConfigs == null) {
            mpConfigs = new ArrayList<WxMpConfig>(getMpConfigMap().values());
        }
        return mpConfigs;
    }

    @NonNull
    public static List<WxOpConfig> getOpConfigs() {
        if (opConfigs == null) {
            opConfigs = new ArrayList<WxOpConfig>(getOpConfigMap().values());
        }
        return opConfigs;
    }

    @NonNull
    public static List<WxCpConfig> getCpConfigs() {
        if (cpConfigs == null) {
            cpConfigs = new ArrayList<WxCpConfig>(getCpConfigMap().values());
        }
        return cpConfigs;
    }

    // public static Map<String, WxMpConfig> getMpConfigMap() {
    // return mpConfigMap;
    // }
    // public static Map<String, WxCpConfig> getCpConfigMap() {
    // return cpConfigMap;
    // }
    // public static Map<String, WxOpConfig> getOpConfigMap() {
    // return opConfigMap;
    // }
    // public static Map<String, WxMaConfig> getMaConfigMap() {
    // return maConfigMap;
    // }

    public static WxMaConfig getMaConfig(String appid) {
        return getMaConfigMap().get(appid);
    }

    public static void setMaConfig(WxMaConfig maConfig) {
        if (maConfig != null) {
            maConfigMap.put(maConfig.getAppId(), maConfig);
            maConfigs = null;
        }
    }

    public static WxMpConfig getMpConfig(String appid) {
        return getMpConfigMap().get(appid);
    }

    public static void setMpConfig(WxMpConfig mpConfig) {
        if (mpConfig != null) {
            mpConfigMap.put(mpConfig.getAppId(), mpConfig);
            mpConfigs = null;
        }
    }

    public static WxOpConfig getOpConfig(String appid) {
        return getOpConfigMap().get(appid);
    }

    public static void setOpConfig(WxOpConfig opConfig) {
        if (opConfig != null) {
            opConfigMap.put(opConfig.getAppId(), opConfig);
            opConfigs = null;
        }
    }

    public static WxCpConfig getCpConfig(String corpAgentId) {
        return getCpConfigMap().get(corpAgentId);
    }

    public static WxCpConfig getCpConfig(String corpId, Integer agentId) {
        return getCpConfigMap().get(corpId + "$" + agentId);
    }

    public static void setCpConfig(WxCpConfig cpConfig) {
        if (cpConfig != null) {
            cpConfigMap.put(cpConfig.getCorpAgentId(), cpConfig);
            cpConfigs = null;
        }
    }

}
