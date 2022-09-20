package Machine;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int accountNumber;
    private float accountBalance;

    User(int id, String firstName, String lastName, int accountNumber, float accountBalance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public float getBalance() {
        return this.accountBalance;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public int getId() {
        return this.id;
    }

    public void setBalance(float balance) {
        this.accountBalance = balance;
    }
}
