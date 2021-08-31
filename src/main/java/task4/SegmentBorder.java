package task4;

/**
 * Класс границы отрезка - с координатой и пометкой, является ли граница началом
 */
public class SegmentBorder implements Comparable<SegmentBorder> {
    private int point;
    private boolean isStart;

    public SegmentBorder(int point, boolean isStart) {
        this.point = point;
        this.isStart = isStart;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    @Override
    public int compareTo(SegmentBorder o) {
        if (getPoint() == o.getPoint()) {
            if (isStart() && !o.isStart()) {
                return 1;
            } else {
                if (!isStart() && o.isStart()) {
                    return -1;
                } else return 0;
            }
        } else {
            return getPoint() > o.getPoint() ? 1 : -1;
        }
    }
}
