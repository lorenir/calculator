package com.principal.calculator.shared.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class BinaryConversionRegister{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Date conversionDate;
	private String decimal;
	private String binary;
	
	
//	 private DateTimeFormat df = DateTimeFormat.getFormat("dd/MM/y hh:mm aaa");
    
    public BinaryConversionRegister(String decimal, String binary, Date conversionDate) {
        setDecimal(decimal);
        setBinary(binary);
        setConversionDate(conversionDate);
    }
    
    public int getId() {
        return id;
    }
    
    public String getDecimal() {
        return decimal;
    }
    
    public String getBinary() {
        return binary;
    }
    
    public Date getConversionDate() {
        return conversionDate;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }
    
    public void setBinary(String binary) {
        this.binary = binary;
    }
    
    public void setConversionDate(Date conversionDate) {
        this.conversionDate = conversionDate;
    }
    
    @Override
    public String toString() {
        return decimal != null ? decimal : super.toString();
    }
    

}
