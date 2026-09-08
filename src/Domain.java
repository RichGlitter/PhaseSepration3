
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Domain {
    private String domainId;
    private String proteinId;
    private int domainNum;
    public int numInPro;

    private static String filePath = "src/data/domainConnectRate";
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
            numOfDomain = (int) Files.lines(path, StandardCharsets.UTF_8).count() - 1;

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

    public Domain() {

        Scanner sc = new Scanner(System.in);
        numOfDomain++;
        domainNum = numOfDomain;

        try (FileWriter writer = new FileWriter(filePath, true)) {
            boolean flag = false;
            StringBuilder line = new StringBuilder();
            while(!flag) {
                try {
                    line.setLength(0);
                    line.append((numOfDomain) + "\t");
                    System.out.print("name of domain:");//readData(i, 1);
                    this.domainId = sc.nextLine();
                    System.out.print("name of Protein:");//readData(i, 2);
                    this.proteinId = sc.nextLine();
                    System.out.print("position in the Protein:");//readData(i, 3);
                    this.numInPro = sc.nextInt();
                    line.append(String.format("%s\t%s\t", domainId, proteinId));
                    System.out.print("connectRate to solvent:");
                    line.append(sc.nextDouble() + "\t");
                    for (int i = 2; i < numOfDomain+1; i++) {
                        System.out.print(String.format("connectRate to %s in Protein %s:", readData(i, 1),readData(i,2)));
                        line.append(sc.nextDouble() + "\t");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input Miss Match! Please Refill!(y)");
                    sc.nextLine();
                    continue;
                }
                flag = true;
            }
            line.setLength(line.length()-1);
            System.out.print(line.append("\n"));
            writer.write(line.toString());
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDomainId() {
        return domainId;
    }

    public String getProteinId() {
        return proteinId;
    }

    public int getDomainNum() {
        return domainNum;
    }

    public static String readData(int lineNo, int tabNo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;
            while ((line = reader.readLine()) != null) {
                currentLine++;
                if (currentLine == lineNo) {
                    String[] parts = line.split("\t", -1);
                    return (tabNo < parts.length) ? parts[tabNo] : null;
                }
            }
        }
        return null;   // 行号超出文件范围
    }
}

