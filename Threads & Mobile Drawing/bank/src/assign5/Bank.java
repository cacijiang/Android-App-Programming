package assign5;

import java.util.concurrent.*;
import java.io.*;

public class Bank {
	private static Account[] accounts;
	private static BlockingQueue<Transaction> blockqueue;
	private static CountDownLatch latch;
	private static int numWorkers;
	
	private static final int queueSize = 10;
	private static final int numAccounts = 20;
	private static final int initBal = 1000;
	private static final int initNumTrans = 0;
	private static final Transaction nullTrans = new Transaction(-1,0,0);
	
	public static class Worker extends Thread {
		
		@Override
		public void run() {
			try {
				while(true) {
					Transaction txn = blockqueue.take();
					if(txn.equals(nullTrans)) {
						break;
					}
					Account fromAcct = accounts[txn.getFromAccount()];
					Account toAcct = accounts[txn.getToAccount()];
					int amount = txn.getAmount();
					fromAcct.withdraw(amount);
					toAcct.deposite(amount);
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println(this.getName()+" is done.");
			latch.countDown();
		}
	}
	
	public static void startWorkers() {
		for(int i = 0; i < numWorkers; i++) {
			Worker iWorker = new Worker();
			iWorker.start();
		}
	}
	
	public static void initializeAccounts() {
		accounts = new Account[numAccounts];
		for(int i = 0; i < numAccounts; i++) {
			accounts[i] = new Account(i, initBal, initNumTrans);
		}
	}
	
	public static void readFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
	        String line;
	        
	        while ((line = reader.readLine()) != null) {
	            String[] words = line.split(" ");
	            int fromAcct = Integer.parseInt(words[0]);
	            int toAcct = Integer.parseInt(words[1]);
	            int amount = Integer.parseInt(words[2]);
	            Transaction txn = new Transaction(fromAcct, toAcct, amount);
	            blockqueue.put(txn);
	        }
	        reader.close();
	        
	        for(int i = 0; i < numWorkers; i++) {
	        	blockqueue.put(nullTrans);
	        }
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
        	e.printStackTrace();
        }
	}
	
	public static void printAccounts() {
		for(Account acct : accounts) {
			System.out.println(acct.toString());
		}
	}
	
	public static void main(String[] args) {
		String fileName = args[0];
		numWorkers = Integer.parseInt(args[1]);
		blockqueue = new ArrayBlockingQueue<Transaction> (queueSize);
		latch = new CountDownLatch(numWorkers);
		startWorkers();
		initializeAccounts();
		readFile(fileName);
		try {
            latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		printAccounts();	
	}
	
}
