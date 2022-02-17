package calc_curve;
import java.math.BigInteger;
import java.util.BitSet;
public class MultiScalar {
	public static BitSet convertTo (BigInteger bi) {
	    byte[] bia = bi.toByteArray();
	    int l = bia.length;
	    byte[] bsa = new byte[l+1];
	    System.arraycopy(bia,0,bsa,0,l);
	    bsa[l] = 0x01;
	    return BitSet.valueOf(bsa);
	}
	
	

	public static BigInteger convertFrom (BitSet bs) {
	    byte[] bsa = bs.toByteArray();
	    int l = bsa.length-0x01;
	    byte[] bia = new byte[l];
	    System.arraycopy(bsa,0,bia,0,l);
	    return new BigInteger(bia);
	}
	
	public static int multiplyScalarPoint(int k) {

		String kAsBinary = Integer.toBinaryString(k);

		int step = 0;

		// decimal to binary conversion
		System.out.println("k = (" + k + ")10 = (" + kAsBinary + ")2\n");

		int q = 1, p = 1;
		int a = 0, b = 0;

		// header
		System.out.println("i\tki\t|\ta\t\tb\n--\t--\t|\t--\t\t--");

		for (int i = 1; i < kAsBinary.length(); i++) {

			System.out.print(i + "\t");

			int currentBit = Integer.parseInt(kAsBinary.substring(i, i + 1));
			System.out.print(currentBit + "\t|\t");

			System.out.print(q + "P+" + q + "P=");

			q = q + q;
			a = q;
			step++;

			System.out.print(a + "P\t");

			if (currentBit == 1) {

				System.out.print(q + "P+" + p + "P=");

				q = q + p;
				b = q;
				step++;

				System.out.print(b + "P\t");

			}

			b = 0;

			System.out.println("");

		}

		System.out.println("\nQ=kP is calculated in " + step + "th step");

		return step;

	}
}
