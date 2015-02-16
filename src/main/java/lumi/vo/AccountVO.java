/**
 *
 */
package lumi.vo;

import lombok.Data;

/**
 * アカウント管理用の抽象クラス。
 * アカウント管理画面でのValidationはこのクラスを継承して実装する。
 * @author A-pZ
 *
 */
@Data
public abstract class AccountVO {
	private String username;
	private String oldPassword;
	private String password;
	private String confirm;

	private String displayName;
	private String twitterId;

}
