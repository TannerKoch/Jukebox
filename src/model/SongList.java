package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


public class SongList implements ListModel, TableModel, Serializable {

	private ArrayList<Song> songList;
	private LinkedList<TableModelListener> tableModelListeners;
	private LinkedList<ListDataListener> listDataListeners;
	
	public SongList() {
		songList = new ArrayList<Song>();
		tableModelListeners = new LinkedList<TableModelListener>();
		listDataListeners = new LinkedList<ListDataListener>();
	}
	
	public void add(Song song){
		songList.add(song);
		changed();
	}
	
	public void remove(Song song){
		songList.remove(song);
		changed();
	}
	
	private void changed() {
		for (ListDataListener l : listDataListeners) {
			l.contentsChanged(new ListDataEvent(this,
					ListDataEvent.CONTENTS_CHANGED, 0, songList.size()));
		}
		for(TableModelListener l : tableModelListeners){
			l.tableChanged(new TableModelEvent(this));
		}
	}

	@Override
	public int getRowCount() {
		return songList.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Artist";
		case 1:
			return "Title";
		case 2:
			return "Seconds";
		}
		return "error";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return Integer.class;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0: return songList.get(rowIndex).getArtist();
		case 1: return songList.get(rowIndex).getTitle();
		case 2: return songList.get(rowIndex).getLengthInSeconds();
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		return;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		tableModelListeners.add(l);
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		tableModelListeners.remove(l);
		
	}

	public int getSize() {
		// TODO Auto-generated method stub
		 return songList.size();
	}

	public Song getElementAt(int arg0) {
		if (arg0 < 9 || arg0 > songList.size()) {
			return null;
		} else
			return songList.get(arg0);
	}

	public void addListDataListener(ListDataListener l) {
		listDataListeners.add(l);
		
	}

	public void removeListDataListener(ListDataListener l) {
		listDataListeners.remove(l);
		
	}
	
}
