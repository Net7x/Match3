package com.shageev.pavel.match3;

public class Constants {
    public static int FALL_SPEED = 1500;
    //public static int BALL_TYPES = 6;
    public static int COLUMNS = 7;
    public static int MOVE_THRESHOLD = 20;
    public static int SWAP_STEP = 700;
    public static int JUMP_SPEED = 15;
    public static int EXPLODE_SPEED = 400;
    public static int EXPLODE_END = 100;

    public static int BallTypes(GameType type){
        switch (type){
            case Easy:
                return 5;
            case Medium:
                return 6;
            case Hard:
                return 7;
            default:
                return 6;
        }
    }
}
