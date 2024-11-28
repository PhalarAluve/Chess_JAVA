package model;

import view.ChessboardPoint;
import controller.ClickController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {
    private List<ChessboardPoint> chessboardPoints = new ArrayList<>();

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size);
        this.name='_';
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    public List<ChessboardPoint> canMovePoints(ChessboardPoint source, ChessComponent[][] chessComponents) {
        return chessboardPoints;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }

    public void refresh(ChessComponent[][] chessComponents){
        ChessboardPoint source = getChessboardPoint();
        int row = source.getX();
        int col = source.getY();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                chessComponents[x][y].setCanBeMoved(false);
                chessComponents[x][y].repaint();
            }
        }
    }

}
