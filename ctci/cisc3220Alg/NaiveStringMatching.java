package cisc3220Alg;

public class NaiveStringMatching {
	public void NSM(String T, String P){
		int tlen = T.length();
		int plen = P.length();
		boolean found = false;
		for(int s=0; s <= (tlen - plen); s++){
			String sub = T.substring(s, s + plen);
			System.out.println("now at: " + s 
					+ " its " + sub);
			if(P.equals(sub)){
				System.out.println("found at: " + s 
						+ " its " + sub);
				found = true;
				return;
			}
		}
		if(!found)
			System.out.println("not found");
	}
	
	public void NSM2(String T, String P){
		int tlen = T.length();
		int plen = P.length();
		String ans = "";
		int tInd = 0;
		int pInd = 0;
		while( tInd <= (tlen - plen)){
			
			if(P.charAt(pInd) == T.charAt(tInd)){
				System.out.println( T.charAt(tInd)+ " index "
						+ tInd);
				ans += P.charAt(pInd);
				if(ans.equals(P))
					System.out.println("found: " + ans);
				pInd++;
				tInd++;
				
				
			}
			else{
				ans = "";
				pInd = 0;
				tInd++;
			}
		}
	}
	
	public static void main(String [] args){
		NaiveStringMatching obj = new NaiveStringMatching();
		String T = "abcbdcabcdcad";
		String P = "abcd";
		obj.NSM2(T,P);
	}
}
