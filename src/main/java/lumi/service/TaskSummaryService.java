/**
 *
 */
package lumi.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;
import lumi.vo.TaskSummaryVO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * タスクの集計Service。
 * @author A-pZ
 *
 */
@Scope("prototype")
@Service
@Slf4j
@Transactional(
	    propagation = Propagation.REQUIRED,
	    isolation = Isolation.DEFAULT,
	    readOnly = true,
	    rollbackFor = { RuntimeException.class, Exception.class })
public class TaskSummaryService extends LumiService {

	public List<TaskSummaryVO> taskSummaryData() throws Exception {
		// ユーザID
		String username = getUserId();

		// タスクサマリー検索
		List<TaskSummaryVO> result = dao.select(Query.taskSummary.name(), username);

		return result;
	}

	/**
	 * タスクサマリーのJFreeChartインスタンスを返す。
	 * @return タスクサマリーグラフのJFreeChartインスタンス
	 */
	public JFreeChart getTaskSummaryChart() throws Exception {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// 検索結果の取得
		List<TaskSummaryVO> tasks = taskSummaryData();

		// 図に値を貼り付け
		for ( TaskSummaryVO vo:tasks) {
			dataset.addValue(vo.getTask(), "task", vo.getCheckdate());
			dataset.addValue(vo.getClosed(), "closed", vo.getCheckdate());
		}

		// 図の作成
		String title = "タスクサマリー";			// タイトル
		String categoryAxisLabel = "Axis";		// カテゴリ軸ラベル
		String valueAxisLabel = "value";		// 値軸ラベル

		PlotOrientation orientation = PlotOrientation.VERTICAL;		// データプロット方向。値＝Y軸がVERTICAL
		boolean legend = true;		// 凡例
		boolean tooltips = true;	// ツールチップ表示
		boolean urls = false;		// グラフのURL生成

		JFreeChart chart =
			ChartFactory.createAreaChart(
					title, categoryAxisLabel, valueAxisLabel, dataset, orientation, legend, tooltips, urls);

		//CatgoryPlot catePlot = chart.getCategoryPlot();
		XYPlot xyplot = chart.getXYPlot();

		CategoryPlot categoryPlot = chart.getCategoryPlot();
		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();



		//axis.setDateFormatOverride(new SimpleDateFormat("MM/dd" , Locale.JAPANESE));

		return chart;
	}

	enum Query {
		taskSummary ,
	}

	@Autowired
	DAO dao;
}
