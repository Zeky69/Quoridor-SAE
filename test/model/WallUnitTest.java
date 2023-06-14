package model;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;



import model.Wall.Direction;

public class WallUnitTest {


    private Wall wall;

    @BeforeEach
    public void setUp() {
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
    public void testIsEmpty() {
        assertFalse(wall.isEmpty());
        Wall emptyWall = new Wall(new boolean[4], null);
        assertTrue(emptyWall.isEmpty());
    }


    @Test
    public void testSetWall() {
        wall.setWall(Direction.UP, false, 0);
        assertFalse(wall.getWall(Direction.UP));
        assertArrayEquals(new int[]{1, 0, 0, 0}, wall.getColorWall());

        wall.setWall(Direction.DOWN, true, 1);
        assertTrue(wall.getWall(Direction.DOWN));
        assertArrayEquals(new int[]{1, 2, 0, 0}, wall.getColorWall());

        wall.setWall(Direction.LEFT, false, 2);
        assertFalse(wall.getWall(Direction.LEFT));
        assertArrayEquals(new int[]{1, 2, 3, 0}, wall.getColorWall());


        wall.setWall(Direction.RIGHT, true, 3);
        assertTrue(wall.getWall(Direction.RIGHT));
        assertArrayEquals(new int[]{1, 2, 3, 4}, wall.getColorWall());

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

    @Test
    public void testToString() {
        String expected = "[true, false, true, false]";
        String actual = wall.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testGet2ndWallUp() {
        int x1 = 0;
        int y1 = 0;
        int[] expected = {1, 0};
        int[] result = Wall.get2ndWall(Direction.UP, x1, y1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGet2ndWallDown() {
        int x1 = 0;
        int y1 = 0;
        int[] expected = {1, 0};
        int[] result = Wall.get2ndWall(Direction.DOWN, x1, y1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGet2ndWallLeft() {
        int x1 = 0;
        int y1 = 0;
        int[] expected = {0, 1};
        int[] result = Wall.get2ndWall(Direction.LEFT, x1, y1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGet2ndWallRight() {
        int x1 = 0;
        int y1 = 0;
        int[] expected = {0, 1};
        int[] result = Wall.get2ndWall(Direction.RIGHT, x1, y1);
        assertArrayEquals(expected, result);
    }

}


