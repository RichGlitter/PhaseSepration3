
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Domain {
    private String domainName;
    private String proteinName;
    private int proteinId;
    private int domainId;

    public int valence;

    private static final String filePath = "src/data/domainConnectRate";
    public static int numOfDomain;

    static {
        Path path = Path.of(filePath);
        String header = "0\tSolvent\tNULL\n";

        try {
            // 1) 先完整读出内容（此时没有动文件）
            String content = Files.readString(path, StandardCharsets.UTF_8);   // Java 11+
            // 2) 缺表头才补上（拼到最前面一次性写回）
            if (!content.startsWith(header)) {
                Files.writeString(path, header, StandardCharsets.UTF_8);
            }
            // 3) 统计：此时文件必含表头，数据行数 = 总行数 - 1
            try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
                numOfDomain = (int) lines
                        .filter(l -> !l.isBlank())
                        .count() - 1;
            }
//            numOfDomain = (int) Files.lines(path, StandardCharsets.UTF_8).count() - 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); FileWriter writer = new FileWriter(filePath)) {
//            String line;
//            int currentLine = 0;
//            line = reader.readLine();
//            if (!"1\tSolvent\tNULL".equals(line)) {
//                writer.write("1\tSolvent\tNULL\n");
//                writer.flush();
//            }
//            while ((line = reader.readLine()) != null) {
//                currentLine++;
//            }
//
//            numOfDomain = currentLine-1;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
    }

    public String refreshDomain(String nameOfDomain, String nameOfProtein, int proteinId, int valence, boolean printed) {

        Scanner sc = new Scanner(System.in);
        numOfDomain++;
        domainId = numOfDomain;
        this.proteinId = 0;
        StringBuilder line = new StringBuilder();

        try (FileWriter writer = new FileWriter(filePath, true)) {
            boolean flag = false;
            while (!flag) {
                try {
                    line.setLength(0);
                    line.append((numOfDomain) + "\t");

                    if (nameOfDomain == null) {
                        System.out.print("name of domain:");//readData(i, 1);
                        this.domainName = sc.nextLine();
                    } else {
                        this.domainName = nameOfDomain;
                    }

                    if (nameOfProtein == null) {
                        //TODO:new a protein with only one domain
                        System.out.print("name of Protein:");//readData(i, 2);
                        this.proteinName = sc.nextLine();
                        //TODO:add proteinId part.

                    } else {
                        this.proteinName = nameOfProtein;
                        this.proteinId = proteinId;
                    }


                    //todo: want to add valence part. new data: num(finished)  or position(like 00000000 )(notFinished)
                    if (valence == Integer.MAX_VALUE) {
                        System.out.print("valence of Domain:");//readData(i, 2);
                        this.valence = sc.nextInt();
                    } else {
                        this.valence = valence;
                    }


                    line.append(String.format("%s\t%d\t%s\t%d\t", domainName, this.proteinId, proteinName, this.valence));
                    System.out.print("connectRate to solvent:");
                    line.append(sc.nextDouble() + "\t");
                    for (int i = 2; i < numOfDomain + 1; i++) {
                        System.out.print(String.format("connectRate to %s in %s:", readData(i, 1), readData(i, 3)));
                        line.append(sc.nextDouble() + "\t");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input Miss Match! Please Refill!(y)");
                    sc.nextLine();
                    continue;
                }
                flag = true;
            }
            line.setLength(line.length() - 1);
            System.out.print(line.append("\n"));
            if (printed) {
                writer.write(line.toString());
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line.toString();
    }


    public String getDomainName() {
        return domainName;
    }

    public int getProteinId() {
        return proteinId;
    }

    public int getDomainId() {
        return domainId;
    }

    public static String readData(int lineNo, int tabNo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            if (lineNo <= numOfDomain)
                while (true) {
                    line = reader.readLine();
                    String[] parts = line.split("\\t", -1);
                    if (Integer.toString(lineNo).equals(parts[0])) {
                        return (tabNo < parts.length) ? parts[tabNo] : null;
                    }
                }
            else
                return null;
        }
    }

    public static String toString(int domainId) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            if (domainId <= numOfDomain)
                while (true) {
                    line = reader.readLine();
                    String[] parts = line.split("\\t", -1);
                    if (Integer.toString(domainId).equals(parts[0])) {
                        return line;
                    }
                }
            else
                return null;
        }
    }
}

