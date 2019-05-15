package weather;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MainWindowController
{
	//features
	@FXML
    private AnchorPane pane;
	@FXML
	private TextField cityBox;
	@FXML
	private TextField countryBox;
	@FXML
	private ChoiceBox<String> unitBox;
	@FXML
	private Button goButton;
	@FXML
	private Text errorCode;
	
	//dates
	@FXML
	private Text date1;
	@FXML
	private Text date2;
	@FXML
	private Text date3;
	@FXML
	private Text date4;
	@FXML
	private Text date5;
	
	//current weather
	@FXML
	private Text currTemp;
	@FXML
	private Text currHum;
	@FXML
	private Text currWind;
	@FXML
	private Text currDesc;
	
	//list of forecast texts
	//If FXML has a better way of listing these,
	//I would very much like to hear about it.
	@FXML
	private Text morn1;
	@FXML
	private Text after1;
	@FXML
	private Text evening1;
	@FXML
	private Text night1;
	@FXML
	private Text hum1;
	@FXML
	private Text wind1;
	
	@FXML
	private Text morn2;
	@FXML
	private Text after2;
	@FXML
	private Text evening2;
	@FXML
	private Text night2;
	@FXML
	private Text hum2;
	@FXML
	private Text wind2;
	
	@FXML
	private Text morn3;
	@FXML
	private Text after3;
	@FXML
	private Text evening3;
	@FXML
	private Text night3;
	@FXML
	private Text hum3;
	@FXML
	private Text wind3;
	
	@FXML
	private Text morn4;
	@FXML
	private Text after4;
	@FXML
	private Text evening4;
	@FXML
	private Text night4;
	@FXML
	private Text hum4;
	@FXML
	private Text wind4;
	
	@FXML
	private Text morn5;
	@FXML
	private Text after5;
	@FXML
	private Text evening5;
	@FXML
	private Text night5;
	@FXML
	private Text hum5;
	@FXML
	private Text wind5;
	
	
	
    
    //selections of unit box
    ObservableList<String> units = FXCollections.observableArrayList("Fahrenheit", "Celsius", "Kelvin");
    
    //needed to set up selection box
    @FXML
	private void initialize()
	{
		unitBox.setItems(units);
		unitBox.getSelectionModel().selectFirst();
	}
    
    //collect data and make API call if valid
    //then display data
    @FXML
    void buttonPressed(ActionEvent event)
    {
    	String city = cityBox.getText();
    	String unit = (String)unitBox.getValue();
    	String country = countryBox.getText();
    	
    	//validate input
    	if(city.isEmpty() || country.isEmpty())
    	{
    		errorCode.setText("Error: Please enter in a city and country.");
    	}
    	else
    	{
    		errorCode.setText(""); //in case still there
    		String label;
    		//set unit to api style
    		if(unit.equals("Fahrenheit"))
    		{
    			unit = "imperial";
    			label = " °F";
    		}
    		else if(unit.equals("Celsius"))
    		{
    			unit = "metric";
    			label = " °C";
    		}
    		else
    		{
    			unit = "kelvin";
    			label = " °K";
    		}
    		
    		city = city + "," + country;
    		
    		//call
    		String[] dates = new String[5];
    		String[] forecast = new String[30];
    		String[] current = new String[4];
    		
    		boolean worked = WeatherData.weatherData(city, unit, dates, forecast, current, label);
    		
    		//check if it worked
    		if(!worked)
    		{
    			errorCode.setText("Error: Invalid city or country code.");
    			return;
    		}
    		//now update gui
    		
    		//dates
    		//values are passed back correct, not displaying...?
    		date1.setText(dates[0]);
    		date2.setText(dates[1]);
    		date3.setText(dates[2]);
    		date4.setText(dates[3]);
    		date5.setText(dates[4]);
    		
    		//current weather
    		currTemp.setText(current[0]+label);
    		currHum.setText(current[1]+"%");
    		currWind.setText(current[2]);
    		currDesc.setText(current[3]);
    		
    		//forecast
    		//please update this one day, it looks terrible lol
    		morn1.setText(forecast[0]);
    		after1.setText(forecast[1]);
    		evening1.setText(forecast[2]);
    		night1.setText(forecast[3]);
    		hum1.setText(forecast[4]+"%");
    		wind1.setText(forecast[5]);
    		
    		morn2.setText(forecast[6]);
    		after2.setText(forecast[7]);
    		evening2.setText(forecast[8]);
    		night2.setText(forecast[9]);
    		hum2.setText(forecast[10]+"%");
    		wind2.setText(forecast[11]);
    		
    		morn3.setText(forecast[12]);
    		after3.setText(forecast[13]);
    		evening3.setText(forecast[14]);
    		night3.setText(forecast[15]);
    		hum3.setText(forecast[16]+"%");
    		wind3.setText(forecast[17]);
    		
    		morn4.setText(forecast[18]);
    		after4.setText(forecast[19]);
    		evening4.setText(forecast[20]);
    		night4.setText(forecast[21]);
    		hum4.setText(forecast[22]+"%");
    		wind4.setText(forecast[23]);
    		
    		morn5.setText(forecast[24]);
    		after5.setText(forecast[25]);
    		evening5.setText("-");
    		night5.setText("-");
    		hum5.setText(forecast[28]+"%");
    		wind5.setText(forecast[29]);
    		
    		
    	}
    	
    	
    }
    
    
    
    
}
