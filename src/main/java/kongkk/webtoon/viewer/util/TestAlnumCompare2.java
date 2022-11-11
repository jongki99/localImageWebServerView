package kongkk.webtoon.viewer.util;

import java.io.File;

import kongkk.webtoon.viewer.api.service.OrderEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestAlnumCompare2 extends AlnumCompareSuper implements java.util.Comparator<File> {

	public TestAlnumCompare2(OrderEnum order, int listMaxNumber) {
		super(order, listMaxNumber);
	}
	
	@Override
	public int compare(File file1, File file2) {
		String str1 = file1.getName();
		String str2 = file2.getName();
		
		return compareString(str1, str2);
	}

	public static void main3(String[] args) {
		String s1 = ".-"; // .- 문자를 숫자로 인식해서 정렬 테스트.
		char[] charArray = s1.toCharArray();
		s1.contains(".");

		char testCh = ".".charAt(0);
		boolean isDigit = Character.isDigit(testCh) || s1.contains(String.valueOf(testCh));
//		log.debug("s1.contains(\".\"); = {}", s1.contains("."));
//		log.debug("testCh = {}", testCh);
//		log.debug("charArray = {}", charArray);
//		log.debug("isDigit = {}", isDigit);
	}
	
	public int compare(String str1, String str2) {
		if ( orderNum == OrderEnum.NUMBER_FILE_NAME_ASC ) {
			return -compareString(str1, str2);
		}
		return compareString(str1, str2);
	}
	
}