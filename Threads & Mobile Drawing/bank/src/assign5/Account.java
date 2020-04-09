package assign5;

public class Account {
	private int id;
	private int balance;
	private int numTrans;
	
	// Constructor
	public Account(int id, int balance, int numTrans) {
		this.id = id;
		this.balance = balance;
		this.numTrans = numTrans;
	}

	public synchronized void withdraw(int amount) {
		balance -= amount;
		numTrans++;
	}
	
	public synchronized void deposite(int amount) {
		balance += amount;
		numTrans++;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("acct:").append(id).
		append(" bal:").append(balance).
		append(" trans:").append(numTrans);
		return sb.toString();
	}
}
