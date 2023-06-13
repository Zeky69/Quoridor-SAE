package graph;


import model.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class NoeudUnitTest {

    private Noeud noeud1;
    private Noeud noeud2;
    private Noeud noeud3;

    @BeforeEach
    public void setUp() {
        int[] position1 = {0, 0};
        int[] position2 = {1, 1};
        int[] position3 = {2, 2};
        noeud1 = new Noeud(position1);
        noeud2 = new Noeud(position2);
        noeud3 = new Noeud(position3);
    }

    @Test
    public void testAddArete() {
        assertTrue(noeud1.getArete().isEmpty());
        noeud1.addArete(noeud2);
        assertEquals(1, noeud1.getArete().size());
        assertTrue(noeud1.getArete().contains(noeud2));
    }

    @Test
    public void testHasArete() {
        assertFalse(noeud1.hasArete(noeud2));
        assertFalse(noeud2.hasArete(noeud1));
        noeud1.addArete(noeud2);
        assertTrue(noeud1.hasArete(noeud2));
        assertFalse(noeud2.hasArete(noeud1));
    }

    @Test
    public void testRemoveArete() {
        noeud1.addArete(noeud2);
        assertTrue(noeud1.hasArete(noeud2));
        assertFalse(noeud2.hasArete(noeud1));
        noeud1.removeArete(noeud2);
        assertTrue(noeud1.hasArete(noeud2));
        assertFalse(noeud2.hasArete(noeud1));
    }

    @Test
    public void testGetPosition() {
        int[] position = {0, 0};
        assertArrayEquals(position, noeud1.getPosition());
    }

    @Test
    public void testEquals() {
        int[] position1 = {0, 0};
        int[] position2 = {0, 0};
        Noeud noeud = new Noeud(position2);
        assertEquals(noeud1, noeud);
        assertNotEquals(noeud2, noeud1);
    }

    @Test
    public void testHashCode() {
        int[] position1 = {0, 0};
        int[] position2 = {0, 0};
        Noeud noeud = new Noeud(position2);
        assertEquals(noeud1.hashCode(), noeud.hashCode());
        assertNotEquals(noeud2.hashCode(), noeud1.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Noeud : [0, 0] Arete : ";
        assertEquals(expected, noeud1.toString());
        noeud1.addArete(noeud2);
        expected = "Noeud : [0, 0] Arete : [1, 1] ";
        assertEquals(expected, noeud1.toString());
    }
}
