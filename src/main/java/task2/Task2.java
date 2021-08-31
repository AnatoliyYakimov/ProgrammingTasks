package task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Task2  {

    public static void main(String[] args) {
        CustomList<String> list = new CustomList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.add("Four");
        list.add("Five");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("==============");
        swapElements(list);
        for (String s : list) {
            System.out.println(s);
        }
        Gui gui = new Gui();
    }

    /**
     * Основная задача: поменять местами 2*n и 2*n + 1 элементы
     */
    public static void swapElements(CustomList<String> customList) {
        for (int i = 0; (i + 1) < customList.length(); i += 2) {
            customList.swap(i, i + 1);
        }
    }

    /**
     * Загрузка списка из файла.
     * Файл должен быть тесктовый, элементы списка должны быть разделены символом {@code \n} - признаком новой строки.
     * @param file файл
     * @return список
     * @throws FileNotFoundException если файл не найден
     */
    public static CustomList<String> loadFromFile(File file) throws FileNotFoundException {
        CustomList<String> strings = new CustomList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(file))){
            while (scanner.hasNextLine()) {
                strings.add(scanner.nextLine());
            }
            return strings;
        }
    }

}
