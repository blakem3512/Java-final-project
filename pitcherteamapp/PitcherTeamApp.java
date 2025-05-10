/*
    Created by:    Hilary Philistin,
                   Matthew Blake, 
                   Henry Sesay, 
                   Braden Henry, 
                   Zach Thompson, 
                   Wyatt Metcalf
    Created on:    05/08/2025
    Team’s name:   Pitcher
    Description:   Create a baseball statistics program that produce a report's 
                   list of the statistics for all pitchers in that game and 
                   calculates the earned run average for each pitcher.
                   Updated to include a multi-game summary that reads multiple game files
                   and summarizes each pitcher's statistics over a specified number of games.
*/

package csd2522.wrm.mavenproject1;

// JavaFX imports for application lifecycle and window management.
import javafx.application.Application;      // Used to extend the Application class.
import javafx.stage.Stage;                  // Manages the primary window (stage) of the app.
import javafx.scene.Scene;                  // Represents a scene (container for all content).
import javafx.scene.control.*;              // Provides JavaFX UI controls (Button, Label, DatePicker, Alert, TextArea, etc.).
import javafx.scene.layout.*;               // Contains layout containers like VBox, HBox, BorderPane.
import javafx.geometry.*;                   // Supplies layout geometry classes (Insets, Pos, etc.).
import javafx.scene.Node;                   // Base class for all scene graph nodes (used for dynamic UI element access).

// Standard Java library imports.
import java.util.*;                         // Provides collection classes like List and ArrayList.
import java.time.LocalDate;                 // Deals with dates without time-of-day details.
import java.time.format.DateTimeFormatter;  // Formats LocalDate for file naming.
import java.io.*;                           // Provides I/O functionalities for reading and writing files.
import javafx.scene.text.Font;              // Allows for fonts to be added to reports 

public class PitcherTeamApp extends Application {

    // Global reference to the primary stage.
    private Stage primaryStage;

