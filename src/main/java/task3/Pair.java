package task3;

public class Pair<X, Y> {
    private final X x;
    private final Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public static <X,Y> Pair<X, Y> of(X x, Y y) {
        return new Pair<>(x, y);
    }
}
