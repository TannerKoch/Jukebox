package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StudentList {

	private Map<String , String> ids;
	private Student Ali;
	private Student Chris;
	private Student River;
	private Student Ryan;
	public StudentList(){
		this.ids = new HashMap<String, String>();
		Ali = new Student("Ali", "1111");
		ids.put("Ali", "1111");
		Chris = new Student("Chris", "2222");
		ids.put("Chris", "2222");
		River = new Student("River", "3333");
		ids.put("River", "3333");
		Ryan = new Student("Ryan", "4444");
		ids.put("Ryan", "4444");
		
		
	}

	public Map<String, String> getIds() {
		return ids;
	}

	public void setIds(Map<String, String> ids) {
		this.ids = ids;
	}


}