    // Formatter for dates (used in file names)
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Team Pitcher Application");
        // Set the initial scene to the main menu.
        Scene mainMenu = createMainMenuScene();
        stage.setScene(mainMenu);
        stage.show();
    }

    /*
     * Create the Main Menu Scene.
     * Changes made from the previous code:
     * - Added a main menu for navigation between data entry, report generation, multi-game summary, and exit.
     * - Buttons provided to either enter data, generate report, or exit.
     * Edited by Wyatt.
     */
    private Scene createMainMenuScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Pitcher Team Program");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button btnEnterData = new Button("Enter Game Data");
        Button btnGenerateReport = new Button("Generate Game Report");
        Button btnMultiGameSummary = new Button("Multi-Game Summary");
        Button btnExit = new Button("Exit");

        // Action: Switch to Data Entry Scene.
        btnEnterData.setOnAction(e -> {
            Scene dataEntryScene = createDataEntryScene();
            primaryStage.setScene(dataEntryScene);
            /*
            -Positions the data entry screen to be in the middle when it shows up
            -Edited by Matthew Blake
            */
            primaryStage.setX(165); //  X coordinate (left position)
            primaryStage.setY(150); // Y coordinate (top position)
            primaryStage.show();
        });

        // Action: Switch to Single Game Report Scene.
        btnGenerateReport.setOnAction(e -> {
            Scene gameReportScene = createGameReportScene();
            primaryStage.setScene(gameReportScene);
        });

        // Action: Switch to Multi-Game Summary Scene.
        btnMultiGameSummary.setOnAction(e -> {
            Scene summaryScene = createSummaryReportScene();
            primaryStage.setScene(summaryScene);
        });

        btnExit.setOnAction(e -> primaryStage.close());

        root.getChildren().addAll(titleLabel, btnEnterData, btnGenerateReport, btnMultiGameSummary, btnExit);
        return new Scene(root, 400, 300);
    }

    /*
     * Data Entry Scene for entering pitcher data.
     * Changes made from your previous code:
     * - Replaced the fixed GridPane with a dynamic VBox for pitcher entry rows.
     * - Introduced a DatePicker control for the game date.
     * - Added two new buttons: "Add Pitcher" and "Remove Pitcher" that let the user dynamically
     *   add or remove a row for entering pitcher data.
     * - The "Submit" button now iterates over these rows to validate and save data.
     * - Modified the header labels and text fields to have fixed preferred widths for alignment.
     * Edited by Wyatt.
     */
    private Scene createDataEntryScene() {
        // Create the root pane for the scene.
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        // TOP: HBox with DatePicker for selecting game date.
        HBox topBox = new HBox(10);
        topBox.setAlignment(Pos.CENTER_LEFT);
        Label dateLabel = new Label("Game Date: ");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        topBox.getChildren().addAll(dateLabel, datePicker);
        pane.setTop(topBox);

        // CENTER: VBox to hold header, dynamic pitcher rows, and add/remove row buttons.
        VBox centerContainer = new VBox(10);
        centerContainer.setPadding(new Insets(10));

        // Header row for labels with fixed widths for alignment.
        HBox headerRow = new HBox(10);
        // Set fixed widths to align with the text fields below.
        Label nameHeader = new Label("Pitcher Name");
        nameHeader.setPrefWidth(150);  // edited by Wyatt
        Label inningsHeader = new Label("Innings Pitched");
        inningsHeader.setPrefWidth(135);  // edited by Wyatt
        Label hitsHeader = new Label("Hits");
        hitsHeader.setPrefWidth(120);  // edited by Wyatt
        
        Label runHeader = new Label("Runs");
        runHeader.setPrefWidth(120);  // edited by Matthew Blake
        Label earnedHeader = new Label("Earned Runs");
        earnedHeader.setPrefWidth(120);  // edited by Matthew Blake
        Label baseOnBallsHeader = new Label("Base on Balls");
        baseOnBallsHeader.setPrefWidth(120);  // edited by Matthew Blake
        Label strikeOutHeader = new Label("Strikeouts");
        strikeOutHeader.setPrefWidth(120);  // edited by Matthew Blake
        Label atBatsHeader = new Label("At Bats");
        atBatsHeader.setPrefWidth(120);  // edited by Matthew Blake
        Label battersFacedHeader = new Label("Batters Faced");
        battersFacedHeader.setPrefWidth(120);  // edited by Matthew Blake
        Label numPitchesHeader = new Label("Number of Pitches");
        numPitchesHeader.setPrefWidth(120);  // edited by Matthew Blake

        /*
        -Added the remaining headers
        -Edited by Matthew Blake
        */
        headerRow.getChildren().addAll(nameHeader, inningsHeader, hitsHeader, runHeader, earnedHeader, baseOnBallsHeader,
                strikeOutHeader, atBatsHeader, battersFacedHeader, numPitchesHeader);
        centerContainer.getChildren().add(headerRow);

        // VBox container that will hold dynamic pitcher rows.
        VBox pitcherRowsContainer = new VBox(10);
        // Initially, add 5 rows.
        final int initialRows = 5;
        for (int i = 0; i < initialRows; i++) {
            pitcherRowsContainer.getChildren().add(createPitcherRow());
        }
        centerContainer.getChildren().add(pitcherRowsContainer);

        // HBox for "Add Pitcher" and "Remove Pitcher" buttons.
        HBox rowButtons = new HBox(10);
        rowButtons.setAlignment(Pos.CENTER_LEFT);
        Button btnAddPitcher = new Button("Add Pitcher");
        Button btnRemovePitcher = new Button("Remove Pitcher");
        rowButtons.getChildren().addAll(btnAddPitcher, btnRemovePitcher);
        centerContainer.getChildren().add(rowButtons);

        pane.setCenter(centerContainer);

        // BOTTOM: HBox with "Submit" and "Back" buttons.
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        Button btnSubmit = new Button("Submit");
        Button btnBack = new Button("Back");
        bottomBox.getChildren().addAll(btnSubmit, btnBack);
        pane.setBottom(bottomBox);

        // Action for "Add Pitcher" button.
        btnAddPitcher.setOnAction(e -> {
            // Append a new pitcher row.
            pitcherRowsContainer.getChildren().add(createPitcherRow());
        });

        // Action for "Remove Pitcher" button.
        btnRemovePitcher.setOnAction(e -> {
            // Remove the last pitcher row if one exists.
            int count = pitcherRowsContainer.getChildren().size();
            /*
            - Changed to >= 2 in order to make sure there's at least 1 row for
            - data to be entered
            - Edited by Matthew Blake
            */
            if (count >= 2) {
                pitcherRowsContainer.getChildren().remove(count - 1);
            }
        });

        // "Back" returns to the main menu.
        btnBack.setOnAction(e -> primaryStage.setScene(createMainMenuScene()));

        // "Submit" validates each row and writes the data to a CSV file.
        btnSubmit.setOnAction(e -> {
            LocalDate gameDate = datePicker.getValue();
            if (gameDate == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a game date.");
                return;
            }
            List<Pitcher> pitchers = new ArrayList<>();
            int rowIndex = 1; // For error reporting.
            // Iterate through each dynamic pitcher row.
            for (Node node : pitcherRowsContainer.getChildren()) {
                if (node instanceof HBox) {
                    HBox row = (HBox) node;
                    // Expect ten TextFields in each row.
                    if (row.getChildren().size() < 10)
                        continue;
                    TextField tfName = (TextField) row.getChildren().get(0);
                    TextField tfInnings = (TextField) row.getChildren().get(1);
                    TextField tfHits = (TextField) row.getChildren().get(2);
                    TextField tfRuns = (TextField) row.getChildren().get(3);
                    TextField tfEarned = (TextField) row.getChildren().get(4);
                    TextField tfBaseOnBalls = (TextField) row.getChildren().get(5);
                    TextField tfSO = (TextField) row.getChildren().get(6);
                    TextField tfAtBats = (TextField) row.getChildren().get(7);
                    TextField tfBattFaced = (TextField) row.getChildren().get(8);
                    TextField tfNumOfPitches = (TextField) row.getChildren().get(9);
                   
                    // Get text from the TextFields
                    String name = tfName.getText().trim();
                    String inningsStr = tfInnings.getText().trim();
                    String hitsStr = tfHits.getText().trim();
                    String runsStr = tfRuns.getText().trim();
                    String earnedStr = tfEarned.getText().trim();
                    String baseOnBallsStr = tfBaseOnBalls.getText().trim();  // This was missing previously
                    String soStr = tfSO.getText().trim();
                    String atBatsStr = tfAtBats.getText().trim();
                    String battFacedStr = tfBattFaced.getText().trim();
                    String numPitchesStr = tfNumOfPitches.getText().trim();
                    
                    // If the entire row is empty, skip it.
                    if (name.isEmpty() && inningsStr.isEmpty() && hitsStr.isEmpty() && runsStr.isEmpty() &&
                            earnedStr.isEmpty() && baseOnBallsStr.isEmpty() && soStr.isEmpty() && 
                            atBatsStr.isEmpty() && battFacedStr.isEmpty() && numPitchesStr.isEmpty()) {
                        rowIndex++;
                        continue;
                    }
                    
                    // If partially filled, show an error.
                    if (name.isEmpty() || inningsStr.isEmpty() || hitsStr.isEmpty() || runsStr.isEmpty() ||
                            earnedStr.isEmpty() || baseOnBallsStr.isEmpty() || soStr.isEmpty() ||
                            atBatsStr.isEmpty() || battFacedStr.isEmpty() || numPitchesStr.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Validation Error",
                            "Incomplete data in pitcher row " + rowIndex + ". Please fill all fields or leave all blank.");
                        return;
                    }
                    
                    // Validate innings pitched as a double.
                    double innings;
                    try {
                        innings = Double.parseDouble(inningsStr);
                        if (innings < 0) {
                            showAlert(Alert.AlertType.ERROR, "Validation Error",
                                "Innings pitched cannot be negative in row " + rowIndex);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Validation Error",
                            "Invalid innings pitched in row " + rowIndex);
                        return;
                    }
                    
                    // Validate earned runs as an integer.
                    int earnedRuns;
                    try {
                        earnedRuns = Integer.parseInt(earnedStr);
                        if (earnedRuns < 0) {
                            showAlert(Alert.AlertType.ERROR, "Validation Error",
                                "Earned runs cannot be negative in row " + rowIndex);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Validation Error",
                            "Invalid earned runs in row " + rowIndex);
                        return;
                    }
                    
                    /*
                    NOTE: ADD VALIDATION FOR THE REMAINING BOXES: Hits, Runs (not earned runs), 
                    Base on Balls, Strikeouts, At Bats, Batters faced, and # of pitches
                    */
                    
                    // Create the Pitcher object from validated data.
                    Pitcher p = new Pitcher(
                        name,
                        Double.parseDouble(inningsStr),
                        Integer.parseInt(earnedStr),
                        Integer.parseInt(hitsStr),
                        Integer.parseInt(runsStr),
                        Integer.parseInt(baseOnBallsStr),
                        Integer.parseInt(soStr),
                        Integer.parseInt(atBatsStr),
                        Integer.parseInt(battFacedStr),
                        Integer.parseInt(numPitchesStr)
                    );
                    pitchers.add(p);
                    rowIndex++;
                }
            }

            if (pitchers.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "No pitcher data entered.");
                return;
            }

            // Write the pitcher data to a CSV file named by game date.
            String fileName = "game_" + gameDate.format(formatter) + ".csv";
            try {
                writeGameDataToFile(fileName, pitchers);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Game data saved to " + fileName);
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "File Error", "Error writing to file: " + ex.getMessage());
            }
        });
        
        /*
        - Temporarily made bigger to incorporate more data to be entered
        - Edited by Matthew Blake
        */
        return new Scene(pane, 1300, 450);
    }

    /*
     * Helper method to create a new pitcher row (HBox) containing ten TextFields.
     * Each text field is given a fixed preferred width to line up with the header labels.
     * Edited by Matthew Blake.
     */
    private HBox createPitcherRow() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        
        TextField tfName = new TextField();
        tfName.setPromptText("Name");
        tfName.setPrefWidth(150);  // edited by Wyatt: Fixed width for alignment.
        
        TextField tfInnings = new TextField();
        tfInnings.setPromptText("Innings");
        tfInnings.setPrefWidth(130);  // edited by Wyatt: Fixed width for alignment.
        
        TextField tfHits = new TextField();
        tfHits.setPromptText("Hits");
        tfHits.setPrefWidth(120);   // edited by Matthew Blake

        TextField tfRuns = new TextField();
        tfRuns.setPromptText("Runs");
        tfRuns.setPrefWidth(120); 
        
        TextField tfEarned = new TextField();
        tfEarned.setPromptText("Earned Runs");
        tfEarned.setPrefWidth(120);  // edited by Wyatt

        TextField tfBaseOnBalls = new TextField();
        tfBaseOnBalls.setPromptText("Base on Balls");
        tfBaseOnBalls.setPrefWidth(120);  // edited by Matthew Blake

        TextField tfSO = new TextField();
        tfSO.setPromptText("Strikeouts");
        tfSO.setPrefWidth(120);  // edited by Matthew Blake

        TextField tfAtBats = new TextField();
        tfAtBats.setPromptText("At Bats");
        tfAtBats.setPrefWidth(120);  // edited by Matthew Blake

        TextField tfBattFaced = new TextField();
        tfBattFaced.setPromptText("Batters Faced");
        tfBattFaced.setPrefWidth(120);  // edited by Matthew Blake

        TextField tfNumOfPitches = new TextField();
        tfNumOfPitches.setPromptText("Number of Pitches");
        tfNumOfPitches.setPrefWidth(120);  // edited by Matthew Blake

        row.getChildren().addAll(tfName, tfInnings, tfHits, tfRuns, tfEarned, tfBaseOnBalls, tfSO, tfAtBats, tfBattFaced, tfNumOfPitches);
        return row;
    }

    /*
     * Create the Game Report Scene.
     * Changes made from the previous code:
     * - Added a scene for loading a saved game CSV file.
     * - Uses a DatePicker to select a game date.
     * - Reads data from the CSV, creates Pitcher objects, calculates ERA, and generates a report.
     * - Includes a Save Report button to store the report in a text file for printing.
     * Edited by Matthew, Henry, and Wyatt.
     */   
