package scheduleReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

import Assets.Day;
import Assets.TimeCardRow;
import scheduleAnalyzer.WeeklyShiftSheet;

public class ScheduleInterpreterA {
	private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M/d/yyyy");
	
	/**
	 * Converts a path into a WeekleyShifrSheet
	 * @param row the path of the schedule v1
	 * @return an object representing the same values, but in an object format
	 */
	public static WeeklyShiftSheet convertToTimeArray(String path) {

		WeeklyShiftSheet fullTimeCard = new WeeklyShiftSheet();
		String[][] sheet = CSVReader.convertToArray(path);
		boolean isManager = false;
		for(String[] row : sheet) {
			if(row[0].contains("MIC")){
				isManager = true;
			}
			if(rowCheck(row)) {
				String name = row[0].trim();
				TreeSet<Day> times = getTime(row, sheet[0]);
				fullTimeCard.add(new TimeCardRow(name,isManager,times));
			}
		}
		return fullTimeCard;
	}
	private static TreeSet<Day> getTime(String[] row, String[] header) {
		TreeSet<Day> shifts = new TreeSet<>();
		for(int i = 2; i <= 14; i += 2){
			LocalDate date = LocalDate.parse(header[i-1].substring(header[i-1].indexOf(' ') + 1) + "/2019", DATE_FORMAT);
			String shift;
			if(row[i].length() > 0 && !row[i].contains("w/")){
				shift = row[i];
			}else{
				shift = row[i-1];
			}
			shifts.add(new Day(date, convertToFormal(shift)));
		}
		return shifts;
	}
	
	/**
	 * Checks to see if a row is meant to show shifts or for something else
	 * @param row the row to be checked
	 * @return a boolean on if the row contains shifts
	 */
	private static boolean rowCheck(String[] row) {
		//new line
		if(row.length == 0) {
			return false;
		}
		//first row, and dividers
		if(row[0].length() == 0 || row[1].contains("Monday")){
			return false;
		}
		//initials (not sure what this is)
		if(row[0].equals("MIC")) {
			return false;
		}
		//positions
		if(row[0].equals("Grill") || row[0].equals("Register") || row[0].equals("Dressing") || row[0].equals("Fries")) {
			return false;
		}
		//ending
		if(row[0].equals("Bodies") || Character.isDigit(row[0].charAt(0)) || row[0].equals("Total Hours") || row[0].contains("Projected")) {
			return false;
		}
		return true;
	}

	/**
	 * Re formats the raw data in cells in order to convert them to LocalDate
	 * @param shift the raw cell contents
	 * @return a String that has the reformatted time
	 */
	private static String convertToFormal(String shift){
		//remove suffixes
		if(shift.contains("cl") || shift.contains("CL")){
			shift = shift.substring(0, shift.length()-2) + "11";
		}
		if(shift.contains("pm") || shift.contains("PM")){
			shift = shift.substring(0, shift.length()-2);
		}

		//add colons if needed
		if(shift.contains("-") && shift.indexOf('-') == 4){
			shift = shift.substring(0, 2) + ":" + shift.substring(2);
		}
		if(shift.contains("-") && shift.indexOf('-') == 3){
			shift = shift.substring(0, 1) + ":" + shift.substring(1);
		}
		if(shift.contains("-") && shift.length() - shift.indexOf('-') == 5){
			shift = shift.substring(0, shift.length()-2) + ":" + shift.substring(shift.length()-2);
		}
		if(shift.contains("-") && shift.length() - shift.indexOf('-') == 4){
			shift = shift.substring(0, shift.length()-2) + ":" + shift.substring(shift.length()-2);
		}

		//add :00 if needed
		if(shift.contains("-") && shift.indexOf('-') == 1){
			shift = shift.substring(0, 1) + ":00" + shift.substring(1);
		}
		else if(shift.contains("-") && shift.indexOf('-') == 2){
			shift = shift.substring(0, 2) + ":00" + shift.substring(2);
		}

		if(shift.contains("-") && shift.charAt(shift.length()-2) == '-'){
			shift = shift.substring(0, shift.length()) + ":00" + shift.substring(shift.length());
		}
		else if(shift.contains("-") && shift.charAt(shift.length()-3) == '-'){
			shift = shift.substring(0, shift.length()) + ":00";
		}

		//no shift
		if(shift.equalsIgnoreCase("off")){
			shift = "OFF";
		}
		if(shift.equalsIgnoreCase("r/o")){
			shift = "Requested off";
		}
		return shift;

	}
}
