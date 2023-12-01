package easy.tuto.easycalculator;

import java.lang.Math;
public class MathFunction {
    public static String power(double base, double exponent) {
        if (base == 0 && exponent == 0) {
            return "Undefined";
        } else {
            double a = Math.pow(base, exponent);

            return String.valueOf(a);
        }
    }
    public static String decimalToBinary(String decimal) {
        try {
            // Parse the decimal string to a double
            double decimalValue = Double.parseDouble(decimal);

            // Check if the decimal value is an integer
            if (Math.floor(decimalValue) != decimalValue) {
                throw new IllegalArgumentException("Please enter a decimal number that is an integer.");
            }

            // Ensure the number is non-negative
            int decimalNumber = (int) decimalValue;
            if (decimalNumber < 0) {
                return "Please enter a non-negative decimal number.";
            }

            // Binary representation as a StringBuilder
            StringBuilder binaryStringBuilder = new StringBuilder();

            // Special case for 0
            if (decimalNumber == 0) {
                binaryStringBuilder.append("0");
            } else {
                // Convert decimal to binary
                while (decimalNumber > 0) {
                    int remainder = decimalNumber % 2;
                    binaryStringBuilder.insert(0, remainder); // Insert at the beginning
                    decimalNumber /= 2;
                }
            }

            return binaryStringBuilder.toString();
        } catch (NumberFormatException e) {
            return "Invalid decimal format.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }


    public static String decimalToOctal(String decimal) {
        try {
            // Parse the decimal string to a double
            double decimalValue = Double.parseDouble(decimal);

            // Check if the decimal value is an integer
            if (Math.floor(decimalValue) != decimalValue) {
                throw new IllegalArgumentException("Please enter a decimal number that is an integer.");
            }

            // Ensure the number is non-negative
            int decimalNumber = (int) decimalValue;
            if (decimalNumber < 0) {
                return "Please enter a non-negative decimal number.";
            }

            // Octal representation as a StringBuilder
            StringBuilder octalStringBuilder = new StringBuilder();

            // Special case for 0
            if (decimalNumber == 0) {
                octalStringBuilder.append("0");
            } else {
                // Convert decimal to octal
                while (decimalNumber > 0) {
                    int remainder = decimalNumber % 8;
                    octalStringBuilder.insert(0, remainder); // Insert at the beginning
                    decimalNumber /= 8;
                }
            }

            return octalStringBuilder.toString();
        } catch (NumberFormatException e) {
            return "Invalid decimal format.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }


    public static String decimalToHexadecimal(String decimal) {
        try {
            // Parse the decimal string to a double
            double decimalValue = Double.parseDouble(decimal);

            // Check if the decimal value is an integer
            if (Math.floor(decimalValue) != decimalValue) {
                throw new IllegalArgumentException("Please enter a decimal number that is an integer.");
            }

            // Ensure the number is non-negative
            int decimalNumber = (int) decimalValue;
            if (decimalNumber < 0) {
                return "Please enter a non-negative decimal number.";
            }

            // Hexadecimal representation as a StringBuilder
            StringBuilder hexadecimalStringBuilder = new StringBuilder();

            // Special case for 0
            if (decimalNumber == 0) {
                hexadecimalStringBuilder.append("0");
            } else {
                // Convert decimal to hexadecimal
                while (decimalNumber > 0) {
                    int remainder = decimalNumber % 16;
                    hexadecimalStringBuilder.insert(0, getHexChar(remainder)); // Insert at the beginning
                    decimalNumber /= 16;
                }
            }

            return hexadecimalStringBuilder.toString();
        } catch (NumberFormatException e) {
            return "Invalid decimal format.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // Helper function to get the hexadecimal character for a given remainder
    private static char getHexChar(int remainder) {
        if (remainder < 10) {
            return (char) ('0' + remainder);
        } else {
            return (char) ('A' + remainder - 10);
        }
    }

    public static String convertToASCII(String inputString) {
        // Check if the input string is not empty
        if (inputString == null || inputString.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        StringBuilder asciiValues = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            int asciiValue = (int) currentChar;

            // Append each ASCII value to the result string
            asciiValues.append(asciiValue);

            // If it's not the last character, add a separator (e.g., comma)
            if (i < inputString.length() - 1) {
                asciiValues.append(", ");
            }
        }

        return asciiValues.toString();
    }


}