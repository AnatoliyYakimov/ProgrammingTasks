package task1;

import java.time.format.DateTimeFormatter;

public class Task1 {

    public static void main(String[] args) throws Exception {
        CustomDate date = new CustomDate()
                .plusYears(2020)
                .plusMonths(10)
                .plusDays(2);
        //Форматируем в произвольный формат
        System.out.println(date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        //Форматируем в стандартный формат
        System.out.println(date);

        CustomDate nextDay = date.plusDays(1);

        if (nextDay.compareTo(date) > 0) {
            System.out.printf("%s is greater than %s\n", nextDay, date);
        } else {
            throw new Exception("Assertion failed. nextDay should be greater than original date");
        }

        CustomDate previousMonth = date.minusMonths(1);

        if (previousMonth.compareTo(date) < 0) {
            System.out.printf("%s is lesser than %s\n", previousMonth, date);
        } else {
            throw new Exception("Assertion failed. previousMonth should be lesser than original date");
        }

        if (date.compareTo(date) == 0) {
            System.out.printf("%s is equal to %s\n", date, date);
        } else {
            throw new Exception("Assertion failed. date should ne equal to itself");
        }
    }
}
