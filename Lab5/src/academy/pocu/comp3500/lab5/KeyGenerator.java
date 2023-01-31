package academy.pocu.comp3500.lab5;

import java.math.BigInteger;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

public class KeyGenerator {
    // public static boolean[] prime;
    static long primeTest[]= {2,325,9375,28178,450775,9780504,1795265022};
    public static ArrayList<Boolean> prime;

    public static boolean isPrime(final BigInteger number) {

        if (number.compareTo(BigInteger.ZERO) <= 0) {
            return false;
        }

        // make_prime(number);

        // if (prime.get(number.intValue()) == false) {
        // return true;
        // }

        if (is_prime(number.longValue())) {
            return true;
        }

        return false;
    }

    // public static void make_prime(BigInteger N) {

    //     // prime = new boolean[N + 1]; // 0 ~ N

    //     prime = new ArrayList<>()

    //     /*
    //      * 소수가 아닌 index = true
    //      * 소수인 index = false
    //      */

    //     // 2 미만의 N 을 입력받으면 소수는 판별할 필요 없으므로 바로 return
    //     if (N < 2) {
    //         return;
    //     }

    //     prime[0] = prime[1] = true;

    //     // 제곱근 함수 : Math.sqrt()
    //     for (int i = 2; i <= Math.sqrt(N); i++) {

    //         // 이미 체크된 배열이면 다음 반복문으로 skip
    //         if (prime[i] == true) {
    //             continue;
    //         }

    //         // i 의 배수들을 걸러주기 위한 반복문
    //         for (int j = i * i; j < prime.length; j = j + i) {
    //             prime[j] = true;
    //         }
    //     }

    // }

    public static long addmod(long x, long y, long m) {
        x %= m;
        y %= m;
        return (x >= m - y? x - (m - y) : x + y);
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
            if (powmod(a, d, n) == n-1)
                return true;
            d /= 2;
        }
        long tmp = powmod(a, d, n);
        return tmp == n-1 || tmp == 1;
    }
     
    public static boolean is_prime(long n) {
        if (n <= 1)
            return false;
        if (n <= Long.parseLong("10000000000")) {
            for (long i = 2; i*i <= n; i++)
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