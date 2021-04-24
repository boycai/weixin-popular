package weixin.popular.util;

public class RandomUtils {

	private static final String RANDOM_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	private static final java.util.Random RANDOM = new java.util.Random();

	/**
	 * 随机生成16位字符串
	 * @return
	 */
	public static String getRandomStr() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			//sb.append(RANDOM_STR.charAt(RANDOM.nextInt(RANDOM_STR.length())));
			sb.append(RANDOM_STR.charAt(RANDOM.nextInt(62)));
		}
		return sb.toString();
	}
	
	/*public static void main(String[] args) {
		System.out.println(RANDOM_STR.length());
		System.out.println(getRandomStr());
	}*/

}
