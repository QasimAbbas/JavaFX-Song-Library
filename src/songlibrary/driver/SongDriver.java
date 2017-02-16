/* Qasim Abbas netid: qsa1 and Cathy Pei netid: cwp33 */

package songlibrary.driver;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import songlibrary.view.*;

public class SongDriver {
	
	private FileWriter filewriter;
	
	public List<SongNode> Add(List<SongNode> songList, String song, String artist, String album, String year) throws IOException {
	
		
		SongNode addSong = new SongNode(song, artist, album, year);
		songList.add(addSong);
				
		save(songList);
		
		return songList;
		
	}
	
	public List<SongNode> load() throws IOException{
		
		List<SongNode> songList = new ArrayList<SongNode>();
		SongLibraryController control = new SongLibraryController();
		
		List<String> currLine = new ArrayList<String>();
	
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader("songs.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
			    currLine.add(line);
			}
			
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		
		
		for(String word: currLine){
			SongNode song = control.StringToSongNode(word);
			songList.add(song);
		}
		
		Collections.sort(songList);
		return songList;
	}
	
	public void save(List<SongNode> songList) throws IOException{
		
		try {

			  File file = new File("songs.txt");
			  filewriter = new FileWriter(file);
			  
		      if (file.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		         Collections.sort(songList);
				
				for(SongNode song1: songList){
					String prep = song1.songName + "\t" + song1.artist + "\t" + song1.album + "\t" + song1.year + "\n";
					
					////System.out.println(prep);
					filewriter.append(prep);
					filewriter.flush();
					
				}
		      }

	    	} catch (IOException e) {
		      e.printStackTrace();
		}
		
		
		
		filewriter.close();
		
	}
	
	public List<SongNode> Edit(List<SongNode> songList, String field,String name, String artist, String album, String year) throws IOException{
		for(SongNode song: songList){
			
			
			if(song.songName.compareTo(field) == 0){
				song.songName = name;
				song.artist = artist;
				song.album = album;
				song.year = year;
				
				
			}
		}		
		save(songList);

		return songList;
	}
		
		
	public List<SongNode> Delete(List<SongNode> songList, String field) throws IOException{
		for(int i = 0; i < songList.size(); i++){
			System.out.println("IN SONG NODE DELETE WITH: " + field);
			if(songList.get(i).songName.equals(field)){
				
				songList.remove(songList.get(i));
				
			
			}
		}
		save(songList);
		return songList;
	}
}
