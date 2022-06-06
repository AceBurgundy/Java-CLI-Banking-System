package Machine;

import java.io.IOException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Tools {

    private final static Scanner input = new Scanner(System.in);
    private static PreparedStatement inSql = null;
    private static Connection connect = null;

    public static void showMenu() {
        System.out.println("\n\tMENU");
        System.out.println("\t*******************************");
        System.out.println("\t*                             *");
        System.out.println("\t*       1. Create Account     *");
        System.out.println("\t*       2. Check Balance      *");
        System.out.println("\t*       3. Deposit            *");
        System.out.println("\t*       4. Withdraw           *");
        System.out.println("\t*       5. Exit               *");
        System.out.println("\t*                             *");
        System.out.println("\t*******************************");

    }

    public static void createAccount() {

        Helpers.clear();
        showMenu();
        System.out.println("\n\t*******************************");
        System.out.println("\t**      Create Account      ***");
        System.out.println("\t*******************************");
        System.out.println("\n\tEnter Details: ");
        System.out.println("\t*******************************");
        System.out.println("\tFirst Name: ");
        String dbfirstName = Helpers.inputString();
        System.out.println("\t*******************************");
        System.out.println("\tLast Name: ");
        String dblastName = Helpers.inputString();
        System.out.println("\t*******************************");
        System.out.println("\tPassword: ");
        String dbpassword = Helpers.inputString();
        System.out.println("\t*******************************");
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

            Helpers.prompt();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("\tAccount Not Saved\n");
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
        showMenu();

        System.out.println("\n\tAccount number: ");
        int search = Helpers.inputInt(9999, 999);

        System.out.println("\n\tPassword: ");
        String password = Helpers.hidePassword();

        try {
            String uri = "jdbc:sqlite:user.db";
            connect = DriverManager.getConnection(uri);

            inSql = connect.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND password = ?");
            inSql.setInt(1, search);
            inSql.setString(2, Helpers.encrypt(password));

            ResultSet sqlOutput = inSql.executeQuery();
            float balance = sqlOutput.getInt("balance");

            System.out
                    .println("\n\tYour still have P" + balance + "\n");
            Helpers.prompt();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void deposit() throws IOException {

        Helpers.clear();
        showMenu();

        System.out.println("\n\tAccount number: ");
        int search = Helpers.inputInt(9999, 999);

        System.out.println("\n\tPassword: ");
        String password = Helpers.hidePassword();

        System.out.println("\n\tDeposit Amount: ");
        float deposit = Helpers.inputFloat(2000000000);

        try {
            String uri = "jdbc:sqlite:user.db";
            connect = DriverManager.getConnection(uri);

            inSql = connect.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND password = ?;");
            inSql.setInt(1, search);
            inSql.setString(2, Helpers.encrypt(password));

            ResultSet sqlOutput = inSql.executeQuery();
            float balance = sqlOutput.getInt("balance");

            float newBalance = deposit += balance;

            PreparedStatement inSql = connect.prepareStatement(
                    "UPDATE accounts SET balance = ? WHERE account_number = ? AND password = ?");
            inSql.setFloat(1, newBalance);
            inSql.setInt(2, search);
            inSql.setString(3, Helpers.encrypt(password));
            inSql.executeUpdate();

            System.out
                    .println("\n\tHi " + sqlOutput.getString("first_name") + " " + sqlOutput.getString("last_name")
                            + " your new balance is P" + newBalance + "\n");

            Helpers.prompt();

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

        System.out.println("\n\tAccount number: ");
        int search = Helpers.inputInt(9999, 999);

        System.out.println("\n\tPassword: ");
        String password = Helpers.hidePassword();

        System.out.println("\n\tWithdraw Amount: ");
        float withdraw = Helpers.inputFloat(2000000000);

        try {
            String uri = "jdbc:sqlite:user.db";
            connect = DriverManager.getConnection(uri);

            inSql = connect.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND password = ?");
            inSql.setInt(1, search);
            inSql.setString(2, Helpers.encrypt(password));

            ResultSet sqlOutput = inSql.executeQuery();
            float balance = sqlOutput.getInt("balance");

            while (withdraw > balance) {
                System.out.println("\tWithdraw amount exceeded");
                withdraw = input.nextFloat();
            }

            float newBalance = balance - withdraw;

            PreparedStatement inSql = connect.prepareStatement(
                    "UPDATE accounts SET balance = ? WHERE account_number = ? AND password = ?");
            inSql.setFloat(1, newBalance);
            inSql.setInt(2, search);
            inSql.setString(3, Helpers.encrypt(password));
            inSql.executeUpdate();

            System.out
                    .println("\n\tHi " + sqlOutput.getString("first_name") + " " + sqlOutput.getString("last_name")
                            + " your new balance is P" + newBalance + "\n");

            Helpers.prompt();

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
}