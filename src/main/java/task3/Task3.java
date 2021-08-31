package task3;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Scanner;

/**
 * Даны две очереди X и Y, содержащие вещественные числа.
 * Из каждой очереди одновременно извлекается по одному числу,
 * х и у соответственно. Если х < у, то число (х + у)
 * помещается в конец очереди X, иначе число (х–у)
 * помещается в конец очереди Y.
 * Необходимо определить число шагов, через которое одна из очередей станет пустой.
 */
public class Task3 {
    public static void main(String[] args) {
        new Gui();
    }

    public static Pair<Queue<Float>, Queue<Float>> loadFromFile(File file) throws FileNotFoundException {
        Queue<Float> x = new MyQueue<>();
        Queue<Float> y = new MyQueue<>();
        try (Scanner scanner = new Scanner(new FileInputStream(file))){
            String[] split = scanner.nextLine().split(",");
            for (String s : split) {
                x.add(Float.parseFloat(s));
            }
            split = scanner.nextLine().split(",");
            for (String s : split) {
                y.add(Float.parseFloat(s));
            }
            return Pair.of(x, y);
        }
    }

    /**
     * .Даны две очереди X и Y, содержащие вещественные числа. Из каждой очереди одновременно извлекается по одному числу,
     * х и у соответственно. Если х < у, то число (х + у) помещается в конец очереди X,
     * иначе число (х–у) помещается в конец очереди Y. Необходимо определить число шагов,
     * через которое одна из очередей станет пустой.
     */
    public static int countSteps(Queue<Float> queue1, Queue<Float> queue2) {
        int count = 0;
        Float x;
        Float y;
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            x = queue1.poll();
            y = queue2.poll();
            count++;
            if (x < y) {
                queue1.offer(x + y);
            } else {
                queue2.offer(x - y);
            }

        }
        return count;
    }
}
