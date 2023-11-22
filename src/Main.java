import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Character, Integer> ROMAN_VALUES = new HashMap<>();

    static {
        ROMAN_VALUES.put('I', 1);
        ROMAN_VALUES.put('V', 5);
        ROMAN_VALUES.put('X', 10);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the expression: ");
            String expression = scanner.nextLine();

            System.out.println(calculate(expression));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Object calculate(String input) {
        String[] parts = input.replaceAll(" ", "").split("[+\\-*/]");

        String num1Str = parts[0];
        String num2Str = parts[1];

        boolean isRoman = isRoman(num1Str) && isRoman(num2Str);

        int num1 = isRoman ? romanToArabic(num1Str) : Integer.parseInt(num1Str);
        int num2 = isRoman ? romanToArabic(num2Str) : Integer.parseInt(num2Str);

        validateInput(num1, num2);

        int result;
        if (input.contains("+")) {
            result = num1 + num2;
        } else if (input.contains("-")) {
            result = num1 - num2;
        } else if (input.contains("*")) {
            result = num1 * num2;
        } else if (input.contains("/")) {
            result = num1 / num2;
        } else {
            throw new IllegalArgumentException();
        }

        if (isRoman) {
            return arabicToRoman(result);
        }
        return result;
    }

    private static void validateInput(int num1, int num2) {
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Numbers must be between 1 and 10 inclusive");
        }
    }

    private static boolean isRoman(String str) {
        return str.matches("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = ROMAN_VALUES.get(roman.charAt(i));

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }

            prevValue = value;
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        String[] romanNumbers = {"NULL", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] romanNumbersDec = {"NULL", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX","XC", "C"};

        if(number <= 0){
            throw new RuntimeException("Roman numerals cannot represent zero or negative numbers");
        } else if (number < 10) {
            return romanNumbers[number];
        } else if (number == 10) {
            return romanNumbers[(number)];
        }else if (number % 10 == 0) {
            return romanNumbersDec[(number / 10)];
        }

        return romanNumbersDec[(number / 10)] + romanNumbers[number % 10];
    }
}

