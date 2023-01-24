package academy.pocu.comp3500.lab4;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;

public final class Cracker {
    private User[] userTable;
    private String email;
    private String password;

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;
    }

    public String[] run(final RainbowTable[] rainbowTables) {
        // rainbowTables 의 길이는 언제나 5
        // rainbowTables[0]은 CRC32용 레인보우 테이블
        // rainbowTables[1]은 MD2용 레인보우 테이블
        // rainbowTables[2]은 MD5용 레인보우 테이블
        // rainbowTables[3]은 SHA1용 레인보우 테이블
        // rainbowTables[4]은 SHA256용 레인보우 테이블

        boolean bCheck = false;

        String[] result = new String[this.userTable.length];
        for (int i = 0; i < rainbowTables.length; i++) {
            for (int j = 0; j < this.userTable.length; j++) {
                if (rainbowTables[i].contains(this.userTable[j].getPasswordHash())) {
                    result[j] = rainbowTables[i].get(this.userTable[j].getPasswordHash());
                    bCheck = true;
                }
            }

            if (bCheck) {
                break;
            }
        }

        return result;
    }
}