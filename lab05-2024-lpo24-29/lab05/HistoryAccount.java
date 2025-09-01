package lab05;

public class HistoryAccount extends CreditAccount implements HistoryAccountInterface {

    private static int value = 0;
    private static boolean deposit = false, withdraw = false, undo = false;
    
    //costruttori e metodi factory
    public HistoryAccount(int limit, int balance, PersonInterface owner){
        super(limit, balance, owner); // super() va a richiamare i costruttori della classe superiore
    }

    public static HistoryAccount newOfLimitBalanceOwner(int limit, int balance, PersonInterface owner){ // se in CreditAccount il metodo è static allora anche qui deve esserlo
        return new HistoryAccount(limit, balance, owner);
    }

    public static HistoryAccount newOfBalanceOwner(int balance, PersonInterface owner){
        return new HistoryAccount(CreditAccount.getLimitUpperBound(), balance, owner);
    }


    //metodi
    @Override
    public int getBalance(){
        return super.getBalance();
    }

    @Override
    public int getLimit(){
        return super.getLimit();
    }

    @Override
    public PersonInterface getOwner(){
        return super.getOwner();
    }

    @Override
    public void setLimit(int limit){
        super.setLimit(limit);
    }

    @Override
    public int deposit(int amount){
        value = amount;
        deposit = true;
        withdraw = false;
        undo = false;
        return super.deposit(amount);
    }

    @Override
    public int withdraw(int amount){
        value = amount;
        withdraw = true;
        deposit = false;
        undo = false;
        return super.withdraw(amount);
    }

    public int undo(){
        if(undo || !deposit && !withdraw)
            throw new IllegalStateException();
        undo = true;
        if(deposit) return withdraw(value);
        return deposit(value);
    }

    public int redo(){
        if(!deposit && !withdraw)
            throw new IllegalStateException();
        undo = false;
        if(deposit) return deposit(value);
        return withdraw(value);
    }
}
