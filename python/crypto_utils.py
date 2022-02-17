def gcd(number1, number2):
    """Calculates the greatest common divisor of two integers.

    Uses the Euclidean algorithm in order to find the GCD of
    two numbers.

    Returns:
        The GCD.
    """
    if number2 == 0:
        return number1
    else:
        return gcd(number2, number1 % number2)


def egcd(number1, number2):
    """Extended Euclidean Algorithm.

    Uses the Extended Euclidean Algorithm to find the GCD of two numbers
    and the the coefficients of Bézout's identity
    (https://en.wikipedia.org/wiki/B%C3%A9zout%27s_identity)

    Returns:
        A tuple with the CGD and the coefficients.
        (gcd, coefficient_s, coefficient_t)

    """
    s, old_s = 0, 1
    t, old_t = 1, 0
    r, old_r = number2, number1
    while r != 0:
        quotient = old_r // r
        old_r, r = r, old_r - quotient * r
        old_s, s = s, old_s - quotient * s
        old_t, t = t, old_t - quotient * t
    return (old_r, old_s, old_t)


def modular_inverse(number, modulo):
    """Gets the inverse of number mod modulo.

    Uses the Extended Euclidean Algorithm to get the modular inverse
    of a number.

    Args:
        number: number to find the inverse.
        modulo: modulo in which to find the inverse

    Returns:
        The modular inverse of number mod modulo.

    Raises:
        Exception: If the modular inverse doesn't exists (number and modulo
                   are not coprime.
    """
    gcd, x, y = egcd(number, modulo)
    if gcd != 1:
        raise Exception('Modular inverse doesn''t exists')
    else:
        t = x % modulo
        if (t < 0):
            t = t + modulo
        return t


def fast_exp(number, exp, modulo):
    """Calculates the power exp of number, mod modulo.

    Uses Fast Exponentiation to calculate the exp-th power of number,
    mod modulo. Ref for the algorithm:
    https://www.khanacademy.org/computing/computer-science/cryptography/modarithmetic/a/fast-modular-exponentiation

    Args:
        number: Number to exponentiate.
        exp: Exponent
        modulo: Modulus of the calculation

    Returns:
        The exp-th power of n.
    """
    exponents = []
    index = 0
    exp_orig = exp
    while exp != 0:  # First, we write the exp as sums of powers of two.
        if exp & 1:
            exponents.append(2 ** index)
        exp = exp >> 1
        index += 1
    # Then we take the biggest exponent, and calculate the values for all the powers of
    # 2 that are less than the biggest one and keep them in a dictionary for future use.
    largest_exp = exponents[-1]
    dictionary = {}
    current_exponent = 1
    dictionary[current_exponent] = number % modulo;
    while (current_exponent < largest_exp):
        next_exponent = current_exponent + current_exponent;
        dictionary[next_exponent] = (dictionary[current_exponent] * dictionary[current_exponent] % modulo)
        current_exponent = next_exponent
    # The result will be the multiplication of all the num^power_of_two mod modulo
    result = 1;
    for exponent in exponents:
        result = result * (dictionary[exponent]) % modulo
    return result


def get_ascii_from_number(number):
    """Gets an ascii word given a integer number.

    Gets the ascii representation of a number where each
    two diggits represent an ascii letter. For example:
    909090 represents ZZZ.

    Args:
        number: Integer representation of the word.

    Returns:
        The string represented by number.
    """
    ascii_string = []
    while number > 0:
        mchar = number % 100
        ascii_string.insert(0, chr(mchar))
        number = number // 100
    return ''.join(ascii_string)


def iroot(k, n):
    """Calculates the integer k root of n.

    Args:
        k: k root that is desired.
        n: number to calculate the k root
    """
    u, s = n, n + 1
    while u < s:
        s = u
        t = (k - 1) * s + n // pow(s, k - 1)
        u = t // k
    return s


