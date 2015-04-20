package A2Q2;

import java.util.*;

/**
 * Adaptible priority queue using location-aware entries in a min-heap, based on
 * an extendable array.  The order in which equal entries were added is preserved.
 *
 * @author Anton Sitkovets
 * @param <E> The entry type.
 */
public class APQ<E> {

    private final ArrayList<E> apq; //will store the min heap
    private final Comparator<E> comparator; //to compare the entries
    private final Locator<E> locator;  //to locate the entries within the queue
    
    
    /**
     * Constructor
     * @param comparator used to compare the entries
     * @param locator used to locate the entries in the queue
     */
    public APQ(Comparator<E> comparator, Locator<E> locator) throws NullPointerException {
        if (comparator == null || locator == null) {
            throw new NullPointerException();
        }
        apq = new ArrayList<>();
        apq.add(null); //dummy value at index = 0
        this.comparator = comparator;
        this.locator = locator;
    }

    /**
     * Inserts the specified entry into this priority queue.
     *
     * @param e the entry to insert
     * @throws NullPointerException
     */
    public void offer(E e) throws NullPointerException {
    	if ( e == null){
    		throw new NullPointerException("Element is null");
    	}
    	if(apq.isEmpty()){
    		apq.add( e);
    	}else{
    		apq.add(e);
    		locator.set(e,  size());
        	upheap(size());
    	}
    
    }

   /**
     * Removes the entry at the specified location.
     *
     * @param pos the location of the entry to remove
     * @throws BoundaryViolationException
     */
    public void remove(int pos) throws BoundaryViolationException {
    	
    	if ((pos > size()) || (pos < 1)){
			throw new BoundaryViolationException("Out of bounds");
    	}
    	locator.set(apq.get(pos), 0);
    	E lastElement = apq.remove(this.size());
    	if(pos != size() +1){
    		apq.set(pos, lastElement);
        	locator.set(lastElement,  pos);
        	downheap(pos);
    	}
    }

   /**
     * Removes the first entry in the priority queue.
     */
    public E poll() {
    	E firstElement = apq.get(1);
    	try
		{
			this.remove(1);
		} catch (BoundaryViolationException e)
		{
			e.printStackTrace();
		}
    	
    	return firstElement;
    	
    }

  /**
     * Returns but does not remove the first entry in the priority queue.
     */
     public E peek() {
        if (isEmpty()) {
            return null;
        }
        return apq.get(1);
    }

   public boolean isEmpty() {
        return (size() == 0); 
    }

    public int size() {
        return apq.size() - 1; //dummy node at location 0
    }


    /**
     * Shift the entry at pos upward in the heap to restore the minheap property
     * @param pos the location of the entry to move
     */
    private void upheap(int pos) {
        if (pos <= 1){
        	return;
        }
    	int parentLocation = pos / 2;
        int childLocation = pos;
        E parentNode = apq.get(parentLocation);
        E childNode = apq.get(childLocation);
        
        if( comparator.compare(parentNode, childNode) > 0 ){
        	this.swap(parentLocation, childLocation);
        }
        if(parentLocation != 1){
        	upheap(parentLocation);
        }
        
    }

    /**
     * Shift the entry at pos downward in the heap to restore the minheap property
     * @param pos the location of the entry to move
     */
    private void downheap(int pos) {
    	if (2*pos + 1 < this.size()){
    		int parentLocation = pos;
    		int left = 2 * parentLocation;
    		int right = 2 * parentLocation + 1;
    		
    		E parentNode = apq.get(parentLocation);
            E leftChild = apq.get(left);
            E rightChild = apq.get(right);
            
    		int size = this.size();
    		
    		if (right < size && (comparator.compare(rightChild, parentNode) < 0 ) && (comparator.compare(rightChild, leftChild) < 0)){
    			this.swap(right, parentLocation);
    			downheap(right);
    		}else if (left < size && (comparator.compare(leftChild, parentNode) < 0 )){
    			this.swap(left, parentLocation);
    			downheap(left);
    		}
    	}else{
    		return;
    	}
    	
    	
    }

    /**
     * Swaps the entries at the specified locations.
     *
     * @param pos1 the location of the first entry 
     * @param pos2 the location of the second entry 
     */
    private void swap(int pos1, int pos2) {
    	E pos1Node = apq.get(pos1);
    	E pos2Node = apq.get(pos2);
    	apq.set(pos1, pos2Node);
    	locator.set(pos2Node, pos1);
    	apq.set(pos2, pos1Node);
    	locator.set(pos1Node, pos2);
        
        
        
    }
  
}