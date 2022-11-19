// Create a method that counts a strings number of consonants & vowels

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        scanner.close();

        System.out.println("Vowels: " + countVowels(str));
        System.out.println("Consonants: " + countConsonants(str));
    }

    public static int countVowels(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isVowel(str.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public static int countConsonants(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }

        return lettersCount(str) - countVowels(str);
    }

    public static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y';
    }

    public static int lettersCount(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}