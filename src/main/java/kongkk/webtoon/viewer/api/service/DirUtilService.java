package kongkk.webtoon.viewer.api.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import kongkk.webtoon.viewer.api.Dir;
import kongkk.webtoon.viewer.api.Dir2;

@Service
public class DirUtilService {
	
	public Dir2 getListSubNextDir(Dir param) {
		String tempListSubName = param.getListSubName();
		param.setListSubName(null);
		List<Dir2> dirList = this.getListSubName(param);
		Dir2 selectDir = null;
		for (Dir2 dir2 : dirList) {
			if ( dir2.getName().equals(tempListSubName)) {
				break;
			}
			selectDir = dir2;
		}
		param.setListSubName(tempListSubName);
		return selectDir;
	}

	public Dir2 getListSubPrevDir(Dir param) {
		String tempListSubName = param.getListSubName();
		try {
			tempListSubName = URLDecoder.decode(tempListSubName, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		param.setListSubName(null);
		List<Dir2> dirList = this.getListSubName(param);
		Dir2 selectDir = null;
		boolean matched = false;
		for (Dir2 dir2 : dirList) {
			if ( matched == true ) {
				selectDir = dir2;
				break;
			}
			if ( dir2.getName().equals(tempListSubName)) {
				matched = true;
			}
		}
		param.setListSubName(tempListSubName);
		return selectDir;
	}

	public List<Dir2> getListSubName(Dir param) {
		List<Dir2> dirList = new ArrayList<>();
		try {
			for (File file : param.getFiles(OrderEnum.NUMBER_FILE_NAME_DESC)) {
				String dirName = file.getName();
				String lastDate = getDateString(file);
				dirList.add(
						Dir2.builder()
						.baseName(param.getBaseName())
						.cat(param.getCat())
						.param(param)
						.url(URLEncoder.encode(dirName, "utf8"))
						.name(dirName)
						.lastDate(lastDate)
						.build()
				);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return dirList;
	}

	public String getDateString(File file) {
		long lastModified = file.lastModified();
		String pattern = "yyyy-MM-dd hh:mm aa";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date lastModifiedDate = new Date( lastModified );
		return simpleDateFormat.format( lastModifiedDate );
	}

}
