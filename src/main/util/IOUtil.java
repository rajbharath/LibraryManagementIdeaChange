package main.util;

import java.util.Scanner;

public class IOUtil {
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static String readString() {
        String s = scanner.next();

        while (s.trim().length() == 0) {
            print("Input should not be empty");
            s = scanner.next();
        }
        return s;
    }

    public static int readInt() {
        int number = 0;
        boolean isNumber = false;
        while (!isNumber) {
            try {
                isNumber = true;
                number = Integer.parseInt(readString());
            } catch (NumberFormatException e) {
                println("Input should be numeric");
                isNumber = false;
            }
        }
        return number;
    }

    public static String readLine() {
        return readString();
    }

}
