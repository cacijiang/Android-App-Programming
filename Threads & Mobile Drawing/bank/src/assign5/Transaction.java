package assign5;

public class Transaction {
	private int fromAcct;
	private int toAcct;
	private int amount;
	
	public Transaction(int fromAcct, int toAcct, int amount) {
		this.fromAcct = fromAcct;
		this.toAcct = toAcct;
		this.amount = amount;
	}

	public int getFromAccount() {
		return fromAcct;
	}
	
	public int getToAccount() {
		return toAcct;
	}
	
	public int getAmount() {
		return amount;
	}
}
