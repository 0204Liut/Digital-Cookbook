package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Check if a string is a positive number true if the string is positive number
 * 
 * @author Liu Tao
 *
 */
public class IsNumeric {
	public static boolean isNumeric(String str) {
		String regEx = "^[0-9]+$";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

}
