package scheduleAnalyzer;

import java.time.LocalDate;
import java.util.ArrayList;
import Assets.TimeCardRow;

public class WeeklyShiftSheet extends ArrayList<TimeCardRow> implements Comparable<WeeklyShiftSheet>{
	public LocalDate getFirstDate() {
		if(this.size() > 0) {
			return this.get(0).getFirstDay();
		}else {
			return null;
		}
		
	}
	public LocalDate getLastDate() {
		if(this.size() > 0) {
			return this.get(0).getLastDay();
		}else {
			return null;
		}
	}

	public int compareTo(WeeklyShiftSheet other) {
		return this.getFirstDate().compareTo(other.getFirstDate());
	}
	
	public String toString(){
		String sum = "################# Shift from " + this.getFirstDate() + " - " + this.getLastDate() + "################\n";
		for(TimeCardRow r : this){
			sum += (r.toString() + "\n");
		}
		return sum;
	}
}
