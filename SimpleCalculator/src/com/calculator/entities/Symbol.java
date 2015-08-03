package com.calculator.entities;

public enum Symbol {
	ONE ("1","ONE",1),
	TWO ("2","TWO",2),
	THREE ("3","THREE",3),
	FOUR ("4","FOUR",4),
	FIVE ("5","FIVE",5),
	SIX ("6","SIX",6),
	SEVEN ("7","SEVEN",7),
	EIGHT ("8","EIGHT",8),
	NINE ("9","NINE",9),
	ZERO ("0","ZERO",0),
	SIGN ("+/-","SIGN",10),
	DEC_SEPARATOR (".","DEC_SEPARATOR",11),
	PLUS ("+","Adition",12),
	MINUS ("-","Subtraction",13),
	MULT ("*","Multiplication",14),
	DIV ("/","Division",15),
	PORC ("%","Porcentage",16),
	EQUAL ("=", "Equal", 17),
	CLEAN ("C", "Clean All",18),
	CLEAN_ELEMENT ("CE", "Clean Element",19);
	
	private String buttonSymbol;
	private String description;
	private int value;

	Symbol(String buttonSymbol, String description, int value){
		this.buttonSymbol = buttonSymbol;
		this.description = description;
		this.value = value;
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
