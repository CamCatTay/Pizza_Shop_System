package pizza_shop_system.gui.reports;
/*
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pizza_shop_system.reports.ReportGenerator;

import java.io.IOException;
import java.time.LocalDate;

// Need to implement Tyler's report generator handler into this later

public class ReportsController extends BaseController {

    @FXML
    private ChoiceBox<String> timeSelectionChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label weeklyStartDateLabel;

    @FXML
    private Button generateButton;

    @FXML
    private TextArea reportTextArea;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Populate the ChoiceBox with report types
        timeSelectionChoiceBox.getItems().addAll("Daily Report", "Weekly Report");

        // Add a listener for the ChoiceBox selection
        timeSelectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleReportTypeSelection(newVal);
        });

        // Disable the Generate Button initially
        generateButton.setDisable(true);

        // Add listener for Generate Button
        generateButton.setOnAction(e -> {
            try {
                generateReport();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Add listener for Back Button
        backButton.setOnAction(e -> goBack());
    }

    private void handleReportTypeSelection(String reportType) {
        if (reportType != null) {
            if (reportType.equals("Weekly Report")) {
                // Show Weekly Report fields
                weeklyStartDateLabel.setVisible(true);
                datePicker.setVisible(true);
                datePicker.setValue(LocalDate.now());
            } else {
                // Hide Weekly Report fields for other types
                weeklyStartDateLabel.setVisible(false);
                datePicker.setVisible(false);
            }
            generateButton.setDisable(false);
            datePicker.setVisible(true);
            datePicker.setValue(LocalDate.now());
            // Enable the button
        } else {
            generateButton.setDisable(true);
        }
    }

    private void generateReport() throws IOException {
        String reportType = timeSelectionChoiceBox.getValue();
        String reportContent = null;
        LocalDate endDate = datePicker.getValue().plusDays(6);
        //String reportContent = "Generating " + reportType + "..."; // Placeholder for actual logic

        try {


            if ("Daily Report".equals(reportType) && datePicker.getValue() != null) {
                reportContent = ReportGenerator.generateDailyReport(datePicker.getValue());
            }

            if ("Weekly Report".equals(reportType) && datePicker.getValue() != null) {
                System.out.println("Generating Weekly Report for days: " + datePicker.getValue().toString() + " - " + endDate.toString());
                reportContent = ReportGenerator.generateWeeklyReport(datePicker.getValue(), endDate);
            }

            // Show report in TextArea
            reportTextArea.setText(reportContent);
            reportTextArea.setVisible(true);
        } catch (IOException e){
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void goBack() {
        System.out.println("Navigating back to the Manager Dashboard...");
        // Add navigation logic here if applicable
    }
}

 */