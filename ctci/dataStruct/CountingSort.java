package dataStruct;

public class CountingSort {
	public void countingsort(int [] A, int min, int max){
		int count[]= new int[max- min+1];
		int z=0;
		for(int i= min; i<= max; i++){
			count[i] = 0;
		}
		for(int i= 0; i< A.length; i++){
			count[A[i]]++;
		}
		for(int i= min; i<= max; i++){
			while(count[i] > 0){
				A[z] = i;
				z++;
				count[i]--;
			}
		}
	}
}
