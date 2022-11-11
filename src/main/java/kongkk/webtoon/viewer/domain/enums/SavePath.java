package kongkk.webtoon.viewer.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SavePath {
	jongki_manatoki(SaveServer.jongki + "\\" + "hitomi_downloaded_manatoki")
	, jongki_manatoki_old(SaveServer.jongki + "\\" + "hitomi_downloaded_manatoki_old")
	, jongki_navertoon(SaveServer.jongki + "\\" + "hitomi_downloaded_navertoon")
	, nbwork_manatoki(SaveServer.nbwork + "\\" + "hitomi_downloaded_manatoki")
	, nbwork_navertoon(SaveServer.nbwork + "\\" + "hitomi_downloaded_navertoon")
	;
	
	String fullPath;
}
