package com.budgetTracker;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

class Transaction {
	protected String transactionId;
	protected Type type;
	protected String category;
	protected double amount;
	protected LocalDate date;
}

enum Type {
	INCOME, EXPENSE;
}



public class TransactionManager {

	BudgetManager manager = new BudgetManager();

	Scanner sc = new Scanner(System.in);
	protected String transactionId;
	private Type type;
	protected String category;
	protected double amount;
	protected LocalDate date;

	public TransactionManager()
	{
		transactionOptions();

	}

	// giving options to user

	private void transactionOptions() {
		while (true) {
			System.out.println("Choose one:");
			System.out.println("1 - To add income");
			System.out.println("2 - To add expense");
			System.out.println("3 - To see the total income");
			System.out.println("4 - To see the total expense");
			System.out.println("5 - To see the remaining amount after all expenses from income");
			System.out.println("6 - To see the list of all income transactions.");
			System.out.println("7 - To see the list of all expense transactions.");
			System.out.println("8 - Exit");
			System.out.println();

			int choice;
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a number.");
				continue;
			}

			switch (choice) {

			case 1 -> addIncome();
			case 2 -> addExpense();
			case 3 -> totalIncome();
			case 4 -> totalExpense();
			case 5 -> balanceAmountAfterExpenses();
			case 6 -> print_allIncomeListTransactions();
			case 7 -> print_allExpenseListTransactions();
			case 8 -> {
				System.out.println("Exiting...");
				return; // exit the method and loop
			}
			default -> System.out.println("Invalid choice! Please choose between 1-6.");
			}
			System.out.println(); // extra line for readability
		}
	}


	// transaction id generator
	private String generateTransactionId() {
		int length = 14;
		String setOfCharacters = "1234567890";
		Random random = new Random();
		StringBuilder id = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int rand = random.nextInt(setOfCharacters.length());
			id.append(setOfCharacters.charAt(rand));
		}
		return id.toString();

	}

	// Adding income
	private void addIncome() {
		ReentrantLock lock = new ReentrantLock(); // The project is single-threaded, but ReentrantLock was used to
													// demonstrate understanding of thread-safety.
		lock.lock();

		var t = new Transaction();

		System.out.println("Enter income amount :");
		double income = Double.parseDouble(sc.nextLine());

		t.amount = income;

		System.out.println("enter income type(Salary,Rent,profits,ect...) : ");
		String categoryOfIncome = sc.nextLine();
		t.category = categoryOfIncome.substring(0, 1).toUpperCase() + categoryOfIncome.substring(1).toLowerCase();

		t.type = Type.INCOME;

		LocalDate date = LocalDate.now();
		t.date = date;

		t.transactionId = generateTransactionId();
		System.out.println();

		//		System.out.println(" Transaction details are :");
		//		System.out.println(income + "\n" + this.category + "\n" + this.transactionId + "\n+" + this.date);

		manager.addTransactionToList(t);
		manager.printTransactionList();

		lock.unlock();
	}

	// Adding expenses
	private void addExpense() {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		var t = new Transaction();

		System.out.println("Enter expense amount :");
		double expense = Double.parseDouble(sc.nextLine());
		t.amount = expense;

		System.out.println("enter expense type(Salary,Rent,profits,ect...) : ");
		String categoryOfExpense = sc.nextLine();
		t.category = categoryOfExpense.substring(0, 1).toUpperCase() + categoryOfExpense.substring(1).toLowerCase();

		t.type = Type.EXPENSE;

		LocalDate date = LocalDate.now();
		t.date = date;

		t.transactionId = generateTransactionId();
		System.out.println();

		//		System.out.println(" Transaction details are :");
		//		System.out.println(expense + "\n" + this.category + "\n" + this.transactionId + "\n+" + this.date);


		manager.addTransactionToList(t);
		manager.printTransactionList();

		lock.unlock();

	}

	// Calculating tolal income
	private double totalIncome;
	private void totalIncome() {
		double totalIncome = manager.transactionList.stream().filter(e -> e.type == Type.INCOME)
				.mapToDouble(e -> e.amount).sum();
		this.totalIncome = totalIncome;

		System.out.println("Total Income: " + totalIncome);
		System.out.println();

	}

	// Calculating total expense
	private double totalExpense;
	private void totalExpense() {
		double totalExpense = manager.transactionList.stream().filter(e -> e.type == Type.EXPENSE)
				.mapToDouble(e -> e.amount).sum();
		this.totalExpense = totalExpense;
		System.out.println();
		System.out.println("Total Expenses: " + totalExpense);
		System.out.println();
	}

	// Printing balence amount
	private void balanceAmountAfterExpenses() {
		totalIncome();
		totalExpense();
		double remaing_amount = this.totalIncome - this.totalExpense;
		System.out.println("Balence amount: " + remaing_amount);
	}

	// printiong the list of income transactions
	public void print_allIncomeListTransactions() {
		System.out.println();
		System.out.println("*************** Income Transactions List ***************");

		manager.transactionList.stream().filter(e -> e.type == Type.INCOME)
		.forEach(e -> System.out.println("Transaction ID: " + e.transactionId + ", Category: " + e.category
				+ ", Amount: " + e.amount + ", Date: " + e.date));

		System.out.println("********************************************************");
		System.out.println();
	}

	// printiong the list of expense transactions
	public void print_allExpenseListTransactions() {
		System.out.println();
		System.out.println("*************** ExpenseTransactions List ***************");

		manager.transactionList.stream().filter(e -> e.type == Type.EXPENSE)
		.forEach(e -> System.out.println("Transaction ID: " + e.transactionId + ", Category: " + e.category
				+ ", Amount: " + e.amount + ", Date: " + e.date));

		System.out.println("********************************************************");
		System.out.println();
	}


	public static void main(String[] args) {

		TransactionManager transactionManager = new TransactionManager();
	}

}
