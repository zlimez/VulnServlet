package JamesChiu;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String myName;
	private String password;
	private LocalDateTime timeCreated;
	
	public UserInfo(String name, String pw) {
		myName = name;
		password = pw;
		timeCreated = LocalDateTime.now();
	}
	
	public void changePassword(String pw, String newPw) {
		if (this.password == pw) {
			if (newPw != "") {
				this.password = newPw;
			}
		}
	}
	
	public String[] getAccountInfo() {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		return new String[] {this.myName, this.timeCreated.format(formatter)};
	}
	
	
}
