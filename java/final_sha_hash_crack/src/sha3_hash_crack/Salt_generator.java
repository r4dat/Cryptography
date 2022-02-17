package sha3_hash_crack;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class Salt_generator {
	ArrayList<byte[]> binaries = new ArrayList<byte[]>() ;
	Salt_generator(){
	int bytes = 2;
	int nBits = bytes * 8;
	int maxNumber = 1 << nBits; //this equals 2^nBits.
	for (int i = 0; i < maxNumber; i++) {
		byte [] int_buffer = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(i).array();
		// int is 4 bytes wide, but we only want the last 2 since we're looking at 2^16. 
		// so drop 0,1; keep 2,3. This is endian dependent. 
		byte[] out_buffer = new byte[2];
		// ArrayCopy
		//src, src pos, dest, dest pos, how many
		System.arraycopy(int_buffer,2,out_buffer,0,2);
		binaries.add(out_buffer);
	}
	}
	
	Salt_generator(String manual){
		// manually generated bytes for testing the normal generator. 
		// This is the naive version and will create duplicates due to symmetry.
		byte[] all_8bit_bytes = {(byte)0b00000000, (byte)0b00000001, (byte)0b00000010, (byte)0b00000011, (byte)0b00000100, (byte)0b00000101, (byte)0b00000110, (byte)0b00000111, (byte)0b00001000, (byte)0b00001001, (byte)0b00001010, (byte)0b00001011, (byte)0b00001100, (byte)0b00001101, (byte)0b00001110, (byte)0b00001111, (byte)0b00010000, (byte)0b00010001, (byte)0b00010010, (byte)0b00010011, (byte)0b00010100, (byte)0b00010101, (byte)0b00010110, (byte)0b00010111, (byte)0b00011000, (byte)0b00011001, (byte)0b00011010, (byte)0b00011011, (byte)0b00011100, (byte)0b00011101, (byte)0b00011110, (byte)0b00011111, (byte)0b00100000, (byte)0b00100001, (byte)0b00100010, (byte)0b00100011, (byte)0b00100100, (byte)0b00100101, (byte)0b00100110, (byte)0b00100111, (byte)0b00101000, (byte)0b00101001, (byte)0b00101010, (byte)0b00101011, (byte)0b00101100, (byte)0b00101101, (byte)0b00101110, (byte)0b00101111, (byte)0b00110000, (byte)0b00110001, (byte)0b00110010, (byte)0b00110011, (byte)0b00110100, (byte)0b00110101, (byte)0b00110110, (byte)0b00110111, (byte)0b00111000, (byte)0b00111001, (byte)0b00111010, (byte)0b00111011, (byte)0b00111100, (byte)0b00111101, (byte)0b00111110, (byte)0b00111111, (byte)0b01000000, (byte)0b01000001, (byte)0b01000010, (byte)0b01000011, (byte)0b01000100, (byte)0b01000101, (byte)0b01000110, (byte)0b01000111, (byte)0b01001000, (byte)0b01001001, (byte)0b01001010, (byte)0b01001011, (byte)0b01001100, (byte)0b01001101, (byte)0b01001110, (byte)0b01001111, (byte)0b01010000, (byte)0b01010001, (byte)0b01010010, (byte)0b01010011, (byte)0b01010100, (byte)0b01010101, (byte)0b01010110, (byte)0b01010111, (byte)0b01011000, (byte)0b01011001, (byte)0b01011010, (byte)0b01011011, (byte)0b01011100, (byte)0b01011101, (byte)0b01011110, (byte)0b01011111, (byte)0b01100000, (byte)0b01100001, (byte)0b01100010, (byte)0b01100011, (byte)0b01100100, (byte)0b01100101, (byte)0b01100110, (byte)0b01100111, (byte)0b01101000, (byte)0b01101001, (byte)0b01101010, (byte)0b01101011, (byte)0b01101100, (byte)0b01101101, (byte)0b01101110, (byte)0b01101111, (byte)0b01110000, (byte)0b01110001, (byte)0b01110010, (byte)0b01110011, (byte)0b01110100, (byte)0b01110101, (byte)0b01110110, (byte)0b01110111, (byte)0b01111000, (byte)0b01111001, (byte)0b01111010, (byte)0b01111011, (byte)0b01111100, (byte)0b01111101, (byte)0b01111110, (byte)0b01111111, (byte)0b10000000, (byte)0b10000001, (byte)0b10000010, (byte)0b10000011, (byte)0b10000100, (byte)0b10000101, (byte)0b10000110, (byte)0b10000111, (byte)0b10001000, (byte)0b10001001, (byte)0b10001010, (byte)0b10001011, (byte)0b10001100, (byte)0b10001101, (byte)0b10001110, (byte)0b10001111, (byte)0b10010000, (byte)0b10010001, (byte)0b10010010, (byte)0b10010011, (byte)0b10010100, (byte)0b10010101, (byte)0b10010110, (byte)0b10010111, (byte)0b10011000, (byte)0b10011001, (byte)0b10011010, (byte)0b10011011, (byte)0b10011100, (byte)0b10011101, (byte)0b10011110, (byte)0b10011111, (byte)0b10100000, (byte)0b10100001, (byte)0b10100010, (byte)0b10100011, (byte)0b10100100, (byte)0b10100101, (byte)0b10100110, (byte)0b10100111, (byte)0b10101000, (byte)0b10101001, (byte)0b10101010, (byte)0b10101011, (byte)0b10101100, (byte)0b10101101, (byte)0b10101110, (byte)0b10101111, (byte)0b10110000, (byte)0b10110001, (byte)0b10110010, (byte)0b10110011, (byte)0b10110100, (byte)0b10110101, (byte)0b10110110, (byte)0b10110111, (byte)0b10111000, (byte)0b10111001, (byte)0b10111010, (byte)0b10111011, (byte)0b10111100, (byte)0b10111101, (byte)0b10111110, (byte)0b10111111, (byte)0b11000000, (byte)0b11000001, (byte)0b11000010, (byte)0b11000011, (byte)0b11000100, (byte)0b11000101, (byte)0b11000110, (byte)0b11000111, (byte)0b11001000, (byte)0b11001001, (byte)0b11001010, (byte)0b11001011, (byte)0b11001100, (byte)0b11001101, (byte)0b11001110, (byte)0b11001111, (byte)0b11010000, (byte)0b11010001, (byte)0b11010010, (byte)0b11010011, (byte)0b11010100, (byte)0b11010101, (byte)0b11010110, (byte)0b11010111, (byte)0b11011000, (byte)0b11011001, (byte)0b11011010, (byte)0b11011011, (byte)0b11011100, (byte)0b11011101, (byte)0b11011110, (byte)0b11011111, (byte)0b11100000, (byte)0b11100001, (byte)0b11100010, (byte)0b11100011, (byte)0b11100100, (byte)0b11100101, (byte)0b11100110, (byte)0b11100111, (byte)0b11101000, (byte)0b11101001, (byte)0b11101010, (byte)0b11101011, (byte)0b11101100, (byte)0b11101101, (byte)0b11101110, (byte)0b11101111, (byte)0b11110000, (byte)0b11110001, (byte)0b11110010, (byte)0b11110011, (byte)0b11110100, (byte)0b11110101, (byte)0b11110110, (byte)0b11110111, (byte)0b11111000, (byte)0b11111001, (byte)0b11111010, (byte)0b11111011, (byte)0b11111100, (byte)0b11111101, (byte)0b11111110, (byte)0b11111111};
		for( int i=0; i<all_8bit_bytes.length; i++) {
			for(int j = 0; j<all_8bit_bytes.length; j++) {
				byte[] a = {all_8bit_bytes[i],all_8bit_bytes[j]};
				byte[] b = {all_8bit_bytes[j],all_8bit_bytes[i]};
				binaries.add(a);
				binaries.add(b);
			}
	
		}
		
}
	
	
	ArrayList<byte[]> get_salt_bytes(){
		return binaries;
	}
	
}

