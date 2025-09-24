package com.wcq.collection;

/**
 * 红色节点不能连续，任一节点到叶的所有路径黑色节点数相同
 * 最长路径不会超过最短路径两倍
 */
// 泛型定义在树上而非节点
public class RedBlackTree<K extends Comparable<K>, V> {
    private Node root;

    public void put(K key, V value){
        if(root == null){
            root = new Node(key, value, null);
            root.isRed = false;
            return;
        }

        Node cur = root, parent = null;
        int cmp = 0;
        while(cur != null){
            parent = cur;
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
        // 只有父节点是红色才需要处理，旋转不是一步完成，所以加while
        while(x != null && x.parent != null && x.parent.isRed){
            // 第一步找uncle
            Node uncle;
            if(x.parent == x.parent.parent){
                uncle = x.parent.parent.right;
            } else{
                uncle = x.parent.parent.left;
            }

            // 叔叔节点是红色，叔父爷变色，爷为插入节点
            if(uncle != null && uncle.isRed){
                x.parent.isRed = !x.parent.isRed;
                x.parent.parent.isRed = !x.parent.parent.isRed;
                uncle.isRed = !uncle.isRed;
                x = x.parent;
            } else{
                // 判断LL/LR/RR/RL
                if(x == x.parent.left && x.parent == x.parent.parent.left){
                    x.parent.isRed = !x.parent.isRed;
                    x.parent.parent.isRed = !x.parent.parent.isRed;
                    rotateRight(x.parent.parent);
                } else if(x == x.parent.right && x.parent == x.parent.parent.right){

                } else if(x == x.parent.left && x.parent == x.parent.parent.right){

                } else{
                    // rotateLeft(x.parent);
                }
            }
        }
    }

    private void rotateRight(Node y){
        Node x = y.left;

        if(x.right != null){
            y.left = x.right;
            x.right.parent = y;
        } else{
            y.left = null;
        }

        if(y.parent != null){
            x.parent = y.parent;
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

        if(x.left != null){
            y.left = x.left;
            x.left.parent = y;
        } else{
            y.left = null;
        }

        if(y.parent != null){
            x.parent = y.parent;
            if(y == y.parent.left){
                y.parent.left = x;
            } else{
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
