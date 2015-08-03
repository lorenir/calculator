package com.calculator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.calculator.controllers.Calculator;
import com.calculator.entities.Operation;
import com.calculator.entities.Operator;
import com.calculator.entities.States;
import com.calculator.entities.Symbol;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class ControllerTest {

	private Operator op1;
	private Operator op2;
	private Calculator calculator;

	@Before
	public void createOperator() {
		op1 = new Operator("");
		op2 = new Operator("");
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
	public void addSymbolTest() {
		calculator.resetValues();

		assertEquals(calculator.AddSymbol(Symbol.EIGHT.getSymbol()), "8");
		assertEquals(calculator.getOp1().getValue(), "8");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		assertEquals(calculator.AddSymbol(Symbol.SEVEN.getSymbol()), "87");
		assertEquals(calculator.getOp1().getValue(), "87");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		assertEquals(calculator.AddSymbol(Symbol.DEC_SEPARATOR.getSymbol()), "87.");
		assertEquals(calculator.getOp1().getValue(), "87.");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		assertEquals(calculator.AddSymbol(Symbol.SIGN.getSymbol()), "-87.");
		assertEquals(calculator.getOp1().getValue(), "-87.");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		assertEquals(calculator.AddSymbol(Symbol.ONE.getSymbol()), "-87.1");
		assertEquals(calculator.AddSymbol(Symbol.FOUR.getSymbol()), "-87.14");
		assertEquals(calculator.AddSymbol(Symbol.SIGN.getSymbol()), "87.14");
		assertEquals(calculator.getOp1().getValue(), "87.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), false);

		assertEquals(calculator.Operate(Symbol.PLUS.getSymbol()), "87.14");
		assertEquals(calculator.getOp1().getValue(), "87.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Operation.PLUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), true);

		assertEquals(calculator.AddSymbol(Symbol.ONE.getSymbol()), "1");
		assertEquals(calculator.AddSymbol(Symbol.FOUR.getSymbol()), "14");
		assertEquals(calculator.getOp1().getValue(), "87.14");
		assertEquals(calculator.getOp2().getValue(), "14");
		assertEquals(calculator.getOperation(), Operation.PLUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), false);

		assertEquals(calculator.Operate(Symbol.MINUS.getSymbol()), "101.14");
		assertEquals(calculator.getOp1().getValue(), "101.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Operation.MINUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), true);
		
		assertEquals(calculator.AddSymbol(Symbol.FIVE.getSymbol()), "5");
		assertEquals(calculator.AddSymbol(Symbol.SIX.getSymbol()), "56");
		assertEquals(calculator.getOp1().getValue(), "101.14");
		assertEquals(calculator.getOp2().getValue(), "56");
		assertEquals(calculator.getOperation(), Operation.MINUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), false);
		
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "45.14");
		assertEquals(calculator.getOp1().getValue(), "45.14");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Operation.MINUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), true);
		
		assertEquals(calculator.Operate(Operation.CLEAN_ELEMENT.getSymbol()), "0");
		assertEquals(calculator.getOp1().getValue(), "45.14");
		assertEquals(calculator.getOp2().getValue(), "0");		
		assertEquals(calculator.getOperation(), Operation.MINUS);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), true);
		
		assertEquals(calculator.Operate(Operation.CLEAN.getSymbol()), "0");
		assertEquals(calculator.getOp1().getValue(), "0");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), null);
		assertEquals(calculator.getState(), States.GETTING_OP1);
		assertEquals(calculator.isFinalOperator(), true);
		
		assertEquals(calculator.AddSymbol(Symbol.TWO.getSymbol()), "2");
		assertEquals(calculator.Operate(Operation.PORC.getSymbol()), "2");
		assertEquals(calculator.AddSymbol(Symbol.FIVE.getSymbol()), "5");
		assertEquals(calculator.AddSymbol(Symbol.ZERO.getSymbol()), "50");
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "1.0");

		assertEquals(calculator.getOp1().getValue(), "1.0");
		assertEquals(calculator.getOp2().getValue(), "0");
		assertEquals(calculator.getOperation(), Operation.PORC);
		assertEquals(calculator.getState(), States.GETTING_OP2);
		assertEquals(calculator.isFinalOperator(), true);
		
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "Error");
		
		assertEquals(calculator.AddSymbol(Symbol.ZERO.getSymbol()), "0");
		assertEquals(calculator.Operate(Symbol.EQUAL.getSymbol()), "Error");



		
	}
}
