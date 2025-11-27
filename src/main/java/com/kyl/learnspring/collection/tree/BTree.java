package com.kyl.learnspring.collection.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 数据量太大不能放入内存，只能磁盘IO，降低深度减少访问节点次数，多叉平衡搜索树，访问节点在磁盘上进行，节点内查找在内存上进行
 * B树的应用 数据库索引
 * 所有叶子节点都在同一层
 */
public class BTree<K extends Comparable<K>> {
    private Node root;
    private final int degree;

    public BTree(int degree) {
        if (degree < 3) {
            throw new IllegalArgumentException("BTree degree must be >= 3");
        }
        this.degree = degree;
        this.root = new Node(true);
    }

    /** 插入操作 */
    public void put(K key) {
        Node node = root;
        insertAndSplit(node, key);

        // 根节点可能被分裂
        if (root.isOverflow()) {
            Node newRoot = new Node(false);
            splitChild(newRoot, root);
            root = newRoot;
        }
    }

    /** 递归插入：先插入，再判断是否需要分裂 */
    private void insertAndSplit(Node node, K key) {
        if (node.isLeaf) {
            node.keys.add(key);
            node.keys.sort(Comparator.naturalOrder());
        } else {
            // 找到要插入的子节点
            int i = 0;
            while (i < node.keys.size() && key.compareTo(node.keys.get(i)) > 0) {
                i++;
            }
            Node child = node.children.get(i);
            insertAndSplit(child, key);

            // 如果子节点插入后溢出（超过4个key）
            if (child.isOverflow()) {
                splitChild(node, child);
            }
        }
    }

    /** 分裂子节点：在插入之后执行 */
    private void splitChild(Node parent, Node child) {
        int midIndex = degree / 2; // 5阶 -> midIndex=2
        K midKey = child.keys.get(midIndex);

        Node newChild = new Node(child.isLeaf);

        // 右半部分key转移
        for (int i = midIndex + 1; i < child.keys.size(); i++) {
            newChild.keys.add(child.keys.get(i));
        }
        child.keys.subList(midIndex, child.keys.size()).clear();

        // 如果不是叶子，也要迁移子节点
        if (!child.isLeaf) {
            for (int i = midIndex + 1; i < child.children.size(); i++) {
                newChild.children.add(child.children.get(i));
            }
            child.children.subList(midIndex + 1, child.children.size()).clear();
        }

        // midKey上升到父节点
        int insertPos = findInsertPos(parent.keys, midKey);
        parent.keys.add(insertPos, midKey);
        parent.children.add(insertPos + 1, newChild);
    }

    /** 辅助函数：查找插入位置 */
    private int findInsertPos(List<K> keys, K key) {
        int i = 0;
        while (i < keys.size() && key.compareTo(keys.get(i)) > 0) i++;
        return i;
    }

    /** 打印整棵树 */
    public void print() {
        printNode(root, 0);
    }

    private void printNode(Node node, int level) {
        System.out.println("  ".repeat(level) + node.keys);
        for (Node child : node.children) {
            printNode(child, level + 1);
        }
    }

    /**
     * 节点定义
     */
    public class Node {
        private List<K> keys;
        private List<Node> children;
        private boolean isLeaf;

        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = new ArrayList<>();
            this.children = new ArrayList<>();
        }

        /** 超过maxKeys才算溢出 */
        public boolean isOverflow() {
            return keys.size() > degree - 1;
        }
    }
}
