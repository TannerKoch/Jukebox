package model;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;

import songplayer.SongPlayer;
import view.JukeBoxGUI;

/*
 * Update the queue as songs are clicked
 * 
 * Play the songs not overlapping
 * 
 * Can't play same song 5 times in one session.
 * 
 * 
 * 
 */

public class PlaySong {
	private ArrayList<Song> list;
	SongPlayer sp;
	Student thisStudent;

	public PlaySong(Student student) {
		list = new ArrayList<Song>();
		thisStudent = student;
	}

	public void update(Object arg1) {
		list = (ArrayList<Song>) arg1;
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).canPlay() && thisStudent.canPlay()) {
				list.get(i).play();
				thisStudent.play(list.get(i));
				System.out.println(list.get(i).getTitle() + " by "
						+ list.get(i).getArtist() + " is playing.");
				Play(list.get(i));
				System.out.println(list.get(i).getPlaysToday());
				
			} else if (!list.get(i).canPlay()) {
				System.out.println("Can't play that song anymore");
				
			} else if (!thisStudent.canPlay()) {
				System.out.println("You're out of song choices for the day");
				return;
			}
			try {
				//JukeBoxGUI.currentSong(list.get(i))
				JukeBoxGUI.currentSong(list.get(i));
				Thread.sleep((list.get(i).getLengthInSeconds() * 1000) + 1000);
				JukeBoxGUI.updateSongScreen();
				//list.remove(i);
				File file = new File("list.dat");
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream obStream = new ObjectOutputStream(fos);
				obStream.writeObject(list);
				obStream.close();
				// need to deque the songs as theyre played.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void Play(Song s) {

		sp.playFile(s.getAudioFileName());
	}

}
