package com.budgetTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BudgetManager {

	List<Transaction> transactionList = new ArrayList<>();

	public void addTransactionToList(Transaction t) {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		try {
			transactionList.add(t);
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		lock.unlock();
	}

	public void printTransactionList()
	{
		System.out.println("****************Transaction list*******************");
		System.out.println();
		transactionList.stream()
		.forEach(t -> System.out.println("Transaction Id: " + t.transactionId + " | Amount: " + t.amount
				+ " | Category: " + t.category + " | Date: " + t.date + " | Type of amount: " + t.type));
		System.out.println();

		System.out.println("********************************************************");
		System.out.println();

	}







}
