package merkle_test;

import java.security.MessageDigest;

public class hash {
	public static void main(String[] args) throws Exception {
	   
	     String message="d";
	     byte[] message_byte=message.getBytes();// trans from String to byte      
	     byte[] hash1=message.getBytes();
	     MessageDigest MD= MessageDigest.getInstance("SHA-256");
	     String b0="2e7d2c03a9507ae265ecf5b5356885a53393a2029d241394997265a1a25aefc6";
	     String b1="62af5c3cb8da3e4f25061e829ebeea5c7513c54949115b1acc225930a90154da";
	     String b2="d6cf2ad3f66d0599d97346c6aad0f1081913df26d8b80e4ffa052e0a1f8391c6";
	     String b3="2e15ecd6e7d77f95c9275a7cc4cf9338518ef7b9490097510f994d8111ccf1d8";
	     String b4="17b5f9a86dde814969ed0e97baebf01f8d9b1cdc393875765e0fce03d9824d0c";
  
	     String v= bytesToHexstring(MD.digest(message_byte));
	     System.out.println(v);
	     
	     String ph1=bytesToHexstring(MD.digest((b0+v).getBytes()));
	     
	     String ph2=bytesToHexstring(MD.digest((b1+ph1).getBytes()));
	     
	     String ph3=bytesToHexstring(MD.digest((ph2+b2).getBytes()));
	     
	     String ph4=bytesToHexstring(MD.digest((ph3+b3).getBytes()));
	     
	     String root=bytesToHexstring(MD.digest((ph4+b4).getBytes()));
	     
	     System.out.println(root);
	     
	}
	
	static String bytesToHexstring(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) {
	        sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}
	public static String byteArrayToHex(byte[] a) {
	    StringBuilder sb = new StringBuilder(a.length * 2);
	    for (byte b : a)
	        sb.append(String.format("%02x:", b));
	    return sb.toString();
	}
	public static byte[] hexStringToBytes(String hexString) {
//        if (StringUtils.isEmpty(hexString)) {
//            return null;
//        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index  > hexString.length() - 1) {
                return byteArray;
            }
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        return byteArray;
    }
	
}

