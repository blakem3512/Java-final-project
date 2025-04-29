import java.io.*;

public class ReportGenerator {
    public static void generateReport(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            System.out.println("Pitcher Stats Report");
            System.out.println("====================");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String fileName = "PitcherStats_" + java.time.LocalDate.now() + ".txt";
        generateReport(fileName);
    }
}

