package com.kyl.learnspring.collection.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 红色节点不能连续，任一节点到叶的所有路径黑色节点数相同
 * 最长路径不会超过最短路径两倍
 */
// 泛型定义在树上而非节点
public class RedBlackTree<K extends Comparable<K>, V> {
    private Node root;
    private Integer depth;

    /**
     * 插入，默认插入节点是红色不会改变黑色节点数量
     */
    public void put(K key, V value){
        if(root == null){
            root = new Node(key, value, null);
            root.isRed = false;
            return;
        }

        Node cur = root, parent = null;
        int cmp = 0;
        while(cur != null){
            if((cmp = key.compareTo(cur.key)) < 0){
                parent = cur;
                cur = cur.left;
            } else if(cmp > 0){
                parent = cur;
                cur = cur.right;
            } else{
                cur.value = value;
                return;
            }
        }

        Node newNode = new Node(key, value, parent);
        if(cmp < 0){
            parent.left = newNode;
        } else{
            parent.right = newNode;
        }

        fixAfterPut(newNode);
    }

    private void fixAfterPut(Node x){
        // 只有父节点是红色才需要处理
        while(x != null && x.parent != null && x.parent.isRed){
            // 第一步找uncle
            Node uncle, father, grandfather;
            if(x.parent == x.parent.parent.left){
                uncle = x.parent.parent.right;
            } else{
                uncle = x.parent.parent.left;
            }
            father = x.parent;
            grandfather = father.parent;

            // 叔叔节点是红色，叔父爷变色，爷为插入节点
            if(uncle != null && uncle.isRed){
                father.isRed = !father.isRed;
                grandfather.isRed = !grandfather.isRed;
                uncle.isRed = !uncle.isRed;
                x = x.parent.parent;
            } else{
                // 判断类型 LL/LR/RR/RL 先旋转后变色，对旋转点和中心点进行变色
                if(x == x.parent.left && x.parent == x.parent.parent.left){
                    rotateRight(grandfather);
                    father.isRed = !father.isRed;
                    grandfather.isRed = !grandfather.isRed;
                } else if(x == x.parent.right && x.parent == x.parent.parent.right){
                    rotateLeft(grandfather);
                    father.isRed = !father.isRed;
                    grandfather.isRed = !grandfather.isRed;
                } else if(x == x.parent.right && x.parent == x.parent.parent.left){
                    rotateLeft(father);
                    rotateRight(grandfather);
                    x.isRed = !x.isRed;
                    grandfather.isRed = !grandfather.isRed;
                } else{
                    rotateRight(father);
                    rotateLeft(grandfather);
                    x.isRed = !x.isRed;
                    grandfather.isRed = !grandfather.isRed;
                }
            }
        }
        root.isRed = false;
    }

    private void rotateRight(Node y){
        Node x = y.left;
        y.left = x.right;
        if(x.right != null){
            x.right.parent = y;
        }

        x.parent = y.parent;
        if(y.parent != null){
            if(y == y.parent.left){
                y.parent.left = x;
            } else{
                y.parent.right = x;
            }
        } else{
            x.parent = null;
            root = x;
        }

        x.right = y;
        y.parent = x;
    }

    private void rotateLeft(Node y){
        Node x = y.right;
        y.right = x.left;
        if(x.left != null){
            x.left.parent = y;
        }

        x.parent = y.parent;
        if(y.parent != null){
            if(y == y.parent.left){
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
        } else{
            x.parent = null;
            root = x;
        }

        x.left = y;
        y.parent = x;
    }

    /**
     * 打印红黑树
     */
    public void printTree() {
        StringBuilder treeStr = new StringBuilder();
        if (root == null)
            return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                treeStr.append(node.isRed ? "🔴" : "⚫️").append(node.key);
                if(node.left != null){
                    queue.add(node.left);
                }
                if(node.right != null){
                    queue.add(node.right);
                }
            }
            treeStr.append("\n");
        }
        System.out.println(treeStr);
    }

    public Integer getDepth(){
        if(depth != null){
            return depth;
        }
        if(root == null){
            return 0;
        }
        return getDepth(root, 0);
    }

    private Integer getDepth(Node node, Integer curDepth){
        if(node == null){
            return curDepth;
        }
        return Math.max(getDepth(node.left, curDepth + 1), getDepth(node.right, curDepth + 1));
    }

    /**
     * 1. 只有左子树/右子树 变黑
     * 2. 没有孩子节点
     *      红色直接删除
     *      黑色节点 兄弟节点至少有一个红孩子  变色+旋转
     */
    public void delete(K key){

    }


    /**
     * 内部类实例 不能独立于 外部类实例存在
     * 外部类实例存在后
     */
    public class Node{
        private boolean isRed;
        private K key;
        private V value;
        private Node parent, left, right;

        // 插入节点默认是红色
        Node(K key, V value, Node parent) {
            this.isRed = true;
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
}
