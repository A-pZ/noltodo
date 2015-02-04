/**
 *
 */
package lumi.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * タスク検索VO。
 *
 * @author A-pZ
 *
 */
@Data
public class SearchVO {
	/** タスクID */
	private int id;
	/** タスク名 */
	private String name;
	/** タスク本文(markdown変換前) */
	private String task;
	/** 登録（更新）日時 */
	private Timestamp writedate;
	/** ユーザID */
	private String userid;
	/** 職業・特化ID */
	private String jobid;
	/** 職業名 */
	private String jobname;
	/** 期限 */
	private Timestamp limitdate;
	/** タスクステータス */
	private String status;
	/** 更新レコード判定 */
	private boolean update;
	/** 公開/非公開フラグ */
	private int publish;
	/** 優先度 */
	private String priority;
}