def crt(a_tuple, m_tuple):
    """Chinese Reminder Theorem to find x, given partial information about it: .

    Uses the chinese reminder theorem to find x, given that:
        x = a1 mod m1
        x = a2 mod m2
        ...
        x = an mod mn

    Args:
        a_tuple: Tuple of size n representing (a1, a2, ..., an).
        m_tuple: Tuple of size m representing (m1, m2, ..., mn).

    Returns:
        The value of X.

    Raises:
        Exception: If a_tuple and m_tuple are not tuples of the same size.
        Exception: If it is not possible to find X due to numbers not being coprime
    """
    if (not isinstance(a_tuple, tuple) or
            not isinstance(m_tuple, tuple) or
            len(a_tuple) != len(m_tuple)):
        raise Exception("a and m should have the same size")
    # Find M
    M = 1
    for m in m_tuple:
        M *= m
    # Find x
    x = 0
    for index, a in enumerate(a_tuple):
        M_index = M // m_tuple[index]
        N_index = modular_inverse(M_index, m_tuple[index])
        x += a * N_index * M_index
    return x % M


def paillier_dec(ciphertext, N, phi_N, N_power_2=-1, inverse_phy_N=-1):
    """Decripts a ciphertext using the Paillier System.

    Args:
        c: ciphertext to decrypt.
        N: Paillier public key.
        phi_N: Paillier private key phi(N)
        [Optional] N_power_2: N to the power of 2, for eficiency when decrypting several ciphertexts
        [Optional] inverse_phy_N: The inverse of phi_N modulo N, for eficiency when decrypting several ciphertexts

    Returns:
        The decrypted text.
    """
    if N_power_2 == -1:
        N_power_2 = N ** 2
    if inverse_phy_N == -1:
        inverse_phy_N = modular_inverse(phi_N, N)
    fast_exp_result = fast_exp(ciphertext, phi_N, N_power_2)
    return (((fast_exp_result - 1) // N) * inverse_phy_N) % N;  # Missing mod here


def paillier_aggregation(ciphertext_list, N, N_power_2=-1):
    """Calculates the aggregation of several Paillier ciphertexts.

    Calculates the aggregation of several Paillier ciphertexts.

    Args:
        ciphertext_list: List of all the ciphertexts.
        N: N: Paillier public key.
        [Optional] N_power_2: N to the power of 2, for efficiency when decrypting several ciphertexts

    Returns:
        The aggregation (C*) of all the ciphertexts.
    """
    if N_power_2 == -1:
        N_power_2 = N ** 2
    result = 1
    for c in ciphertext_list:
        result *= c % N_power_2
    return result

def garners_formula(a_tuple, m_tuple):
    """ d = a mod p, d = b mod q
        a_tuple = [a,b]
        m_tuple = [p,q]
        Calculate T, u, and d."""
    a,b = a_tuple
    p,q = m_tuple
    T = modular_inverse(p,q)
    u = ((b-a)*T)%q
    d = a + (u*p)
    return (T,u,d)

def ascii_to_int_array(input_string):
    out = []
    for c in input_string:
        out.append(ord(c))
    return out


def phi(n):
    # Initialize result as n
    result = n;

    # Consider all prime factors
    # of n and subtract their
    # multiples from result
    p = 2;
    while (p * p <= n):

        # Check if p is a
        # prime factor.
        if (n % p == 0):

            # If yes, then
            # update n and result
            while (n % p == 0):
                n = int(n / p);
            result -= int(result / p);
        p += 1;

    # If n has a prime factor
    # greater than sqrt(n)
    # (There can be at-most
    # one such prime factor)
    if (n > 1):
        result -= int(result / n);
    return result;

def rsa_crack(n,e):
    m = 13
    cipher_text = fast_exp(m,e,n)
    for d in range(1,n):
        tmp = fast_exp(c,d,n)
        if tmp==m:
            print(d)
            break


if __name__ == "__main__":
    n1 = 4459740564724538700519142326997
    c1 = 191699250018696932235548276819

    n2 = 2281806784635143785292256501293
    c2 = 2262766903820045933008151262425

    n3 = 2467881921864340392351277277159
    c3 = 1643829619688527494120778009131

    a_tup = (n1,n2,n3)
    m_tup = (c1,c2,c3)
    print(crt(m_tup, a_tup))
    out_num = crt(m_tup, a_tup)
    print(iroot(3,out_num));
