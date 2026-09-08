import java.util.Scanner;

public class main {

    static {

    }



    static void main(String[] args) {
        Scanner mainSc = new Scanner(System.in);
        String input = new String();
        while(true){
            System.out.print(">>>");
            input = mainSc.nextLine();
            if (input.isBlank()) continue;
            if (input.equals("quit")) break;
            switch (input){
                case "new" -> System.out.print("");
                case "new domain" -> new Domain();
            }
        }
    }
}
