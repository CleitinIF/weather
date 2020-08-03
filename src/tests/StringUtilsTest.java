package tests;

import app.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {
	@Test
	public void testConvertSpaces() {
		Assert.assertEquals("São+Paulo", StringUtils.convertSpaces("São Paulo"));
		Assert.assertEquals("Rio+de+Janeiro", StringUtils.convertSpaces("Rio de Janeiro"));
		Assert.assertEquals("Bahia", StringUtils.convertSpaces("Bahia"));
	}

	@Test
	public void testRemoveSpecialCharacters() {
		Assert.assertEquals("São Paulo", StringUtils.removeSpecialCharacters("São Paulo"));
		Assert.assertEquals("São Paulo", StringUtils.removeSpecialCharacters("São/ Paulo}"));
		Assert.assertEquals("São Paulo", StringUtils.removeSpecialCharacters("São{ ^`}Paulo"));
	}

	@Test
	public void testReplaceComma() {
		Assert.assertEquals("São Paulo%2C BR", StringUtils.replaceComma("São Paulo, BR"));
		Assert.assertEquals("Rio de Janeiro%2C BR", StringUtils.replaceComma("Rio de Janeiro, BR"));
	}
}
