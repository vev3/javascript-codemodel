package org.hisrc.jscm.parser.literal.tests;

import org.hisrc.jscm.parser.ParseException;
import org.hisrc.jscm.parser.literal.HexIntegerParser;
import org.junit.Assert;
import org.junit.Test;

public class HexIntegerParserTest {

	@Test
	public void check() throws ParseException {
		Assert.assertEquals(0xA0, HexIntegerParser.INSTANCE.parse("0xa0").longValue());
		Assert.assertEquals(0x19, HexIntegerParser.INSTANCE.parse("0X19").longValue());
	}
}
