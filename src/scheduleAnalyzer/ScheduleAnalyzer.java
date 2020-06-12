package scheduleAnalyzer;

import java.sql.Time;

import java.util.ArrayList;
import java.util.TreeSet;

import Assets.TimeCardRow;
import scheduleReader.ScheduleInterpreterA;
import Assets.FullShiftList;

public class ScheduleAnalyzer {

	/**
	 * Converts paths of a CSV Schedule into a WeeklwyShiftSheet object in bulk
	 * @param paths the paths of the CSV Schedules
	 * @return A TreeSet containing the WeekleyShiftSheet objects that represent the schedules
	 */
	public static TreeSet<WeeklyShiftSheet> getShiftSheetsFromPaths(ArrayList<String> paths) {
		TreeSet<WeeklyShiftSheet> fullList = new TreeSet<>();
		for(String path : paths) {
			WeeklyShiftSheet sheet = ScheduleInterpreterA.convertToTimeArray(path);
			fullList.add(sheet);
		}
		return fullList;
	}

	
	 /**
	 * Gets the WeekleyShiftSheets and creates an ArrayList that contains a full list of employees and every shift they had
	 * @param fullList the list of WeekleyShiftSheets that comes from getShiftsFromPaths
	 * @return an ArrayList containing the shifts each person had 
	 */
	public static FullShiftList shiftMerge(TreeSet<WeeklyShiftSheet> fullList){
		FullShiftList mergedShifts = new FullShiftList();
		//itterate through the time cards for each week, check if they are already in the list, then either add them to an existing TimeCard row or make a new entry
		for(WeeklyShiftSheet selectedSheet : fullList){
			for(TimeCardRow selectedRow : selectedSheet){
				//Check for an existing entry in the list
				boolean alreadyExists = false;
				for(TimeCardRow existing : mergedShifts){
					if(selectedRow.getName().equals(existing.getName())){
						existing.merge(selectedRow);
						alreadyExists = true;
						break;
					}
				}
				if(alreadyExists==false){
					mergedShifts.add(selectedRow);
				}
			}
		}
		return mergedShifts;
	}
}
