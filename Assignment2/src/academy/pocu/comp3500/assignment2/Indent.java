package academy.pocu.comp3500.assignment2;

public final class Indent {

    private Indent next;
    private String data;
    private int indentLevel;
    private boolean isDiscarded;

    public Indent() {
        isDiscarded = false;
    }

    public boolean getDiscarede() {
        return isDiscarded;
    }

    public void setNext(Indent i) {
        this.next = i;
    }

    public Indent getNext() {
        return this.next;
    }

    public void setData(String text) {
        this.data = text;
    }

    public String getData() {
        return this.data;
    }

    public void discard() {

        Indent start = this;

        if (start.isDiscarded == true) {
            return;
        }

        int currentIndentLevel = indentLevel;

        while (start != null) {
            if (currentIndentLevel <= start.getIndentLevel()) {
                start.isDiscarded = true;

            } else {
                break;
            }
            start = start.getNext();
        }

    }

    public void setIndentLevel(int value) {
        this.indentLevel = value;
    }

    public int getIndentLevel() {
        return indentLevel;
    }
}