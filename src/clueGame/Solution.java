package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	public void setPerson(String person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Solution [person=" + person + ", room=" + room + ", weapon=" + weapon + "]";
	}

	public String print() {
		String temp = person + ", " + room + ", " + weapon;
		return temp;
	}
	
	
}
