package A2Q2;

import java.util.*;

/**
 * Triages patients in Emergency Ward according to medical priority and wait time.
 * Priorities are positive integers;  the highest priority is 1. 
 * Normally patients are seen in priority order, however, if there are patients 
 * who have waited longer than a specified time (maxWait), they are seen first, 
 * in order of their arrival.  
 * @author Anton Sitkovets
 */
public class PatientTriage {

    private APQ<Patient> priorityHeap; //maintain patients in priority order
    private APQ<Patient> timeHeap;  //maintain patients in order of arrival
    private Time maxWait; //maximum waiting time

    /**
     * Constructor
     *
     * @param time Maximum wait time.  Patients waiting longer than this are seen first.
     */
    public PatientTriage(Time time) {
        Comparator<Patient> priorityComparator = new PatientPriorityComparator();
        Comparator<Patient> timeComparator = new PatientTimeComparator();
        Locator<Patient> priorityLocator = new PatientPriorityLocator();
        Locator<Patient> timeLocator = new PatientTimeLocator();
        priorityHeap = new APQ<>(priorityComparator, priorityLocator);
        timeHeap = new APQ<>(timeComparator, timeLocator);
        setMaxWait(time);
    }

   /**
     * Adds patient to queues.  
     * @param patient to add.
     */
     public void add(Patient patient) throws NullPointerException {
        if (patient == null) {
            throw new NullPointerException();
        }
        priorityHeap.offer(patient); //add to priority queue
        timeHeap.offer(patient); //add to arrival time queue
    }

   /**
     * Removes next patient in queue.  
     * @param currentTime used to determine whether to use priority or arrival time
     */
    public Patient remove(Time currentTime) throws NullPointerException, BoundaryViolationException {
    	Comparator<Time> timeComparator = new TimeComparator();
    	
    	if (priorityHeap.isEmpty() && timeHeap.isEmpty()){
    		throw new NullPointerException();
    	}
    	Time arrival = timeHeap.peek().getArrivalTime();
    	if( timeComparator.compare(arrival.elapsed(currentTime), getMaxWait()) > 0){
    		PatientPriorityLocator locator = new PatientPriorityLocator();
    		priorityHeap.remove(locator.get(timeHeap.peek()));
    		return timeHeap.poll();
    	}else{
    		PatientTimeLocator locator = new PatientTimeLocator();
            timeHeap.remove(locator.get(priorityHeap.peek()));
    		return priorityHeap.poll();
    	}
    }

   /**
     * @return maximum wait time
     */
    public Time getMaxWait() {
        return maxWait;
    }

   /**
    * Set the maximum wait time
     * @param time - the maximum wait time
     */
    public void setMaxWait(Time time) throws NullPointerException {
        if (time == null) {
            throw new NullPointerException();
        }
        maxWait = time;
    }

}
