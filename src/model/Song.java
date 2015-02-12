package model;
/*
 * This version of Song was completed between lectures as
 * it would take too long.  It uses an ArrayList to keep
 * track of the all dates the song is played.  It also
 * added a pretendTheDateHasChanged method to simulate the 
 * two most recent plays were a day previous to the actual 
 * dates of plays.
 */

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Song  implements Serializable{
	public final static int MAX_PLAYS = 5;

	// Instance Variables
	private String title;
	private String fileName;
	private String artist;
	private int playsToday;
	private int lengthInSeconds;
	private GregorianCalendar mostRecentDate;

	public Song(String artist, String title, int length, String fileName) {
		this.title = title;
		this.fileName = fileName;
		this.lengthInSeconds = length;
		this.artist = artist;
		//playsToday = 0;
		mostRecentDate = new GregorianCalendar(1970, 0, 1);
	}

	public String getTitle() {
		return title;
	}

	public String getAudioFileName() {
		return fileName;
	}
	
	public int getLengthInSeconds(){
		return lengthInSeconds;
	}
	
	public String getArtist(){
		return artist; 
	}

	public boolean canPlay() {
		return playsToday < MAX_PLAYS;
	}

	public void play() {
		GregorianCalendar today = new GregorianCalendar();
		if (!sameDay(today, this.mostRecentDate)) {
			playsToday = 1;
			mostRecentDate = today;
		} else {
			if (canPlay()) {
				playsToday++;
			}
		}
	}
	public int getPlaysToday(){
		return playsToday;
	}

	private boolean sameDay(GregorianCalendar today, GregorianCalendar other) {
		return today.get(Calendar.YEAR) == other.get(Calendar.YEAR)
				&& today.get(Calendar.MONTH) == other.get(Calendar.MONTH)
				&& today.get(Calendar.DAY_OF_MONTH) == other
						.get(Calendar.DAY_OF_MONTH);
	}

	public void pretendTheDateHasChanged() {
		mostRecentDate.add(Calendar.DATE, 1);
	}

}
