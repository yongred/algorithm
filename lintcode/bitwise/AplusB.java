
public class AplusB{
	
	public int add(int a, int b){
		int carry;

		//b is the val that needs to add, (or new carry vals)
	    while(b!=0){
	    	//carry is only when 1 + 1, so only when both digit has 1. ANDing it will find out which pos have carry. (ex: 1101 & 1001 = 1001)
		    carry = a & b;
		    //adding 1+1=0 c=1, 0+0=0, this is a condition with xor. where same vals becomes 0. (ex: 1101 ^ 1001 = 0100)
		    a = a ^ b;
		    //shift 1s to 1 pos left, carry over the 1s. b is new carry val that needs to add. (ex: 1001 << 1 = 10010 carrying over, 0100 + 10010)
	      	b = carry << 1;
	    }
	    return a;
	}
	public static void main(String [] args){
		AplusB obj = new AplusB();
		int ans = obj.add(13, 10);
		System.out.println("ans: " + ans);
	}
}