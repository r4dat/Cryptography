
MAGIC = 'ADFGX'

def encode(message, secret_alphabet1, keyword):
    # remove duplicate character from keyword -> key
    # keyword = 'checkio' -> key = ['c','h','e','k','i','o']
    key = []
    for c in keyword:
        if c not in key: key.append(c)

    # make sort index -> k
    # keyword = 'cipher' -> k = [0, 4, 3, 2, 1, 2, 5]
    n = len(key)
    k = sorted(range(n), key=lambda i: key[i])

    # encode
    # message = 'I am going' ->
    # s = ['F','A','D','V','A','G','X','X','D','X','F','A','G','D','X','X']
    s = []
    for c in message.lower():
        if c.isalpha() or c.isdigit():
            row, col = divmod(secret_alphabet1.index(c), 5)
            s += [MAGIC[row], MAGIC[col]]

    # reorder
    # reorder index = [0, 6, 12, 4, 10, 3, 9, 15, 1, 7, 13, 2, 8, 14, 5, 11]
    return ''.join(s[j] for i in k for j in range(i, len(s), n))


def decode(message, secret_alphabet1, keyword):
    # remove duplicate character from keyword -> key
    # keyword = 'checkio' -> key = ['c', 'h', 'e', 'k', 'i', 'o']
    key = []
    for c in keyword:
        if c not in key: key.append(c)

    # make sort index -> k
    # keyword = 'cipher' -> k = [0, 4, 3, 2, 1, 2, 5]
    n = len(key)
    k = sorted(range(n), key=lambda i: key[i])

    # make reorder index
    # len(message) == 16 ->
    # x = [0, 6, 12, 4, 10, 3, 9, 15, 1, 7, 13, 2, 8, 14, 5, 11]
    m = len(message)
    x = [j for i in k for j in range(i, m, n)]

    # reorder
    # message = 'FXGAFVXXAXDDDXGA' ->
    # y = ['F','A','D','V','A','G','X','X','D','X','F','A','G','D','X','X']
    y = ['']*m
    for i, c in zip(x, message): y[i] = c

    # decode
    # y -> s = ['i','a','m','g','o','i','n','g']
    s = []
    for i in range(0, m, 2):
        row, col = y[i:i+2]
        s.append(secret_alphabet1[5 * MAGIC.index(row) + MAGIC.index(col)])
    return ''.join(s)



if __name__ == '__main__':
    # input message, keygrid, keyphrase
    t = decode('DXXGGFFADDFAXADDAADADGDGAFFFGFDGAGFXGA','PATREILYBCDFGHKMNOQSUVWXZ','DULCE')
    print(t)
    # assert encode("I am going",
    #               "dhxmu4p3j6aoibzv9w1n70qkfslyc8tr5e2g",
    #               "cipher") == 'FXGAFVXXAXDDDXGA', "encode I am going"
    # assert decode("FXGAFVXXAXDDDXGA",
    #               "dhxmu4p3j6aoibzv9w1n70qkfslyc8tr5e2g",
    #               "cipher") == 'iamgoing', "decode I am going"
    # assert encode("attack at 12:00 am",
    #               "na1c3h8tb2ome5wrpd4f6g7i9j0kjqsuvxyz",
    #               "privacy") == 'DGDDDAGDDGAFADDFDADVDVFAADVX', "encode attack"
    # assert decode("DGDDDAGDDGAFADDFDADVDVFAADVX",
    #               "na1c3h8tb2ome5wrpd4f6g7i9j0kjqsuvxyz",
    #               "privacy") == 'attackat1200am', "decode attack"
    # assert encode("ditiszeergeheim",
    #               "na1c3h8tb2ome5wrpd4f6g7i9j0kjqsuvxyz",
    #               "piloten") == 'DFGGXXAAXGAFXGAFXXXGFFXFADDXGA', "encode ditiszeergeheim"
    # assert decode("DFGGXXAAXGAFXGAFXXXGFFXFADDXGA",
    #               "na1c3h8tb2ome5wrpd4f6g7i9j0kjqsuvxyz",
    #               "piloten") == 'ditiszeergeheim', "decode ditiszeergeheim"
