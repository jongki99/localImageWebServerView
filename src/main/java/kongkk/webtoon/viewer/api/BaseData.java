package kongkk.webtoon.viewer.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseData {
	String basePath;
	String baseName;

	public static List<BaseData> dataMapList = new ArrayList<>();
	static {
		dataMapList.add(BaseData.builder()
				.basePath("h:\\hitomi_downloader_GUI\\hitomi_downloaded")
				.baseName("nb-work")
				.build());
		dataMapList.add(BaseData.builder()
				.basePath("V:\\hd-note1\\hitomi_downloaded")
				.baseName("wifi")
				.build());
//		dataMapList.add(BaseData.builder()
//				.basePath("K:\\hitomi_downloader_GUI\\hitomi_downloaded")
//				.baseName("work")
//				.build());
	}
	
	@SuppressWarnings("deprecation")
	public static String getBasePath(String baseName) {
		if ( StringUtils.isEmpty(baseName) ) {
			throw new RuntimeException("baseName is not empty!!!");
		}
		for (BaseData baseData : dataMapList) {
			if(baseName.equals(baseData.getBaseName())) {
				return baseData.getBasePath();
			}
		}
		throw new RuntimeException("baseName is not matched!!! : " + baseName);
	}
}
