package com.shorturl.repository;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceRepoImpl implements SequenceRepo {
	/**
	 * EntityManagerFactory variable which is used for getting the sequence value
	 * from database.
	 */
	@Autowired
	private EntityManagerFactory emf;

	/**
	 * getNextSequence method is used to initialize new value from database sequence
	 * it buffers certain preset values in memory so that for every call it doesnot
	 * need to hit database.
	 * <ul>
	 * This buffer value needs to configured in two places first is while creating
	 * the sequence which is by how much value sequence needs to be incremented.
	 */
	@Override
	public BigInteger getNextSequence() {
		EntityManager em = null;
		BigInteger num = null;
		try {
			em = emf.createEntityManager();
			Query query = em.createNativeQuery("select counter_seq.nextval");
			Object temp= query.getSingleResult();
			if (temp instanceof BigInteger) {
				num=(BigInteger)temp; 
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return num;
	}
}
