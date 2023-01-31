package academy.pocu.comp3500.lab5;

import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator {
    // public static boolean[] prime;

    public static boolean isPrime(final BigInteger number) {

        if (number.compareTo(BigInteger.ZERO) <= 0) {
            return false;
        }

        if (number.intValue() == 1) {
            return false;
        }

        if (number.intValue() == 2) {
            return true;
        }

        if (number.intValue() == 3) {
            return true;
        }

        if (millerRabin(number, 50))
            return true;
        else
            return false;

    }

    public static boolean fermatTest(BigInteger n, Random r) {

        // Ensures that temp > 1 and temp < n.
        BigInteger temp = BigInteger.ZERO;
        do {
            temp = new BigInteger(n.bitLength() - 1, r);
        } while (temp.compareTo(BigInteger.ONE) <= 0);

        // Just calculate temp^*(n-1) mod n
        BigInteger ans = temp.modPow(n.subtract(BigInteger.ONE), n);

        // Return true iff it passes the Fermat Test!
        return (ans.equals(BigInteger.ONE));
    }

    private static boolean mr(BigInteger n, Random r) {

        // Ensures that temp > 1 and temp < n.
        BigInteger temp = BigInteger.ZERO;
        do {
            temp = new BigInteger(n.bitLength() - 1, r);
        } while (temp.compareTo(BigInteger.ONE) <= 0);

        // Screen out n if our random number happens to share a factor with n.
        if (!n.gcd(temp).equals(BigInteger.ONE))
            return false;

        // For debugging, prints out the integer to test with.
        // System.out.println("Testing with " + temp);

        BigInteger base = n.subtract(BigInteger.ONE);
        BigInteger two = new BigInteger("2");

        // Figure out the largest power of two that divides evenly into n-1.
        int k = 0;
        while ((base.mod(two)).equals(BigInteger.ZERO)) {
            base = base.divide(two);
            k++;
        }

        // This is the odd value r, as described in our text.
        // System.out.println("base is " + base);

        BigInteger curValue = temp.modPow(base, n);

        // If this works out, we just say it's prime.
        if (curValue.equals(BigInteger.ONE))
            return true;

        // Otherwise, we will check to see if this value successively
        // squared ever yields -1.
        for (int i = 0; i < k; i++) {

            // We need to really check n-1 which is equivalent to -1.
            if (curValue.equals(n.subtract(BigInteger.ONE))) {
                return true;
            } else {
                curValue = curValue.modPow(two, n);
            }

        }

        // If none of our tests pass, we return false. The number is
        // definitively composite if we ever get here.
        return false;
    }

    public static boolean millerRabin(BigInteger n, int numTimes) {

        Random r = new Random();

        // Run Miller-Rabin numTimes number of times.
        for (int i = 0; i < numTimes; i++)
            if (!mr(n, r)) {
                return false;
            }

        // If we get here, we assume n is prime. This will be incorrect with
        // a probability no greater than 1/4^numTimes.
        return true;
    }
}