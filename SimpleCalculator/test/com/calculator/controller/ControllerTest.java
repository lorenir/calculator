package com.calculator.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.principal.calculator.client.controllers.Calculator;
import com.principal.calculator.client.entities.States;
import com.principal.calculator.client.entities.Symbol;

public class ControllerTest {

	private Calculator calculator;

	@Before
	public void createOperator() {
		calculator = new Calculator();

	}

	@Test
	public void resetTest() {
		calculator.resetValues();
		assertEquals(calculator.getOp1().getValue(), "0");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), true);
	}

	@Test
	public void comunOperationTest() {
		calculator.resetValues();

		// add the first number
		assertEquals(calculator.AddSymbol(Symbol.EIGHT.getSymbol()), "8");
		assertEquals(calculator.getOp1().getValue(), "8");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		// add the second number
		assertEquals(calculator.AddSymbol(Symbol.SEVEN.getSymbol()), "87");
		assertEquals(calculator.getOp1().getValue(), "87");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		// add a decimal separator
		assertEquals(calculator.AddSymbol(Symbol.DEC_SEPARATOR.getSymbol()), "87.");
		assertEquals(calculator.getOp1().getValue(), "87.");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		// change sign
		assertEquals(calculator.AddSymbol(Symbol.SIGN.getSymbol()), "-87.");
		assertEquals(calculator.getOp1().getValue(), "-87.");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		// add decimal values
		assertEquals(calculator.AddSymbol(Symbol.ONE.getSymbol()), "-87.1");
		assertEquals(calculator.AddSymbol(Symbol.FOUR.getSymbol()), "-87.14");
		assertEquals(calculator.AddSymbol(Symbol.SIGN.getSymbol()), "87.14");
		assertEquals(calculator.getOp1().getValue(), "87.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		// plus operation
		assertEquals(calculator.Operate(Symbol.PLUS.getSymbol()), "87.14");
		assertEquals(calculator.getOp1().getValue(), "87.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Symbol.PLUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), true);

		// add second operator values
		assertEquals(calculator.AddSymbol(Symbol.ONE.getSymbol()), "1");
		assertEquals(calculator.AddSymbol(Symbol.FOUR.getSymbol()), "14");
		assertEquals(calculator.getOp1().getValue(), "87.14");
		assertEquals(calculator.getOp2().getValue(), "14");
		assertEquals(calculator.getOperation(), Symbol.PLUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), false);

		// minus operation
		assertEquals(calculator.Operate(Symbol.MINUS.getSymbol()), "101.14");
		assertEquals(calculator.getOp1().getValue(), "101.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Symbol.MINUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), true);
		assertEquals(calculator.AddSymbol(Symbol.FIVE.getSymbol()), "5");
		assertEquals(calculator.AddSymbol(Symbol.SIX.getSymbol()), "56");
		assertEquals(calculator.getOp1().getValue(), "101.14");
		assertEquals(calculator.getOp2().getValue(), "56");
		assertEquals(calculator.getOperation(), Symbol.MINUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), false);

		// equal operation
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "45.14");
		assertEquals(calculator.getOp1().getValue(), "45.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Symbol.MINUS);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), true);

		// clean element
		assertEquals(calculator.Operate(Symbol.CLEAN_ELEMENT.getSymbol()), "0");
		assertEquals(calculator.getOp1().getValue(), "45.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Symbol.MINUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), true);

		// clean all
		assertEquals(calculator.Operate(Symbol.CLEAN.getSymbol()), "0");
		assertEquals(calculator.getOp1().getValue(), "0");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), true);

		// Porcentage
		assertEquals(calculator.AddSymbol(Symbol.TWO.getSymbol()), "2");
		assertEquals(calculator.Operate(Symbol.PORC.getSymbol()), "2");
		assertEquals(calculator.AddSymbol(Symbol.FIVE.getSymbol()), "5");
		assertEquals(calculator.AddSymbol(Symbol.ZERO.getSymbol()), "50");
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "1.0");
		assertEquals(calculator.getOp1().getValue(), "1.0");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Symbol.PORC);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), true);

		// Percentage with a zero
		assertEquals(calculator.AddSymbol(Symbol.ZERO.getSymbol()), "0");
		assertEquals(calculator.Operate(Symbol.PORC.getSymbol()), "0");
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "Error");

		// Division
		assertEquals(calculator.AddSymbol(Symbol.NINE.getSymbol()), "9");
		assertEquals(calculator.Operate(Symbol.DIV.getSymbol()), "9");
		assertEquals(calculator.AddSymbol(Symbol.THREE.getSymbol()), "3");
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "3.0");
		assertEquals(calculator.getOp1().getValue(), "3.0");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Symbol.DIV);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), true);

		// Multiplication by the previous result
		assertEquals(calculator.Operate(Symbol.MULT.getSymbol()), "3.0");
		assertEquals(calculator.AddSymbol(Symbol.FIVE.getSymbol()), "5");
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "15.0");
		assertEquals(calculator.getOp1().getValue(), "15.0");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Symbol.MULT);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), true);

		// Division with a zero
		assertEquals(calculator.Operate(Symbol.DIV.getSymbol()), "15.0");
		assertEquals(calculator.AddSymbol(Symbol.ZERO.getSymbol()), "0");

		// Operation + Equal + Operation
		calculator.resetValues();
		assertEquals(calculator.AddSymbol(Symbol.SEVEN.getSymbol()), "7");
		assertEquals(calculator.Operate(Symbol.PORC.getSymbol()), "7");
		assertEquals(calculator.AddSymbol(Symbol.FIVE.getSymbol()), "5");
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "0.35");
		assertEquals(calculator.Operate(Symbol.PLUS.getSymbol()), "0.35");
	}
	
	
	@Test
	public void notComunOperationTest() {


		// Operation +  Operation
		calculator.resetValues();
		assertEquals(calculator.Operate(Symbol.PORC.getSymbol()), "0");
		assertEquals(calculator.Operate(Symbol.PLUS.getSymbol()), "0");
	}

}
