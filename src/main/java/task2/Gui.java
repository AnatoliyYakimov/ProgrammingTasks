package task2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Графический интерфейс
 */
public class Gui extends JFrame {

    private static final String PLACEHOLDER_TEXT = "Введите значение";

    private final CustomList<String> list = new CustomList<>();

    public Gui() throws HeadlessException {
        super("Задание 2. Списки");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание панели
        JPanel contents = new JPanel();

        JFileChooser jFileChooser = new JFileChooser();

        //Кнопка добавления
        JButton add = new JButton("Добавить");
        add.setEnabled(false);
        //текстовое поле
        JTextField textField = new JTextField(PLACEHOLDER_TEXT);
        textField.setMaximumSize(new Dimension(200, 24));
        textField.setFont(new Font("Serif", Font.PLAIN, 16));
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(PLACEHOLDER_TEXT)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    add.setEnabled(true);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(PLACEHOLDER_TEXT);
                    add.setEnabled(false);
                }
            }
        });
        add.addActionListener(e -> {
            list.add(textField.getText());
            validate();
        });

        //Кнопка перемешивания
        JButton shuffle = new JButton("Перемешать");
        shuffle.addActionListener(e -> {
            Task2.swapElements(list);
        });

        //Кнопка загрузки из файла
        JButton load = new JButton("Загрузить");
        load.addActionListener(e -> {
            int returnVal = jFileChooser.showDialog(contents, "Выбрать");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                try {
                    CustomList<String> fromFile = Task2.loadFromFile(file);
                    list.clear();
                    list.addAll(fromFile);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });


        // Размещение компонентов в панели

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.X_AXIS));

        listPanel.add(new JScrollPane(new JList<>(list)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        buttonPanel.add(textField);
        buttonPanel.add(add);
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
