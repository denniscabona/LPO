public class CreditAccount implements CreditAccountInterface {
    
    // dichiarare i campi di classe e di oggetto
    private int limit;
    private final static int limitLowerBound = 0, limitUpperBound = 15000;
    //@ invariant limitLowerBound <= limit && limitUpperBound >= limit

    private int balance;
    //@ invariant balance >= limit

    private final PersonInterface owner;
    //@ invariant owner != null

   
    // definire i seguenti metodi private di validazione
    private static PersonInterface requireNonNull(PersonInterface p){
        if(p.getFirstName() == null || p.getLastName() == null)
            return null;
        return p;
    }

    private static int requirePositive(int amount){
        if(amount < 0)
            throw new IllegalArgumentException("amount cannot be less than 0");
        return amount;
    }

    private static int requireBalanceGeqLimit(int balance, int limit){
        if(balance < limit)
            throw new IllegalArgumentException("balance cannot be less than limit");
        return balance;
        
    }

    private static int requireLimitInBounds(int limit){
        if(limitLowerBound > limit || limitUpperBound < limit)
            throw new IllegalArgumentException("limit cannot be outside limitLowerBound and limitUpperBound");
        return limit;
    }



    // definire il costruttore  CreditAccount(int limit, int balance, PersonInterface owner) 
    public CreditAccount(int limit, int balance, PersonInterface owner){
        this.limit = CreditAccount.requireLimitInBounds(limit);
        this.balance = CreditAccount.requireBalanceGeqLimit(balance, limit);
        this.owner = CreditAccount.requireNonNull(owner);
    }


    // definire i seguenti metodi factory
    
    // CreditAccountInterface newOfLimitBalanceOwner(int limit, int balance, PersonInterface owner)
    public static CreditAccountInterface newOfLimitBalanceOwner(int limit, int balance, PersonInterface owner){
        return new CreditAccount(limit, balance, owner);
    }
    // CreditAccountInterface newOfBalanceOwner(int balance, PersonInterface owner)
    public static CreditAccountInterface newOfBalanceOwner(int balance, PersonInterface owner){
        return new CreditAccount(limitUpperBound, balance, owner);
    }


    // definire i metodi dell'interfaccia CreditAccountInterface
	public int deposit(int amount){
        return balance += CreditAccount.requirePositive(amount);
    }

	public int withdraw(int amount){
        return balance -= CreditAccount.requirePositive(amount);
    }

	public void setLimit(int limit){
        this.limit = CreditAccount.requireLimitInBounds(limit);
    }

	public int getBalance(){
        return balance;
    }

	public int getLimit(){
        return limit;
    }

	public PersonInterface getOwner(){
        return owner;
    }
        
    
    // definire i metodi getter che non fanno parte dell'interfaccia
    public static int getLimitLowerBound(){
        return limitLowerBound;
    }

    public static int getLimitUpperBound(){
        return limitUpperBound;
    }
    
}
