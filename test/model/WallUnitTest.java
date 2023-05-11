package model;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;



import model.Wall.Direction;

public class WallUnitTest {


    private Wall wall;

    @BeforeEach
    public void setUp() throws Exception {
        boolean[] wallArray = {true, false, true, false};
        wall = new Wall(wallArray, null);
    }

    @Test
    public void testGetWall() {
        boolean[] expected = {true, false, true, false};
        boolean[] actual = wall.getWall();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetWallDirection() {
        assertTrue(wall.getWall(Direction.UP));
        assertFalse(wall.getWall(Direction.DOWN));
        assertTrue(wall.getWall(Direction.LEFT));
        assertFalse(wall.getWall(Direction.RIGHT));
    }

    @Test
    public void testSetWall() {
        wall.setWall(Direction.UP, false);
        assertFalse(wall.getWall(Direction.UP));
        wall.setWall(Direction.DOWN, true);
        assertTrue(wall.getWall(Direction.DOWN));
        wall.setWall(Direction.LEFT, false);
        assertFalse(wall.getWall(Direction.LEFT));
        wall.setWall(Direction.RIGHT, true);
        assertTrue(wall.getWall(Direction.RIGHT));
    }

    @Test
    public void testIsEmpty() {
        assertFalse(wall.isEmpty());
        wall.setWalls(new boolean[4]);
        assertTrue(wall.isEmpty());
    }

    @Test
    public void testCopy() {
        Wall copy = wall.copy();
        assertArrayEquals(wall.getWall(), copy.getWall());
        assertEquals(wall.getGameStageModel(), copy.getGameStageModel());
        assertNotSame(wall, copy);
    }

    @Test
    public void testDirectionToInt() {
        assertArrayEquals(new int[]{0, -1}, Wall.directionToInt(Direction.UP));
        assertArrayEquals(new int[]{0, 1}, Wall.directionToInt(Direction.DOWN));
        assertArrayEquals(new int[]{-1, 0}, Wall.directionToInt(Direction.LEFT));
        assertArrayEquals(new int[]{1, 0}, Wall.directionToInt(Direction.RIGHT));
    }

    @Test
    public void testIntToDirection() {
        assertEquals(Direction.UP, Wall.intToDirection(0));
        assertEquals(Direction.DOWN, Wall.intToDirection(1));
        assertEquals(Direction.LEFT, Wall.intToDirection(2));
        assertEquals(Direction.RIGHT, Wall.intToDirection(3));
        assertNull(Wall.intToDirection(4));
    }

    @Test
    public void testIsBorder() {
        assertTrue(Wall.isBorder(new int[]{0, 0}, Direction.UP));
        assertTrue(Wall.isBorder(new int[]{1, 0}, Direction.UP));
        assertFalse(Wall.isBorder(new int[]{0, 1}, Direction.UP));
        assertTrue(Wall.isBorder(new int[]{8, 8}, Direction.DOWN));
        assertTrue(Wall.isBorder(new int[]{0, 8}, Direction.LEFT));
        assertFalse(Wall.isBorder(new int[]{1, 8}, Direction.LEFT));
        assertTrue(Wall.isBorder(new int[]{0, 7}, Direction.LEFT));
        assertTrue(Wall.isBorder(new int[]{8, 0}, Direction.RIGHT));
        assertTrue(Wall.isBorder(new int[]{8, 1}, Direction.RIGHT));
        assertFalse(Wall.isBorder(new int[]{7, 0}, Direction.RIGHT));
    }

}


