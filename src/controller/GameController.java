package controller;


import model.ChessColor;
import view.ChessBoard;
import view.StatusPanel;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private ChessBoard chessboard;
    private StatusPanel statusPanel;
    private String PlayerW;
    private String PlayerB;
    private boolean flag = true;

    public GameController(ChessBoard chessBoard,StatusPanel statusPanel,String PlayerW, String PlayerB) {
        this.statusPanel = statusPanel;
        this.chessboard = chessBoard;
        this.PlayerW = PlayerW;
        this.PlayerB = PlayerB;
    }

    public ChessBoard getChessboard() {
        return chessboard;
    }

    public void setChessboard(ChessBoard chessboard) {
        this.chessboard = chessboard;
    }

    public void swapPlayer(){
        if(flag){
            statusPanel.setPresentPlayerText(PlayerB);
            flag = false;
        }
        else{
            statusPanel.setPresentPlayerText(PlayerW);
            flag = true;
        }
        statusPanel.repaint();
    }

    public ChessColor getCurrentcolor(){
        return chessboard.getCurrentColor();
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public void setStatusPanel(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;
    }


}
