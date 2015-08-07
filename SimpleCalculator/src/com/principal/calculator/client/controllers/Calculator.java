package com.principal.calculator.client.controllers;

import com.principal.calculator.client.entities.Operator;
import com.principal.calculator.client.entities.States;
import com.principal.calculator.client.entities.Symbol;

public class Calculator {
	private final static String ERROR_MSG = "Error";

	private Operator op1;
	private Operator op2;
	private Symbol operation;
	private States state;
	private boolean finalEdition;

	public Calculator() {
		super();
		op1 = new Operator("");
		op2 = new Operator("");
		resetValues();
	}

	public Operator getOp1() {
		return op1;
	}

	public void setOp1(Operator op1) {
		this.op1 = op1;
	}

	public Operator getOp2() {
		return op2;
	}

	public void setOp2(Operator op2) {
		this.op2 = op2;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}

	public boolean isFinalOperator() {
		return finalEdition;
	}

	public void setFinalOperator(boolean finalOperator) {
		this.finalEdition = finalOperator;
	}

	public Symbol getOperation() {
		return operation;
	}

	public void setOperation(Symbol operation) {
		if (operation.isOperator()) {
			this.operation = operation;
		}
	}

	public String AddSymbol(String buttonSymbol) {
		Symbol symbol = Symbol.valueOfBySymbol(buttonSymbol);
		String returningValue = "0";
		switch (this.state) {
		case GETTING_OP1:
			if (finalEdition) {
				if (!((symbol.equals(Symbol.DEC_SEPARATOR)) && (op1.isZero()))) {
					op1.clean();
				}
				finalEdition = false;
			}
			if (symbol == null) {
				returningValue = op1.getValue();
			} else {
				returningValue = op1.addSymbol(symbol);
			}

			break;
		case GETTING_OP2:
			if (finalEdition) {
				op2.clean();
				finalEdition = false;
			}
			if (symbol == null) {
				returningValue = op2.getValue();
			} else {
				returningValue = op2.addSymbol(symbol);
			}
			break;

		}
		return returningValue;
	}

	public String Operate(String buttonOperation) {
		Symbol operation = Symbol.valueOfBySymbol(buttonOperation);
		if (operation == null || !operation.isOperator()) {
			resetValues();
			return ERROR_MSG;
		}
		String result = "";
		switch (operation) {
		case CLEAN:
			resetValues();
			result = op1.getValue();
			break;
		case CLEAN_ELEMENT:
			resetOp2Values();
			result = op2.getValue();
			break;
		case EQUAL:
			// status = 1
			if (state.equals(States.GETTING_OP1)) {
				finalEdition = true;
			} else {
				// status = 2
				op1.setValue(Evaluate());
				finalEdition = true;
				op2.clean();
				state = States.GETTING_OP1;
			}

			result = op1.getValue();
			break;
		default:
			// other operations
			if (state.equals(States.GETTING_OP1)) {
				this.operation = operation;
				state = States.GETTING_OP2;
				finalEdition = true;
			} else {
				if (state.equals(States.GETTING_OP2)) {
					String eval = Evaluate();
					if (!eval.equals(ERROR_MSG)){
						op1.setValue(eval);
					}
					op2.clean();
					this.operation = operation;
					state = States.GETTING_OP2;
					finalEdition = true;
				}
			}
			result = op1.getValue();
			break;
		}
		return result;

	}

	private String Evaluate() {
		float op1FloatVal = 0;
		float op2FloatVal = 0;
		float resultValue = 0;

		try {
			op1FloatVal = op1.toFloat();
			op2FloatVal = op2.toFloat();
		} catch (Exception e) {
			resetValues();
			return ERROR_MSG;
		}

		switch (operation) {
		case PLUS:
			resultValue = op1FloatVal + op2FloatVal;
			break;
		case MINUS:
			resultValue = op1FloatVal - op2FloatVal;
			break;
		case MULT:
			resultValue = op1FloatVal * op2FloatVal;
			break;
		case DIV:
			if (op2FloatVal == 0) {
				return ERROR_MSG;
			}
			resultValue = op1FloatVal / op2FloatVal;
			break;
		case PORC:
			if (op1.getValue().equals("0") || op2.getValue().equals("0")) {
				return ERROR_MSG;
			}
			resultValue = op1FloatVal * op2FloatVal / 100;
			break;
		default:
			return ERROR_MSG;
		}
		return formatDisplayText(resultValue);
	}

	private String formatDisplayText(float floatValue) {
		return Float.toString(floatValue);
	}

	public void resetValues() {
		op1.clean();
		op2.clean();
		finalEdition = true;
		operation = null;
		state = States.GETTING_OP1;
	}

	public void resetOp2Values() {
		op2.clean();
		finalEdition = true;
		state = States.GETTING_OP2;
	}

}
