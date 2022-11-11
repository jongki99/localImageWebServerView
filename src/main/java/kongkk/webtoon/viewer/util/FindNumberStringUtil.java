package kongkk.webtoon.viewer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.thymeleaf.util.ArrayUtils;

import kongkk.webtoon.viewer.api.service.OrderEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FindNumberStringUtil {
	private static final String PREV_NUMBER = "0000";
	private static final String SUB_NUMBER = "00";
	private static String NUMBER_FORMAT_PATTERN = "([0-9].[0-9\\.\\-]*)";
	private static String NOT_NUMBER_FORMAT_PATTERN = "([0-9]+)";
	private static Pattern numberPattern = Pattern.compile(NUMBER_FORMAT_PATTERN);
	private static Pattern notNumberPattern = Pattern.compile(NOT_NUMBER_FORMAT_PATTERN);
	
	public static List<String> getFindNumbers(String title) {
		List<String> ret = new ArrayList<>();
		
		Matcher matcher = numberPattern.matcher(title);
		while(matcher.find()) {
			String founded = matcher.group();
//			System.out.println("founded = " + founded);
			
			Matcher subMatcher = notNumberPattern.matcher(founded);
			String prevNumber = "0000";
			String subNumber = "00";
			while(subMatcher.find()  ) {
				String subFounded = subMatcher.group();
				if ( PREV_NUMBER.equals(prevNumber)) {
					prevNumber = PREV_NUMBER + subFounded;
					prevNumber = prevNumber.substring(prevNumber.length()-PREV_NUMBER.length());
				} else {
					subNumber = subFounded + SUB_NUMBER;
					subNumber = subNumber.substring(0, SUB_NUMBER.length());
				}
//				System.out.println("subFounded = " + subFounded);
			}
			ret.add(prevNumber+subNumber);
		}
		
		return ret;
	}
	
	public static int getListMaxNumbers(File[] files) {
		List<String> fileNames = new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			fileNames.add(files[i].getName());
		}
		return getListMaxNumbers(fileNames);
	}
	
	public static int getListMaxNumbers(List<String> names) {
		AtomicInteger listMaxNumbers = new AtomicInteger();
//		log.debug("listMaxNumbers.get() = {}", listMaxNumbers.get());
		names.forEach(fileName -> {
//			log.debug(fileName);
			Matcher matcher = numberPattern.matcher(fileName);
			int findMaxNumbers = 0;
			while(matcher.find()) {
				findMaxNumbers++;
//				log.debug("find() = {}", findMaxNumbers);
			}
			if ( listMaxNumbers.get() < findMaxNumbers ) {
				listMaxNumbers.set(findMaxNumbers);
			}
		});
//		log.debug("listMaxNumbers.get() = {}", listMaxNumbers.get());
		return listMaxNumbers.get();
	}
	
	public static void main(String[] args) {
		List<String> testNormal = new ArrayList<>();
		testNormal.add("거미입니다만, 문제라도？ 30-1화");
		testNormal.add("거미입니다만, 문제라도？ 3화");
		testNormal.add("거미입니다만, 문제라도？ 29.1화");
		testNormal.add("제목 100화");
		testNormal.add("제목 - 101화");
		
//		log.debug("testNormal = {}", testNormal);
		
		getListMaxNumbers(testNormal);
		
		testNormal.forEach(test->{
			List<String> numberStrings = getFindNumbers(test);
			numberStrings.forEach(nums -> System.out.println("nums = " + nums));
		});

		int listMaxNums = AlnumCompareSuper.getMaxListNums(testNormal);
		String[] testArrays = testNormal.toArray(new String[testNormal.size()]);
		Arrays.sort(testArrays, new AlnumCompareString(OrderEnum.NUMBER_FILE_NAME_DESC, listMaxNums));
//		log.debug("testArrays = {}", Arrays.asList(testArrays));
	}
}
