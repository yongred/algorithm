/**
Sort List:
Sort a linked list in O(n log n) time using constant space complexity.
Example
Given 1->3->2->null, sort it to 1->2->3->null.

*/

public class MergeSortList{

	public class ListNode {
	   	int val;
	    ListNode next;
	    ListNode(int val) {
	        this.val = val;
	        this.next = null;
	    }
	}

	private class HeadTailList{
		ListNode head;
		ListNode tail;
		HeadTailList(ListNode head, ListNode tail){
			this.head = head;
			this.tail = tail;
		}
	}

    /*
	Better MergeSort using Bottom-Up:
	Time: O(n Lg n); Space: O(1);
	- slice the lists into sublists first by step length
	ex: 5->2->3->1->4 => 5,2,3,1,4
	- then merge sliced sublists;
	ex: 5,2,3,1,4 => [2->5], [1->3], [4]
	- after that reconnect into whole list;
	ex: [2->5], [1->3], [4] => 2->5->1->3->4
	- repeat till step len > list.size;
    */
    
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;
        
        int length = length(head);
        //points to head
        ListNode beforehead = new ListNode(0);
        beforehead.next = head;
        HeadTailList sublist = new HeadTailList(null, null);
        //step is how big of the slice; ex: step=2 : 1->2->3->4 => [1->2] [3->4]; (step<<=1) == (step*=2)
        for(int step = 1; step < length; step <<= 1) {
        	//point to head
            ListNode left = beforehead.next; 
            //prev sublist to reconnect after merge, since list being splited in previus steps;
            ListNode prev = beforehead;

            while(left != null) {
                ListNode right = split(left, step);
                ListNode next = split(right, step);

                //result with head and tail 
                sublist = merge(left, right);
                //connect splited prev sublist to cur sublist
                prev.next = sublist.head;
                //iterate to cur sublist for next reconnect
                prev = sublist.tail;

                left = next;
            }
        }
        
        return beforehead.next;
    }
    
    public ListNode split(ListNode head, int step) {
    	//loop until the last node of left list
        while(head != null && step != 1) {
            head = head.next;
            step--;
        }
        if(head == null) return null;
        ListNode res = head.next;
        //split; ex: [1->2(head)]->[3(next)->4] 
        head.next = null; 
        //return head of splited right list
        return res;
    }
    
    public int length(ListNode head) {
        int len = 0;
        //1(len:0-1)->2(1-2)->3(2-3)->4(3-4)->null;
        while(head != null) {
            head = head.next;
            len++;
        }
        
        return len;
    }

    public HeadTailList merge(ListNode head1, ListNode head2){
    	//node to point to head
    	ListNode beforehead = new ListNode(0);
    	ListNode tail = beforehead;
    	while(head1 != null && head2 != null){
    		if(head1.val <= head2.val){
    			tail.next = head1;
    			head1 = head1.next;
    		}else{
    			tail.next = head2;
    			head2 = head2.next;
    		}
    		tail = tail.next;
    	}

    	//merge the other list that has leftovers.
    	ListNode leftover = (head1 == null) ? head2 : head1;
    	//loop to tail of the list
    	while(leftover != null){
    		tail.next = leftover;
    		leftover = leftover.next;
    		tail = tail.next;
    	}
    	//return a the head and tail of the list merged
    	return new HeadTailList(beforehead.next, tail);
    }

    public void printList(ListNode head){
    	ListNode cur1 = head;
		while(cur1 != null){
			System.out.print(cur1.val + "->");
			cur1 = cur1.next;
		}
		System.out.println();
    }

    public static void main(String [] args){
		MergeSortList obj = new MergeSortList();
		MergeSortList.ListNode node1 = new MergeSortList().new ListNode(1);
		MergeSortList.ListNode node2 = new MergeSortList().new ListNode(2);
		MergeSortList.ListNode node3 = new MergeSortList().new ListNode(3);
		MergeSortList.ListNode node4 = new MergeSortList().new ListNode(4);
		MergeSortList.ListNode node5 = new MergeSortList().new ListNode(5);
		MergeSortList.ListNode node6 = new MergeSortList().new ListNode(6);
		node2.next = node4;
		node4.next = node1;
		node1.next = node5;
		node5.next = node3;
		node3.next = node6;

		MergeSortList.ListNode head = node2;
		obj.printList(head);
		
		System.out.println("ANS----");
		ListNode newhead = obj.sortList(head);
		obj.printList(newhead);
	}

}