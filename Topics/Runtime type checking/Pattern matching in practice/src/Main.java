import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Object msg = scanner.nextLine();    

        // Write your code here
        if (msg instanceof String str && str.length() > 0) {
            System.out.printf("The variable is not empty, the length is %d", str.length());
        }
    }
}