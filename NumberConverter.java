import java.util.InputMismatchException;
import java.util.Scanner;

public class NumberConverter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice;

        do {
            try {
                System.out.println("------ Conversion Menu -----");
                System.out.println("1: Dec to Bin");
                System.out.println("2: Dec to Hex");
                System.out.println("3: Bin to Dec");
                System.out.println("4: Bin to Hex");
                System.out.println("5: Hex to Bin");
                System.out.println("6: Hex to Dec");
                System.out.println("0: Exit");
                System.out.print("Enter your choice: ");
                choice = in.nextInt();

                switch (choice) {
                    case 1: decToBin(); break;
                    case 2: decToHex(); break;
                    case 3: binToDec(); break;
                    case 4: binToHex(); break;
                    case 5: hexToBin(); break;
                    case 6: hexToDec(); break;
                    case 0: System.out.println("Exiting program. Goodbye!"); break;
                    default: System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a valid integer.");
                in.nextLine(); 
                choice = -1;
            }

        } while (choice != 0);

        in.close();
    }

    private static void decToBin() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter decimal: ");
            int dec = in.nextInt();

            DecConverter conv = new DecConverter();
            String binResult = conv.decToBin(dec);

            System.out.println("Binary: " + binResult);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter a valid integer.");
        }
    }

    private static void decToHex() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter decimal: ");
            int dec = in.nextInt();

            DecConverter conv = new DecConverter();
            String hexResult = conv.decToHex(dec);

            System.out.println("Hexadecimal: " + hexResult);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter a valid integer.");
        }
    }

    private static void binToDec() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter binary: ");
            String bin = in.next();

            BinConverter conv = new BinConverter();
            int decResult = conv.binToDec(bin);

            System.out.println("Decimal: " + decResult);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a valid binary number.");
        }
    }

    private static void binToHex() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter binary: ");
            String bin = in.next();

            BinConverter conv = new BinConverter();
            String hexResult = conv.binToHex(bin);

            System.out.println("Hexadecimal: " + hexResult);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a valid binary number.");
        }
    }

    private static void hexToBin() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter hexadecimal: ");
            String hex = in.next();

            HexConverter conv = new HexConverter();
            String binResult = conv.hexToBin(hex);

            System.out.println("Binary: " + binResult);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void hexToDec() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter hexadecimal: ");
            String hex = in.next();

            HexConverter conv = new HexConverter();
            int decResult = conv.hexToDec(hex);

            System.out.println("Decimal: " + decResult);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

class DecConverter {
    public String decToBin(int dec) {
        if (dec == 0) return "0";

        StringBuilder bin = new StringBuilder();

        while (dec > 0) {
            bin.insert(0, dec % 2);
            dec /= 2;
        }

        return bin.toString();
    }

    public String decToHex(int dec) {
        if (dec == 0) return "0";

        StringBuilder hex = new StringBuilder();

        while (dec > 0) {
            hex.insert(0, getHexDigit(dec % 16));
            dec /= 16;
        }

        return hex.toString();
    }

    private char getHexDigit(int value) {
        if (value >= 0 && value <= 9) return (char) ('0' + value);
        else return (char) ('A' + (value - 10));
    }
}

class BinConverter {
    public int binToDec(String bin) {
        int dec = 0;
        int power = 0;

        for (int i = bin.length() - 1; i >= 0; i--) {
            char digit = bin.charAt(i);
            if (digit == '1') dec += Math.pow(2, power);
            power++;
        }

        return dec;
    }

    public String binToHex(String bin) {
        while (bin.length() % 4 != 0) bin = "0" + bin;

        StringBuilder hex = new StringBuilder();

        for (int i = 0; i < bin.length(); i += 4) {
            String nibble = bin.substring(i, i + 4);
            int decValue = Integer.parseInt(nibble, 2);
            hex.append(getHexDigit(decValue));
        }

        return hex.toString();
    }

    private char getHexDigit(int value) {
        if (value >= 0 && value <= 9) return (char) ('0' + value);
        else return (char) ('A' + (value - 10));
    }
}

class HexConverter {
    public String hexToBin(String hex) {
        StringBuilder bin = new StringBuilder();

        for (int i = 0; i < hex.length(); i++) {
            char hexDigit = hex.charAt(i);
            int decValue = getDecimalValue(hexDigit);
            String binDigit = padBinary(Integer.toBinaryString(decValue));
            bin.append(binDigit);
        }

        return bin.toString();
    }

    public int hexToDec(String hex) {
        int dec = 0;

        for (int i = hex.length() - 1; i >= 0; i--) {
            char hexDigit = hex.charAt(i);
            int decValue = getDecimalValue(hexDigit);
            dec += decValue * Math.pow(16, hex.length() - 1 - i);
        }

        return dec;
    }

    private int getDecimalValue(char hexDigit) {
        if (hexDigit >= '0' && hexDigit <= '9') return hexDigit - '0';
        else if (hexDigit >= 'A' && hexDigit <= 'F') return hexDigit - 'A' + 10;
        else if (hexDigit >= 'a' && hexDigit <= 'f') return hexDigit - 'a' + 10;
        else throw new IllegalArgumentException("Invalid hex digit: " + hexDigit);
    }

    private String padBinary(String binDigit) {
        while (binDigit.length() < 4) binDigit = "0" + binDigit;
        return binDigit;
    }
}
