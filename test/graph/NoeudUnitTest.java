package graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class NoeudUnitTest {

    @Test
    void addArete() {
        Noeud n1 = new Noeud(new int[]{1, 1});
        Noeud n2 = new Noeud(new int[]{2, 2});
        n1.addArete(n2);
        List<Noeud> aretes = n1.getArete();
        Assertions.assertEquals(1, aretes.size());
        Assertions.assertTrue(aretes.contains(n2));
    }

    @Test
    void removeArete() {
        Noeud n1 = new Noeud(new int[]{1, 1});
        Noeud n2 = new Noeud(new int[]{2, 2});
        n1.addArete(n2);
        n1.removeArete(n2);
        List<Noeud> aretes = n1.getArete();
        Assertions.assertEquals(0, aretes.size());
        Assertions.assertFalse(aretes.contains(n2));
    }

    @Test
    void hasArete() {
        Noeud n1 = new Noeud(new int[]{1, 1});
        Noeud n2 = new Noeud(new int[]{2, 2});
        Noeud n3 = new Noeud(new int[]{3, 3});
        n1.addArete(n2);
        Assertions.assertTrue(n1.hasArete(n2));
        Assertions.assertFalse(n1.hasArete(n3));
    }

    @Test
    void setPosition() {
        Noeud n1 = new Noeud(new int[]{1, 1});
        n1.setPosition(new int[]{2, 2});
        Assertions.assertArrayEquals(new int[]{2, 2}, n1.getPosition());
    }

    @Test
    void removeAretes() {
        Noeud n1 = new Noeud(new int[]{1, 1});
        Noeud n2 = new Noeud(new int[]{2, 2});
        Noeud n3 = new Noeud(new int[]{3, 3});
        n1.addArete(n2);
        n2.addArete(n3);
        n1.removeAretes(n2.getPosition());
        List<Noeud> aretes1 = n1.getArete();
        List<Noeud> aretes2 = n2.getArete();
        List<Noeud> aretes3 = n3.getArete();
        Assertions.assertEquals(0, aretes1.size());
        Assertions.assertEquals(1, aretes2.size());
        Assertions.assertEquals(0, aretes3.size());
        Assertions.assertFalse(aretes1.contains(n2));
        Assertions.assertFalse(aretes2.contains(n1));
        Assertions.assertFalse(aretes2.contains(n3));
        Assertions.assertFalse(aretes3.contains(n2));
    }

    @Test
    void removeArete_null() {
        Noeud n1 = new Noeud(new int[]{1, 1});
        Assertions.assertDoesNotThrow(() -> n1.removeArete(null));
    }

    @Test
    void removeArete_notConnected() {
        Noeud n1 = new Noeud(new int[]{1, 1});
        Noeud n2 = new Noeud(new int[]{2, 2});
        Assertions.assertDoesNotThrow(() -> n1.removeArete(n2));
    }
}

