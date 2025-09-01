package lab05;

public class CreditAccount implements CreditAccountInterface {
	private static final int limitLowerBound = -1000_00; // in cents
	private static final int limitUpperBound = 1000_00; // in cents
	
	private int limit; // in cents
	private int balance; // in cents
	private final PersonInterface owner;

	//@ invariant CreditAccount.limitLowerBound <= limit && limit <= CreditAccount.limitUpperBound && balance >= limit && owner != null
	
	// private auxiliary class methods for validation and identifier generation

	private static PersonInterface requireNonNull(PersonInterface p) {
		if (p == null)
			throw new NullPointerException();
		return p;
	}

	private static int requirePositive(int amount) {
		if (amount <= 0)
			throw new IllegalArgumentException("the amount is not positive");
		return amount;
	}

	private static int requireBalanceGeqLimit(int balance, int limit) {
		if (balance < limit)
			throw new IllegalArgumentException("balance below limit");
		return balance;
	}

	private static int requireLimitInBounds(int limit) {
		if (limit < CreditAccount.limitLowerBound || limit > CreditAccount.limitUpperBound)
			throw new IllegalArgumentException("limit out of bounds");
		return limit;
	}

	// constructor

	public CreditAccount(int limit, int balance, PersonInterface owner) {
		this.balance = CreditAccount.requireBalanceGeqLimit(balance, limit);
		this.limit = CreditAccount.requireLimitInBounds(limit);
		this.owner = CreditAccount.requireNonNull(owner);
	}

	// factory class methods

	public static CreditAccount newOfLimitBalanceOwner(int limit, int balance,
			PersonInterface owner) {
		return new CreditAccount(limit, balance, owner);
	}

	public static CreditAccount newOfBalanceOwner(int balance, PersonInterface owner) {
		return new CreditAccount(CreditAccount.limitUpperBound, balance, owner);
	}

	// object methods of the interface

	public int deposit(int amount) { // amount in cents
		return this.balance = Math.addExact(this.balance, CreditAccount.requirePositive(amount)); // throws an exception in case of overflow 																						
	}

	public int withdraw(int amount) { // amount in cents
		final int newBalance = Math.subtractExact(this.balance, CreditAccount.requirePositive(amount)); // throws an exception in case of overflow 
		return this.balance = CreditAccount.requireBalanceGeqLimit(newBalance, this.limit);
	}

	public int getBalance() {
		return this.balance;
	}

	public int getLimit() {
		return this.limit;
	}

	public PersonInterface getOwner() {
		return this.owner;
	}

	public void setLimit(int limit) { // setter method
		CreditAccount.requireBalanceGeqLimit(this.balance, limit);
		this.limit = CreditAccount.requireLimitInBounds(limit);
	}

	// getters for static fields
	
	public static int getLimitLowerBound() {
		return CreditAccount.limitLowerBound;
	}
	
	public static int getLimitUpperBound() {
		return CreditAccount.limitUpperBound;
	}
	
}
