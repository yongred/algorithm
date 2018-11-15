/**
5.8 Draw Line: A monochrome screen is stored as a single array of bytes, allowing eight consecutive
pixels to be stored in one byte. The screen has width w, where w is divisible by 8 (that is, no byte will
be split across rows). The height of the screen, of course, can be derived from the length of the array
and the width. Implement a function that draws a horizontal line from (xl, y) to (x2, y) .
The method signature should look something like:
drawLine(byte[] screen, int width, int x1, int x2, int y)
Hints: #366, #381, #384, #391
*/

public class DrawLine{
	//O(1) time, O(1) space
	public void drawLine(byte [] screen, int width, int x1, int x2, int y){
		int bytesPerRow = width / 8; //ex: w=32, 32/8 = 4 bytes
		int rowStrInd = bytesPerRow * y; //ex: y= 2; 4 * 2 = 8; (0,1,2,3)  (4,5,6,7)  (8*,9,10,11)
		//get starting bit in byte
		int start_offset = x1 % 8; // 13 % 8 = 5; start at the bit-5 of a byte
		int fst_full_byte = x1 / 8; // 13/8 = 1; 0,1, so starts at bit-1 of row y
		if(start_offset != 0)  //offset = 0 means fullbyte, else not
			fst_full_byte++;
		int end_offset = x2 % 8; // 26 % 8 = 2; ends at bit-2 of last byte 
		int last_full_byte = x2 / 8; // 26 / 8 = 3; last byte is index 3 of row y
		if(end_offset != 7)  // == 7 means full byte last byte
			last_full_byte--;
		//set all full bytes, T(b): b< width/8
		for(int i = fst_full_byte; i<= last_full_byte; i++){
			screen[rowStrInd + i] = (byte)0xFF;
		}
		//set first and last bits
		byte firstMask = (byte) (0xFF >> start_offset); // ex: 1111,1111 >> 5 = 0000,0111 
		byte lastMask = (byte) ~(0xFF >> (end_offset+1) ); //ex: 1111,1111 >> 2+1 = 0001,1111; ~0001,1111 = 1110,0000
		if(x1/8 == x2/8){ //first and last is in same byte
			//ex: 0011,1000 & 0000,0111 = 0011,1111
			screen[rowStrInd + (x1/8)] = (byte)(firstMask & lastMask);
		} else{
			if(start_offset != 0){
				screen[rowStrInd + (x1/8)] |= firstMask; 
			} 
			if(end_offset != 7){
				screen[rowStrInd + (x2/8)] |= lastMask;
			}
		}
	} //end drawLine

	public void printScreen(byte [] screen, int width){
		int num = 1, bytesPerRow = width/8;
        for (byte b : screen) {
            for (int i = 7; i >= 0; --i) {
                System.out.print((b >> i) & 1);
            }
            if (num % bytesPerRow == 0) System.out.println();
            ++num;
        }
        System.out.println();
	}

	 public static void main(String[] args) {
	 	DrawLine obj = new DrawLine();
        byte[] screen = new byte[16];
        int width = 32;
        obj.printScreen(screen, width);
        obj.drawLine(screen, width, 0, 6, 0);
        obj.printScreen(screen, width);
        obj.drawLine(screen, width, 19,31, 1);
        obj.drawLine(screen, width, 2, 29, 2);
        obj.drawLine(screen, width, 1, 5, 3);
        obj.printScreen(screen, width);
    }


}