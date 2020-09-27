import btree.BTree;
import btree.BTreeActions;
import function.Taylor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BTreeTest {
    public static BTree<Integer, String> tree;

    @BeforeEach
    public void prepare() {
        tree = new BTree<>();
    }

    @Test
    public void rootSplits() {
        tree.put(1, "1");
        tree.put(2, "2");
        tree.put(3, "3");
        tree.put(4, "4");
        assertEquals(List.of(
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,
                BTreeActions.nodeSplit,
                BTreeActions.rootSplit
        ), tree.actionsLog);
    }
    @Test
    public void nodeInsertSuccess() {
        tree.put(1, "1");
        tree.put(3, "3");
        tree.put(2, "2");
        tree.put(5, "5");
        tree.put(7, "7");
        tree.put(6, "6");
        assertEquals(List.of(
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,

                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,
                BTreeActions.nodeSplit,
                BTreeActions.rootSplit,

                BTreeActions.internalNodeTraverse,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,

                BTreeActions.internalNodeTraverse,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeInserted,
                BTreeActions.nodeSplit,
                BTreeActions.nodeInserted
        ), tree.actionsLog);
    }

    @Test
    public void searchSuccess() {
        tree.put(1, "1");
        tree.put(2, "2");
        tree.put(3, "3");
        tree.put(4, "4");
        tree.actionsLog.clear();
        String result = tree.get(4);
        assertEquals(List.of(
                BTreeActions.internalNodeTraverse,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeFound
        ), tree.actionsLog);
        assertEquals(result, "4");
    }

    @Test
    public void searchFails() {
        tree.put(1, "1");
        tree.put(2, "2");
        tree.put(3, "3");
        tree.put(4, "4");
        tree.actionsLog.clear();
        String result = tree.get(9);
        assertEquals(List.of(
                BTreeActions.internalNodeTraverse,
                BTreeActions.externalNodeTraverse,
                BTreeActions.nodeNotFound
        ), tree.actionsLog);
        assertNull(result);
    }

    @Test
    public void validation() {
        assertThrows(IllegalArgumentException.class, () -> tree.put(null, null));
        assertThrows(IllegalArgumentException.class, () -> tree.get(null));
    }
}
