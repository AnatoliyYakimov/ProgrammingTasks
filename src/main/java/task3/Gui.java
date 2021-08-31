package task3;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Графический интерфейс
 */
public class Gui extends JFrame {

    private final DefaultListModel<Float> list1 = new DefaultListModel<>();
    private final DefaultListModel<Float> list2 = new DefaultListModel<>();

    private Queue<Float> x;
    private Queue<Float> y;

    private static final String[] OPTIONS = {
            "Собстсвенная реализация очереди (task3.MyQueue)",
            "Стандартная очередь Java (java.util.ArrayDeque)"
    };

    public Gui() throws HeadlessException {
        super("Задание 3. Очереди");

        /*
            Требуется решить задачу в 2-х вариантах:
            С использованием стека / очереди, самостоятельно реализованных на основе связного списка.
            С использованием реализации стека / очереди, которая уже есть в стандартной библиотеки языка Java
         */
        int chosenOption = JOptionPane.showOptionDialog(this, "Выберите реализацию очереди",
                "Выбор реализации", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, OPTIONS, 0);
        switch (chosenOption) {
            case 0: {
                x = new MyQueue<>();
                y = new MyQueue<>();
                break;
            }
            case 1: {
                x = new ArrayDeque<>();
                y = new ArrayDeque<>();
                break;
            }
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Создание панели
        JPanel contents = new JPanel();

        JFileChooser jFileChooser = new JFileChooser();


        //Кнопка выполнения задания
        JButton shuffle = new JButton("Задание");
        shuffle.addActionListener(e -> {
            int steps = Task3.countSteps(x, y);
            String message = String.format("Чтобы одна из очередей стала пустой, необходимо %d шагов", steps);
            JOptionPane.showMessageDialog(this, message, "Результат работы", JOptionPane.PLAIN_MESSAGE);
            //Синхронизируем содержимое очередей и списков в GUI
            list1.clear();
            x.forEach(list1::addElement);
            list2.clear();
            y.forEach(list2::addElement);
        });

        //Кнопка загрузки из файла
        JButton load = new JButton("Загрузить");
        load.addActionListener(e -> {
            int returnVal = jFileChooser.showDialog(contents, "Выбрать");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                try {
                    Pair<Queue<Float>, Queue<Float>> queueQueuePair = Task3.loadFromFile(file);
                    //Синхронизируем содержимое очередей и списков в GUI
                    x.clear();
                    x.addAll(queueQueuePair.getX());
                    list1.clear();
                    x.forEach(list1::addElement);
                    y.clear();
                    y.addAll(queueQueuePair.getY());
                    list2.clear();
                    y.forEach(list2::addElement);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });


        // Размещение компонентов в панели

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.X_AXIS));

        listPanel.add(new JScrollPane(new JList<>(list1)));
        listPanel.add(new JScrollPane(new JList<>(list2)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        buttonPanel.add(shuffle);
        buttonPanel.add(load);


        JPanel masterPanel = new JPanel();
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));

        masterPanel.add(buttonPanel);
        masterPanel.add(listPanel);
        masterPanel.setPreferredSize(new Dimension(580, 430));

        contents.add(masterPanel);

        setContentPane(contents);
        // Вывод окна
        setSize(600, 450);
        setVisible(true);
    }
}
