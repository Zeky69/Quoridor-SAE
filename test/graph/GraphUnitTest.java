package graph;

import model.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphUnitTest {

    private Graph graph;

    @BeforeEach
    public void setUp() {
        Wall[][] walls = new Wall[9][9];
        for (int i = 0; i < walls.length; i++) {
            for (int j = 0; j < walls[i].length; j++) {
                walls[i][j] = new Wall(new boolean[]{false,false,false,false},null);
            }
        }
        graph = new Graph(walls);
    }

    @Test
    public void testGetNoeud() {
        int[] position = new int[]{2, 3};
        Noeud noeud = graph.getNoeud(position);

        assertNotNull(noeud);
        assertArrayEquals(position, noeud.getPosition());
    }

    @Test
    public void testAddNoeud() {
        int[] position = new int[]{4, 5};
        graph.addNoeud(position);

        Noeud noeud = graph.getNoeud(position);
        assertNotNull(noeud);
        assertArrayEquals(position, noeud.getPosition());
    }

    @Test
    public void testAddArete() {
        int[] positionNoeud1 = new int[]{2, 3};
        int[] positionNoeud2 = new int[]{2, 4};

        graph.addArete(positionNoeud1, positionNoeud2);

        Noeud noeud1 = graph.getNoeud(positionNoeud1);
        Noeud noeud2 = graph.getNoeud(positionNoeud2);

        assertNotNull(noeud1);
        assertNotNull(noeud2);
        assertTrue(noeud1.hasArete(noeud2));
        assertTrue(noeud2.hasArete(noeud1));
    }

    @Test
    public void testRemoveArete() {
        int[] positionNoeud1 = new int[]{2, 3};
        int[] positionNoeud2 = new int[]{2, 4};

        graph.addArete(positionNoeud1, positionNoeud2);
        graph.removeArete(positionNoeud1, positionNoeud2);

        Noeud noeud1 = graph.getNoeud(positionNoeud1);
        Noeud noeud2 = graph.getNoeud(positionNoeud2);

        assertNotNull(noeud1);
        assertNotNull(noeud2);
        assertTrue(noeud1.hasArete(noeud2));
        assertFalse(noeud2.hasArete(noeud1));
    }

    @Test
    public void testShortestPath() {
        int[] positionInit = new int[]{0, 0};
        int y = 8;

        int shortestPath = graph.shortestPath(positionInit, y);

        assertEquals(8, shortestPath);
    }

    @Test
    public void testIsPathPossibleY() {
        int[] positionInit = new int[]{0, 0};
        int y = 8;

        boolean isPathPossible = graph.isPathPossibleY(positionInit, y);

        assertTrue(isPathPossible);
    }
}
