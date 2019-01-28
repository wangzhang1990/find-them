package cn.becto.findthem.utils;

public class StringReplaceUtils {
	public static String imgSrcFix(String str) {
		while (str.contains("<figure><noscript>")) {
			int indexOf1 = str.indexOf("<figure><noscript>");
			int indexOf2 = str.indexOf(" data-rawwidth");
			int indexOf3 = str.indexOf("</figure>");
			
			String strWeWant = str.substring(indexOf1 + 18, indexOf2);
			strWeWant = strWeWant + ">";
			
			String strNeedBeRe = str.substring(indexOf1, indexOf3 + 9);
			System.out.println(strNeedBeRe);
			
			str = str.replace(strNeedBeRe, strWeWant);			
		}
	
		return str;
	}
}
