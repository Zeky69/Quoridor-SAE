package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PawnUnitTest {

    private Pawn pawn;

    @BeforeEach
    public void setUp() {
        pawn = new Pawn(2, 3, 1, 8, null);
    }

    @Test
    public void testSetPawnX() {
        pawn.setPawnX(4);
        assertEquals(4, pawn.getPawnX());
    }

    @Test
    public void testSetPawnY() {
        pawn.setPawnY(6);
        assertEquals(6, pawn.getPawnY());
    }

    @Test
    public void testSetWallCount() {
        pawn.setWallCount(8);
        assertEquals(8, pawn.getWallCount());
    }

    @Test
    public void testGetWallCount() {
        assertEquals(10, pawn.getWallCount());
    }

    @Test
    public void testDecrementWallCount() {
        pawn.decrementWallCount();
        assertEquals(9, pawn.getWallCount());
    }

    @Test
    public void testIncrementWallCount() {
        pawn.incrementWallCount();
        assertEquals(11, pawn.getWallCount());
    }

    @Test
    public void testGetPawnX() {
        assertEquals(2, pawn.getPawnX());
    }

    @Test
    public void testGetWinY() {
        assertEquals(8, pawn.getWinY());
    }

    @Test
    public void testSetWinY() {
        pawn.setWinY(7);
        assertEquals(7, pawn.getWinY());
    }

    @Test
    public void testGetPawnY() {
        assertEquals(3, pawn.getPawnY());
    }

    @Test
    public void testGetPawnXY() {
        int[] expected = {2, 3};
        int[] actual = pawn.getPawnXY();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testSetPawnXY() {
        int[] coord = {4, 6};
        pawn.setPawnXY(coord);
        int[] expected = {4, 6};
        int[] actual = pawn.getPawnXY();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetPlayer() {
        assertEquals(1, pawn.getPlayer());
    }

    @Test
    public void testCopy() {
        Pawn copy = pawn.copy();
        assertNotSame(pawn, copy);
        assertEquals(pawn.getPawnX(), copy.getPawnX());
        assertEquals(pawn.getPawnY(), copy.getPawnY());
        assertEquals(pawn.getWallCount(), copy.getWallCount());
        assertEquals(pawn.getPlayer(), copy.getPlayer());
        assertEquals(pawn.getWinY(), copy.getWinY());
    }

}