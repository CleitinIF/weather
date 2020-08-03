package app.utils;

public class FormatTextInput {
	public static String format(String text) {
		text = StringUtils.removeSpecialCharacters(text);
		text = StringUtils.convertSpaces(text);
		text = StringUtils.replaceComma(text);

		return text;
	}
}
