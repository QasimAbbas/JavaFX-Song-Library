/* Qasim Abbas netid: qsa1 and Cathy Pei netid: cwp33 */

package songlibrary.view;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import songlibrary.driver.*;

public class SongLibraryController {
	
	

	@FXML Button btnAdd;
	@FXML Button btnEdit;
	@FXML Button btnDelete;
	@FXML Button btnClose;

	@FXML TextField txtName;
	@FXML TextField txtArtist;
	@FXML TextField txtAlbum;
	@FXML TextField txtYear;
	
	@FXML ListView<String> listView;
	
	List<SongNode> songList;
	ObservableList<String> obsList;   
	SongDriver driver;
	int currentSong;
	
	@FXML
    public void initialize() throws IOException {
		
        System.out.println("LOADED");
        songList = new ArrayList<SongNode>();
        driver = new SongDriver();
        
        songList = driver.load();
        //currentSong = 0;
        
        update();
        
        
        listView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
           (obs, oldVal, newVal) -> 
               showCellDetail());
        
        
        
        update();
        listView.getSelectionModel().selectFirst();
    }
	
	public void showCellDetail(){
		//System.out.println("SHOW DETAIL:" + listView.getSelectionModel().getSelectedItem());
		currentSong = listView.getSelectionModel().getSelectedIndex();
		
		if(currentSong >= 0 && currentSong < songList.size()){
			SongNode song = songList.get(listView.getSelectionModel().getSelectedIndex());
		
			txtName.setText(song.songName.trim());
			txtArtist.setText(song.artist.trim());
			txtAlbum.setText(song.album.trim());
			txtYear.setText(song.year.trim());
		}
		
		
		System.out.print("AT POSITION: " +  currentSong);
		System.out.println("WITH: " +  listView.getSelectionModel().getSelectedIndex());

		
		update();
	}
	
	
	public void update(){
		
		obsList = FXCollections.observableArrayList();
		for(SongNode song: songList){
			nameDouble(song.songName);
			String prep = song.songName  + "\t" + " " + "\t" + " " + "\t" + " ";
			System.out.println(prep);
			obsList.add(prep);
		}
	
		
		listView.setItems(obsList);
		
		
	}
	
	public void update(SongNode lastAdded){
		
//		Dialog dialog = new Dialog(AlertType.INFORMATION);
//		   dialog.setTitle("Error");
//		   dialog.setHeaderText("No items to edit!");
//
//		   String content = "Need at least 1 item before you can edit!";
//
//		   alert.setContentText(content);
//		   alert.showAndWait();
		
		
		
		
		obsList = FXCollections.observableArrayList();
		for(SongNode song: songList){
			nameDouble(song.songName);
			String prep = song.songName + "\t" + " " + "\t" + " " + "\t" + " ";
			System.out.println(prep);
			obsList.add(prep);	
		}
	
		
		listView.setItems(obsList);
		
		for(int i = 0; i < songList.size(); i++) {
			System.out.println("UPDATE: entered loop");
			if (lastAdded.compareTo(songList.get(i)) == 0) {
				System.out.println("UPDATE: selected!");
				listView.getSelectionModel().select(i);
			}
		}
		
	}
	
	public void update(int i){
		
		obsList = FXCollections.observableArrayList();
		for(SongNode song: songList){
			nameDouble(song.songName);
			String prep = song.songName + "\t" + " " + "\t" + " " + "\t" + " ";
			System.out.println(prep);
			obsList.add(prep);
		}
	
		
		listView.setItems(obsList);
		
		
		if (i < songList.size()) {
			System.out.println("CURRENT");
			listView.getSelectionModel().select(i);
		} else {
			System.out.println("LAST NODE/FIRST NODE");
			listView.getSelectionModel().select(0);
		}
		
	}
	
	public String SongNodeToString(SongNode song){
		
		return song.songName + "\t" + song.artist + "\t" + song.album + "\t" + song.year;
	}
	
	public SongNode StringToSongNode(String song){
		String songDetail[] = {"","","",""};
		String songSplitter[] = song.split("\t");
		
		for(int i = 0; i < songSplitter.length; i++){
			
			songDetail[i] += songSplitter[i];
		}
	
		return new SongNode(songDetail[0], songDetail[1], songDetail[2], songDetail[3]);
		
	}
	
	public void nameDouble(String name){
		System.out.println("Duplicate Name Fix");
		for(int i = 0; i < songList.size(); i++){
			if(songList.get(i).songName.equals(name)){
				songList.get(i).songName = songList.get(i).songName.trim();
				for(int j = 0; j < i; j ++){
					songList.get(i).songName += " "; 
				}
			}
		}
		
	}
	
	public void actionAdd(ActionEvent e) throws IOException{
		System.out.println("ADD");
		
		if(e.getSource() == btnAdd && EmptyCheck(txtName.getText(), txtArtist.getText())) {
			System.out.println("Check if things were filled");
			
			String name = txtName.getText();
			String artist = txtArtist.getText();
			String album = txtAlbum.getText();
			String year = txtYear.getText();
			
			if(noDuplicateCheck(name, artist)){
				System.out.println("No Duplicates");
				
//				for(SongNode song: this.songList) {
//					if(song.equals(name)){
//						System.out.println("ADDING SONG");
//						this.songList = driver.Add(this.songList, name + " ", artist, album, year);
//						update();
//					} else {
						this.songList = driver.Add(this.songList, name, artist, album, year);
						nameDouble(name);
						
						SongNode select = new SongNode(name, artist, album, year);
						update(select);
						
						
//					}
				}
			}
			
			
	
			
		}
		

	
	public void actionEdit(ActionEvent e) throws IOException{
		
		
			if(e.getSource() == btnEdit) {
				System.out.println("EDIT");
				
				if(!listView.getSelectionModel().isEmpty() && EmptyCheck(txtName.getText(), txtArtist.getText())){
					System.out.println("Is not empty");
					
					SongNode song = StringToSongNode(listView.getSelectionModel().getSelectedItem());
					String field = song.songName;
			
					song.songName = txtName.getText();
					song.artist = txtArtist.getText();
					song.album = txtAlbum.getText();
					song.year = txtYear.getText();
			
			
						if(noDuplicateCheck(song.songName, song.artist) && isSelected()){
				
							try {
								this.songList = driver.Edit(songList, field, song.songName, song.artist, song.album, song.year);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					
						}
				}else{
					   System.out.println("Is not empty");
					   System.out.println("There are no items!");
					   
					   Alert alert = new Alert(AlertType.INFORMATION);
					   alert.setTitle("Error");
					   alert.setHeaderText("No items to edit!");

					   String content = "Need at least 1 item before you can edit!";

					   alert.setContentText(content);
					   alert.showAndWait();
					
				}
			
		}
		
		update();
	
		
	}
	
	public void actionDelete(ActionEvent e) throws IOException{
		//System.out.println("DELETE");
		
		if(e.getSource() == btnDelete) {
			int i = 0;
			//System.out.println("DELETING: " + field);
			if(!listView.getSelectionModel().isEmpty()){
				
				System.out.println(listView.getSelectionModel().getSelectedItem());
				SongNode song = StringToSongNode(listView.getSelectionModel().getSelectedItem());
				
				i = listView.getSelectionModel().getSelectedIndex();
				
				String field = song.songName;
				
				if(isSelected()){
					this.songList = driver.Delete(this.songList, field);
				}
				
				if(songList.isEmpty()){
					txtName.setText("");
					txtArtist.setText("");
					txtAlbum.setText("");
					txtYear.setText("");
				}
				
			} else {
				   Alert alert = new Alert(AlertType.INFORMATION);
				   alert.setTitle("Error");
				   alert.setHeaderText("No items to delete!");

				   String content = "Need at least 1 item before you can delete!";

				   alert.setContentText(content);
				   alert.showAndWait();
			}
			update(i);
		}
		
		
		
	}
	
	public void actionClose(ActionEvent e){
		if(e.getSource() == btnClose){
			Platform.exit();
	        System.exit(0);
		}
		
	}
	
	public boolean noDuplicateCheck(String name, String artist){
		for(SongNode song: songList){
			
			if(song.songName.equalsIgnoreCase(name)){
				if(song.artist.equalsIgnoreCase(artist)){
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error");
					alert.setHeaderText(
							   "Song already exists!");

					   String content = "Name: " + 
							   name + 
					   "\nArtist: " + 
					   artist;

					   alert.setContentText(content);
					   alert.showAndWait();
					return false;
				} else {
					
				}
			}
		}
		
		return true;
	}
	
	public boolean EmptyCheck(String name, String artist){
		if(name.isEmpty() || artist.isEmpty()){
			Alert alert = new Alert(AlertType.INFORMATION);
			  alert.setTitle("Error");
			   alert.setHeaderText(
					   "No Song or Artist!");

			   String content = "Song and artist are necessary for adding/editing!";

			   alert.setContentText(content);
			   alert.showAndWait();
			
			return false;
		}
		
		
		return true;
	}

	public boolean isSelected(){
		//System.out.println(listView.getSelectionModel().getSelectedItems());
		
		if(currentSong < 0 || listView.getSelectionModel().getSelectedItems() == null){
			
				Alert alert = new Alert(AlertType.INFORMATION);
			  alert.setTitle("Error");
			   alert.setHeaderText(
					   "Nothing Selected!");

			   String content = "Please select a song to Edit or Delete!";

			   alert.setContentText(content);
			   alert.showAndWait();
			
			return false;
		}
		
		return true;
	}
	
	
	
}
