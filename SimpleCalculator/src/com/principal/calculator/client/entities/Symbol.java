package com.principal.calculator.client.entities;

public enum Symbol {
	CLEAN ("C", "Clean All",18,true),
	CLEAN_ELEMENT ("CE", "Clean Element",19,true),
	PORC ("%","Porcentage",16,true),
	PLUS ("+","Adition",12,true),
	SEVEN ("7","SEVEN",7,false),
	EIGHT ("8","EIGHT",8,false),
	NINE ("9","NINE",9,false),
	MINUS ("-","Subtraction",13,true),
	FOUR ("4","FOUR",4,false),
	FIVE ("5","FIVE",5,false),
	SIX ("6","SIX",6,false),
	MULT ("*","Multiplication",14,true),
	ONE ("1","ONE",1,false),
	TWO ("2","TWO",2,false),
	THREE ("3","THREE",3,false),
	DIV ("/","Division",15,true),
	ZERO ("0","ZERO",0,false),
	DEC_SEPARATOR (".","DEC_SEPARATOR",11,false),
	SIGN ("+/-","SIGN",10,false),
	EQUAL ("=", "Equal", 17,true);
	
	private String buttonSymbol;
	private String description;
	private int value;
	private boolean operator;

	Symbol(String buttonSymbol, String description, int value, boolean operator){
		this.buttonSymbol = buttonSymbol;
		this.description = description;
		this.value = value;
		this.operator = operator;
	}

	public String getSymbol() {
		return buttonSymbol;
	}

	public String getDescription() {
		return description;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isOperator() {
		return operator;
	}
	
	public boolean isSymbol() {
		return !operator;
	}

	public void setOperator(boolean operator) {
		this.operator = operator;
	}

	public static Symbol valueOfBySymbol(String buttonSymbol) {
		Symbol[] values = Symbol.values();
        for (Symbol symbol : values) {
            if (symbol.buttonSymbol.equals(buttonSymbol) ) {
                return symbol;
            }
        }
        return null;
    }
	
	public static Symbol valueOf(int value) {
		Symbol[] values = Symbol.values();
        for (Symbol symbol : values) {
            if (symbol.value == value ) {
                return symbol;
            }
        }
        return null;
    }
}
