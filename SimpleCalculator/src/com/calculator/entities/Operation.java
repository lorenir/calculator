package com.calculator.entities;

public enum Operation {
	PLUS ("+","Adition"),
	MINUS ("-","Subtraction"),
	MULT ("*","Multiplication"),
	DIV ("/","Division"),
	PORC ("%","Porcentage"),
	EQUAL ("=", "Equal"),
	CLEAN ("C", "Clean All"),
	CLEAN_ELEMENT ("CE", "Clean Element");

	

	
	private String symbol;
	private String description;
	
	Operation(String symbol, String description){
		this.symbol = symbol;
		this.description = description;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getDescription() {
		return description;
	}
	
	public static Operation valueOfBySymbol(String symbol) {
		Operation[] values = Operation.values();
        for (Operation operation : values) {
            if (operation.symbol.equals(symbol) ) {
                return operation;
            }
        }
        return null;
    }
	

}
