package kongkk.webtoon.viewer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kongkk.webtoon.viewer.api.service.OrderEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlnumCompareString extends AlnumCompareSuper implements java.util.Comparator<String> {

	public AlnumCompareString(OrderEnum order, int listMaxSize) {
		super(order, listMaxSize);
	}

	@Override
	public int compare(String str1, String str2) {
		return super.compareString(str1, str2);
	}

	public static void main(String[] args) {
//		main2(args);
//		main2_spider(args);
		main2_daughter(args);
//		main_getFindNums(args);
//		main_compare(args);
	}
	
	private static void main2_daughter(String[] args) {
		List<String> test1 = new ArrayList<>();
		test1.add("제목 123화");
		test1.add("제목 - 124화");
		test1.add("제목 125화");
		mainTest2(test1);
		mainOrderPrint(test1);
	}
	
	private static void main2_spider(String[] args) {
		List<String> test1 = new ArrayList<>();
		test1.add("거미입니다만, 문제라도？ 30-1화");
		test1.add("거미입니다만, 문제라도？ 3화");
		test1.add("거미입니다만, 문제라도？ 29-3화");
		mainTest2(test1);
		mainOrderPrint(test1);
	}

	public static void main2(String[] args) {
		List<String> test = new ArrayList<>();
		test.add("이세계 치트 마술사 프롤로그A");
		test.add("이세계 치트 마술사 프롤로그B");
		test.add("이세계 치트 마술사 128화");
		test.add("이세계 치트 마술사 128화");
		test.add("이세계 치트 마술사 28화");
		test.add("이세계 치트 마술사 129화");
		test.add("이세계 치트 마술사 28-2화");
		test.add("이세계 치트 마술사 28.2화");
		mainTest2(test);
		mainOrderPrint(test);
		
		String str21 = "템빨(카카오) 프롤로그A";
		String str22 = "템빨(카카오) 115화 빌드업"; // 프롤로그를 맨 처음으로 돌리도록 test
		String str23 = "템빨(카카오)! 114화 빌드업"; // 프롤로그를 맨 처음으로 돌리도록 test
		mainTest(str21, str22, str23);
		
		String strT2_368 = "Retry 다시 한번 최강 신선으로 368화"; // 프롤로그를 맨 처음으로 돌리도록 test
		String strT2_370 = "Retry： 다시 한번 최강 신선으로 370화";
		String strT2_371 = "Retry 다시 한번 최강 신선으로 371화"; // 프롤로그를 맨 처음으로 돌리도록 test
		mainTest(strT2_368, strT2_370, strT2_371);
		
		String strT0102 = "Retry 1부 다시 한번 최강 신선으로 2화"; // 프롤로그를 맨 처음으로 돌리도록 test
		String strT0201 = "Retry：2부 다시 한번 최강 신선으로 1화";
		String strT0003 = "Retry 다시 한번 최강 신선으로 371화"; // 프롤로그를 맨 처음으로 돌리도록 test
		mainTest(strT0102, strT0201, strT0003);
	}
	
	private static void mainOrderPrint(List<String> test) {
		if ( test == null ) {System.out.println("input null");};
		if ( test.size() > 0 ) {
			System.out.println(test.get(0));
		} else {
			System.out.println("input is zero");
		}
		String[] test2 = test.toArray(new String[test.size()]);
		int listMaxNums = AlnumCompareSuper.getMaxListNums(test);
		Arrays.sort(test2, new AlnumCompareString(OrderEnum.NUMBER_FILE_NAME_DESC, listMaxNums));
		forEach(test2);
	}

	private static void mainTest2(List<String> test) {
		int compareInt = 0;
		int listMaxNums = AlnumCompareSuper.getMaxListNums(test);
		log.error("listMaxNums={}", listMaxNums);
		TestAlnumCompare2 compare = new TestAlnumCompare2(OrderEnum.NUMBER_FILE_NAME_DESC, listMaxNums);
		if ( test.size() > 1 ) {
			for (int i = 0; i < test.size()-1; i++) {
				compareInt = compare.compareString(test.get(i), test.get(i+1));
//				log.debug("comapretInt, prev, next :: {}, {}, {}", compareInt, test.get(i), test.get(i+1));
			}
		}
	}
	
	private static void mainTest(String str1, String str2, String str3 ) {
		int compareInt = 0;
		List<String> testNames = new ArrayList<>();
		testNames.add(str1);
		testNames.add(str2);
		testNames.add(str3);
		int listMaxNums = AlnumCompareSuper.getMaxListNums(testNames);
		TestAlnumCompare2 compare = new TestAlnumCompare2(OrderEnum.NUMBER_FILE_NAME_DESC, listMaxNums);
		compareInt = compare.compareString(str1, str2);
//		log.debug("comapretInt, str1, str2 :: {}, {}, {}", compareInt, str1, str2 );
		compareInt = compare.compareString(str2, str3);
//		log.debug("comapretInt, str2, str3 :: {}, {}, {}", compareInt, str2, str3 );
		compareInt = compare.compareString(str3, str2);
//		log.debug("comapretInt, str3, str2 :: {}, {}, {}", compareInt, str3, str2 );
//		log.debug("");
	}
	
	public static void main_getFindNums(String[] args) {
		System.out.println("main_getFindNums");

		String strT2_368 = "Retry 다시 한번 최강 신선으로 368화"; // 프롤로그를 맨 처음으로 돌리도록 test
		String strT0102 = "Retry 1부 다시 한번 최강 신선으로 2화"; // 프롤로그를 맨 처음으로 돌리도록 test
		String strT0201 = "Retry 2부 다시 한번 최강 신선으로 1화"; // 프롤로그를 맨 처음으로 돌리도록 test
		String strT0202 = "Retry 2부 다시 한번 최강 신선으로 2화"; // 프롤로그를 맨 처음으로 돌리도록 test
		
		List<String> testNames = new ArrayList<>();
		testNames.add(strT2_368);
		testNames.add(strT0102);
		testNames.add(strT0201);
		testNames.add(strT0202);
		int listMaxNums = AlnumCompareSuper.getMaxListNums(testNames);
		TestAlnumCompare2 compare = new TestAlnumCompare2(OrderEnum.NUMBER_FILE_NAME_DESC, listMaxNums);
		
		List<String> listNums1 = compare.getFindNums(strT2_368);
		listNums1.stream().forEach(System.out::println);
		
		List<String> listNums2 = compare.getFindNums(strT0102);
		listNums2.stream().forEach(System.out::println);
		
		compare.compareString("A", "B");
		compare.compareString(strT0102, strT0201);
		compare.compareString(strT0201, strT0102);
		compare.compareString(strT0201, strT0202);
	}
	
	public static void main_compare(String[] args) {
		String strT2_368 = "Retry 다시 한번 최강 신선으로 368화"; // 프롤로그를 맨 처음으로 돌리도록 test
		String strT0102 = "Retry 1부 다시 한번 최강 신선으로 2화"; // 프롤로그를 맨 처음으로 돌리도록 test
		
		List<String> testNames = new ArrayList<>();
		testNames.add(strT2_368);
		testNames.add(strT0102);
		int listMaxNums = AlnumCompareSuper.getMaxListNums(testNames);
		TestAlnumCompare2 compare = new TestAlnumCompare2(OrderEnum.NUMBER_FILE_NAME_DESC, listMaxNums);
		
		List<String> listNums1 = compare.getFindNums(strT2_368);
		listNums1.stream().forEach(System.out::println);
		
		List<String> listNums2 = compare.getFindNums(strT0102);
		listNums2.stream().forEach(System.out::println);
	}


}
