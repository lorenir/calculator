package com.principal.calculator.server.dao;

import javax.persistence.Persistence;

public class EntityManagerFactory {

	private static final javax.persistence.EntityManagerFactory emInstance = Persistence.createEntityManagerFactory("transactions-optional");
	
	private EntityManagerFactory(){}
	
	public static javax.persistence.EntityManagerFactory get(){
		return emInstance;
	}
}
