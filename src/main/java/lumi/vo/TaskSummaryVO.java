/**
 *
 */
package lumi.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * タスクサマリー用VO。
 * @author A-pZ
 *
 */
@Data
public class TaskSummaryVO implements Serializable {
	private String username;
	private Date checkdate;
	private int task;
	private int closed;
}
