package btree;

import java.util.LinkedList;
import java.util.List;

public class BTree<Key extends Comparable<Key>, Value> {
    private static final int M = 4;

    private Node root;
    private int height;
    private int elemsCount;
    public List<BTreeActions> actionsLog = new LinkedList<>();

    private static final class Node {
        private int childrenCount;
        private Entry[] children = new Entry[M];

        private Node(int k) {
            childrenCount = k;
        }
    }

    private static class Entry<Key extends Comparable<Key>, Value> {
        private Key key;
        private Value val;
        private Node next;

        public Entry(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public BTree() {
        root = new Node(0);
    }

    public int size() {
        return elemsCount;
    }

    public int height() {
        return height;
    }


    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() cannot be null");
        return search(root, key, height);
    }

    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        if (ht == 0) {
            actionsLog.add(BTreeActions.externalNodeTraverse);
            for (int j = 0; j < x.childrenCount; j++) {
                if (eq(key, children[j].key)) {
                    actionsLog.add(BTreeActions.nodeFound);
                    return (Value) children[j].val;
                }
            }
        }

        else {
            actionsLog.add(BTreeActions.internalNodeTraverse);
            for (int j = 0; j < x.childrenCount; j++) {
                if (j + 1 == x.childrenCount || less(key, children[j + 1].key))
                    return search(children[j].next, key, ht - 1);
            }
        }
        actionsLog.add(BTreeActions.nodeNotFound);
        return null;
    }


    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("Argument key to put() cannot be null");
        Node u = insert(root, key, val, height);
        elemsCount++;
        if (u == null) return;

        actionsLog.add(BTreeActions.rootSplit);
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    private Node insert(Node node, Key key, Value val, int ht) {
        int j;
        Entry entry = new Entry(key, val, null);

        if (ht == 0) {
            actionsLog.add(BTreeActions.externalNodeTraverse);
            for (j = 0; j < node.childrenCount; j++) {
                if (less(key, node.children[j].key)) break;
            }
        } else {
            actionsLog.add(BTreeActions.internalNodeTraverse);
            for (j = 0; j < node.childrenCount; j++) {
                if ((j + 1 == node.childrenCount) || less(key, node.children[j + 1].key)) {
                    Node u = insert(node.children[j++].next, key, val, ht - 1);
                    if (u == null) return null;
                    entry.key = u.children[0].key;
                    entry.val = null;
                    entry.next = u;
                    break;
                }
            }
        }

        for (int i = node.childrenCount; i > j; i--)
            node.children[i] = node.children[i - 1];
        node.children[j] = entry;
        node.childrenCount++;
        actionsLog.add(BTreeActions.nodeInserted);
        if (node.childrenCount < M) return null;
        else return split(node);
    }

    private Node split(Node h) {
        actionsLog.add(BTreeActions.nodeSplit);
        Node t = new Node(M / 2);
        h.childrenCount = M / 2;
        for (int j = 0; j < M / 2; j++)
            t.children[j] = h.children[M / 2 + j];
        return t;
    }

    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
}