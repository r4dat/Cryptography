from sympy.ntheory.residue_ntheory import sqrt_mod
from sympy.ntheory.residue_ntheory import quadratic_residues

if __name__ == '__main__':
    print(sqrt_mod(197,2773,all_roots=True))

    print(quadratic_residues(239))