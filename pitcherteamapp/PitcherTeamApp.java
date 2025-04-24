package com.mycompany.pitcherteamapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;


/**
 * JavaFX PitcherTeamApp
 */
public class PitcherTeamApp extends Application {
    
    private TextField pitcher1;
    private TextField pitcher2;
    private TextField pitcher3;
    private TextField pitcher4;
    private TextField pitcher5;
    private TextField pitcher6;
    private TextField pitcher7;
    private TextField pitcher8;
    private TextField pitcher9;
    private TextField pitcher10;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pitcher Statistics");
        
        //creates the grid and positions
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(5);
        grid.setVgap(10);
        
        Scene scene = new Scene(grid, 575, 550);
        
        // adds 3 columns and adds column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(60);  
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(120); 
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(100); 
        grid.getColumnConstraints().addAll(col1, col2, col3);
        

        //Creates the accept and exit buttons
        Button acceptButton = new Button("Accept");
        Button exitButton = new Button("Exit");
        
        
        //runs the acceptButtonClicked and exitButtonClicked functions
        acceptButton.setOnAction(event -> acceptButtonClicked());
        exitButton.setOnAction(event -> exitButtonClicked());
        
        
       
        // adds labels for each pitcher 
        grid.add(new Label("Please enter the data for each pitcher"), 0, 0, 2, 1); // Span 2 columns.
        
        grid.add(new Label("Pitcher 1: "), 0, 1);
        pitcher1 = new TextField();
        pitcher1.setPrefWidth(75);
        grid.add(pitcher1, 1, 1);

        grid.add(new Label("Pitcher 2: "), 0, 2);
        pitcher2 = new TextField();
        pitcher2.setPrefWidth(75);
        grid.add(pitcher2, 1, 2);

        grid.add(new Label("Pitcher 3: "), 0, 3);
        pitcher3 = new TextField(); 
        pitcher3.setPrefWidth(75);
        grid.add(pitcher3, 1, 3);

        grid.add(new Label("Pitcher 4: "), 0, 4);
        pitcher4 = new TextField();
        pitcher4.setPrefWidth(150);
        grid.add(pitcher4, 1, 4);

        grid.add(new Label("Pitcher 5: "), 0, 5);
        pitcher5 = new TextField(); 
        pitcher5.setPrefWidth(150);
        grid.add(pitcher5, 1, 5);

        grid.add(new Label("Pitcher 6: "), 0, 6);
        pitcher6 = new TextField(); 
        pitcher6.setPrefWidth(150);
        grid.add(pitcher6, 1, 6);

        grid.add(new Label("Pitcher 7: "), 0, 7);
        pitcher7 = new TextField(); 
        pitcher7.setPrefWidth(150);
        grid.add(pitcher7, 1, 7);

        grid.add(new Label("Pitcher 8: "), 0, 8);
        pitcher8 = new TextField(); 
        pitcher8.setPrefWidth(150);
        grid.add(pitcher8, 1, 8);

        grid.add(new Label("Pitcher 9: "), 0, 9);
        pitcher9 = new TextField(); 
        pitcher9.setPrefWidth(150);
        grid.add(pitcher9, 1, 9);

        grid.add(new Label("Pitcher 10:"), 0, 10);
        pitcher10 = new TextField();
        pitcher10.setPrefWidth(150);
        grid.add(pitcher10, 1, 10);
        
        //Past this point is mostly alignments on the application

        
        // sets both buttons next to each other
        HBox buttonBox = new HBox(10, acceptButton, exitButton);
        buttonBox.setAlignment(Pos.BOTTOM_LEFT);
        grid.add(buttonBox, 0, 11, 2, 1); 
        
        
        primaryStage.setScene(scene);
        primaryStage.show();   
    }

    private void acceptButtonClicked() {
        System.out.println("Accept button clicked");

    }
    
    private void exitButtonClicked() {
        // Exits out of the application
        System.out.println("Exit button clicked");
        System.exit(0);  
    }

    public static void main(String[] args) {
        launch(args);
    }

}
