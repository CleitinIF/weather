package app.utils;

public class StringUtils {
	public static String removeSpecialCharacters(String string) {
		return string.replaceAll("[-+´^`{}!@#$%*()`/]*", "");
	}

	public static String convertSpaces(String string) {
		return string.replaceAll(" ", "+");
	}

	public static String replaceComma(String string) {
		return string.replaceFirst(",", "%2C");
	}
}
