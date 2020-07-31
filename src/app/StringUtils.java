package app;

public class StringUtils {
	public String RemoveSpecialCharacter(String string) {
		return string.replaceAll("[-+´^`{}!@#$%*()]*", "");
	}

	public String convertSpaces(String string) {
		return string.replaceAll(" ", "+");
	}

	public String replaceComma(String string) {
		return string.replaceFirst(",", "%2C");
	}
}
