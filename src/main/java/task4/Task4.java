package task4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Дано множество отрезков (ai, bi) на прямой. Найти список интервалов этой прямой,
 * которые покрываются одновременно наибольшим кол-вом заданных отрезков.
 * <p>Решение должно иметь сложность O(n*log(n)). </p>
 *
 * <p>Тестовый пример для трех пар отрезков: </p>
 * <pre>
 * 1 4
 * 2 5
 * 3 6
 * Ответ: [3,4]
 * </pre>
 */
public class Task4 {

    public static void main(String[] args) {
        int n;
        List<SegmentBorder> list = new ArrayList<>();

        //считать точки
        System.out.println("Введите число отрезков: ");
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите через пробел границы отрезка(новый отрезок с новой строчки): ");
        for (int i = 0; i < n; i++) {
            String segment = scanner.nextLine();
            String[] s = segment.split(" ");
            list.add(new SegmentBorder(Integer.parseInt(s[0]), true));
            list.add(new SegmentBorder(Integer.parseInt(s[1]), false));
        }

        //сортируем точки
        quickSort(list,0, list.size()-1);

        //подсчет интервалов
        int intervalCount = 0;
        int maxCountInterval = 0;
        List<SegmentBorder> result = new ArrayList<>();
        for (int i = 0; i < 2 * n; i++) {
            if (list.get(i).isStart()) {
                intervalCount++;
                if (intervalCount > maxCountInterval) {
                    result.clear();
                    maxCountInterval = intervalCount;
                }
                if (intervalCount == maxCountInterval) {
                    result.add(list.get(i));
                }

            } else {
                intervalCount--;
                if (result.get(result.size()-1).isStart()) {
                    result.add(list.get(i));
                }
            }
        }

        System.out.println("Кол-во заданных отрезков, которые покрывают вычисленные интервалы:" + maxCountInterval);
        System.out.println("Интервалы");
        for (SegmentBorder segmentBorder : result) {
            if (segmentBorder.isStart()) {
                System.out.print("[" + segmentBorder.getPoint() + ",");
            } else {
                System.out.println(segmentBorder.getPoint() + "]");
            }
        }
        scanner.close();
    }

    /**
     * Быстрая сортировка заключается в рекурсивном делении сортируемого массива
     * данных на 2 части: большую либо равную значению некоторого элемента,
     * называемого pivot, и меньшую либо равную, чем pivot.
     */
    static void quickSort(List<SegmentBorder> list, int left, int right) {
        int index = partition(list, left, right);
        if (left < index - 1)
            quickSort(list, left, index - 1);
        if (index < right)
            quickSort(list, index, right);
    }

    /**
     * Функция разделения массива на две части
     */
    static int partition(List<SegmentBorder> list, int left, int right) {
        int i = left, j = right;
        int tmp;
        boolean tmpb;
        SegmentBorder pivot = list.get((left + right) / 2);

        while (i <= j) {
            while (list.get(i).compareTo(pivot) < 0) {
                i++;
            }
            while (list.get(j).compareTo(pivot) > 0){
                j--;
            }

            if (i <= j) {
                tmp = list.get(i).getPoint();
                tmpb = list.get(i).isStart();
                list.get(i).setPoint(list.get(j).getPoint());
                list.get(i).setStart(list.get(j).isStart());
                list.get(j).setPoint(tmp);
                list.get(j).setStart(tmpb);
                i++;
                j--;
            }
        }

        return i;
    }

}
