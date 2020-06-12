package Assets;

import java.time.LocalDate;
import java.util.TreeSet;

public class TimeCardRow extends TreeSet<Day> implements Comparable<TimeCardRow> {
	private String name;
	private boolean isMinor,  isManager;

	public TimeCardRow(String name, boolean isManager, TreeSet<Day> week) {
		super(week);
		this.name = name;
		this.isManager = isManager;
		if (name.contains("*")) {
			isMinor = true;
			this.name = name.substring(0, name.length() - 1);
		} else {
			isMinor = false;
		}
	}
	public void merge(TimeCardRow other){
		for(Day r : other){
			this.add(r);
		}

		if(this.isManager != other.isManager && this.first().getDate().isBefore(other.first().getDate())){
			this.isManager = other.isManager;
		}

		if(this.isMinor != other.isMinor && this.first().getDate().isBefore(other.first().getDate())){
			this.isMinor = other.isMinor;
		}
	}

	public String getName() {
		return name;
	}

	public boolean isMinor() {
		return isMinor;
	}
	public boolean isManager(){
		return isManager;
	}

	public Day getShift(LocalDate date) {
		for(Day r : this){
			if(date.equals(r.getDate())){
				return r;
			}
		}
		return null;
	}

	public LocalDate getFirstDay() {
		return this.first().getDate();
	}
	public LocalDate getLastDay() {
		return this.last().getDate();
	}

	public int hashCode() {
		return this.name.hashCode() + this.first().getDate().hashCode() + this.last().getDate().hashCode();
	}

	public boolean equals(Object other){
		return (this.hashCode() == other.hashCode());
	}

	public int compareTo(TimeCardRow other) {
		int comp1 = this.getName().compareTo(other.getName());
		if(comp1 == 0){
				int comp2 = this.getFirstDay().compareTo(other.getFirstDay());
				if(comp2 == 0){
					if(this.isMinor == other.isMinor){
					return 0;
					} else if(this.isMinor == true && other.isMinor == false){
					return -1;
					} else{
					return 1;
					}
			}else{
				return comp2;
			}
		}else{
			return comp1;
		 }
	}
	public String toString(){
		String val =  this.name + " |\t" + this.isMinor + " ";
		for(Day i : this){
			val += i.getShift() + "   ";
		}
		return val;
	}
}
