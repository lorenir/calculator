package com.principal.calculator.client.entities;

public class Operator {
	private final static String EMPTY_VALUE = "";

	private String value = EMPTY_VALUE;

	public Operator(String value) {
		this.value = value;
	}

	public String getValue() {
		if (value.equals(EMPTY_VALUE)) {
			return "0";
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String print() {
		return getValue();
	}

	public void clean() {
		value = EMPTY_VALUE;
	}

	public String addSymbol(Symbol symbol) {
		if (symbol.equals(Symbol.SIGN)) {
			if (getValue().equals("0")){
				return getValue();
			}
			return changeSign();
		}
		return addCharacter(symbol);
	}

	private String addCharacter(Symbol symbol) {
		if (symbol.equals(Symbol.DEC_SEPARATOR)) {
			if (hasDecimalSeparator()) {
				return this.value;
			}
			if (value.equals(Symbol.ZERO.getSymbol()) || value.isEmpty()){
				this.value = Symbol.ZERO.getSymbol() + Symbol.DEC_SEPARATOR.getSymbol();
				return this.value;
			}
		}
		if (getValue().equals(Symbol.ZERO.getSymbol())){
			setValue(symbol.getSymbol());
		}else{
			this.value = this.value + symbol.getSymbol();
		}
		
		return this.value;
	}

	private String changeSign() {
		if (!isZero()) {
			if (value.substring(0, 1).equals("-")) {
				value = value.substring(1);
			} else {
				value = "-" + value;
			}
		}
		return value;
	}

	public float toFloat() throws NumberFormatException {
		// check the decimal separator in locale
		return Float.parseFloat(getValue());
	}

	public boolean isValid() {
		Float floatValue = null;
		try {
			floatValue = Float.parseFloat(value);
		} catch (Exception e) {
			return false;

		}
		return floatValue != null;
	}

	public boolean hasDecimalSeparator() {
		return value.contains(Symbol.DEC_SEPARATOR.getSymbol());
	}

	public boolean isZero() {
		return this.value.equals("0");
	}
}
