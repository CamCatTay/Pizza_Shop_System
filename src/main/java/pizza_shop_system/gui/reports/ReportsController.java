package pizza_shop_system.gui.reports;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pizza_shop_system.gui.base.BaseController;
import pizza_shop_system.reports.ReportGenerator;

import java.io.IOException;
import java.time.LocalDate;

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
        // Populate ChoiceBox with report types
        timeSelectionChoiceBox.getItems().addAll("Daily Report", "Weekly Report");

        // Listener for report type selection
        timeSelectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleReportTypeSelection(newVal);
        });

        generateButton.setDisable(true);
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

    private void handleReportTypeSelection(String reportType) {
        boolean isWeekly = "Weekly Report".equals(reportType);
        datePicker.setVisible(true);
        datePicker.setValue(LocalDate.now());
        weeklyStartDateLabel.setVisible(isWeekly);
        generateButton.setDisable(false);
    }

    private void generateReport() throws IOException {
        String reportType = timeSelectionChoiceBox.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate == null) {
            showError("Please select a date.");
            return;
        }

        String reportContent = null;

        try {
            if ("Daily Report".equals(reportType)) {
                reportContent = ReportGenerator.generateDailyReport(selectedDate);
            } else if ("Weekly Report".equals(reportType)) {
                LocalDate endDate = selectedDate.plusDays(6);
                reportContent = ReportGenerator.generateWeeklyReport(selectedDate, endDate);
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
