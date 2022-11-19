// Print for numbers from 1 to 100:
//  •"FizzBuzz" if i is divisible by 3 and 5.
//  •"Fizz" if i is divisible by 3.
//  •"Buzz" if i is divisible by 5.
//  • Number (as a string) if none of the above conditions are true.

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }
}