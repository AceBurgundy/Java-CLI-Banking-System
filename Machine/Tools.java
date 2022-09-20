package Machine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Tools {

    static PreparedStatement inSql = null;
    static Connection connect = null;

    static User currentUser;

    public static void showMenu() {
        System.out.println("\n\tMENU");
        System.out.println("\t*******************************");
        System.out.println("\t*                             *");
        System.out.println("\t*       2. Check Balance      *");
        System.out.println("\t*       3. Deposit            *");
        System.out.println("\t*       4. Withdraw           *");
        System.out.println("\t*       5. Exit               *");
        System.out.println("\t*                             *");
        System.out.println("\t*******************************");

    }

    public static boolean Login() {
        Helpers.clear();
        System.out.println("\n\t*******************************");
        System.out.println("\t**          Login           ***");
        System.out.println("\t*******************************");
        System.out.println("\n\tEnter Details: ");
        System.out.println("\t*******************************");
        System.out.println("\tAccount Number: ");
        String dbaccountNumber = Helpers.getString("Account Number");
        System.out.println("\n\t*******************************");
        System.out.println("\tPassword: ");
        String dbpassword = Helpers.getString("Password");
        System.out.println("\n\t*******************************");

        try {
            String uri = "jdbc:sqlite:user.db";
            connect = DriverManager.getConnection(uri);

            inSql = connect.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND password = ?");
            inSql.setString(1, dbaccountNumber);
            inSql.setString(2, Helpers.encrypt(dbpassword));

            Helpers.clear();

            ResultSet sqlOutput = inSql.executeQuery();

            currentUser = new User(
                    sqlOutput.getInt("id"),
                    sqlOutput.getString("first_name"),
                    sqlOutput.getString("last_name"),
                    sqlOutput.getInt("account_number"),
                    sqlOutput.getFloat("balance"));

            System.out.println("\n\tHello, " + currentUser.getFirstName());

            return true;

        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static boolean Register() {

        Helpers.clear();
        System.out.println("\n\t*******************************");
        System.out.println("\t**      Create Account      ***");
        System.out.println("\t*******************************");
        System.out.println("\n\tEnter Details: ");
        System.out.println("\t*******************************");
        System.out.println("\tFirst Name: ");
        String dbfirstName = Helpers.getString("First Name");
        System.out.println("\n\t*******************************");
        System.out.println("\tLast Name: ");
        String dblastName = Helpers.getString("Last Name");
        System.out.println("\n\t*******************************");
        System.out.println("\tPassword: ");
        String dbpassword = Helpers.getString("Password");
        System.out.println("\n\t*******************************");
        System.out.println("\tInitial Deposit: ");
        int dbBalance = Helpers.inputInt(2000000000, 0);

        try {
            String uri = "jdbc:sqlite:user.db";
            connect = DriverManager.getConnection(uri);
            int accountNumber = Helpers.generateAccountNumber();

            inSql = connect.prepareStatement(
                    "INSERT INTO accounts(account_number, first_name, last_name, password, balance) VALUES(?,?,?,?,?)");
            inSql.setInt(1, accountNumber);
            inSql.setString(2, dbfirstName);
            inSql.setString(3, dblastName);
            inSql.setString(4, Helpers.encrypt(dbpassword));
            inSql.setInt(5, dbBalance);
            inSql.executeUpdate();

            Helpers.clear();
            System.out.println("\n\n\t*******************************");
            System.out.println("\t** Acount Saved Sucessfully ***");
            System.out.println("\t*******************************");
            System.out.println("\n\tYour account details are; ");
            System.out.println("\tAccount Number: " + accountNumber);
            System.out.println("\tFirst Name: " + dbfirstName);
            System.out.println("\tLast Name: " + dblastName);
            System.out.println("\tInitial Deposit: " + dbBalance);
            System.out.println("\n\tFor safety reason/s your password will not be shown.");
            System.out.println(
                    "\n\tDont forget to memorize your accout number as you will be using it to access your account.\n");

            return true;

        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static void inquireBalance() throws IOException {

        Helpers.clear();

        try {
            float balance = currentUser.getBalance();

            System.out
                    .println("\n\tYour still have P" + balance + "\n");

        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void deposit() throws IOException {

        Helpers.clear();
        showMenu();

        System.out
                .println("\n\tBalance: " + currentUser.getBalance());

        System.out.print("\n\tDeposit Amount: ");
        float deposit = Helpers.inputFloat(2000000000);

        try {
            String uri = "jdbc:sqlite:user.db";
            connect = DriverManager.getConnection(uri);

            float balance = currentUser.getBalance();

            float newBalance = deposit += balance;

            PreparedStatement inSql = connect.prepareStatement(
                    "UPDATE accounts SET balance = ? WHERE id = ?");
            inSql.setFloat(1, newBalance);
            inSql.setInt(2, currentUser.getId());
            inSql.executeUpdate();

            currentUser.setBalance(newBalance);

            System.out
                    .println("\n\tYour new balance is now P" + newBalance + "\n");

        } catch (SQLException e) {
            System.out.println("\n\tSql Error your account may not have been registered yet\n");
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void withdraw() throws IOException {

        Helpers.clear();
        showMenu();

        try {
            String uri = "jdbc:sqlite:user.db";
            connect = DriverManager.getConnection(uri);

            float balance = currentUser.getBalance();

            System.out.println("\n\tBalance: " + balance);

            System.out.print("\n\tWithdraw Amount: ");
            float withdraw = Helpers.inputFloat(balance);

            float newBalance = balance - withdraw;

            PreparedStatement inSql = connect.prepareStatement(
                    "UPDATE accounts SET balance = ? WHERE id = ?");
            inSql.setFloat(1, newBalance);
            inSql.setInt(2, currentUser.getId());
            inSql.executeUpdate();

            currentUser.setBalance(newBalance);

            System.out
                    .println("\n\tYour new balance is now P" + newBalance + "\n");

        } catch (SQLException e) {
            System.out.println("\tSql Error your account may not have been registered yet\n");
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void program(String message) {
        Helpers.clear();

        if (message.equals("")) {
            System.out.println("\n\tThank you and have a good day.\n");
        } else {
            System.out.println("\n\t" + message + "\n");
        }
        System.exit(0);
    }
}