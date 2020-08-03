package app.test;

import app.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testRemoveSpecialCharacters() {
		StringUtils stringUtils = new StringUtils();
		String result = stringUtils.RemoveSpecialCharacter("`´{}");

		Assert.assertSame(result, "");
	}
}
