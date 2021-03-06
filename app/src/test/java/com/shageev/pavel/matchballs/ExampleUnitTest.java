package com.shageev.pavel.matchballs;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void string_format() {assertEquals("x100", String.format("x%d",100));}

    private GameField initGameField(){
        GameField gf = new GameField(GameType.Medium);
        int[][] data = new int[][]{
                {  1,  2,  3,  4,  5,  6,  7 },
                {  8,  9, 10, 11, 12, 13, 14 },
                { 15, 16, 17, 18, 19, 20, 21 },
                { 22, 23, 24, 25, 26, 27, 28 },
                { 29, 30, 31, 32, 33, 34, 35 },
                { 36, 37, 38, 39, 40, 41, 42 },
                { 43, 44, 45, 46, 47, 48, 49 }
        };
        gf.init(data);
        return gf;
    }


    @Test
    public void GameField_getNeighbour(){
        GameField gf = initGameField();

        Tile t1 = gf.getNeighbour(1,1,0,-15, 10);
        assertEquals(2, t1.Type);
        Tile t2 = gf.getNeighbour(0,0,10,10,20);
        assertEquals(null, t2);
        Tile t3 = gf.getNeighbour(0,4, -10, 0, 0);
        assertEquals(null, t3);
        Tile t4 = gf.getNeighbour(0,4,1,2, 0);
        assertEquals(36, t4.Type);
    }

    @Test
    public void GameField_getTileId(){
        GameField gf = initGameField();

        Tile t = gf.Tiles.get(gf.getTileId(3, 1));
        assertEquals(23, t.Type);
    }

    @Test
    public void GameField_getTileType(){
        GameField gf = initGameField();
        assertEquals(23, gf.getTileType(3,1));
        assertEquals(-1, gf.getTileType(9,1));
    }

    @Test
    public void GameField_swap(){
        GameField gf = initGameField();
        Tile t1 = gf.Tiles.get(0);
        Tile t2 = gf.Tiles.get(1);
        int type1 = t1.Type;
        int type2 = t2.Type;
        gf.swap(t1, t2);
        assertEquals(2, gf.Tiles.get(1).Type);
        assertEquals(type2, gf.getTileType(0, 0));
    }

    @Test
    public void GameType_Test(){
        GameType gType = GameType.Medium;
        assertEquals("Medium", gType.toString());
    }
}