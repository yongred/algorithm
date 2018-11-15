package dataStruct;

public class QuestionTesting {
	
	public boolean isUnique(String str){
		char [] charArray = str.toCharArray();
		boolean unique =true;
		for(int i= 0; i < charArray.length; i++){
			for(int j= i+1; j< charArray.length; j++){
				if(charArray[i] == charArray[j])
					unique = false;
			}
		}
		return unique;
	}
	
	public static void main(String [] args){
		QuestionTesting qt = new QuestionTesting();
		String str = "abcdefghijklmnopq;'3021.";
		
		if(qt.isUnique(str)){
			System.out.println("unique");
		}else{
			System.out.println("not");
		}
	}
}
