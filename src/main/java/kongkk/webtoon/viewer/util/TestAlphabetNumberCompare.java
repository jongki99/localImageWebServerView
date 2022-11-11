package kongkk.webtoon.viewer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.ObjectUtils;

import kongkk.webtoon.viewer.api.service.OrderEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestAlphabetNumberCompare implements java.util.Comparator<File> {
	private enum CHUNK_TYPE {
		CHUNK_ALPHA, CHUNK_DIGIT
	};

	private static class ALNUM_CHUNK {
		public CHUNK_TYPE type;
		public String data;

		public ALNUM_CHUNK(String _data, CHUNK_TYPE _type) {
			data = _data;
			type = _type;
		}
	}

	private OrderEnum order;
	private String s1 = ".-";

	public TestAlphabetNumberCompare(OrderEnum order) {
		this.order = order;
	}

	private ALNUM_CHUNK getChunk(String origin, int from, int end) {
		if (from >= end)
			return null; /// > Throw Exception Generally

		char testCh = origin.charAt(from);
		boolean isDigit = Character.isDigit(testCh) || s1.contains(String.valueOf(testCh));
//		log.debug("Character.isDigit(testCh) || Arrays.asList(charArray).contains(testCh) = {}, {}", isDigit, testCh);
		StringBuffer buf = new StringBuffer();
		char ch;


		while (from < end) {
			ch = origin.charAt(from);
			boolean isTestDigit = Character.isDigit(ch) || s1.contains(String.valueOf(ch));
			if (isDigit != isTestDigit ) {
				break;
			}
			buf.append(ch);
//			log.debug("buf = {}", buf.toString());
			from++;
		}

		return new ALNUM_CHUNK(buf.toString(), isDigit ? CHUNK_TYPE.CHUNK_DIGIT : CHUNK_TYPE.CHUNK_ALPHA);
	}

	@Override
	public int compare(File file1, File file2) {
		String str1 = file1.getName();
		String str2 = file2.getName();
		
		return compare(str1, str2);
	}
	
	private int compareMangaNumber(String str1, String str2) {
		// 뒤에서 부터 숫자, -. 을 포함하여 검색한다.
		
		int num1 = getFindMangaNumber(str1);
		int num2 = getFindMangaNumber(str2);
		
		
		return 0;
	}
	
	/**
	 * debug 용으로 static 을 제외 하였다.
	 */
	private int getFindMangaNumber(String finded) {
		Pattern numberPattern = Pattern.compile("[0-9\\.\\-]*[^0-9\\.\\-]*$"); //숫자만
		Pattern notNumberPattern = Pattern.compile("[^0-9\\.\\-].*$"); //숫영문자만
		
		Matcher numberMatcher = numberPattern.matcher(finded);
		String finded2 = null;
		int numLastIndex = -1;
		int notNumLastIndex = -1;
		if ( numberMatcher.find() ) {
			numLastIndex = numberMatcher.end();
//			log.debug("numberMatcher.group() = {}", numberMatcher.group());
			finded2 = finded.substring(0, numLastIndex);
			Matcher notNumberMatcher = notNumberPattern.matcher(finded2);
			if ( notNumberMatcher.find() ) {
				notNumLastIndex = notNumberMatcher.end();
			}
		}
		
//		log.debug("numLastIndex, finded, ::{}, ::{}", numLastIndex, finded);
//		log.debug("notNumLastIndex, finded2, ::{}, ::{}", notNumLastIndex, finded2);
		
		String findString = finded.substring(notNumLastIndex, numLastIndex);
		int reNumber = 0;
		if ( numLastIndex >= 0 &&  notNumLastIndex >= 0 ) {
			String numString = findString.replace("-", ".");
			try {
				reNumber = Integer.parseInt(Double.parseDouble(numString) * 100 + "");
			} catch (Exception e) {
				// FIXMEK: handle exception
			}
		}
		
//		log.debug("num, findString, finded, finded2, ::{}, ::{}, ::{}, ::{}", reNumber, findString, finded, finded2);
		return reNumber;
	}

	private int compare(String str1, String str2) {
		double diff = 0;

		int i = 0, j = 0;

		int len1 = str1.length(), len2 = str2.length();

		ALNUM_CHUNK chunk1 = null, chunk2 = null;

		while (i < len1 && j < len2) {

			chunk1 = getChunk(str1, i, len1);
			chunk2 = getChunk(str2, j, len2);
			
			log.trace("chunk1, chunk2, i, j, len1, len2 :: {}, {}, {}, {}, {}, {}", chunk1.data, chunk2.data, i, j, len1, len2);

			if (chunk1.type == CHUNK_TYPE.CHUNK_DIGIT && chunk2.type == CHUNK_TYPE.CHUNK_DIGIT) {
				// diff = Integer.parseInt(chunk1.data) - Integer.parseInt(chunk2.data);
				String data1 = chunk1.data.replace("-", ".");
				String data2 = chunk2.data.replace("-", ".");
				if ( Character.isDigit(data1.charAt(0)) && Character.isDigit(data2.charAt(0)) ) {
					diff = Double.parseDouble(data1) - Double.parseDouble(data2);
				} else {
					diff = getPlusMinusDigit(chunk1.data) * chunk1.data.compareToIgnoreCase(chunk2.data);
				}
			} else {
				diff = getPlusMinusDigit(chunk1.data) * chunk1.data.compareToIgnoreCase(chunk2.data);
			}

			if (diff != 0) {
				return -(int)(diff*100);
			}

			i += chunk1.data.length();
			j += chunk2.data.length();
		}

		int ordered = 1;
		if (this.order == OrderEnum.FILE_NAME_DESC) {
			ordered = -1;
		}

		return ordered * (len1 - len2);
	}

	private int getPlusMinusDigit(String data) {
		int pm = -1;
		if ( ! ObjectUtils.isEmpty(data) && Character.isDigit(data.charAt(0)) ) {
			pm = 1;
		}
		return pm;
	}

	public static void main3(String[] args) {
		String s1 = ".-"; // .- 문자를 숫자로 인식해서 정렬 테스트.
		char[] charArray = s1.toCharArray();
		s1.contains(".");

		char testCh = ".".charAt(0);
		boolean isDigit = Character.isDigit(testCh) || s1.contains(String.valueOf(testCh));
		log.debug("s1.contains(\".\"); = {}", s1.contains("."));
		log.debug("testCh = {}", testCh);
		log.debug("charArray = {}", charArray);
		log.debug("isDigit = {}", isDigit);
	}

	public static void main(String[] args) {
		TestAlphabetNumberCompare compare = new TestAlphabetNumberCompare(OrderEnum.NUMBER_FILE_NAME_DESC);
		List<String> testList = new ArrayList<>();
		testList.add("템빨(카카오) 115화 빌드업");
		testList.add("템빨(카카오)! 115.5화 빌드업");
		testList.add("템빨(카카오) 115-1화 빌드업");
		testList.add("템빨(카카오) 115.1-1화 빌드업");
		
		testList.forEach(str -> {
			int number = compare.getFindMangaNumber(str);
			// log.debug("number, str, ::{}, ::{}", number, str);
		});
	}
	
	private static void testCompare() {
		TestAlphabetNumberCompare compare = new TestAlphabetNumberCompare(OrderEnum.NUMBER_FILE_NAME_DESC);
//		String str1 = "이세계 치트 마술사 28화";
//		String str2 = "이세계 치트 마술사 28-2화"; 
//		String str2 = "이세계 치트 마술사 28.2화"; // .- 값을 숫자로 변환해서 order test
		String str1 = "템빨(카카오) 프롤로그";
		String str2 = "템빨(카카오) 115화 빌드업"; // 프롤로그를 맨 처음으로 돌리도록 test
		String str3 = "템빨(카카오)! 114화 빌드업"; // 프롤로그를 맨 처음으로 돌리도록 test
		
		int compareInt = compare.compare(str1, str2);
		log.debug("str1, str2, comapretInt :: {}, {}, {}", str1, str2, compareInt);
		
		compareInt = compare.compare(str2, str3);
		log.debug("str2, str3, comapretInt :: {}, {}, {}", str2, str3, compareInt);
		compareInt = compare.compare(str3, str2);
		log.debug("str3, str2, comapretInt :: {}, {}, {}", str3, str2, compareInt);
	}
	
}