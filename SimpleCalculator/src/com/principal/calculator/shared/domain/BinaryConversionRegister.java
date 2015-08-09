package com.principal.calculator.shared.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
public class BinaryConversionRegister implements IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private java.util.Date dateConversion;
	private String decimal;
	private String binary;

	@Transient
	private final static String dateFormat = "dd/MM/y hh:mm aaa";

	public BinaryConversionRegister() {
	}

	public BinaryConversionRegister(String decimal, String binary, java.util.Date date) {
		setDecimal(decimal);
		setBinary(binary);
		setDateConversion(date);
	}

	public Long getId() {
		return id;
	}

	public String getDecimal() {
		return decimal;
	}

	public String getBinary() {
		return binary;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}

	public void setBinary(String binary) {
		this.binary = binary;
	}
	
	public java.util.Date getDateConversion() {
		return dateConversion;
	}

	public void setDateConversion(java.util.Date conversionDate) {
		this.dateConversion = conversionDate;
	}

	@Override
	public String toString() {
		return decimal != null ? decimal : super.toString();
	}

}
