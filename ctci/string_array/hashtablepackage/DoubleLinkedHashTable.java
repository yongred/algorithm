/**
Hash table chaining with double linked list
*/

package hashtablepackage;
import java.util.*;

public class DoubleLinkedHashTable{
	public class Entry{
		Entry prev;
		Entry next;
		Object data;
		int key;
		public Entry(int key, Object data){
			this.data = data;
			this.key = key;
		}
		public int getKey(){
			return key;
		}
		public void setKey(int key){
			this.key =key;
		}
		public Object getData(){
			return data;
		}
		public void setData(Object data){
			this.data = data;
		}
		public Entry getNext(){
			return next;
		}
		public void setNext(Entry next){
			this.next = next;
		}
		public Entry getPrev(){
			return prev;
		}
		public void setPrev(Entry prev){
			this.prev = prev;
		}
	}//end Entry

	int tableSize = 10;
	Entry [] table;

	public DoubleLinkedHashTable(int size){
		this.tableSize = size;
		table = new Entry[size];
		for(int i=0; i<size; i++)
			table[i] = null;
	}

	public void add(int key, Object data){
		//ind of which slot of the table to insert
		//index is 0 - (size-1), ex: size=10, reminder is 0-9
		int ind = key % tableSize; 
		if(table[ind] == null){ //empty slot
			table[ind] = new Entry(key, data);
			return;
		}
		Entry ent = table[ind];
		while(ent.getNext() != null){
			if(ent.getKey() == key){
				//dulicated key, replace the value data
				ent.setData(data);
				return;
			}
			ent = ent.getNext();
		}
		//loop out when reached last element, no dulicated key
		//add new Entry to the end of the list
		ent.setNext(new Entry(key, data));

	}//end add

	public Object search(int key){
		//ind of which slot of the table to insert
		//index is 0 - (size-1), ex: size=10, reminder is 0-9
		int ind = key % tableSize; 
		if(table[ind] == null) //empty slot
			return null;
		Entry ent = table[ind];
		while(ent != null){
			//find matching key
			if(ent.getKey() == key)
				return ent.getData();
			ent = ent.getNext();
		}
		return null;

	}//end search

	public void delete(int key){
		//ind of which slot of the table to insert
		//index is 0 - (size-1), ex: size=10, reminder is 0-9
		int ind = key % tableSize; 
		if(table[ind] == null) //empty slot
			return;
		Entry ent = table[ind];
		while(ent != null){
			if(ent.getKey() == key){
				if(ent.getPrev() != null){
					//ent.getNext can be null, so if next is empty, next will be null
					ent.getPrev().setNext(ent.getNext());
				}
				else{ 
					//prev is null, ent is first element, set next to be head element
					table[ind] = ent.getNext();
				}
				if(ent.getNext() != null){
					//ent.prev can be null, so if prev is empty, prev will be null
					ent.getNext().setPrev(ent.getPrev());
				}
				return;
			}
			ent = ent.getNext();
		}
		return;

	}//end add

	public static void main(String [] args){
		DoubleLinkedHashTable obj = new DoubleLinkedHashTable(10);
		for(int i= 0; i< 50; i++){
			obj.add(i, i*10);
		}
		System.out.println(obj.search(0));
		obj.delete(0);
		System.out.println(obj.search(0));

	}

}//end DoubleLinkedHashTable