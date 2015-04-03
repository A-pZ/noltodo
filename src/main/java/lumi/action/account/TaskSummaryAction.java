package lumi.action.account;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.action.LumiActionSupport;
import lumi.service.TaskSummaryService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Blocked;

/**
 * タスクサマリー表示Actionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/account")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, type="chart", params={"width","1024","height","768"})
})
@Controller
@Scope("prototype")
@Slf4j
public class TaskSummaryAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("taskSummary")
	public String taskSummary() throws Exception {
		// 検索を実施し、Chartインスタンスを取得
		chart = service.getTaskSummaryChart();

		addActionMessage(getText("summary.display"));

		return SUCCESS;
	}

	public JFreeChart getChart() {
		return chart;
	}

	private JFreeChart chart;

	@Autowired
	@Blocked
	@Getter @Setter
	private TaskSummaryService service;
}
