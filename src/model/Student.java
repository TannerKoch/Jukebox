package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*need student to have a username and 
 * password.. also need to have a counter of the minutes that 
 * have been used and needs to know how many songs this user has played
 * (1500 minutes and 2 songs a day)
 * 
 */

public class Student implements Serializable {

	public final static int MAX_PLAYS = 2;

	private String id;
	private String password;
	private int secondsLeft;
	private int playsToday;
	private GregorianCalendar mostRecentDate;

	public Student(String id, String password) {
		this.id = id;
		this.password = password;
		this.secondsLeft = 90000; // 1500 minutes
		this.playsToday = 0;
		
	}
	
	public int playsLeft(){
		return 2 - this.playsToday;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDisplaySecondsLeft() {
		int x = this.secondsLeft - (getHoursLeft() * 3600);
		x = x - getMinutesLeft() * 60;
		return x;
	}
	
	
	public int getSecondsLeft() {
		return secondsLeft;
	}

	public void setSecondsLeft(int secondsLeft) {
		this.secondsLeft = secondsLeft;
	}

	public int getMinutesLeft(){
		int x = this.secondsLeft - (getHoursLeft() * 3600);
		return x / 60;
	}
	public int getHoursLeft(){
		return this.secondsLeft / 3600;
	}

	public String passwordGivenId(String id) {
		return this.password;
	}

	public boolean canPlay() {
		return playsToday < MAX_PLAYS;
	}

	// figure out how to keep gregorian calendar date
	// it works when not considering the first day

	public void play(Song s) {
		GregorianCalendar today = new GregorianCalendar();
		if (this.mostRecentDate == null || !sameDay(today, this.mostRecentDate)) {
			playsToday = 1;
			mostRecentDate = today;
			setSecondsLeft(secondsLeft - s.getLengthInSeconds());
			
		} else {
			if (canPlay()) {
				setSecondsLeft(secondsLeft - s.getLengthInSeconds());
				playsToday++;
			
			}
		}
	}

	private boolean sameDay(GregorianCalendar today, GregorianCalendar other) {
		return today.get(Calendar.YEAR) == other.get(Calendar.YEAR)
				&& today.get(Calendar.MONTH) == other.get(Calendar.MONTH)
				&& today.get(Calendar.DAY_OF_MONTH) == other
						.get(Calendar.DAY_OF_MONTH);
	}

	public void pretendTheDateHasChanged() {
		if (mostRecentDate != null) {
			mostRecentDate.add(Calendar.DATE, 1);
		} else {
			System.out.println("Flag");
		}
	}

}
