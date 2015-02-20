/**
 *
 */
package lumi.vo;

import lombok.Data;

/**
 * アクセス制御の情報。
 * @author A-pZ
 *
 */
@Data
public class AccessControlDTO {
	/** 編集権限 */
	private boolean hasEditRole;

	/** 参照権限 */
	private boolean hasViewRole;

	/** タスク番号 */
	private String taskid;

	/** ユーザID */
	private String username;
}
