import java.io.IOException;
import java.util.Scanner;

import Machine.Helpers;
import Machine.Tools;

public class Atm {

    static Scanner input = new Scanner(System.in);
    static boolean stop = false;

    public static void main(String[] args) throws IOException {

        while (!stop) {
            Helpers.clear();
            Tools.showMenu();

            System.out.println("\n\tPlease choose an option: ");
            int option = Helpers.inputInt(5, 1);

            switch (option) {
                case 1:
                    Tools.createAccount();
                case 2:
                    Tools.inquireBalance();
                case 3:
                    Tools.deposit();
                case 4:
                    Tools.withdraw();
                case 5:
                    Tools.program(stop);
            }
        }
    }
}