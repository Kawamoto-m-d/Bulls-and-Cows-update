package bullscows;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;


public class Main {
    public static void calculator(int bulls, int cows){
        if(bulls == 0 && cows == 0){
            System.out.println("Guess: None.");
        }

        if (bulls == 1 && cows == 0) {
            System.out.printf("Grade: %d bull%n", bulls);
        }

        if (bulls > 1 && cows == 0) {
            System.out.printf("Grade: %d bulls%n", bulls);
        }

        if (bulls > 1 && cows == 1){
            System.out.printf("Grade: %d bulls and %d cow%n", bulls, cows);
        }

        if (bulls == 1 && cows == 1) {
            System.out.printf("Grade: %d bull and %d cow%n", bulls, cows);
        }

        if (bulls > 1 && cows > 1) {
            System.out.printf("Grade: %d bulls and %d cows%n", bulls, cows);
        }

        if (cows == 1 && bulls == 0){
            System.out.printf("Grade: %d cow%n", cows);
        }

        if (cows > 1 && bulls == 0){
            System.out.printf("Grade: %d cows%n", cows);
        }
        if (cows > 1 && bulls == 1){
            System.out.printf("Grade: %d bull and %d cows%n", bulls, cows);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random random = new Random();

        int charordigit;


        Random r = new Random();

        int bulls = 0;
        int cows = 0;
        int turncounter = 0;



        String guess;
        char[] guessArray;

        System.out.println("Input the length of the secret code:");
        String inputStr = scan.nextLine();
        if(inputStr.equals("0")){
            System.out.println("error");
            System.exit(0);
        }
        try{
            if(!inputStr.matches("\\d+")){
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e){
            System.out.printf("Error: \"%s\" isn't a valid number.", inputStr);
            System.exit(0);
        }
        int input = Integer.parseInt(inputStr);
        char[] mynewrandom = new char[input];

        System.out.println("Input the number of possible symbols in the code:");
        int symbols = scan.nextInt();
        String temp = "*".repeat(input);

        try{
          if(symbols > 36){
               throw new IllegalArgumentException();
            }
          if(input > symbols){
               throw new ArithmeticException();
           }
        } catch (ArithmeticException e){
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n", input, symbols);
            System.exit(0);
        } catch (IllegalArgumentException e){
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }

        // length > 10, else restart
        while (input > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", input);
            input = scan.nextInt();
            mynewrandom = new char[input];
        }

        int numberlength = 0;
        int stringlength = symbols-10;
        char startchar = 'a';
        char endchar = 'a';

        // code generation
        for (int i = 0; i < input; i++) {
            charordigit = (int) Math.round(Math.random());

            //randomize chars and digits
            if(symbols > 10){
                if(charordigit == 1){
                    mynewrandom[i] = (char)(r.nextInt(stringlength) + startchar); // Generate a digit from
                } else {
                    mynewrandom[i] = (char) (random.nextInt(9) + 0 + '0'); // Generate a digit from 0 to 9
                }
                }
            else {
                mynewrandom[i] = (char) (random.nextInt(input) + 0 + '0'); // Generate a digit from 0 to 9
            }
            for (int j = 0; j < i; j++) {
                if (mynewrandom[i] == mynewrandom[j]) {
                    i = 0; // Reset the outer loop to check from the beginning
                    break;
                }
            }
        }

        //start prints

        if(symbols <= 10){
            numberlength = symbols;
            System.out.printf("The secret is prepared: %s (0-%d).%n", temp, (numberlength-1));
        }

        if(symbols > 10){
            for(int i = 0; stringlength > i; i++){
                endchar++;
            }
            System.out.printf("The secret is prepared: %s (0-9, a-%c).%n", temp, endchar-1);
        }


        System.out.println("Okay, let's start a game!");

        while(bulls != input){
            turncounter++;
            System.out.printf("Turn %d:%n", turncounter);
            guess = scan.next();
            guessArray = ("" + guess).toCharArray();

            // reset bulls and cows
            bulls = 0;
            cows = 0;
            // check for bulls and cows
            for (int i = 0; i < mynewrandom.length; i++) {
                for (int j = 0; j < guessArray.length; j++) {
                    if (mynewrandom[i] == guessArray[j]) {
                        if (mynewrandom[i] == guessArray[i]) {
                            bulls++;
                            break;
                        } else {
                            cows++;
                            break;
                        }
                    }
                }
            }
            calculator(bulls, cows);
        }

        // check if won after loop
        if(bulls == input){
            System.out.printf("Grade: %d bulls%n", bulls);
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }
}
