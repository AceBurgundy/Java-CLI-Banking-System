import java.io.IOException;
import java.util.Scanner;
import Machine.Helpers;
import Machine.Tools;
import javax.swing.*;

public class Atm {

    static Scanner input = new Scanner(System.in);

    public static void showOption() throws IOException {

        int option = 0;
        boolean another = false;

        Tools.showMenu();
        option = 0;

        System.out.print("\n\tOption: ");
        option = Helpers.inputInt(5, 1);

        switch (option) {
            case 2:
                Tools.inquireBalance();
                break;
            case 3:
                Tools.deposit();
                break;
            case 4:
                Tools.withdraw();
                break;
            case 5:
                Tools.program("Thank you have a nice day :)");
                break;
        }

        another = Helpers.getBoolean("Do another transaction?");

        if (another == true) {
            showOption();
            another = false;
            option = 0;
        } else {
            Tools.program("Thank you for using the program.");
        }
    }

    public static void run() throws IOException {

        int option = 0;

        Helpers.clear();

        System.out.println("\n\t*******************************");
        System.out.println("\t***         Welcome         ***");
        System.out.println("\t*******************************");
        System.out.println("\t1. Login");
        System.out.println("\t2. Register");
        System.out.println("\t*******************************");

        option = Helpers.inputInt(2, 1);

        switch (option) {
            case 1:
                if (Tools.Login()) {
                    showOption();
                    break;
                } else {
                    Tools.program("Account Number or Password doesn't have any matches.");
                }
            case 2:
                if (Tools.Register()) {
                    Tools.Login();
                } else {
                    Tools.program("Register Failed");
                }
        }
    }

    public static void main(String[] args) throws IOException {

        run();

    }
}
