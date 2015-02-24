/**
 *
 */
package lumi.vo;

import lombok.Data;

/**
 * タスクに紐づけるタグのVO。
 * @author A-pZ
 *
 */
@Data
public class TagVO {
	/** タグのID */
	private String tagid;
	/** タグの表示ラベル */
	private String display;
	/** タスクのID */
	private String taskid;
	/** タグの件数 */
	private int mark;
}
