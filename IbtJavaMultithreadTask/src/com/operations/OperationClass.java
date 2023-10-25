package com.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.model.AccountTrx;
import com.model.AccountTrxFinal;

public class OperationClass implements Runnable {

	private final int startNumber;
    private final int endNumber;
    private final int threadNo;
    private final Session session;

    public OperationClass(int startNumber, int endNumber, int threadNo) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.threadNo = threadNo;
        this.session = new Configuration().configure().buildSessionFactory().openSession();
    }

    public void run() {
    	
    	List<AccountTrx> dataList = new ArrayList<AccountTrx>();
    	
    	dataList = (List<AccountTrx>) session.createQuery("FROM AccountTrx Order By sirano").getResultList();

        for (int i = startNumber; i < endNumber; i++) {
        	
            System.out.println("Thread: " + threadNo 
            		+ " (" + startNumber + " - " + endNumber 
            		+ ") -> Sıra: " + dataList.get(i).getSirano() 
            		+ " / Miktar: " + dataList.get(i).getAmount() 
            		+ "\n");
            
            
            //Optimistic Locking
            session.beginTransaction();
            AccountTrx dataUpdate = new AccountTrx();
            dataUpdate = session.get(AccountTrx.class, dataList.get(i).getSirano());         
            dataUpdate.setStatus(1);
            session.getTransaction().commit();
            
            
            
//            AccountTrxFinal dataInsert = new AccountTrxFinal();
//        	dataInsert.setSirano(dataList.get(i).getSirano());
//        	dataInsert.setStatus(dataList.get(i).getStatus());
//        	dataInsert.setAccountno(dataList.get(i).getAccountno());
//        	dataInsert.setAmount(dataList.get(i).getAmount());
//        	dataInsert.setTransactiontype(dataList.get(i).getTransactiontype());
//        	
//        	session.beginTransaction();
//        	session.persist(dataInsert);
//        	session.getTransaction().commit();
        }
    	
    }
    
	
}
