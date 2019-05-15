package weather;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Main extends Application
{

	@Override
	public void start(Stage primaryStage)
	{
		try 
		{
			
			FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("MainWindow.fxml"));
			MainWindowController cont = loader.getController();
			
			Scene scene = new Scene(loader.load());			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Weather");
			primaryStage.show();
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}

	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
