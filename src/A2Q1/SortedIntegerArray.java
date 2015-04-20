package A2Q1;
import java.util.*;
/**
 * Represents a sorted integer array.  Provides a method, kpairSum, that
 * determines whether the array contains two elements that sum to a given
 * integer k.  Runs in O(n) time, where n is the length of the array.
 * @author jameselder
 */
public class SortedIntegerArray {

    protected int[] sortedIntegerArray;

    public SortedIntegerArray(int[] integerArray) {
        sortedIntegerArray = integerArray.clone();
        Arrays.sort(sortedIntegerArray);
    }
    /**
     * Checks to see if the array contains 2 integers that sum to the value of k.
     * @param k Desired sum
     * @param i Left index of array
     * @param j right index of array
     * @return True if the array contains an element that sums to k, false otherwise.
     */
    private boolean kPairSumInterval(Integer k, int i, int j){
    	// if i and j are decremented to equal each other
    	if (j == i){
    		return false;
    	}
    	// if i is out of bounds
    	if (i < 0 || i >= sortedIntegerArray.length){
    		return false;
    	}
    	// if j is out of bounds
    	if ( j < 0 || j >= sortedIntegerArray.length){
    		return false;
    	}
    	// Check the value of the sum of the int at i and j
    	int sum = (sortedIntegerArray[i] + sortedIntegerArray[j]);
    	// Check to see if the sum is larger than the maximum int value
    	if ( sum > Integer.MAX_VALUE){
    		long temp = (long)sum;
    		if ((temp == k)){
    			return true;
    		}else if((temp > k)){
    			return kPairSumInterval(k, i , j - 1);
    		}else if(( temp < k)){
    			return kPairSumInterval(k, i + 1, j);
    		}else{
    			return false;
    		}
    	}
    	if ((sum == k)){
			return true;
		}else if((sum > k)){
			return kPairSumInterval(k, i , j - 1);
		}else if(( sum < k)){
			return kPairSumInterval(k, i + 1, j);
		}else{
			return false;
		}
    }

/**
 * Determines whether the array contains two elements that sum to a given
 * integer k. Runs in O(n) time, where n is the length of the array.
 * @author jameselder
 */
    public boolean kPairSum(Integer k) {
    	int i = 0;
    	int j = sortedIntegerArray.length -1 ;
    	return kPairSumInterval(k, i, j );
    }
}