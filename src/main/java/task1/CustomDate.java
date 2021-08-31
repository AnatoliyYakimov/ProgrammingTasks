package task1;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.List;

public class CustomDate implements TemporalAccessor, Comparable<CustomDate> {

    private final int daysFromStart;

    public static final int DAYS_PER_MONTH = 30;
    public static final int DAYS_PER_YEAR = 365;

    private static final List<TemporalField> SUPPORTED_FIELDS = Arrays.asList(ChronoField.YEAR_OF_ERA, ChronoField.YEAR, ChronoField.DAY_OF_MONTH, ChronoField.MONTH_OF_YEAR);

    public CustomDate() {
        this.daysFromStart = 0;
    }
    public CustomDate(int daysFromStart) {
        this.daysFromStart = Math.max(daysFromStart, 0);
    }

    public CustomDate plusDays(int days) {
        return new CustomDate(daysFromStart + days);
    }

    public CustomDate plusMonths(int months) {
        return new CustomDate(daysFromStart + months * DAYS_PER_MONTH);
    }

    public CustomDate plusYears(int years) {
        return new CustomDate(daysFromStart + years * DAYS_PER_YEAR);
    }

    public CustomDate minusDays(int days) {
        return new CustomDate(daysFromStart - days);
    }

    public CustomDate minusMonths(int months) {
        return new CustomDate(daysFromStart - months * DAYS_PER_MONTH);
    }

    public CustomDate minusYears(int years) {
        return new CustomDate(daysFromStart - years * DAYS_PER_YEAR);
    }

    public String format(DateTimeFormatter formatter) {
        return formatter.format(this);
    }

    @Override
    public String toString() {
        return format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Метод интерфейса {@link Comparable} для сравнения объекктов.
     */
    @Override
    public int compareTo(CustomDate other) {
        return Integer.compare(daysFromStart, other.daysFromStart);
    }

    /**
     * Метод интерфейса {@link TemporalAccessor}
     * Необходим для работы с {@link DateTimeFormatter}
     */
    @Override
    public boolean isSupported(TemporalField field) {
        return SUPPORTED_FIELDS.contains(field);
    }

    /**
     * Метод интерфейса {@link TemporalAccessor}
     * Необходим для работы с {@link DateTimeFormatter}
     */
    @Override
    public long getLong(TemporalField field) {
        if (ChronoField.DAY_OF_MONTH.equals(field)) {
            //Текущий день месяца
            return 1 + daysFromStart % DAYS_PER_YEAR % DAYS_PER_MONTH;
        }
        if (ChronoField.MONTH_OF_YEAR.equals(field)) {
            //Текущий месяц года
            return 1 + daysFromStart % DAYS_PER_YEAR / DAYS_PER_MONTH;
        }
        if (ChronoField.YEAR.equals(field) || ChronoField.YEAR_OF_ERA.equals(field)) {
            //Теущий год
            return 1 + daysFromStart / DAYS_PER_YEAR;
        }
        throw new UnsupportedTemporalTypeException("Field unsupported");
    }
}
