package Assets;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.TreeSet;

public class FullShiftList extends TreeSet<TimeCardRow> {
    /**
     * Gets the first person with the specific given name
     * @param name the name of the person you want to get
     * @return the TimeCardRow that you want to get
     */
    public TimeCardRow get(String name){
        for(TimeCardRow r : this){
            if(r.getName().equalsIgnoreCase(name)){
                return r;
            }
        }
        return null;
    }

    /**
     * Combines two TimeCardRows, intended use is for changes in the spelling of someone's name
     * @param hostName the reciever TimeCardRow
     * @param insertionName the sending TimeCardRow
     */
    public void mergeTimeCards(String hostName, String insertionName){
        TimeCardRow host = this.get(hostName), insert = this.get(insertionName);
        if(host != null && insert != null){
            host.merge(insert);
            this.remove(insert);
        }else{
            System.out.println("FAILED TO MERGE. Host found: " + !(host == null) + " and insert found: " + !(insert == null) + "");
        }
    }

    /**
     * Returns all names that are in this object
     * @return all names
     */
    public String namesToString(){
        String sum = "";
        for(TimeCardRow r : this){
            sum += r.getName() + "\t";
        }
        return sum;
    }

    /**
     * gets the first and last dates of timecards an employee enrolled in
     * @param employee a TimeCardRow to be analyzed
     * @return a String containing the two dates of the first date of the first time card and the last date of the last time card
     */
    public String getEmploymentRange(TimeCardRow employee){
        return employee.getFirstDay().toString() + "-" + employee.getLastDay().toString();
    }

    public TimeCardRow getNextAnniversary(){
        LocalDate today = LocalDate.now();
        TimeCardRow currentClosest = this.first();
        int daysUntil = 366;

        for(TimeCardRow r : this){
            if(!(r.getFirstShift() == null)){
                LocalDate dateNextYear = LocalDate.parse((today.getYear()+1) + r.getFirstShift().getDate().toString().substring(4));
                long days = Math.abs(dateNextYear.until(today, ChronoUnit.DAYS));

                if(days < daysUntil && !r.getFirstDay().equals(LocalDate.parse("2019-03-04"))){
                    System.out.println("Now " + days);
                    currentClosest = r;
                    daysUntil = (int) days;
                }
            }
        }
        return currentClosest;
    }
} 