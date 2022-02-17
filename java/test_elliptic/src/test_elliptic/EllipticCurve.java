package test_elliptic;

import java.math.BigInteger;

public class EllipticCurve {
    private BigInteger a, b, p;
    public static BigInteger ZERO = BigInteger.valueOf(0);
    public static BigInteger ONE = BigInteger.valueOf(1);
    public static BigInteger TWO = BigInteger.valueOf(2);
    public static BigInteger THREE = BigInteger.valueOf(3);
    public static BigInteger FOUR = BigInteger.valueOf(4);

    public EllipticCurve(BigInteger a, BigInteger b, BigInteger p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }

    public EllipticCurve(String a, String b, String p) {
        this.a = new BigInteger(a);
        this.b = new BigInteger(b);
        this.p = new BigInteger(p);
    }

    public BigInteger[] uncompressPoint(BigInteger px, BigInteger py) {
        BigInteger res[] = { null, null };

        BigInteger y = (px.pow(3).mod(p).add(a.multiply(px)).add(b)).mod(p); // find y^2
        // y = y.sqrt().mod(p);
        y = y.modPow(p.add(ONE).divide(FOUR), p); // sqrt to find y
        if (py.equals(ONE) == y.testBit(y.bitLength() - 1)) {
            res[0] = px;
            res[1] = p.subtract(y);
        } else {
            res[0] = px;
            res[1] = y;
        }
        return res;
    }

    public BigInteger[] uncompressPoint(String px, String py) {
        BigInteger x = new BigInteger(px);
        BigInteger y = new BigInteger(py);
        return this.uncompressPoint(x, y);
    }

    public BigInteger[] nP(BigInteger px, BigInteger py, BigInteger n) {
        BigInteger res[] = { null, null };
        BigInteger i;
        BigInteger tmp[] = { null, null };
        BigInteger newx;
        BigInteger newy;
        try {
            tmp = this.pointDouble(px, py);
            newx = tmp[0];
            newy = tmp[1];
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return res = tmp;
        }
        for (i = TWO; i.compareTo(n) < 0; i = i.add(ONE)) {
            try {
                tmp = this.add(px, py, newx, newy);
                newx = tmp[0];
                newy = tmp[1];
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("POI: " + i);
                return res = tmp;
            }
        }
        res = tmp;
        return res;
    }

    public BigInteger[] nP(String px, String py, String n) {
        BigInteger x = new BigInteger(px);
        BigInteger y = new BigInteger(py);
        BigInteger modn = new BigInteger(n);
        return this.nP(x, y, modn);
    }

    public BigInteger[] add(BigInteger px, BigInteger py, BigInteger qx, BigInteger qy) throws Exception {
        BigInteger res[] = { null, null };
        BigInteger numerator = qy.subtract(py);
        BigInteger denominator = qx.subtract(px);
        if (denominator.equals(ZERO)) {
            throw new Exception("cannot add. reached Point Of Infinity");
        }

        numerator = numerator.mod(p);
        denominator = denominator.modInverse(p);

        BigInteger s = numerator.multiply(denominator).mod(p);
        BigInteger newx = s.pow(2).subtract(px).subtract(qx).mod(p);
        BigInteger newy = s.multiply(px.subtract(newx)).subtract(py).mod(p);
        res[0] = newx;
        res[1] = newy;
        return res;
    }

    public BigInteger[] pointDouble(BigInteger px, BigInteger py) throws Exception {
        BigInteger res[] = { null, null };
        BigInteger numerator = px.pow(2).multiply(THREE).add(a);
        BigInteger denominator = py.multiply(TWO);
        numerator = numerator.mod(p);
        denominator = denominator.modInverse(p);
        if (denominator.equals(ZERO)) {
            throw new Exception("cannot double. reached Point Of Infinity");
        }
        BigInteger s = numerator.multiply(denominator).mod(p);
        BigInteger newx = s.pow(2).subtract(px).subtract(px).mod(p);
        BigInteger newy = s.multiply(px.subtract(newx)).subtract(py).mod(p);
        res[0] = newx;
        res[1] = newy;
        return res;
    }

    public BigInteger twoPointPointOrder(BigInteger px, BigInteger py, BigInteger qx, BigInteger qy, BigInteger i) {
        while (true) {
            i = i.add(ONE);
            BigInteger newPoints[];
            try {
                newPoints = this.add(px, py, qx, qy);
                px = newPoints[0];
                py = newPoints[1];
            } catch (Exception e) {
                return i;
            }
        }
    }

    public BigInteger twoPointPointOrder(String px, String py, String qx, String qy, String i) {
        BigInteger x1 = new BigInteger(px);
        BigInteger y1 = new BigInteger(py);
        BigInteger x2 = new BigInteger(qx);
        BigInteger y2 = new BigInteger(qy);
        BigInteger mul = new BigInteger(i);

        return twoPointPointOrder(x1, y1, x2, y2, mul);
    }

    public BigInteger singlePointOrder(BigInteger px, BigInteger py) {
        BigInteger i = TWO;
        BigInteger[] res = { null, null };
        try {
            res = this.pointDouble(px, py);
        } catch (Exception e) {
            return i;
        }
        BigInteger newx = res[0];
        BigInteger newy = res[1];
        while (true) {
            i = i.add(ONE);
            BigInteger newPoints[];
            try {
                newPoints = this.add(newx, newy, px, py);
                newx = newPoints[0];
                newy = newPoints[1];
            } catch (Exception e) {
                // System.out.println("End: " + i);
                return i;
            }
        }
    }

    public BigInteger singlePointOrder(String px, String py) {
        BigInteger x = new BigInteger(px);
        BigInteger y = new BigInteger(py);
        return singlePointOrder(x, y);
    }
}
