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
}