import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Protein {
    int size;
    int[] domains;
    int id;
    String name;
    private static String filePathDomain = "src/data/domainConnectRate";
    private static String filePathProtein = "src/data/proteinDomainId";

    static int numberOfProtein;
    //TODO:static block to load the protein number.

    //TODO: protein is just arrange the domains.

    //TODO: new protein function.

    public Protein(){

    }
    public Protein(String name, int size, int[] domains){
        this.name = name;
        this.size = size;

        try {
            for (int i : domains){
                if (Domain.toString(i) == null) {
                    System.out.print("setup failed.\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.domains = domains.clone();
    }

    public void setProtein(){
        Scanner sc = new Scanner(System.in);
        try (FileWriter writerProtein = new FileWriter(filePathProtein, true)) {
            boolean flag = false;
            StringBuilder proteinMessage = new StringBuilder();
            Domain domainCache = new Domain();
            while(!flag) {
                try {
                    proteinMessage.setLength(0);
                    numberOfProtein++;
                    this.id = numberOfProtein;
                    proteinMessage.append(id).append("\t");

                    //name
                    System.out.print("The name of Protein: ");
                    this.name = sc.nextLine();
                    proteinMessage.append(name).append("\t");

                    //number
                    System.out.print("The number of Domains: ");
                    this.size = sc.nextInt();
                    proteinMessage.append(size).append("\t");

                    //fill domains
                    this.domains = new int[size];
                    for (int i = 0; i<size; i++){
                        domainCache.refreshDomain(null, name,id,Integer.MAX_VALUE,true);
                        this.domains[i] = domainCache.getDomainId();
                    }

                    for(int i : domains){
                        proteinMessage.append(i).append("\t");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Input Miss Match! Please Refill!(y)");
                    sc.nextLine();
                    continue;
                }
                flag = true;
            }
            System.out.print(proteinMessage.append("\n"));
            writerProtein.write(proteinMessage.toString());
            writerProtein.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Protein find(int id)throws java.io.IOException{
        String[] parts;
        Protein protein = new Protein();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathProtein))) {
            String line;
            if (id <= numberOfProtein)
                while (true) {
                    line = reader.readLine();
                    parts = line.split("\\t", -1);
                    if (Integer.toString(id).equals(parts[0])) {
                        break;
                    }
                }
            else
                return null;
        }
        protein.id = id;
        protein.name = parts[1];
        protein.size = Integer.parseInt(parts[2]);
        for (int i =0; i<size;i++){
            protein.domains[i] = Integer.parseInt(parts[i+3]);
        }
        return protein;
    }

    public static void printProtein(Protein protein){
        try{

        }catch (Exception e){

        }
    }

    //TODO: protein check function
}