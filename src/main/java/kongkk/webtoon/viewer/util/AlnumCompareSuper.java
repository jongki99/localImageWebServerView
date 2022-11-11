package kongkk.webtoon.viewer.util;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import kongkk.webtoon.viewer.api.service.OrderEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlnumCompareSuper {

	private static final String DEFAULT_STRING_NUMBER = "000000";

//	private enum CHUNK_TYPE {
//		CHUNK_ALPHA, CHUNK_DIGIT
//	};

//	private static class ALNUM_CHUNK {
//		public CHUNK_TYPE type;
//		public String data;
//
//		public ALNUM_CHUNK(String _data, CHUNK_TYPE _type) {
//			data = _data;
//			type = _type;
//		}
//	}
	
	protected OrderEnum orderNum;
	protected int listMaxSize = 1;

	public AlnumCompareSuper(OrderEnum order, int listMaxSize) {
		this.orderNum = order;
		this.listMaxSize = listMaxSize;
	}

//	private ALNUM_CHUNK getChunk(String origin, int from, int end) {
//		if (from >= end)
//			return null; /// > Throw Exception Generally
//
//		char testCh = origin.charAt(from);
//		boolean isDigit = Character.isDigit(testCh) || s1.contains(String.valueOf(testCh));
////		log.debug("Character.isDigit(testCh) || Arrays.asList(charArray).contains(testCh) = {}, {}", isDigit, testCh);
//		StringBuffer buf = new StringBuffer();
//		char ch;
//
//
//		while (from < end) {
//			ch = origin.charAt(from);
//			boolean isTestDigit = Character.isDigit(ch) || s1.contains(String.valueOf(ch));
//			if (isDigit != isTestDigit ) {
//				break;
//			}
//			buf.append(ch);
////			log.debug("buf = {}", buf.toString());
//			from++;
//		}
//
//		return new ALNUM_CHUNK(buf.toString(), isDigit ? CHUNK_TYPE.CHUNK_DIGIT : CHUNK_TYPE.CHUNK_ALPHA);
//	}

	protected int compareString(String str1, String str2) {
		List<String> listNums1 = getFindNums(str1);
		List<String> listNums2 = getFindNums(str2);
		
		log.debug("compareString params :: {}, {}, listNums1 :: {}, listNums2 :: {}", str1, str2, listNums1, listNums2);
		
		if ( listMaxSize == 0 ) {
			return str1.compareToIgnoreCase(str2);
		}
		
		for(int i=listNums1.size(); i<listMaxSize; i++) {
			listNums1.add(DEFAULT_STRING_NUMBER);
		}
		for(int i=listNums2.size(); i<listMaxSize; i++) {
			listNums2.add(DEFAULT_STRING_NUMBER);
		}
		StringBuilder nums1 = new StringBuilder();
		listNums1.stream().forEach(num1 -> {
			nums1.append(num1);
		});
		StringBuilder nums2 = new StringBuilder();
		listNums2.stream().forEach(num2 -> {
			nums2.append(num2);
		});
		
		BigInteger bnum1 = new BigInteger(nums1.toString());
		BigInteger bnum2 = new BigInteger(nums2.toString());
//		log.debug("compareString nums1, nums2 :: {}, {}", nums1, nums2 );
//		log.debug("compareString bnum1, bnum2, bnum1.compareTo(bnum2) :: {}, {}, {}", bnum1, bnum2, bnum1.compareTo(bnum2));
		
		return - bnum1.compareTo(bnum2);
	}
	
	/**
	 * 
	 * @param findString 제목명
	 * @return 부, 화 등을 구분해서 출력, 앞자리 뒤자리 구분해서 출력.
	 */
	protected List<String> getFindNums(String findString) {
		return FindNumberStringUtil.getFindNumbers(findString);
	}

//	@SuppressWarnings("unused")
//	private int compare2(String str1, String str2) {
//		double diff = 0;
//
//		int i = 0, j = 0;
//
//		int len1 = str1.length(), len2 = str2.length();
//
//		ALNUM_CHUNK chunk1 = null, chunk2 = null;
//
//		while (i < len1 && j < len2) {
//
//			chunk1 = getChunk(str1, i, len1);
//			chunk2 = getChunk(str2, j, len2);
//			
//			// log.debug("chunk1, chunk2, i, j, len1, len2 :: {}, {}, {}, {}, {}, {}", chunk1.data, chunk2.data, i, j, len1, len2);
//
//			if (chunk1.type == CHUNK_TYPE.CHUNK_DIGIT && chunk2.type == CHUNK_TYPE.CHUNK_DIGIT) {
//				// diff = Integer.parseInt(chunk1.data) - Integer.parseInt(chunk2.data);
//				String data1 = chunk1.data.replace("-", ".");
//				String data2 = chunk2.data.replace("-", ".");
//				if ( Character.isDigit(data1.charAt(0)) && Character.isDigit(data2.charAt(0)) ) {
//					diff = Double.parseDouble(data1) - Double.parseDouble(data2);
//				} else {
//					diff = getPlusMinusDigit(chunk1.data) * chunk1.data.compareToIgnoreCase(chunk2.data);
//				}
//			} else {
//				diff = getPlusMinusDigit(chunk1.data) * chunk1.data.compareToIgnoreCase(chunk2.data);
//			}
//
//			if (diff != 0) {
//				return -(int)(diff*100);
//			}
//
//			i += chunk1.data.length();
//			j += chunk2.data.length();
//		}
//
//		int ordered = 1;
//		if (this.order == OrderEnum.FILE_NAME_DESC) {
//			ordered = -1;
//		}
//
//		return ordered * (len1 - len2);
//	}
//
//	private int getPlusMinusDigit(String data) {
//		int pm = -1;
//		if ( ! ObjectUtils.isEmpty(data) && Character.isDigit(data.charAt(0)) ) {
//			pm = 1;
//		}
//		return pm;
//	}

	protected static void forEach(String[] test2) {
		for (int i = 0; i < test2.length; i++) {
			String str = String.format("[%10d] %s", i, test2[i]);
			System.out.println(str);
			// log.debug(test2[i]);
		}
	}
	
	public static int getMaxListNums(File[] files) {
		return FindNumberStringUtil.getListMaxNumbers(files);
	}
	public static int getMaxListNums(List<String> files) {
		return FindNumberStringUtil.getListMaxNumbers(files);
	}

}
