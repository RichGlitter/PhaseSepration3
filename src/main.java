import java.util.Scanner;

public class main {

    static {
        //TODO: read data and build protein.
        System.out.print("Load data...\n");

    }


    public void check() {
        //TODO:print the data
    }

    static void main(String[] args) {
        Scanner mainSc = new Scanner(System.in);
        String input = new String();
        Domain domain = new Domain();
        while (true) {
            System.out.print(">>>");
            input = mainSc.nextLine();
            if (input.isBlank()) continue;
            if (input.equals("quit")) break;
            switch (input) {
                case "new" -> System.out.print("");
                case "new domain" -> domain.refreshDomain(null, null, Integer.MAX_VALUE, Integer.MAX_VALUE, true);
                case "new protein" -> new Protein().setProtein();
                case "find" -> {
                    try {
                        System.out.print(Domain.readData(mainSc.nextInt(), mainSc.nextInt()));
                    } catch (Exception e) {
                    }
                }
                case "print" ->{

                }
            }
        }
    }
}
