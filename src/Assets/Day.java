package Assets;

import java.time.LocalDate;

public class Day implements Comparable<Day> {
	private LocalDate date;
	private String shift;
	
	public Day(LocalDate date, String shift) {
		this.date = date;
		this.shift = shift;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getShift() {
		return shift;
	}

	public boolean equals(Day other) {
		return this.compareTo(other) == 0;
	}
	public int compareTo(Day other) {
		return this.getDate().compareTo(other.getDate());
	}
	public int hashCode() {
		return date.hashCode()+shift.hashCode();
	}
	public String toString() {
		return date.toString() + " " + shift;
	}
}
