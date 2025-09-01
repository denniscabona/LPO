package lab05;

public interface CreditAccountInterface {

	int deposit(int amount);

	int withdraw(int amount);

	int getBalance();

	int getLimit();

	PersonInterface getOwner();

	void setLimit(int limit);

}