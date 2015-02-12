package view;

/**
 * @author James DeVeuve & Tanner Koch
 * TA: Mike Collins & Ben Whitely
 * JukeBox Iteration 1
 * 
 * This class is designed to provide a JukeBoxGUI object that extends observable.  The
 * goal of this class is to provide the GUI framework for the program.  The GUI
 * displays a JTable on the left side of the display and a list of the queued songs
 * on the left side of the display.  The JTable displays all available songs and when
 * clicked, the clicked song will move to the queue.  The play button will then play
 * the songs that are in the queue.  
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.PlaySong;
import model.Song;
import model.SongList;
import model.Student;
import model.StudentList;

public class JukeBoxGUI implements Serializable {
	private JTable table;
	private SongList songList;
	private Song song;
	private static JFrame frame;
	private String baseDir = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "songfiles"
			+ System.getProperty("file.separator");

	private static JTextArea text;
	private JTextField currentSong;
	private static JTextArea bottom;
	ArrayList<Song> playList;

	/*
	 * Todo List; comments
	 * dont have to click ok on joption pane
	 * start with previous state
	 * 
	 */
	private Song BlueRidgeMountain = new Song("Ralph Schuckett",
			"Blue Ridge Mountain Mist", 38, baseDir
					+ "BlueRidgeMountainMist.mp3");
	private Song DeterminedTumbao = new Song("FreePlay Music",
			"Determined Tumbao", 20, baseDir + "DeterminedTumbao.mp3");
	private Song Flute = new Song("Sun Microsystems", "Flute", 5, baseDir
			+ "flute.aif");
	private Song SpaceMusic = new Song("Unknown", "Space Music", 6, baseDir
			+ "spacemusic.au");
	private Song SwingCheese = new Song("FreePlay Music", "Swing Cheese", 15,
			baseDir + "SwingCheese.mp3");
	private Song Tada = new Song("Microsoft", "Tada", 2, baseDir + "tada.wav");
	private Song UntameableFire = new Song("Pierre Langer", "Untameable Fire",
			282, baseDir + "UntameableFire.mp3");
	private JButton button = new JButton("Play");
	static Student currentStudent;
	static String line = "";

	public JukeBoxGUI() {
		frame = new JFrame();
		text = new JTextArea();
		songList = new SongList();
		playList = new ArrayList<Song>();
		bottom = new JTextArea();
		songList.add(BlueRidgeMountain);
		songList.add(DeterminedTumbao);
		songList.add(Flute);
		songList.add(SpaceMusic);
		songList.add(SwingCheese);
		songList.add(Tada);
		songList.add(UntameableFire);
		table = new JTable(songList);
		table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));
		// set frame layout
		// set frame layout
		frame.setLayout(new GridLayout(1, 2));
		frame.addWindowListener(new SaveDataListener());

		// left side
		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());

		table.addMouseListener(new MouseListener());

		// add the table to the center of the left side
		left.add(new JScrollPane(table), BorderLayout.CENTER);

		// top = centered label
		JPanel top = new JPanel();
		top.add(new JLabel("Song Table"));

		bottom.setText("User ID:" + currentStudent.getId() + "	"
				+ "Time Left: h:" + currentStudent.getHoursLeft() + " m:"
				+ currentStudent.getMinutesLeft() + " s:"
				+ currentStudent.getDisplaySecondsLeft() + "	"
				+ "Plays Left Today:" + currentStudent.playsLeft());
		bottom.setEditable(false);

		// add centered label to top of left side
		left.add(top, BorderLayout.NORTH);
		left.add(bottom, BorderLayout.SOUTH);

		frame.add(left);

		// right side
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout());
		// centered label in the north of right side
		JPanel north = new JPanel();
		north.add(new JLabel("Song Queue"));
		text.setEditable(false);
		currentSong = new JTextField(12);
		currentSong.setText("No Song Playing");
		currentSong.setEditable(false);
		right.add(new JScrollPane(text), BorderLayout.CENTER); // add the list
																// to the center
																// of the right
																// side
		right.add(north, BorderLayout.NORTH);
		button.addActionListener(new ButtonActionListener());
		right.add(button, BorderLayout.SOUTH);

		frame.add(right);

		frame.setSize(800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		

	}

	private class MouseListener extends MouseAdapter {
		int row;
		int column;
		String title;
		String artist;
		int length;

		public void mouseClicked(MouseEvent e) {
			JTable temp = (JTable) e.getSource();
			String line = "";
			row = temp.getSelectedRow();
			column = temp.getSelectedColumn();
			artist = (String) songList.getValueAt(row, 0);
			title = (String) songList.getValueAt(row, 1);
			length = (Integer) songList.getValueAt(row, 2);
			if (artist.equals(BlueRidgeMountain.getArtist())
					&& title.equals(BlueRidgeMountain.getTitle())) {
				song = BlueRidgeMountain;
			} else if (artist.equals(DeterminedTumbao.getArtist())
					&& title.equals(DeterminedTumbao.getTitle())) {
				song = DeterminedTumbao;
			} else if (artist.equals(Flute.getArtist())
					&& title.equals(Flute.getTitle())) {
				song = Flute;
			} else if (artist.equals(SpaceMusic.getArtist())
					&& title.equals(SpaceMusic.getTitle())) {
				song = SpaceMusic;
			} else if (artist.equals(SwingCheese.getArtist())
					&& title.equals(SwingCheese.getTitle())) {
				song = SwingCheese;
			} else if (artist.equals(Tada.getArtist())
					&& title.equals(Tada.getTitle())) {
				song = Tada;
			} else if (artist.equals(UntameableFire.getArtist())
					&& title.equals(UntameableFire.getTitle())) {
				song = UntameableFire;
			}
			playList.add(song);
			for (int i = 0; i < playList.size(); i++) {
				line += playList.get(i).getTitle() + " by "
						+ playList.get(i).getArtist() + "\n";
				text.setText("");
				text.setText(line);

			}
			bottom.setText("User ID:" + currentStudent.getId() + "	"
					+ "Time Left: h:" + currentStudent.getHoursLeft() + " m:"
					+ currentStudent.getMinutesLeft() + " s:"
					+ currentStudent.getDisplaySecondsLeft() + "	"
					+ "Plays Left Today:" + currentStudent.playsLeft());
		}
	}

	private class ButtonActionListener implements ActionListener {
		PlaySong ps = new PlaySong(currentStudent);
		public void actionPerformed(ActionEvent e) {
			if (currentStudent.canPlay()) {
				ps.update(playList);
				playList.remove(0);
			} else {
				JOptionPane.showMessageDialog(frame,
						"You can't play anymore tracks today");
			}

		}
	}


	public ArrayList<Song> getList() {
		return playList;
	}

	public static void login() {
		String password;
		StudentList studentList = new StudentList();
		String id = JOptionPane.showInputDialog("Enter the Student's ID");
		JPasswordField p = new JPasswordField();

		int okCxl = JOptionPane.showConfirmDialog(null, p, "Enter Password",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		password = new String(p.getPassword());
		if ((okCxl == JOptionPane.OK_OPTION)
				&& (password.equals(studentList.getIds().get(id)))) {

			// pass the current student
			try {
				File file = new File(id + ".dat");
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					ObjectInputStream objectStream = new ObjectInputStream(fis);
					currentStudent = (Student) objectStream.readObject();
					objectStream.close();
				}
				if (currentStudent == null) {
					currentStudent = new Student(id, password);
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream obStream = new ObjectOutputStream(fos);
					obStream.writeObject(currentStudent);
					obStream.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} else if (okCxl == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(null, "Error! Invalid Input");
			login();
		}
	}

	public void saveData() {
		int x = JOptionPane.showConfirmDialog(null, "Save Data?");
		if (x == JOptionPane.OK_OPTION) {
			try {
				File file = new File(currentStudent.getId() + ".dat");
				if (file.canWrite()) {
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream objectStream = new ObjectOutputStream(
							fos);
					objectStream.writeObject(currentStudent);
					objectStream.close();
					System.out.println("System saved");
				} else {
					System.out.println("Didn't Save!");
				}
			} catch (IOException e) {

			}
		} else {
			System.out.println("Didn't Save");
		}
	}
	
	
	
	private class SaveDataListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			saveData();
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowOpened(WindowEvent e) {
		}

	}
	
	public static void saveFromStartup(){
		int e = JOptionPane.showConfirmDialog(null, "Start with previous saved state?");
		if(e == JOptionPane.NO_OPTION){
				File file = new File("Ali.dat");
				file.delete();
				 file = new File("Chris.dat");
				file.delete();
				 file = new File("River.dat");
				file.delete();
				 file = new File("Ryan.dat");
				file.delete();
				 file = new File("list.dat");
				file.delete();
				
			
		}
		
	}
	public static void updateSongScreen() {
		int j = 1;
		int index = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.substring(i, j).equals(System.getProperty("line.separator"))) {
				line = line.substring(j);
				text.setText(line);
				return;
			}
			j++;
		}
		
		
	}
	public static void currentSong(Song s) {
		JOptionPane.showMessageDialog(frame, s.getTitle()+" is playing");
		bottom.setText("User ID:" + currentStudent.getId() + "	"
				+ "Time Left: h:" + currentStudent.getHoursLeft() + " m:"
				+ currentStudent.getMinutesLeft() + " s:"
				+ currentStudent.getDisplaySecondsLeft() + "	"
				+ "Plays Left Today:" + currentStudent.playsLeft());
	}

	public static void main(String[] args) {
		JukeBoxGUI.saveFromStartup();
		JukeBoxGUI.login();
		JukeBoxGUI j = new JukeBoxGUI(); // to the jukebox in constructor
	}

}