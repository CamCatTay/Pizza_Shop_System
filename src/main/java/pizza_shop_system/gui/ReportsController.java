package pizza_shop_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

// Need to implement Tylers report generator handler into this later

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
        generateButton.setOnAction(e -> generateReport());

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

    private void generateReport() {
        String reportType = timeSelectionChoiceBox.getValue();
        String reportContent = "Generating " + reportType + "..."; // Placeholder for actual logic

        if ("Weekly Report".equals(reportType) && datePicker.getValue() != null) {
            reportContent += "\nStart Date: " + datePicker.getValue();
        }

        // Show report in TextArea
        reportTextArea.setText(reportContent);
        reportTextArea.setVisible(true);
    }

    private void goBack() {
        System.out.println("Navigating back to the Manager Dashboard...");
        // Add navigation logic here if applicable
    }
}