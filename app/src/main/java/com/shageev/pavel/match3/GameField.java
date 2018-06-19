package com.shageev.pavel.match3;

import java.util.ArrayList;

public class GameField {
    public ArrayList<Tile> Tiles;
    private int[][] tileIDs;
    private int[][] tileTypes;
    private int cols;

    public GameField(int columns){
        cols = columns;
        tileIDs = new int[columns][columns];
        tileTypes = new int[columns][columns];
    }

    public void init(int[][] tiles){
        Tiles = new ArrayList<>();
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < cols; j++){
                Tile t = new Tile();
                t.Column = j;
                t.Row = i;
                t.Type = tiles[i][j];
                t.dX = 0;
                t.dY = 0;
                Tiles.add(t);
                tileIDs[i][j] = Tiles.indexOf(t);
                tileTypes[i][j] = t.Type;
            }
        }
    }

    public int getTileId(int row, int col){
        if(fits(row, col)){
            return tileIDs[row][col];
        }
        return -1;
    }

    public Tile getTile(int row, int col){
        int tileId = getTileId(row, col);
        if(tileId >= 0){
            return Tiles.get(tileId);
        }
        return null;
    }

    public int getTileType(int row, int col){
        if(fits(row, col)){
            return tileTypes[row][col];
        }
        return -1;
    }

    public Tile getNeighbour(int x, int y, int dx, int dy, int threshold){
        //определяет, есть ли соседняя клетка в направлении dx, dy
        //возвращает ID соседа или -1, если нет соседа
        if(Math.abs(dx) <= threshold)
            dx = 0;
        if(Math.abs(dy) <= threshold)
            dy = 0;
        if(Math.abs(dx) > Math.abs(dy)){
            dx = dx / Math.abs(dx);
            dy = 0;
        } else {
            if(dy != 0){
                dy = dy / Math.abs(dy);
            }
            dx = 0;
        }
        int newX = x + dx;
        int newY = y + dy;

        if (fits(newY, newX) && (dx != 0 || dy != 0)){
            return Tiles.get(tileIDs[newY][newX]);
        }
        return null;
    }

    private boolean fits(int row, int col){
        return row >= 0 && row < cols && col >= 0 && col < cols;
    }
}

