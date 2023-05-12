package graph;


import graph.Graph;
import model.Wall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphUnitTest {

    @Test
    void testHasArete() {
        Graph graph = new Graph();
        graph.addNoeud(new int[]{0, 0});
        graph.addNoeud(new int[]{1, 0});
        graph.addArete(new int[]{0, 0}, new int[]{1, 0});
        assertTrue(graph.hasArete(new int[]{0, 0}, new int[]{1, 0}));
        assertFalse(graph.hasArete(new int[]{0, 0}, new int[]{0, 1}));
    }

    @Test
    void testRemoveArete() {
        Graph graph = new Graph();
        graph.addNoeud(new int[]{0, 0});
        graph.addNoeud(new int[]{1, 0});
        graph.addArete(new int[]{0, 0}, new int[]{1, 0});
        assertTrue(graph.hasArete(new int[]{0, 0}, new int[]{1, 0}));
        graph.removeArete(new int[]{0, 0}, new int[]{1, 0});
        assertTrue(graph.hasArete(new int[]{0, 0}, new int[]{1, 0}));
    }

    @Test
    void testRemoveAreteWithDirection() {
        Graph graph = new Graph();
        graph.addNoeud(new int[]{0, 0});
        graph.addNoeud(new int[]{1, 0});
        graph.addArete(new int[]{0, 0}, new int[]{1, 0});
        assertTrue(graph.hasArete(new int[]{0, 0}, new int[]{1, 0}));
        graph.removeArete(new int[]{0, 0}, new int[]{1, 0}, Wall.Direction.RIGHT);
        assertTrue(graph.hasArete(new int[]{0, 0}, new int[]{1, 0}));
    }

    @Test
    void testAddArete() {
        Graph graph = new Graph();
        graph.addNoeud(new int[]{0, 0});
        graph.addNoeud(new int[]{1, 0});
        assertFalse(graph.hasArete(new int[]{0, 0}, new int[]{1, 0}));
        graph.addArete(new int[]{0, 0}, new int[]{1, 0});
        assertTrue(graph.hasArete(new int[]{0, 0}, new int[]{1, 0}));
    }

    @Test
    void testShortestPath() {
        Graph graph = new Graph();
        graph.addNoeud(new int[]{0, 0});
        graph.addNoeud(new int[]{1, 0});
        graph.addNoeud(new int[]{2, 0});
        graph.addNoeud(new int[]{3, 0});
        graph.addNoeud(new int[]{3, 1});
        graph.addNoeud(new int[]{3, 2});
        graph.addNoeud(new int[]{3, 3});
        graph.addArete(new int[]{0, 0}, new int[]{1, 0});
        graph.addArete(new int[]{1, 0}, new int[]{2, 0});
        graph.addArete(new int[]{2, 0}, new int[]{3, 0});
        graph.addArete(new int[]{3, 0}, new int[]{3, 1});
        graph.addArete(new int[]{3, 1}, new int[]{3, 2});
        graph.addArete(new int[]{3, 2}, new int[]{3, 3});
        assertEquals(6, graph.shortestPath(new int[]{0, 0}, 3));
    }

}