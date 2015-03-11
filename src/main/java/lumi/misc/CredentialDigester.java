/**
 *
 */
package lumi.misc;

import java.security.MessageDigest;

/**
 * パスワード生成クラス。
 * @author A-pZ
 *
 */
public class CredentialDigester {

	/**
	 * SHA-256でパスワードを生成する。Twitter連携用。
	 * @param target ハッシュを生成する文字列
	 * @return ハッシュ値
	 * @throws Exception
	 */
	public static String generate(String target) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		digest.update(target.getBytes());
		byte[] result = digest.digest();

		StringBuilder sb = new StringBuilder();
		for (byte b:result) {
			sb.append(String.format("%02x" ,b));
		}

		return sb.toString();
	}
}
