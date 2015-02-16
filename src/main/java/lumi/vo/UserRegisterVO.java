/**
 *
 */
package lumi.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * アカウント情報を管理するVO。UserVOを継承する。
 * @author A-pZ
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserRegisterVO extends UserVO {
	private int activate;
	private String userrole;

	/** 表示名 */
	private String displayName;
}
