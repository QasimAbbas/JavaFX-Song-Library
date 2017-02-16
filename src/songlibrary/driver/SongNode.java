/* Qasim Abbas netid: qsa1 and Cathy Pei netid: cwp33 */

package songlibrary.driver;

public class SongNode implements Comparable<SongNode>{

	SongNode node;
	
	public String songName = " ";
	public String artist = " ";
	public String album = " ";
	public String year = " ";
	
	public SongNode(String songName, String artist, String album, String year){
		this.songName = songName;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	@Override
	public int compareTo(SongNode o) {
		if(songName.trim().equalsIgnoreCase(o.songName.trim())){
			return artist.trim().compareTo(o.artist.trim());
		}
		
		return songName.toLowerCase().trim().compareTo(o.songName.toLowerCase().trim());
	}
	
}
