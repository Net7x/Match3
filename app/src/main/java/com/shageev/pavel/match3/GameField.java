package com.shageev.pavel.match3;

import java.util.ArrayList;

public class GameField {
    int[][] tileIDs;
    int[][] tileTypes;
    private int cols;

    public GameField(int columns){
        cols = columns;
        tileIDs = new int[columns][columns];
        tileTypes = new int[columns][columns];
    }

    public void init(ArrayList<Tile> tiles){
        for(int i = 0; i < tiles.size(); i++){
            Tile t = tiles.get(i);
            if(fits(t.Row, t.Column)){
                tileIDs[t.Row][t.Column] = i;
                tileTypes[t.Row][t.Column] = t.Type;
            }
        }
    }

    public int getTileId(int row, int col){
        if(fits(row, col)){
            return tileIDs[row][col];
        }
        return -1;
    }

    public int getTileType(int row, int col){
        if(fits(row, col)){
            return tileTypes[row][col];
        }
        return -1;
    }

    private boolean fits(int row, int col){
        if(row >= 0 && row < cols && col >= 0 && col < cols){
            return true;
        }
        return false;
    }
}

