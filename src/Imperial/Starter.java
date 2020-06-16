package Imperial;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeSet;

import Assets.FullShiftList;
import scheduleAnalyzer.ScheduleAnalyzer;
import scheduleAnalyzer.WeeklyShiftSheet;

public class Starter {
	private final static DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("M.d");
	public static void main(String [] args) {

		
		ArrayList<String> paths = getPaths("C:/users/costi/Onedrive/Desktop/", "2019-03-04", "2019-03-10");
		TreeSet<WeeklyShiftSheet> r = ScheduleAnalyzer.getShiftSheetsFromPaths(paths);

		FullShiftList s = ScheduleAnalyzer.shiftMerge(r);
		System.out.println(s.namesToString());
		System.out.println(s.getNextAnniversary().getName() + " " + s.getNextAnniversary().getFirstShift());
	}

	/**
	 * Gets a list of files to be analyzed
	 * @param base the base file path
	 * @param start the beginning date
	 * @param end the ending date
	 * @return an ArrayList of Strings that have the file paths
	 */
	private static ArrayList<String> getPaths(String base, String start, String end){
		ArrayList<String> paths = new ArrayList<String>();

		String prefix = "Almaden Schedule ", ext = ".csv";
		LocalDate starting = LocalDate.parse(start), ending = LocalDate.parse(end);
		String currentPath = base + prefix + starting.format(FILE_DATE_FORMAT) + " to " + ending.format(FILE_DATE_FORMAT) + ext;
		File currentFile = new File(currentPath);

		while(currentFile.exists()){
			paths.add(currentPath);
			System.out.println(currentPath);
			starting = starting.plusDays(7);
			ending = ending.plusDays(7);
			currentPath = base + prefix + starting.format(FILE_DATE_FORMAT) + " to " + ending.format(FILE_DATE_FORMAT) + ext;
			currentFile = new File(currentPath);
		}
		return paths;
	}
}
