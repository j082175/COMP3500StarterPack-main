package academy.pocu.comp3500.lab5;

import java.math.BigInteger;

public class KeyGenerator {
    // public static boolean[] prime;
    static long primeTest[] = { 2, 325, 9375, 28178, 450775, 9780504, 1795265022 };

    public static boolean isPrime(final BigInteger number) {

        if (number.compareTo(BigInteger.ZERO) <= 0) {
            return false;
        }

        // long val = number.longValue();

        // if (is_prime(number.longValue())) {
        // return true;
        // }

        if (number.intValue() == 1) {
            return false;
        }

        if (number.intValue() == 2) {
            return true;
        }

        if (number.remainder(BigInteger.TWO) == BigInteger.ZERO) {
            return false;
        }

        if (MillerRabin.isProbablePrime(number, 10)) {
            return true;
        }

        return false;
    }

    public static long addmod(long x, long y, long m) {
        x %= m;
        y %= m;
        return (x >= m - y ? x - (m - y) : x + y);
    }

    // calculate (x * y) % m; overlow-safe
    public static long mulmod(long x, long y, long m) {
        x %= m;
        y %= m;
        long r = 0;
        while (y > 0) {
            if (y % 2 == 1)
                r = addmod(r, x, m);
            x = addmod(x, x, m);
            y /= 2;
        }
        return r;
    }

    // calculate x^y % m; overflow-safe
    public static long powmod(long x, long y, long m) {
        x %= m;
        long r = 1;
        while (y > 0) {
            if (y % 2 == 1)
                r = mulmod(r, x, m);
            x = mulmod(x, x, m);
            y /= 2;
        }
        return r;
    }

    // true for probable prime, false for composite
    public static boolean miller_rabin(long n, long a) {
        long d = n - 1;
        while (d % 2 == 0) {
            if (powmod(a, d, n) == n - 1)
                return true;
            d /= 2;
        }
        long tmp = powmod(a, d, n);
        return tmp == n - 1 || tmp == 1;
    }

    public static boolean is_prime(long n) {
        if (n <= 1)
            return false;
        if (n <= Long.parseLong("10000000000")) {
            for (long i = 2; i * i <= n; i++)
                if (n % i == 0)
                    return false;
            return true;
        }
        for (long a : primeTest)
            if (!miller_rabin(n, a))
                return false;
        return true;
    }

}