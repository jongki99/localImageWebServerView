package kongkk.webtoon.viewer.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum SaveServer {
	jongki("\\\\Jongki99\\contents\\hitomi_downloader_GUI\\")
	, nbwork("C:\\\\hitomi_downloader_GUI\\")
	;
	
	String accessPath;
}
