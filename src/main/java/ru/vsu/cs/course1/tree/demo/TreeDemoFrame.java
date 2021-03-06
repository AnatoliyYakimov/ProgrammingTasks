package ru.vsu.cs.course1.tree.demo;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.vsu.cs.course1.tree.BinaryTreeAlgorithms;
import ru.vsu.cs.course1.tree.SimpleBinaryTree;
import ru.vsu.cs.course1.tree.BinaryTreePainter;
import ru.vsu.cs.course1.tree.BinaryTree;
import ru.vsu.cs.course1.tree.bst.BSTree;
import ru.vsu.cs.course1.tree.bst.SimpleBSTree;
import ru.vsu.cs.course1.tree.bst.SimpleBSTreeMap;
import ru.vsu.cs.course1.tree.bst.avl.AVLTree;
import ru.vsu.cs.course1.tree.bst.avl.AVLTreeMap;
import ru.vsu.cs.course1.tree.bst.rb.RBTree;
import ru.vsu.cs.course1.tree.bst.rb.RBTreeMap;
import ru.vsu.cs.util.ArrayUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class TreeDemoFrame extends JFrame {
    private JPanel panelMain;
    private JButton buttonPreOrderTraverse;
    private JButton buttonInOrderTraverse;
    private JButton buttonPostOrderTraverse;
    private JButton buttonByLevelTraverse;
    private JTextArea textAreaSystemOut;
    private JTextField textFieldBracketNotationTree;
    private JButton buttonMakeTree;
    private JButton buttonMakeBSTree;
    private JSplitPane splitPaneMain;
    private JTextField textFieldValues;
    private JSpinner spinnerRandomCount;
    private JButton buttonRandomGenerate;
    private JButton buttonSortValues;
    private JButton buttonMakeBSTree2;
    private JButton buttonMakeAVLTree;
    private JButton buttonMakeRBTree;
    private JTextField textFieldSingleValue;
    private JButton buttonAddValue;
    private JButton buttonRemoveValue;
    private JPanel panelPaintArea;
    private JButton buttonSaveImage;
    private JButton buttonToBracketNotation;
    private JCheckBox checkBoxTransparent;
    private JSpinner spinnerSingleValue;
    private JButton reorganize;

    private JMenuBar menuBarMain;
    private JPanel paintPanel = null;
    private JFileChooser fileChooserSave;

    BinaryTree<Integer> tree = new SimpleBinaryTree<>();


    public TreeDemoFrame() {
        this.setTitle("???????????????? ??????????????");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        createMenu();

        splitPaneMain.setDividerLocation(0.5);
        splitPaneMain.setResizeWeight(1.0);
        splitPaneMain.setBorder(null);

        paintPanel = new JPanel() {
            private Dimension paintSize = new Dimension(0, 0);

            @Override
            public void paintComponent(Graphics gr) {
                super.paintComponent(gr);
                paintSize = BinaryTreePainter.paint(tree, gr);
                if (!paintSize.equals(this.getPreferredSize())) {
                    SwingUtils.setFixedSize(this, paintSize.width, paintSize.height);
                }
            }
        };
        JScrollPane paintJScrollPane = new JScrollPane(paintPanel);
        panelPaintArea.add(paintJScrollPane);

        fileChooserSave = new JFileChooser();
        fileChooserSave.setCurrentDirectory(new File("./images"));
        FileFilter filter = new FileNameExtensionFilter("SVG images", "svg");
        fileChooserSave.addChoosableFileFilter(filter);
        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        spinnerRandomCount.setValue(30);
        spinnerSingleValue.setValue(10);

        buttonMakeTree.addActionListener(actionEvent -> {
            try {
                SimpleBinaryTree<Integer> tree = new SimpleBinaryTree<>(Integer::parseInt);
                tree.fromBracketNotation(textFieldBracketNotationTree.getText());
                this.tree = tree;
                repaintTree();
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });
        buttonMakeBSTree.addActionListener(actionEvent -> {
            try {
                SimpleBSTree<Integer> tree = new SimpleBSTree<>(Integer::parseInt);
                tree.fromBracketNotation(textFieldBracketNotationTree.getText());
                this.tree = tree;
                repaintTree();
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        buttonRandomGenerate.addActionListener(actionEvent -> {
            int size = ((Integer) spinnerRandomCount.getValue()).intValue();
            int[] arr = ArrayUtils.createRandomIntArray(size, (size <= 50) ? 100 : 1000);
            textFieldValues.setText(ArrayUtils.toString(arr));
        });
        buttonSortValues.addActionListener(actionEvent -> {
            try {
                int[] arr = ArrayUtils.toIntArray(textFieldValues.getText());
                Arrays.sort(arr);
                textFieldValues.setText(ArrayUtils.toString(arr));
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        buttonMakeBSTree2.addActionListener(actionEvent -> {
            try {
                makeBSTFromValues(new SimpleBSTree<>(Integer::parseInt));
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });
        buttonMakeAVLTree.addActionListener(actionEvent -> {
            try {
                makeBSTFromValues(new AVLTree<>());
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });
        buttonMakeRBTree.addActionListener(actionEvent -> {
            try {
                makeBSTFromValues(new RBTree<>());
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        buttonAddValue.addActionListener(actionEvent -> {
            if (!(tree instanceof BSTree)) {
                SwingUtils.showInfoMessageBox("?????????????? ???????????? ???? ???????????????? ?????????????? ????????????!");
                return;
            }
            try {
                int value = Integer.parseInt(spinnerSingleValue.getValue().toString());
                ((BSTree<Integer>) tree).put(value);
                repaintTree();
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });
        buttonRemoveValue.addActionListener(actionEvent -> {
            if (!(tree instanceof BSTree)) {
                SwingUtils.showInfoMessageBox("?????????????? ???????????? ???? ???????????????? ?????????????? ????????????!");
                return;
            }
            try {
                int value = Integer.parseInt(spinnerSingleValue.getValue().toString());
                ((BSTree<Integer>) tree).remove(value);
                repaintTree();
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        buttonToBracketNotation.addActionListener(actionEvent -> {
            if (tree == null) {
                return;
            }
            textFieldBracketNotationTree.setText(tree.toBracketStr());
        });

        buttonSaveImage.addActionListener(actionEvent -> {
            if (tree == null) {
                return;
            }
            try {
                if (fileChooserSave.showSaveDialog(TreeDemoFrame.this) == JFileChooser.APPROVE_OPTION) {
                    String filename = fileChooserSave.getSelectedFile().getPath();
                    if (!filename.toLowerCase().endsWith(".svg")) {
                        filename += ".svg";
                    }
                    BinaryTreePainter.saveIntoFile(tree, filename, checkBoxTransparent.isSelected());
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonPreOrderTraverse.addActionListener(actionEvent -> {
            showSystemOut(() -> {
                System.out.println("????????????????????:");
                BinaryTreeAlgorithms.preOrderVisit(tree.getRoot(), (value, level) -> {
                    System.out.println(value + " (?????????????? " + level + ")");
                });
                /*
                // ?????????????????????????? ???????????? ?????? ????????????-??????????????????
                class InnerVisitor implements BinaryTreeAlgorithms.Visitor<Integer> {
                    @Override
                    public void visit(Integer value, int level) {
                        System.out.println(value + " (?????????????? " + level + ")");
                    }
                }
                BinaryTreeAlgorithms.preOrderVisit(tree.getRoot(), new InnerVisitor());
                */
                System.out.println();
                System.out.println("????????????????:");
                for (Integer i : BinaryTreeAlgorithms.preOrderValues(tree.getRoot())) {
                    System.out.println(i);
                }
            });
        });
        buttonInOrderTraverse.addActionListener(actionEvent -> {
            showSystemOut(() -> {
                System.out.println("????????????????????:");
                BinaryTreeAlgorithms.inOrderVisit(tree.getRoot(), (value, level) -> {
                    System.out.println(value + " (?????????????? " + level + ")");
                });
                System.out.println();
                System.out.println("????????????????:");
                for (Integer i : BinaryTreeAlgorithms.inOrderValues(tree.getRoot())) {
                    System.out.println(i);
                }
            });
        });
        buttonPostOrderTraverse.addActionListener(actionEvent -> {
            showSystemOut(() -> {
                System.out.println("????????????????????:");
                BinaryTreeAlgorithms.postOrderVisit(tree.getRoot(), (value, level) -> {
                    System.out.println(value + " (?????????????? " + level + ")");
                });
                System.out.println();
                System.out.println("????????????????:");
                for (Integer i : BinaryTreeAlgorithms.postOrderValues(tree.getRoot())) {
                    System.out.println(i);
                }
            });
        });
        buttonByLevelTraverse.addActionListener(actionEvent -> {
            showSystemOut(() -> {
                System.out.println("????????????????????:");
                BinaryTreeAlgorithms.byLevelVisit(tree.getRoot(), (value, level) -> {
                    System.out.println(value + " (?????????????? " + level + ")");
                });
                System.out.println();
                System.out.println("????????????????:");
                for (Integer i : BinaryTreeAlgorithms.byLevelValues(tree.getRoot())) {
                    System.out.println(i);
                }
            });
        });
    }

    /**
     * ???????????????? ????????
     */
    private void createMenu() {
        JMenu menuTesting = new JMenu("????????????????????????");
        Class[] mapClasses = {SimpleBSTreeMap.class, AVLTreeMap.class, RBTreeMap.class};
        for (Class mapClass : mapClasses) {
            JMenuItem menuItem = new JMenuItem("???????????????????????? " + mapClass.getSimpleName());
            menuItem.addActionListener(actionEvent -> {
                try {
                    Map<Integer, Integer> map = (Map<Integer, Integer>) mapClass.getConstructor().newInstance();
                    showSystemOut(() -> {
                        MapTest.testCorrect(map);
                    });
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            });
            menuTesting.add(menuItem);
        }

        menuBarMain = new JMenuBar();
        menuBarMain.add(menuTesting);
        setJMenuBar(menuBarMain);
    }

    /**
     * ?????????????????????? ????????????
     */
    public void repaintTree() {
        //panelPaintArea.repaint();
        paintPanel.repaint();
        //panelPaintArea.revalidate();
    }

    /**
     * ???????????????????? ???????????????? ?? ?????????????? ???????????????????????? ???????????? ?? ???????? (textAreaSystemOut)
     *
     * @param action ?????????????????????? ????????????????
     */
    private void showSystemOut(Runnable action) {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(baos, true, "UTF-8"));

            action.run();

            textAreaSystemOut.setText(baos.toString("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            SwingUtils.showErrorMessageBox(e);
        }
        System.setOut(oldOut);
    }

    /**
     * ?????????????????? ???????????? ?????????????????????? ???????? ?????????????????? (textFieldValues)
     *
     * @param tree ????????????
     */
    private void makeBSTFromValues(BSTree<Integer> tree) {
        int[] values = ArrayUtils.toIntArray(textFieldValues.getText());
        tree.clear();
        for (int v : values) {
            tree.put(v);
        }
        this.tree = tree;
        repaintTree();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), 10, 10));
        splitPaneMain = new JSplitPane();
        panelMain.add(splitPaneMain, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneMain.setLeftComponent(panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("???????????? ?? ?????????????????? ??????????????:");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldBracketNotationTree = new JTextField();
        textFieldBracketNotationTree.setText("8 (6 (4 (5), 6), 5 (, 5 (2, 8)))");
        panel2.add(textFieldBracketNotationTree, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonMakeTree = new JButton();
        buttonMakeTree.setText("?????????????????? ????????????");
        panel2.add(buttonMakeTree, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonMakeBSTree = new JButton();
        buttonMakeBSTree.setText("?????????????????? ???????????? ????????????");
        panel2.add(buttonMakeBSTree, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textFieldValues = new JTextField();
        textFieldValues.setText("6, 8, 3, 5, 7, 2, 16, 1, 15, 12, 9");
        panel3.add(textFieldValues, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spinnerRandomCount = new JSpinner();
        panel4.add(spinnerRandomCount, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, -1), new Dimension(80, -1), new Dimension(80, -1), 0, false));
        buttonRandomGenerate = new JButton();
        buttonRandomGenerate.setText("??????????????????????????");
        panel4.add(buttonRandomGenerate, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonSortValues = new JButton();
        buttonSortValues.setText("??????????????????????");
        panel4.add(buttonSortValues, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("c???????????????? ??????????");
        panel4.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonMakeBSTree2 = new JButton();
        buttonMakeBSTree2.setText("?????????????????? ???????????????? ????????????");
        panel5.add(buttonMakeBSTree2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel5.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        reorganize = new JButton();
        reorganize.setText("????????????????????????????");
        panel5.add(reorganize, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel6.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        buttonAddValue = new JButton();
        buttonAddValue.setText("????????????????");
        panel6.add(buttonAddValue, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonRemoveValue = new JButton();
        buttonRemoveValue.setText("??????????????");
        panel6.add(buttonRemoveValue, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerSingleValue = new JSpinner();
        panel6.add(spinnerSingleValue, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, -1), new Dimension(80, -1), new Dimension(80, -1), 0, false));
        panelPaintArea = new JPanel();
        panelPaintArea.setLayout(new BorderLayout(0, 0));
        panel1.add(panelPaintArea, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panelPaintArea.add(spacer5, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSaveImage = new JButton();
        buttonSaveImage.setText("?????????????????? ?????????????????????? ?? SVG");
        panel7.add(buttonSaveImage, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonToBracketNotation = new JButton();
        buttonToBracketNotation.setText("?? ?????????????????? ??????????????????????????");
        panel7.add(buttonToBracketNotation, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panel7.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        checkBoxTransparent = new JCheckBox();
        checkBoxTransparent.setText("????????????????????????");
        panel7.add(checkBoxTransparent, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneMain.setRightComponent(panel8);
        buttonPreOrderTraverse = new JButton();
        buttonPreOrderTraverse.setText("???????????? ??????????");
        panel8.add(buttonPreOrderTraverse, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonInOrderTraverse = new JButton();
        buttonInOrderTraverse.setText("???????????????????????? ??????????");
        panel8.add(buttonInOrderTraverse, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonPostOrderTraverse = new JButton();
        buttonPostOrderTraverse.setText("???????????????? ??????????");
        panel8.add(buttonPostOrderTraverse, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonByLevelTraverse = new JButton();
        buttonByLevelTraverse.setText("?????????? ?? ????????????");
        panel8.add(buttonByLevelTraverse, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel8.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textAreaSystemOut = new JTextArea();
        scrollPane1.setViewportView(textAreaSystemOut);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
