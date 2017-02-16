/* Qasim Abbas netid: qsa1 and Cathy Pei netid: cwp33 */

package songlibrary.app;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

//import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import songlibrary.driver.SongDriver;
import songlibrary.driver.SongNode;


public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlibrary/view/songlayout.fxml"));
		
		Pane root = (Pane)loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	
	public static void main(String[] args) throws IOException {
		SongDriver driver = new SongDriver();
		
		for(SongNode song: driver.load()){
			System.out.println("LOADING: " + song.songName);
		}
		
//		
//		List<SongNode> songList = new ArrayList<SongNode>();
		
//		songList = driver.Add(songList, "Campfire Song2", "Spongebob", "Bikini Bottom Hits", "");
//		songList = driver.Add(songList, "Campfire Song1", "Spongebob", "Bikini Bottom Hits", "");
//		songList = driver.Add(songList, "Campfire Song", "Spongebob", "Bikini Bottom Hits", "");
//		
//		songList = driver.Edit(songList, "Campfire Song", "Kruty Krab Pizza", "Spongebob", "Bikini Bottom", "2011");
//		songList = driver.Delete(songList, "Campfire Song2");
		
		
		
		launch(args);
	}

}