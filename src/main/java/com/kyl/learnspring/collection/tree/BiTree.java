package com.kyl.learnspring.collection.tree;

public class BiTree<K extends Comparable<K>, V>{


    /**
     * 最终落脚到 删除叶子节点 删除只有左子树/右子树的节点
     * 左右子树都有的情况会转化为 找到前驱/后继值替换，删除前驱/后继
     */
    public void delete(K key){

    }
    public class Node {
        private K key;
        private V value;
        private Node parent, left, right;

        // 插入节点默认是红色
        Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
}
