package lab05;

public class Test {

	public static void main(String[] args) {
		final Person robert = new Person("Robert", "Smith");
		final Person mary = new Person("Mary", "Linda", "Mc Wright");
		final int balance = HistoryAccount.getLimitUpperBound();
		final int amount = (HistoryAccount.getLimitUpperBound() - HistoryAccount.getLimitLowerBound()) / 2;
		HistoryAccountInterface robertAcc = HistoryAccount.newOfBalanceOwner(balance, robert);
		HistoryAccountInterface maryAcc = HistoryAccount.newOfLimitBalanceOwner(HistoryAccount.getLimitLowerBound(),
				balance, mary);
		assert robertAcc.getOwner().equals(robert);
		assert robertAcc.getBalance() == balance;
		assert robertAcc.getLimit() == HistoryAccount.getLimitUpperBound();
		assert maryAcc.getOwner().equals(mary);
		assert maryAcc.getBalance() == balance;
		assert maryAcc.getLimit() == HistoryAccount.getLimitLowerBound();
		// robertAcc.redo(); // solleverebbe IllegalStateException
		// maryAcc.undo(); // solleverebbe IllegalStateException
		assert robertAcc.deposit(amount) == balance + amount;
		assert robertAcc.redo() == balance + 2 * amount;
		assert robertAcc.redo() == balance + 3 * amount;
		assert robertAcc.undo() == balance + 2 * amount;
		// robertAcc.undo(); // solleverebbe IllegalStateException
		assert maryAcc.deposit(balance) == 2 * balance;
		assert maryAcc.undo() == balance;
		// maryAcc.redo(); // solleverebbe IllegalStateException
		assert maryAcc.deposit(amount) == balance + amount;
		assert robertAcc.withdraw(amount) == balance + amount;
		maryAcc.setLimit(amount);
		assert maryAcc.withdraw(balance) == amount;
	}

}
