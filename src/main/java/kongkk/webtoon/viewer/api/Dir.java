package kongkk.webtoon.viewer.api;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import kongkk.webtoon.viewer.api.service.OrderEnum;
import kongkk.webtoon.viewer.util.TestAlnumCompare2;
import kongkk.webtoon.viewer.util.AlnumCompareSuper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Dir {
	String baseName;
	
	public Dir() {
	}

	
	public Dir(String baseName) {
		this.baseName = baseName;
	}
	
	String cat;
	String listName;
	String listNameUrl;
	String listSubName;
	String listSubNameUrl;
	String imgFileName;
	String imgFileNameUrl;
	
	public String getRootPath(String remote) {
		return new File(getBasePath_new()).getParent();
	}

	public String getBasePath() {
		return getBasePath_new();
	}

	public String getFullParentPath() {
		
		StringBuffer sb = new StringBuffer(getBasePath_new());
		if ( listName!=null ) {
			sb.append("\\").append(getDecode(listName));
			log.info("listName = {}, decode = {}", listName, getDecode(listName));
		}
		if ( listSubName!=null ) {
			sb.append("\\").append(getDecode(listSubName));
			log.info("listSubName = {}, decode = {}", listSubName, getDecode(listSubName));
		}
		
		return sb.toString();
	}
	
	private String getDecode(String encoded) {
		try {
			return URLDecoder.decode(encoded, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	String getBasePath_new() {
		String basePath = BaseData.getBasePath(baseName);
		if ( ! ObjectUtils.isEmpty(cat)) {
			basePath = basePath + "_" + cat;
		}
		return basePath;
	}

	String getBasePath_old() {
		String basePath = "D\\contents\\hitomi_downloader_GUI\\hitomi_downloaded_";
		return basePath + cat;
	}
	
	private File[] getFiles(OrderEnum order, String basePath) {
		String path = basePath;
		log.debug("path = {}", path);
		Path baseDir = Paths.get(path);
		File[] files = baseDir.toFile().listFiles();
		log.debug("baseDir.toFile() = {}", baseDir.toFile());
		if ( files == null ) return new File[0];
		int listMaxNums = AlnumCompareSuper.getMaxListNums(files);
		switch (order) {
			case FILE_NAME_ASC:
				Arrays.sort(files, NameFileComparator.NAME_COMPARATOR);
				break;
			case FILE_NAME_DESC:
				Arrays.sort(files, NameFileComparator.NAME_REVERSE);
				break;
			case NUMBER_FILE_NAME_ASC:
				Arrays.sort(files, new TestAlnumCompare2(order, listMaxNums));
				break;
			case NUMBER_FILE_NAME_DESC:
				Arrays.sort(files, new TestAlnumCompare2(order, listMaxNums));
				break;
			case UPDATE_DATE_ASC:
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR );
				break;
			case UPDATE_DATE_DESC:
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE );
				break;
			default:
				break;
		}
//		if ( isSort ) {
//			Arrays.sort(files, new AlnumCompare());
//		}
		return files;
	}
	
	@SuppressWarnings("deprecation")
	public File[] getFiles(OrderEnum order) throws UnsupportedEncodingException {
		String path = getBasePath();
		if (! StringUtils.isEmpty(listName) ) {
			path += "\\" + URLDecoder.decode(listName, "utf8");
			listNameUrl = URLEncoder.encode(listName, "utf8");
		}
		if (! StringUtils.isEmpty(listSubName) ) {
			path += "\\" + URLDecoder.decode(listSubName, "utf8");
			listSubNameUrl = URLEncoder.encode(listSubName, "utf8");
		}
		return this.getFiles(order, path);
	}
	
	@SuppressWarnings("deprecation")
	String getFullImagePath() throws UnsupportedEncodingException {
		String path = getBasePath();
		if (! StringUtils.isEmpty(listName) ) {
			path += "\\" + URLDecoder.decode(listName, "utf8");
			listNameUrl = URLEncoder.encode(listName, "utf8");
		}
		if (! StringUtils.isEmpty(listSubName) ) {
			path += "\\" + URLDecoder.decode(listSubName, "utf8");
			listSubNameUrl = URLEncoder.encode(listSubName, "utf8");
		}
		if (! StringUtils.isEmpty(imgFileName) ) {
			path += "\\" + URLDecoder.decode(imgFileName, "utf8");
			imgFileNameUrl = URLEncoder.encode(imgFileName, "utf8");
		}
		
		log.debug("path : {}", path);
		return path ;
	}

	public File[] getRootList() {
		String path = getRootPath(null);
		File[] files = this.getFiles(OrderEnum.FILE_NAME_ASC, path);

		return files;
	}

}
