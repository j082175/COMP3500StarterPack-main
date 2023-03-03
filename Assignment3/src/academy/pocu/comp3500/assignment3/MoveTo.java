package academy.pocu.comp3500.assignment3;


import java.util.HashSet;
import java.util.Objects;

public class MoveTo {
    public int toX;
    public int toY;

    public MoveTo(final int toX, final int toY) {
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MoveTo) {
            if (toX == ((MoveTo) obj).toX && toY == ((MoveTo) obj).toY) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toX, toY);
    }
}
