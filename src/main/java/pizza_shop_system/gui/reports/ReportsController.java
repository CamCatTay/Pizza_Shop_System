package pizza_shop_system.gui.reports;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pizza_shop_system.gui.base.BaseController;
import pizza_shop_system.reports.ReportGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReportsController extends BaseController {

    @FXML
    private ComboBox<String> timeSelectionChoiceBox;

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

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        // Populate ComboBox with report types
        timeSelectionChoiceBox.getItems().addAll("Daily Report", "Weekly Report");
        timeSelectionChoiceBox.getSelectionModel().select("Daily Report");

        // Set visible row count to show both options at once
        timeSelectionChoiceBox.setVisibleRowCount(2);

        // Set default date to today and make sure the DatePicker is visible
        datePicker.setValue(LocalDate.now());
        datePicker.setVisible(true);
        datePicker.setEditable(true); // Allow user to type in the DatePicker

        // Add a listener to detect text changes in the DatePicker's text field
        datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidDate(newValue)) {
                LocalDate typedDate = LocalDate.parse(newValue, dateFormatter);
                datePicker.setValue(typedDate); // Update the DatePicker value with the typed date
            }
        });

        // Listener for report type selection
        timeSelectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleReportTypeSelection(newVal);
        });

        generateButton.setDisable(false);
        reportTextArea.setVisible(false);

        generateButton.setOnAction(e -> {
            try {
                generateReport();
            } catch (IOException ex) {
                ex.printStackTrace();
                showError("Failed to generate report: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> goBack());
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void handleReportTypeSelection(String reportType) {
        boolean isWeekly = "Weekly Report".equals(reportType);
        weeklyStartDateLabel.setVisible(isWeekly);
    }

    private void generateReport() throws IOException {
        String reportType = timeSelectionChoiceBox.getValue();
        LocalDate selectedDate = datePicker.getValue();

        // Ensure that the date is selected or typed in
        if (selectedDate == null) {
            showError("Please select or type a date.");
            return;
        }

        String reportContent = null;

        try {
            if ("Daily Report".equals(reportType)) {
                reportContent = ReportGenerator.generateDailyReport(selectedDate);
            } else if ("Weekly Report".equals(reportType)) {
                reportContent = ReportGenerator.generateWeeklyReport(selectedDate);
            }

            reportTextArea.setText(reportContent != null ? reportContent : "No data available.");
            reportTextArea.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            showError("An error occurred while generating the report: " + e.getMessage());
        }
    }

    private void goBack() {
        System.out.println("Navigating back to the Manager Dashboard...");
        switchScene("ManagerHome");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Report Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
