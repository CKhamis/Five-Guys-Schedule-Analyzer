package Assets;

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

    public void mergeTimeCards(String hostName, String insertionName){
        TimeCardRow host = this.get(hostName), insert = this.get(insertionName);
        if(host != null && insert != null){
            host.merge(insert);
            this.remove(insert);
        }else{
            System.out.println("FAILED TO MERGE. Host found: " + !(host == null) + " and insert found: " + !(insert == null) + "");
        }
    }

    public String namesToString(){
        String sum = "";
        for(TimeCardRow r : this){
            sum += r.getName() + "\t";
        }
        return sum;
    }

    public String getEmploymentRange(TimeCardRow employee){
        return employee.getFirstDay().toString() + "-" + employee.getLastDay().toString();
    }
} 