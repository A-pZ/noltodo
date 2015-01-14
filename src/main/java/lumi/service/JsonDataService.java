/**
 *
 */
package lumi.service;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.vo.MasterVO;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.TypeReference;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * データベース化されていない、マスター化した区分値を管理する。データはJSONファイルから取得する。
 * @author koji.azuma
 *
 */
@Scope("singleton")
@Service("master")
@Slf4j
public class JsonDataService {
	@PostConstruct
	public void start() throws Exception {
		log.info("-- start.");

		log.debug(" - load json.");
		loadJSON();

		log.debug(" - filtering category.");
		filteringCategory();
	}

	/**
	 * JSONファイルの読み込み。master.jsonファイルを読み込み、List<MasterVO>へ格納する。
	 * @throws Exception
	 */
	private void loadJSON() throws Exception {
		String path = Thread.currentThread().getContextClassLoader().getResource(JSON_FILE_NAME).getPath();
		log.debug("  -- path :" + path);

		File file = new File(path);
		FileReader fr = new FileReader(file);
		String string = FileUtils.readFileToString(file);
		log.debug("  -- load to text complete.");

		List<MasterVO> masterList = JSON.decode(string , new TypeReference<List<MasterVO>>() {});
		log.debug("  -- convert to json complete.");

		this.masterList = masterList;
	}

	/**
	 * 読み込んだJSONから、カテゴリ名を抽出する。
	 * @throws Exception
	 */
	private void filteringCategory() throws Exception {
		Map<String , Map<String, String>> master = new HashMap<String , Map<String, String>>();

		log.debug("  -- filtering...");
		for (MasterVO vo:masterList) {
			String category = vo.getCategory();

			if ( master.containsKey(category)) {
				Map<String, String> recordMap = master.get(category);
				recordMap.put(vo.getCode(), vo.getValue());
			} else {
				log.debug("    + category add:" + category);
				Map<String, String> recordMap = new HashMap<String, String>();
				master.put(category, recordMap);
				recordMap.put(vo.getCode(), vo.getValue());
			}
		}

		log.debug(" -- complete.");
		this.masterMap = master;
	}

	/**
	 * マスター区部一覧から、カテゴリー名＋コードを指定して、表示する値を取得する。
	 * @param category カテゴリー名
	 * @param code コード
	 * @return カテゴリー＋コードに該当する値。
	 */
	public  String getValue( String category, String code) {
		Map<String, String> map = masterMap.get(category);
		if (map == null ) {
			return "no-hit category:" + category;
		}
		String value = map.get(code);

		if ( value == null) {
			return "no-hit code:" + code;
		}
		return value;
	}

	private static final String JSON_FILE_NAME = "master.json";

	@Getter @Setter
	private List<MasterVO> masterList;

	@Getter
	private Map<String, Map<String, String>> masterMap;
}
