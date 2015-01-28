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
public class AccountVO extends UserVO {
	private int activate;
	private String userrole;
}
