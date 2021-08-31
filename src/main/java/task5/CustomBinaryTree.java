package task5;

import ru.vsu.cs.course1.tree.SimpleBinaryTree;

import java.util.function.Function;

public class CustomBinaryTree<T> extends SimpleBinaryTree<T> {

    public CustomBinaryTree(Function<String, T> fromStrFunc, Function<T, String> toStrFunc) {
        super(fromStrFunc, toStrFunc);
    }

    public CustomBinaryTree(Function<String, T> fromStrFunc) {
        super(fromStrFunc);
    }

    public CustomBinaryTree() {
    }

    /**
     * .Реализовать функцию, которая изменить двоичное дерево следующим образом: Присоединит к самому правому листу
     * в дереве в качестве правого потомка левое поддерево корня дерева (левый потомок у корня дерева при этом надо убрать)
     */
    public void reorganize() {
        if (root == null || root.left == null) {
            return;
        }
        SimpleTreeNode left = root.left;
        root.left = null;

        SimpleTreeNode pointer = root;
        while (pointer.right != null) {
            pointer = pointer.right;
        }
        pointer.right = left;
    }
}
