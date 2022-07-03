package Machine;

import java.util.Scanner;
import java.io.Console;
import java.util.InputMismatchException;
import java.util.Random;

public class Helpers {
   private final static Console console = System.console();
   static Scanner input = new Scanner(System.in);

   public static String encrypt(String message) {
      String encryptedMessage = "";
      char ch;

      for (int i = 0; i < message.length(); ++i) {
         ch = message.charAt(i);
         if (ch >= 'a' && ch <= 'z') {
            ch = (char) (ch + 12);

            if (ch > 'z') {
               ch = (char) (ch - 'z' + 'a' - 1);
            }

            encryptedMessage += ch;
         } else if (ch >= 'A' && ch <= 'Z') {
            ch = (char) (ch + 12);

            if (ch > 'Z') {
               ch = (char) (ch - 'Z' + 'A' - 1);
            }

            encryptedMessage += ch;
         } else {
            encryptedMessage += ch;
         }
      }
      return encryptedMessage;
   }

   public static String decrypt(String message) {
      String decryptedMessage = "";
      char ch;

      for (int i = 0; i < message.length(); ++i) {
         ch = message.charAt(i);

         if (ch >= 'a' && ch <= 'z') {
            ch = (char) (ch - 12);

            if (ch < 'a') {
               ch = (char) (ch + 'z' - 'a' + 1);
            }

            decryptedMessage += ch;
         } else if (ch >= 'A' && ch <= 'Z') {
            ch = (char) (ch - 12);

            if (ch < 'A') {
               ch = (char) (ch + 'Z' - 'A' + 1);
            }

            decryptedMessage += ch;
         } else {
            decryptedMessage += ch;
         }
      }
      return decryptedMessage;
   }

   static Random rand = new Random();

   public static int generateAccountNumber() {
      int accumulator = 1 + rand.nextInt(9); // ensures that the 16th digit isn't 0

      for (int i = 0; i < 3; i++) {
         accumulator *= 10;
         accumulator += rand.nextInt(10);
      }
      return accumulator;
   }

   public static String inputString() {
      String value;

      do {
         System.out.print("        ");

         try {
            value = input.next();
         } catch (InputMismatchException e) {
            System.out.println("\tPlease enter a valid string.");
            value = input.nextLine();
            continue;
         }
         return value;

      } while (true);
   }

   public static int inputInt(int upperLimit, int lowerLimit) {
      int value;

      do {
         System.out.print("        ");

         try {
            value = input.nextInt();
         } catch (InputMismatchException e) {
            System.out.println("\tPlease enter a valid number.");
            value = input.nextInt();
            continue;
         }

         while (value > upperLimit || value < lowerLimit) {
            Helpers.clear();
            Tools.showMenu();
            System.out.println("\n\tShould be > " + lowerLimit + " and < " + upperLimit);
            System.out.print("        ");
            value = input.nextInt();
         }
         return value;

      } while (true);
   }

   public static float inputFloat(int limit) {
      float value;

      do {
         System.out.print("        ");

         try {
            value = input.nextFloat();
         } catch (InputMismatchException e) {
            System.out.println("\tPlease enter a valid number.");
            value = input.nextFloat();
            continue;
         }

         while (value > limit || value < 0) {
            value = input.nextFloat();
            System.out.println("\tEnter the exact number of input.");
         }
         return value;

      } while (true);
   }

   public static void clear() {
      try {
         new ProcessBuilder("cmd", "/c", "cls", "clear").inheritIO().start().waitFor();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static String hidePassword() {
      char[] password = console.readPassword("        Hidden: ");
      String str = new String(password);
      return str;
   }

   public static void prompt() {

      System.out.println("\tDo another transaction? press y or n");
      char choice = input.next().charAt(0);

      while (choice != 'y' || choice != 'n') {

         if (choice == 'y') {
            break;
         }
         if (choice == 'n') {
            System.out.println("\tThank you and have a good day.\n");
            System.exit(0);
         }
      }
   }
}