package academy.pocu.comp3500.lab4;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;

public final class Cracker {
    private User[] userTable;
    private String email;
    private String password;

    private int rainbowCheck;

    public Cracker(User[] userTable, String email, String password) throws NoSuchAlgorithmException {
        this.userTable = userTable;
        this.email = email;
        this.password = password;

        String pshash = null;
        for (int i = 0; i < this.userTable.length; i++) {
            if (this.userTable[i].getEmail().equals(this.email)) {
                pshash = this.userTable[i].getPasswordHash();
                break;
            }
        }

        String[] algorithm = {"MD2", "MD5"};

        for (int i = 0; i < algorithm.length; i++) {
            MessageDigest md = MessageDigest.getInstance(algorithm[i]);
            //해쉬값 업데이트
            md.update(this.password.getBytes());
            //String result = DatatypeConverter.printBase64Binary(md.digest());
            Base64.Encoder encoder = Base64.getEncoder();
            String result = encoder.encodeToString(md.digest());

            if (result.equals(pshash)) {
                this.rainbowCheck = i + 1;
                break;
            }
        }
    }

    public String[] run(final RainbowTable[] rainbowTables) {
        // rainbowTables 의 길이는 언제나 5
        // rainbowTables[0]은 CRC32용 레인보우 테이블
        // rainbowTables[1]은 MD2용 레인보우 테이블
        // rainbowTables[2]은 MD5용 레인보우 테이블
        // rainbowTables[3]은 SHA1용 레인보우 테이블
        // rainbowTables[4]은 SHA256용 레인보우 테이블

        String[] result = new String[this.userTable.length];

        final int CRC32 = 0;
        final int MD2 = 1;
        final int MD5 = 2;
        final int SHA1 = 3;
        final int SHA256 = 4;

        String checkPasswordHash = this.userTable[0].getPasswordHash();

        if (this.userTable.length == 1) {
            for (int i = 0; i < rainbowTables.length; i++) {
                if (rainbowTables[i].contains(this.userTable[0].getPasswordHash())) {
                    result[0] = rainbowTables[i].get(this.userTable[0].getPasswordHash());
                    return result;
                }
            }
        }

        if (this.rainbowCheck == 1) {
            printResult(rainbowTables, userTable, MD2, result);
            return result;
        } else if (this.rainbowCheck == 2) {
            printResult(rainbowTables, userTable, MD5, result);
            return result;
        }

        if (checkPasswordHash.length() < 24) {
            printResult(rainbowTables, userTable, CRC32, result);
            return result;

        } else if (checkPasswordHash.length() == 28) {
            printResult(rainbowTables, userTable, SHA1, result);
            return result;
        } else if (checkPasswordHash.length() == 44) {
            printResult(rainbowTables, userTable, SHA256, result);
            return result;
        }

        return result;
    }

    private boolean printResult(final RainbowTable[] rainbowTables, final User[] userTable, int key, String[] outResult) {

        boolean bCheck = false;
        for (int j = 0; j < this.userTable.length; j++) {
            if (rainbowTables[key].contains(this.userTable[j].getPasswordHash())) {
                outResult[j] = rainbowTables[key].get(this.userTable[j].getPasswordHash());
                bCheck = true;
            }
        }
        return bCheck;
    }
}