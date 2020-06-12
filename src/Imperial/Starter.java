package Imperial;

import java.util.ArrayList;
import java.util.TreeSet;

import Assets.FullShiftList;
import scheduleAnalyzer.ScheduleAnalyzer;
import scheduleReader.ScheduleInterpreterA;
import scheduleAnalyzer.WeeklyShiftSheet;

public class Starter {
	public static void main(String [] args) {
		ArrayList<String> paths = new ArrayList<String>();
		paths.add("C:/users/costi/Onedrive/Desktop/Almaden Schedule 3.4 to 3.10.csv");
		paths.add("C:/users/costi/Onedrive/Desktop/Almaden Schedule 3.11 to 3.17.csv");
		paths.add("C:/users/costi/Onedrive/Desktop/Almaden Schedule 3.18 to 3.24.csv");
		paths.add("C:/users/costi/Onedrive/Desktop/Almaden Schedule 3.25 to 3.31.csv");
		paths.add("C:/users/costi/Onedrive/Desktop/Almaden Schedule 4.1 to 4.7.csv");
		paths.add("C:/users/costi/Onedrive/Desktop/Almaden Schedule 4.8 to 4.14.csv");
		paths.add("C:/users/costi/Onedrive/Desktop/Almaden Schedule 4.15 to 4.21.csv");
		TreeSet<WeeklyShiftSheet> r = ScheduleAnalyzer.getShiftSheetsFromPaths(paths);

		FullShiftList s = ScheduleAnalyzer.shiftMerge(r);
	}
}
