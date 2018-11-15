/**
2.1 Remove Dups: Write code to remove duplicates from an unsorted linked list.
FOLLOW UP
How would you solve this problem if a temporary buffer is not allowed?
Hints: #9, #40
*/
import java.util.*;

public class RemoveDup{

	public void removeDupl(LinkedList targetList){
		Hashtable hash = new Hashtable();
		Enumeration keys;
		LinkedList tmpList = (LinkedList)targetList.clone();
		for(Object item : tmpList){
			System.out.println(item);
			if(hash.contains(item)){
				targetList.remove(item);
				//System.out.println(item);
			}
			else{
				hash.put(item,item);
				System.out.println("else: " + item);
			}
			
		}
		/*keys = hash.keys();
		while(keys.hasMoreElements()){
			Object key = keys.nextElement();
			targetList.add(key);
		}*/
	}

	public static void main(String [] args){
		RemoveDup obj = new RemoveDup();
		Integer [] arr = new Integer[]{1,2,2,3,3,3,4,5};
		LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(arr));
		obj.removeDupl(list);
		for(int item : list){
			System.out.print(" " + item);
		}
	}

}