"""
BINARY POLYNOMIAL ARITHMETIC

These functions operate on binary polynomials (GF(2^n)), expressed as coefficient bitmasks, etc:
  0b100111 -> x^5 + x^2 + x + 1
As an implied precondition, parameters must be integers unless otherwise noted.
This code is NOT safe to use for cryptography.
"""

def p_mul(a, b):
    """ Binary polynomial multiplication (peasant). """
    result = 0
    while a and b:
        if a & 1: result ^= b
        a >>= 1; b <<= 1
    return result

def p_mod(a, b):
    """ Binary polynomial remainder / modulus.
        Divides a by b and returns resulting remainder polynomial.
        Precondition: b != 0 """
    bl = b.bit_length()
    while True:
        shift = a.bit_length() - bl
        if shift < 0: return a
        a ^= b << shift

def p_divmod(a, b):
    """ Binary polynomial division.
        Divides a by b and returns resulting (quotient, remainder) polynomials.
        Precondition: b != 0 """
    q = 0; bl = b.bit_length()
    while True:
        shift = a.bit_length() - bl
        if shift < 0: return (q, a)
        q ^= 1 << shift; a ^= b << shift

def p_mod_mul(a, b, modulus):
    """ Binary polynomial modular multiplication (peasant).
        Returns p_mod(p_mul(a, b), modulus)
        Precondition: modulus != 0 and b < modulus """
    result = 0; deg = p_degree(modulus)
    assert p_degree(b) < deg
    while a and b:
        if a & 1: result ^= b
        a >>= 1; b <<= 1
        if (b >> deg) & 1: b ^= modulus
    return result

def p_exp(a, exponent):
    """ Binary polynomial exponentiation by squaring (iterative).
        Returns polynomial `a` multiplied by itself `exponent` times.
        Precondition: exponent >= 0
        Precondition: not (x == 0 and exponent == 0) """
    factor = a; result = 1
    while exponent:
        if exponent & 1: result = p_mul(result, factor)
        factor = p_mul(factor, factor)
        exponent >>= 1
    return result

def p_gcd(a, b):
    """ Binary polynomial euclidean algorithm (iterative).
        Returns the Greatest Common Divisor of polynomials a and b. """
    while b: a, b = b, p_mod(a, b)
    return a

def p_egcd(a, b):
    """ Binary polynomial Extended Euclidean algorithm (iterative).
        Returns (d, x, y) where d is the Greatest Common Divisor of polynomials a and b.
        x, y are polynomials that satisfy: p_mul(a,x) ^ p_mul(b,y) = d
        Precondition: b != 0
        Postcondition: x <= p_div(b,d) and y <= p_div(a,d) """
    a = (a, 1, 0)
    b = (b, 0, 1)
    while True:
        q, r = p_divmod(a[0], b[0])
        if not r: return b
        a, b = b, (r, a[1] ^ p_mul(q, b[1]), a[2] ^ p_mul(q, b[2]))

def p_mult_inv(a, modulus):
    """ Binary polynomial modular multiplicative inverse.
        Returns b so that: p_mod(p_mul(a, b), modulus) == 1
        Precondition: modulus != 0 and p_coprime(a, modulus)
        Postcondition: b < modulus """
    d, x, y = p_egcd(a, modulus)
    assert d == 1 # inverse exists
    return x

def p_mod_pow(x, exponent, modulus):
    """ Binary polynomial modular exponentiation by squaring (iterative).
        Returns: p_mod(p_exp(x, exponent), modulus)
        Precondition: exponent >= 0 and modulus > 0
        Precondition: not (x == 0 and exponent == 0) """
    factor = x = p_mod(x, modulus); result = 1
    while exponent:
        if exponent & 1:
            result = p_mod_mul(result, factor, modulus)
        factor = p_mod_mul(factor, factor, modulus)
        exponent >>= 1
    return result

"""
UTILITIES
"""

def to_bits(n):
    bits = set(); bit = 0
    while n:
        if n & 1: bits.add(bit)
        bit += 1; n >>= 1
    return bits

def from_bits(bits):
    n = 0
    for bit in bits: n |= 1 << bit
    return n

def polynomial_str(n, variable="x", unicode=False, separator=" + ", constant="1"):
    sup = lambda s: "".join("⁰¹²³⁴⁵⁶⁷⁸⁹"[ord(c) & 0xF] for c in s)
    power = lambda s: variable + sup(s) if unicode else variable + "^" + s
    term = lambda bit: constant if bit == 0 else ( \
        variable if bit == 1 else power(str(bit)) )
    return separator.join(map(term, sorted(to_bits(n), reverse=True)))

if __name__ == '__main__':

    polynomial_str((2 ** 256 + 2 ** 128 + 1))
    polynomial_str((2 ** 255 + 2 ** 126 + 1))

    p_1 = (2**3+2+1)
    p_2 = (2**6+2**5+2**3+2**2+2**1+2**0)
    print(polynomial_str(p_1))
    print(polynomial_str(p_2))
    print(p_egcd(0b1011,0b1101111))