private Scene createGameReportScene() {
    BorderPane pane = new BorderPane();
    pane.setPadding(new Insets(15));

    // TOP: HBox for selecting the game date.
    HBox topBox = new HBox(10);
    topBox.setAlignment(Pos.CENTER_LEFT);
    Label lblDate = new Label("Select Game Date:");
    DatePicker dpGameDate = new DatePicker(LocalDate.now());
    topBox.getChildren().addAll(lblDate, dpGameDate);
    pane.setTop(topBox);

    // CENTER: TextArea to display the generated report.
    TextArea reportArea = new TextArea();
    reportArea.setEditable(false);
    // Disable text wrapping so the formatted report columns don't break into multiple lines.
    reportArea.setWrapText(false);
    // Use a monospaced font to ensure each character takes up the same width.
    reportArea.setFont(Font.font("monospaced"));
    pane.setCenter(reportArea);

    // BOTTOM: HBox with buttons for report actions.
    HBox bottomBox = new HBox(10);
    bottomBox.setAlignment(Pos.CENTER_RIGHT);
    Button btnLoad = new Button("Load Report");
    Button btnSave = new Button("Save Report");
    Button btnBack = new Button("Back");
    bottomBox.getChildren().addAll(btnLoad, btnSave, btnBack);
    pane.setBottom(bottomBox);

    // "Back" button returns to the main menu.
    btnBack.setOnAction(e -> primaryStage.setScene(createMainMenuScene()));

    // "Load Report": Read the CSV file, generate the report (including ERA calculations), and display it.
    btnLoad.setOnAction(e -> {
        LocalDate gameDate = dpGameDate.getValue();
        if (gameDate == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a game date.");
            return;
        }
        String fileName = "game_" + gameDate.format(formatter) + ".csv";
        File file = new File(fileName);
        if (!file.exists()) {
            showAlert(Alert.AlertType.ERROR, "File Not Found", "No data file found for the selected date.");
            return;
        }
        try {
            List<Pitcher> pitchers = readGameDataFromFile(fileName);
            String report = generateReport(pitchers);
            reportArea.setText(report);
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error reading file: " + ex.getMessage());
        }
    });

    // "Save Report": Writes the report displayed in the TextArea to a text file.
    btnSave.setOnAction(e -> {
        String reportText = reportArea.getText();
        if (reportText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No Report", "There is no report to save.");
            return;
        }
        LocalDate gameDate = dpGameDate.getValue();
        String reportFileName = "report_" + gameDate.format(formatter) + ".txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(reportFileName))) {
            pw.write(reportText);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Report saved to " + reportFileName);
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error saving report: " + ex.getMessage());
        }
    });

    return new Scene(pane, 1200, 400);
}


    /*
     * Create the Multi-Game Summary Scene.
     * This new scene allows the user to specify the number of recent game files 
     * to combined and summarize pitcher statistics over those games.
     * Edited by Matthew, Wyatt, Zach
     * Final Edit -  5/9/25.
     */
