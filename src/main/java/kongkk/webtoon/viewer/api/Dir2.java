package kongkk.webtoon.viewer.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dir2 {
	String baseName;
	String cat;
	String name;
	String url;
	String lastDate;
	String imageFullPath;
	Dir param;
}
