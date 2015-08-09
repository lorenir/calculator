package com.principal.calculator.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.principal.calculator.shared.domain.BinaryConversionRegister;

public class BinaryConversionRegisterDAO {
	private EntityManager em;

	public boolean addRegister(BinaryConversionRegister bcr) {
		em = EntityManagerFactory.get().createEntityManager();
		boolean result = true;
		try {
			em.getTransaction().begin();
			em.persist(bcr);
			em.getTransaction().commit();
		} catch (Exception e) {
			result = false;
		} 
		finally{
			if (em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			em.close();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<BinaryConversionRegister> findAllRegisters(){
		em = EntityManagerFactory.get().createEntityManager();
		List<BinaryConversionRegister> result = null;
		try{
			Query query = em.createQuery("SELECT b FROM BinaryConversionRegister b");
			result = (query != null)?new ArrayList<BinaryConversionRegister>(query.getResultList()):null;
		}finally{
			em.close();
		}
		return result;
	}
}