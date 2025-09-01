public class TestCreditAccount {

	public static void main(String[] args) {
		final Person robert = new Person("Robert", "Smith");
		final Person mary = new Person("Mary", "Linda", "Mc Wright");
		final int balance = CreditAccount.getLimitUpperBound();
		final int amount = (CreditAccount.getLimitUpperBound() - CreditAccount.getLimitLowerBound()) / 2;
		CreditAccountInterface robertAcc = CreditAccount.newOfBalanceOwner(balance, robert);
		CreditAccountInterface maryAcc = CreditAccount.newOfLimitBalanceOwner(CreditAccount.getLimitLowerBound(),
				balance, mary);
		assert robertAcc.getOwner().equals(robert);
		assert robertAcc.getBalance() == balance;
		assert robertAcc.getLimit() == CreditAccount.getLimitUpperBound();
		assert maryAcc.getOwner().equals(mary);
		assert maryAcc.getBalance() == balance;
		assert maryAcc.getLimit() == CreditAccount.getLimitLowerBound();
		assert robertAcc.deposit(amount) == balance + amount;
		assert maryAcc.deposit(balance) == 2 * balance;
		assert robertAcc.withdraw(amount) == balance;
		maryAcc.setLimit(amount);
		assert maryAcc.withdraw(balance) == balance;
	}

}
