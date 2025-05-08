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
     * - Added a main menu for navigation between data entry and report generation.
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

        // Action: Switch to Game Report Scene.
        btnGenerateReport.setOnAction(e -> {
            Scene gameReportScene = createGameReportScene();
            primaryStage.setScene(gameReportScene);
        });

        btnExit.setOnAction(e -> primaryStage.close());

        root.getChildren().addAll(titleLabel, btnEnterData, btnGenerateReport, btnExit);
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
        Label  hitsHeader = new Label("Hits");
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
                    // Expect three TextFields (Name, Innings, Earned Runs) in each row.
                    if (row.getChildren().size() < 3)
                        continue;
                    TextField tfName = (TextField) row.getChildren().get(0);
                    TextField tfInnings = (TextField) row.getChildren().get(1);
                    TextField tfHits = (TextField) row.getChildren().get(2);
                    
                    /*
                    -Added other textfields for remaining information
                    -Edited by Matthew Blake
                    */
                    TextField tfRuns = (TextField) row.getChildren().get(3);
                    TextField tfEarned = (TextField) row.getChildren().get(4);
                    TextField tfBaseOnBalls = (TextField) row.getChildren().get(5);
                    TextField tfSO = (TextField) row.getChildren().get(6);
                    TextField tfAtBats = (TextField) row.getChildren().get(7);
                    TextField tfBattFaced = (TextField) row.getChildren().get(8);
                    TextField tfNumOfPitches = (TextField) row.getChildren().get(9);
                   
                    
                    String name = tfName.getText().trim();
                    String inningsStr = tfInnings.getText().trim();
                    String hitsStr = tfHits.getText().trim();
                    
                    /*
                    -Added other strings for remaining textfields
                    -Edited by Matthew Blake
                    */
                    String runsStr = tfRuns.getText().trim();
                    String earnedStr = tfEarned.getText().trim();
                    String BaseOnBallsStr = tfBaseOnBalls.getText().trim();
                    String SOStr = tfSO.getText().trim();
                    String AtBatsStr = tfAtBats.getText().trim();
                    String BattFacedStr = tfBattFaced.getText().trim();
                    String NumPitchesStr = tfNumOfPitches.getText().trim();

                    // If the entire row is empty, skip it.
                    if (name.isEmpty() && inningsStr.isEmpty() && earnedStr.isEmpty() && 
                            hitsStr.isEmpty() && runsStr.isEmpty() && BaseOnBallsStr.isEmpty() &&
                            SOStr.isEmpty() && AtBatsStr.isEmpty() && BattFacedStr.isEmpty() && NumPitchesStr.isEmpty()) {
                        rowIndex++;
                        continue;
                    }
                    
                    // If partially filled, show an error.
                    if (name.isEmpty() || inningsStr.isEmpty() || earnedStr.isEmpty() || 
                            hitsStr.isEmpty() || runsStr.isEmpty() || BaseOnBallsStr.isEmpty() ||
                            SOStr.isEmpty() ||AtBatsStr.isEmpty() || BattFacedStr.isEmpty() || NumPitchesStr.isEmpty()) {
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
                    Pitcher p = new Pitcher(name, innings, earnedRuns);
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
     * Helper method to create a new pitcher row (HBox) containing three TextFields.
     * Each text field is given a fixed preferred width to line up with the header labels.
     * Edited by Wyatt.
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
        
        TextField tfEarned = new TextField();
        tfEarned.setPromptText("Earned Runs");
        tfEarned.setPrefWidth(120);  // edited by Wyatt: Fixed width for alignment.
        
        TextField tfHits = new TextField();
        tfHits.setPromptText("Hits");
        tfHits.setPrefWidth(120);   // edited by Matthew Blake

        TextField tfRuns = new TextField();
        tfRuns.setPromptText("Runs");
        tfRuns.setPrefWidth(120); 
        
        TextField tfBaseOnBalls = new TextField();
        tfBaseOnBalls.setPromptText("Base on Balls");
        tfBaseOnBalls.setPrefWidth(120);

        TextField tfSO = new TextField();
        tfSO.setPromptText("Strikeouts");
        tfSO.setPrefWidth(120);

        TextField tfAtBats = new TextField();
        tfAtBats.setPromptText("At Bats");
        tfAtBats.setPrefWidth(120);

        TextField tfBattFaced = new TextField();
        tfBattFaced.setPromptText("Batters Faced");
        tfBattFaced.setPrefWidth(120);

        TextField tfNumOfPitches = new TextField();
        tfNumOfPitches.setPromptText("Number of Pitches");
        tfNumOfPitches.setPrefWidth(120);

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
     * Edited by Wyatt.
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

        return new Scene(pane, 600, 400);
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
     * Edited by Wyatt.
     */
    private void writeGameDataToFile(String fileName, List<Pitcher> pitchers) throws IOException {
        File file = new File(fileName);
        boolean fileExists = file.exists();

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            // Write a header line if the file is new.
            if (!fileExists || file.length() == 0) {
                pw.println("Name,InningsPitched,EarnedRuns");
            }
            for (Pitcher p : pitchers) {
                pw.println(p.getName() + "," + p.getInningsPitched() + "," + p.getEarnedRuns());
            }
        }
    }

    /*
     * Read game data from a CSV file and convert each line to a Pitcher object.
     * Edited by Wyatt.
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
                if (parts.length == 3) {
                    String name = parts[0];
                    double innings = Double.parseDouble(parts[1]);
                    int earned = Integer.parseInt(parts[2]);
                    pitchers.add(new Pitcher(name, innings, earned));
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
        sb.append(String.format("%-20s %-15s %-15s %-10s\n", "Name", "Innings", "Earned Runs", "ERA"));
        sb.append("-----------------------------------------------------------\n");
        for (Pitcher p : pitchers) {
            sb.append(String.format("%-20s %-15.2f %-15d %-10.2f\n",
                    p.getName(), p.getInningsPitched(), p.getEarnedRuns(), p.calculateERA()));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }    
}
