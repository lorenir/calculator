package com.calculator.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.principal.calculator.client.entities.Operator;
import com.principal.calculator.client.entities.Symbol;

public class OperatorTest {
	private Operator opTest;

	@Before
	public void createOperator() {
		opTest = new Operator("");
	}

	@Test
	public void getValueTest() {
		assertEquals(opTest.getValue(), "0");
	}

	@Test
	public void addSymbolTest() {
		// ADD number
		opTest.setValue("0");
		assertEquals(opTest.addSymbol(Symbol.FOUR), "4");

		// ADD number
		opTest.setValue("123");
		assertEquals(opTest.addSymbol(Symbol.FOUR), "1234");

		// ADD decimal
		assertEquals(opTest.addSymbol(Symbol.DEC_SEPARATOR), "1234.");
		assertEquals(opTest.addSymbol(Symbol.DEC_SEPARATOR), "1234.");

		// ADD sign
		opTest.setValue("123");
		assertEquals(opTest.addSymbol(Symbol.SIGN), "-123");
		assertEquals(opTest.addSymbol(Symbol.SIGN), "123");

		// from clean
		opTest.clean();
		assertEquals(opTest.addSymbol(Symbol.FIVE), "5");

		opTest.clean();
		assertEquals(opTest.addSymbol(Symbol.DEC_SEPARATOR), "0.");
		
		opTest.setValue("0.");
		assertEquals(opTest.addSymbol(Symbol.ZERO), "0.0");


	}

	@Test
	public void toFloatTest() {
		opTest.setValue("123");
		assertEquals(opTest.toFloat(), 123f, 0);

		opTest.setValue("12.3");
		assertEquals(opTest.toFloat(), 12.3f, 0);

		opTest.setValue("123.");
		assertEquals(opTest.toFloat(), 123f, 0);

		opTest.setValue("0.");
		assertEquals(opTest.toFloat(), 0f, 0);

	}

	@Test
	public void isValidTest() {
		opTest.setValue("12.3");
		assertEquals(opTest.isValid(), true);

		opTest.setValue("1.2g");
		assertEquals(opTest.isValid(), false);

	}

}
