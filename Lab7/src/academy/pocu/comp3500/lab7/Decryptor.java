package academy.pocu.comp3500.lab7;

public class Decryptor {

    public Tri tri = new Tri();
    public Decryptor(final String[] codeWords) {

        for (int i = 0; i < codeWords.length; i++) {
            tri.inputData(codeWords[i]);
        }
    }

    public String[] findCandidates(final String word) {
        return new String[]{};
    }
}