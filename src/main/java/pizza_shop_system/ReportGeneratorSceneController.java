package pizza_shop_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportGeneratorSceneController implements Initializable {

    @FXML
    private ChoiceBox<String> TimeSelectionChoiceBox = new ChoiceBox<>();

    private String[] timeChoices = {"Daily", "Weekly", "Monthly"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TimeSelectionChoiceBox.getItems().addAll(timeChoices);
        TimeSelectionChoiceBox.setOnAction(this::getTimeSelection);
    }

    public String getTimeSelection(ActionEvent event){
        return TimeSelectionChoiceBox.getValue();

    }

}
