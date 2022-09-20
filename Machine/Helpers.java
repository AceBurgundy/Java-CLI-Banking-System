package Machine;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;
import javax.swing.*;

public class Helpers {
   static Scanner input = new Scanner(System.in);
   static JFrame dialog = new JFrame();

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

   public static int inputInt(int upperLimit, int lowerLimit) {
      int value;

      do {
         try {
            value = Integer.parseInt((JOptionPane.showInputDialog(dialog, "\n\tOption: ")));
         } catch (InputMismatchException e) {
            System.out.println("\tPlease enter a valid number.");
            value = Integer.parseInt((JOptionPane.showInputDialog(dialog, "\n\tOption: ")));
            continue;
         }

         while (value > upperLimit || value < lowerLimit) {
            Helpers.clear();
            Tools.showMenu();
            System.out.println("\n\tShould be >= " + lowerLimit + " and <= " + upperLimit);
            System.out.print("        ");
            value = Integer.parseInt((JOptionPane.showInputDialog(dialog, "\n\tOption: ")));
         }
         return value;

      } while (true);
   }

   public static boolean getBoolean(String message) {
      boolean option = false;

      if (message.equals("")) {
         message = "Yes or No?";
      } else {
         int choice = JOptionPane.showConfirmDialog(dialog, message, "Choose", JOptionPane.YES_NO_OPTION);

         if (choice == 1) {
            option = false;
         } else {
            option = true;
         }
      }

      return option;
   }

   public static float inputFloat(float limit) {
      float value;

      do {
         try {
            value = Float.parseFloat((JOptionPane.showInputDialog(dialog, "\n\tOption: ")));
         } catch (InputMismatchException e) {
            System.out.println("\tPlease enter a valid number.");
            value = Integer.parseInt((JOptionPane.showInputDialog(dialog, "\n\tOption: ")));
            continue;
         }

         while (value > limit || value < 0) {
            value = Float.parseFloat((JOptionPane.showInputDialog(dialog, "\n\tOption: ")));
            System.out.println("\tEnter the exact number of input.");
         }
         return value;

      } while (true);
   }

   public static String getString(String message) {
      String word = "";

      if (message.equals("")) {
         message = "Input";
      } else {
         do {
            try {
               word = JOptionPane.showInputDialog(dialog, message);

               if (word.equals("")) {
                  word = JOptionPane.showInputDialog(dialog, message);
               } else {
                  break;
               }
            } catch (InputMismatchException e) {
               System.out.println("Mismatch");
               word = JOptionPane.showInputDialog(dialog, message);
            }
         } while (true);
      }
      return word;
   }

   public static void clear() {
      try {
         new ProcessBuilder("cmd", "/c", "cls", "clear").inheritIO().start().waitFor();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}