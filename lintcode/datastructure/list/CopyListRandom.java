/**
105. Copy List with Random Pointer
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.

Challenge
Could you solve it with O(1) space?
*/

public class CopyListRandom {
	class RandomListNode {
      	int label;
      	RandomListNode next, random;
      	RandomListNode(int x) { this.label = x; }
 	};
    /**
     * @param head: The head of linked list with a random pointer.
     * @return: A new head of a deep copy of the list.
     * Solution:
     * Use a hash to Map<original, copy>; b/c We need to know if already visited
     * Create new copyNode if Not visited, otherwise get the exist copyNode from hash.
     * Time: O(N); Space: O(N);
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null)
            return null;
        RandomListNode copyHead = new RandomListNode(head.label);
        RandomListNode cur = head;
        RandomListNode curCopy = copyHead;
        HashMap<RandomListNode, RandomListNode> hash = new HashMap<RandomListNode, RandomListNode>();
        hash.put(cur, curCopy);
        
        while(cur != null)
        {
            if(cur.next != null)
            {
                if(!hash.containsKey(cur.next))
                {
                    RandomListNode newNode = new RandomListNode(cur.next.label);
                    curCopy.next = newNode;
                    hash.put(cur.next, curCopy.next);
                }
                else
                {
                    curCopy.next = hash.get(cur.next);
                }
            }
            
            if(cur.random != null)
            {
                if(!hash.containsKey(cur.random))
                {
                    RandomListNode newNode = new RandomListNode(cur.random.label);
                    curCopy.random = newNode;
                    hash.put(cur.random, newNode);
                }
                else
                {
                    curCopy.random = hash.get(cur.random);
                }
            }
            cur = cur.next;
            curCopy = curCopy.next;
        }
        return copyHead;
    }

    /**
     * @param head: The head of linked list with a random pointer.
     * @return: A new head of a deep copy of the list.
     * Solution: O(1) Space, copyNode in between original nodes.
     * 
            +--+    
    src:    1->2->3


    step1: copy node
            +------+
    src:    1->1'->2->2'->3->3'


    step2: copy random link
            +------+
    src:    1->1'->2->2'->3->3'
               +------+


    step3: split two lists
            +--+    
    src:    1->2->3

            +---+    
    dst:    1'->2'->3'
     * Time: O(N); Space: O(1) b/c Auxiliary space is only occupied O(1)
     * Auxiliary is intended to be all the memory that is not used to store the original input.
     * Auxiliary Space is the extra space or temporary space used by an algorithm.
     */
    public RandomListNode copyRandomList2(RandomListNode head) {
        RandomListNode cur = head;
        //insert copy in between nodes
        //Copied datas are in input list.
        while(cur != null)
        {
            RandomListNode copy = new RandomListNode(cur.label);
            //1->1'->2
            copy.next = cur.next;
            cur.next = copy;
            cur = cur.next.next;
        }
        cur = head;
        //assign random to copy
        while(cur != null)
        {
            RandomListNode copy = cur.next;
            //random.next is the copy of random
            if(cur.random != null)
            {
                copy.random = cur.random.next;
            }
            cur = cur.next.next;
        }
        
        RandomListNode dummy = new RandomListNode(0);
        cur = head;
        RandomListNode copyPre = dummy;
        
        while(cur != null)
        {
            RandomListNode copyNode = cur.next;
            //split and reconnect w/ original next node
            cur.next = copyNode.next;
            //connect the copied node
            copyPre.next = copyNode;
            copyPre = copyNode; //iterates copy
            cur = cur.next; //iterates
        }
        
        return dummy.next;
    }
}