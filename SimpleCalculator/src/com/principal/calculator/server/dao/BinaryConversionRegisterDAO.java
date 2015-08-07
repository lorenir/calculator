package com.principal.calculator.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.principal.calculator.shared.domain.BinaryConversionRegister;

public class BinaryConversionRegisterDAO {
	private EntityManager em;

	public BinaryConversionRegisterDAO() {
		em = EntityManagerFactory.get().createEntityManager();
	}

	public boolean addRegister(BinaryConversionRegister bcr) {
		boolean result = true;
		try {
			em.getTransaction().begin();
			em.persist(bcr);
			em.getTransaction().commit();
		} catch (Exception e) {
			result = false;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<BinaryConversionRegister> findAllRegisters(){
		Query query = em.createQuery("SELECT * FROM BinaryConversionREgister");
		return (List<BinaryConversionRegister>)query.getResultList();
	}
}