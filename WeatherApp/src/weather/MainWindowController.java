package weather;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindowController
{
	@FXML
    private AnchorPane anchorPane;
	private TextField cityBox;
	private ChoiceBox<String> countryBox;
	private ChoiceBox<String> unitBox;
	private Button goButton;
	
    private Stage stage;
}