private Scene createSummaryReportScene() {
    BorderPane pane = new BorderPane();
    pane.setPadding(new Insets(15));

    // TOP: User enters number of recent games to summarize.
    HBox topBox = new HBox(10);
    topBox.setAlignment(Pos.CENTER_LEFT);
    Label lblInstruction = new Label("Enter number of recent games to summarize:");
    TextField tfNumGames = new TextField();
    tfNumGames.setPrefWidth(50);
    Button btnSummarize = new Button("Summarize");
    topBox.getChildren().addAll(lblInstruction, tfNumGames, btnSummarize);
    pane.setTop(topBox);

    // CENTER: TextArea displays summary report.
    TextArea summaryArea = new TextArea();
    summaryArea.setEditable(false);
    summaryArea.setWrapText(false);                      // Disable wrap to maintain alignment.
    summaryArea.setFont(Font.font("monospaced"));        // Use monospaced font for fixed-width characters.
    pane.setCenter(summaryArea);

    // BOTTOM: Back button.
    HBox bottomBox = new HBox(10);
    bottomBox.setAlignment(Pos.CENTER_RIGHT);
    Button btnBack = new Button("Back");
    bottomBox.getChildren().add(btnBack);
    pane.setBottom(bottomBox);

    btnBack.setOnAction(e -> primaryStage.setScene(createMainMenuScene()));

    btnSummarize.setOnAction(e -> {
        int numGames;
        try {
            numGames = Integer.parseInt(tfNumGames.getText().trim());
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid number of games.");
            return;
        }
        // Retrieves all game files 
        File dir = new File(".");
        File[] gameFiles = dir.listFiles((d, name) -> name.matches("game_\\d{4}-\\d{2}-\\d{2}\\.csv"));
        if (gameFiles == null || gameFiles.length < numGames) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Not enough game files available for summarization.");
            return;
        }
        // Sorts game files by name so that the most recent games (by date in the filename) come first.
        Arrays.sort(gameFiles, Comparator.comparing(File::getName).reversed());

        // Use a Map to combine the statistics per pitcher.
        Map<String, CombinedStats> statsMap = new HashMap<>();
        for (int i = 0; i < numGames; i++) {
            try {
                List<Pitcher> gamePitchers = readGameDataFromFile(gameFiles[i].getName());
                for (Pitcher p : gamePitchers) {
                    CombinedStats agg = statsMap.getOrDefault(p.getName(), new CombinedStats());
                    agg.inningsPitched   += p.getInningsPitched();
                    agg.earnedRuns       += p.getEarnedRuns();
                    agg.hits             += p.getHits();
                    agg.runs             += p.getRuns();
                    agg.baseOnBalls      += p.getBaseOnBalls();
                    agg.strikeouts       += p.getStrikeouts();
                    agg.atBats           += p.getAtBats();
                    agg.battersFaced     += p.getBattersFaced();
                    agg.numPitches       += p.getNumberOfPitches();
                    statsMap.put(p.getName(), agg);
                }
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "File Error", "Error reading file " + gameFiles[i].getName() + ": " + ex.getMessage());
                return;
            }
        }

        // Generates a formatted summary report.
        StringBuilder sb = new StringBuilder();
        sb.append("Summary Report for Last ").append(numGames).append(" Games\n");
        sb.append("=".repeat(130)).append("\n\n");

        // Defines column widths for the headers and data.
        int nameWidth = 15;
        int inningsWidth = 15;
        int erWidth = 15; 
        int hitsWidth = 10;
        int runsWidth = 10;
        int bbWidth = 10;
        int soWidth = 10;
        int atBatsWidth = 15;
        int battersWidth = 17;
        int pitchesWidth = 13;
        int eraWidth = 10;

        // Writes header using a consistent format string.
        String headerFormat = "%-15s %15s %15s %10s %10s %10s %10s %15s %17s %13s %10s\n";  // Each conversion specifier uses the widths above.
        sb.append(String.format(headerFormat, 
            "Name", "Innings", "ER", "Hits", "Runs", "BB", "SO", "At Bats", "Batters Faced", "Pitches", "ERA"));
        sb.append("-".repeat(nameWidth + inningsWidth + erWidth + hitsWidth + runsWidth + bbWidth 
            + soWidth + atBatsWidth + battersWidth + pitchesWidth + eraWidth)).append("\n");

        // Writes combined stat data using a consistent format string.
        String rowFormat = "%-15s %15.2f %15d %10d %10d %10d %10d %15d %17d %13d %10.2f\n";
        for (Map.Entry<String, CombinedStats> entry : statsMap.entrySet()) {
            String name = entry.getKey();
            CombinedStats agg = entry.getValue();
            // Calculates ERA based on aggregated totals.
            double era = (agg.inningsPitched > 0) ? (agg.earnedRuns * 9) / agg.inningsPitched : 0;

            sb.append(String.format(rowFormat,
                name,
                agg.inningsPitched,
                agg.earnedRuns,
                agg.hits,
                agg.runs,
                agg.baseOnBalls,
                agg.strikeouts,
                agg.atBats,
                agg.battersFaced,
                agg.numPitches,
                era));
        }
        summaryArea.setText(sb.toString());
    });

    // Increase the scene width to 800 so all data displays without stretching.
    return new Scene(pane, 800, 500);
}


    /*
     * Helper method to display an alert.
     * Edited by Wyatt: This method is reused throughout the app to display messages.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
     * Write game data to a CSV file (Flat file type). Data is appended if the file already exists.
     * Edited by Henry.
     */
    private void writeGameDataToFile(String fileName, List<Pitcher> pitchers) throws IOException {
        File file = new File(fileName);
        boolean fileExists = file.exists();

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            // Write a header line if the file is new.
            if (!fileExists || file.length() == 0) {
                pw.println("Name,InningsPitched,Hits,Runs,EarnedRuns,BaseOnBalls,Strikeouts,AtBats,BattersFaced,NumberOfPitches");
            }
            for (Pitcher p : pitchers) {
                pw.write(String.format("%s,%.2f,%d,%d,%d,%d,%d,%d,%d,%d\n",
                        p.getName(),
                        p.getInningsPitched(),
                        p.getHits(),
                        p.getRuns(),
                        p.getEarnedRuns(),
                        p.getBaseOnBalls(),
                        p.getStrikeouts(),
                        p.getAtBats(),
                        p.getBattersFaced(),
                        p.getNumberOfPitches()));
            }
        }
    }

    /*
     * Read game data from a CSV file and convert each line to a Pitcher object.
     * Edited by Matthew and Wyatt.
     */
    private List<Pitcher> readGameDataFromFile(String fileName) throws IOException {
        List<Pitcher> pitchers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 10) {  // Ensure we have all 10 fields
                    String name = parts[0];
                    double innings = Double.parseDouble(parts[1]);
                    int hits = Integer.parseInt(parts[2]);
                    int runs = Integer.parseInt(parts[3]);
                    int earnedRuns = Integer.parseInt(parts[4]);
                    int baseOnBalls = Integer.parseInt(parts[5]);
                    int strikeouts = Integer.parseInt(parts[6]);
                    int atBats = Integer.parseInt(parts[7]);
                    int battersFaced = Integer.parseInt(parts[8]);
                    int numberOfPitches = Integer.parseInt(parts[9]);

                    // Add a new Pitcher object with all the required fields.
                    pitchers.add(new Pitcher(name, innings, earnedRuns, hits, runs, baseOnBalls, strikeouts, atBats, battersFaced, numberOfPitches));
                }
            }
        }
        return pitchers;
    }

    /*
     * Generate a formatted report from the list of Pitcher objects.
     * Edited by Wyatt.
     */
    private String generateReport(List<Pitcher> pitchers) {
        StringBuilder sb = new StringBuilder();
        sb.append("Game Pitcher Report\n");
        sb.append("====================\n\n");

        // Define the column widths for headers and data.
        int nameWidth = 15;
        int inningsWidth = 15;
        int earnedRunsWidth = 17;
        int hitsWidth = 10;
        int runsWidth = 10;
        int bbWidth = 10;
        int soWidth = 10;
        int atBatsWidth = 15;
        int battersFacedWidth = 17;
        int pitchesWidth = 13;
        int eraWidth = 10;

        // Add the header with padding to ensure proper alignment.
        sb.append(String.format("%-" + nameWidth + "s %" + inningsWidth + "s %" + earnedRunsWidth + "s %" + hitsWidth + "s %" + runsWidth + "s %" + bbWidth + "s %" + soWidth + "s %" + atBatsWidth + "s %" + battersFacedWidth + "s %" + pitchesWidth + "s %" + eraWidth + "s\n", 
                "Name", "Innings", "Earned Runs", "Hits", "Runs", "BB", "SO", "At Bats", "Batters Faced", "Pitches", "ERA"));
        sb.append("-".repeat(nameWidth + inningsWidth + earnedRunsWidth + hitsWidth + runsWidth + bbWidth + soWidth + atBatsWidth + battersFacedWidth + pitchesWidth + eraWidth)).append("\n");

        // Add data for each pitcher.
        for (Pitcher p : pitchers) {
            sb.append(String.format("%-" + nameWidth + "s  %" + inningsWidth + ".2f     %" + earnedRunsWidth + "d         %" + hitsWidth + "d   %" + runsWidth + "d    %" + bbWidth + "d   %" + soWidth + "d %" + atBatsWidth + "d   %" + battersFacedWidth + "d         %" + pitchesWidth + "d      %" + eraWidth + ".2f\n",
                    p.getName(),
                    p.getInningsPitched(),
                    p.getEarnedRuns(),
                    p.getHits(),
                    p.getRuns(),
                    p.getBaseOnBalls(),
                    p.getStrikeouts(),
                    p.getAtBats(),
                    p.getBattersFaced(),
                    p.getNumberOfPitches(),
                    p.calculateERA()));
        }
        return sb.toString();
    }

    // Private helper class to holf the combined stats for multi-game summaries.
    // Final Edit - 5/9/25
    private class CombinedStats {
        double inningsPitched = 0;
        int earnedRuns = 0;
        int hits = 0;
        int runs = 0;
        int baseOnBalls = 0;
        int strikeouts = 0;
        int atBats = 0;
        int battersFaced = 0;
        int numPitches = 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
