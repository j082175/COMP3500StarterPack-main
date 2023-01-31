package academy.pocu.comp3500.lab5;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Bank {
    private byte[][] pubKeys;
    private long[] amounts;

    private HashMap<byte[], Long> hashMap = new HashMap<>();

    public Bank(byte[][] pubKeys, final long[] amounts)
            throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        this.pubKeys = pubKeys;
        this.amounts = amounts;

        // Cipher cipher = Cipher.getInstance("RSA");
        // PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new
        // X509EncodedKeySpec(pubKeys[0]));

        // cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // byte[] bytes =

        for (int i = 0; i < amounts.length; i++) {
            hashMap.put(pubKeys[i], amounts[i]);
        }

    }

    public long getBalance(final byte[] pubKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (hashMap.containsKey(pubKey)) {
            return hashMap.get(pubKey);
        }

        return 0;
    }

    public boolean transfer(final byte[] from, byte[] to, final long amount, final byte[] signature)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        if (!hashMap.containsKey(from)) {
            return false;
        }

        if (!hashMap.containsKey(to)) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] byteArray = ByteBuffer.allocate(8).putLong(amount).array();

        md.update(from);
        md.update(to);
        md.update(byteArray);

        PublicKey senderPub = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(from));

        String sigStr = encodeToHexString(signature);

        byte[] decryptByte;
        try {
            decryptByte = decrypt(sigStr, senderPub);
        } catch (Exception e) {
            return false;
        }

        byte[] result = md.digest();

        String compare1 = encodeToHexString(decryptByte);
        String compare2 = encodeToHexString(result);

        if (compare1.equals(compare2)) {

            if (hashMap.get(from) >= amount) {

                if (hashMap.get(to) + amount >= 0) {
                    hashMap.replace(to, hashMap.get(to) + amount);
                    hashMap.replace(from, hashMap.get(from) - amount);
                } else {
                    return false;
                }
            } else {
                return false;
            }

            return true;
        }

        return false;
    }

    private static byte[] decrypt(String ciphertext, PublicKey publicKey) {
        try {
            // byte[] bytes = Base64.getDecoder()
            // .decode(ciphertext);

            byte[] bytes = decodeFromHexString(ciphertext);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            byte[] plaintext = cipher.doFinal(bytes);

            return plaintext;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String encrypt(byte[] plaintext, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] ciphertext = cipher.doFinal(plaintext);

            return encodeToHexString(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static byte[] decodeFromHexString(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            int firstDigit = Character.digit(hexString.charAt(i), 16);
            int secondDigit = Character.digit(hexString.charAt(i + 1), 16);
            bytes[i / 2] = (byte) ((firstDigit << 4) + secondDigit);
        }
        return bytes;
    }

    private static String encodeToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte oneByte : bytes) {
            result.append(String.format("%02x", oneByte));
        }
        return result.toString();
    }

}