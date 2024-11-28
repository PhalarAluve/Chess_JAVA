package controller;


import model.*;
import view.Bgm;
import view.ChessBoard;
import view.Click;
import view.StatusPanel;
import view.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClickController {
    private final ChessBoard chessboard;
    private ChessComponent first;
    private GameController gameController;
    private StatusPanel statusPanel;

    public ClickController(ChessBoard chessboard, StatusPanel statusPanel,GameController gameController) {
        this.chessboard = chessboard;
        this.statusPanel = statusPanel;
        this.gameController = gameController;
    }

    public void onClick(ChessComponent chessComponent) {
        String filepath="bgm/click.WAV";
        Click bgmObject = new Click();
        bgmObject. playMusic(filepath);
        if (first == null) {  //说明这是第一次点击
            if (handleFirst(chessComponent)) {  //如果false，那就是无效点击
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if(chessboard.getChessComponents()[i][j].getClass().getName().equals("model.KingChessComponent")){
                            KingChessComponent king=(KingChessComponent)chessboard.getChessComponents()[i][j];
                            king.refresh(chessboard.getChessComponents());
                        }
                    }
                }
                chessComponent.setSelected(true);
                chessComponent.canBeMoved(chessboard.getChessComponents());
                chessboard.repaint();
                first = chessComponent;
//                System.out.println("My name is"+first.getClass().getName());
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                first.refresh(chessboard.getChessComponents());
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                first.refresh(chessboard.getChessComponents());
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();

                //实现兵底线升变   first就是这个兵的chesscomponent
                if(first.getClass().getName().equals("model.PawnChessComponent")&&(first.getChessboardPoint().getX() == 0||first.getChessboardPoint().getX() == 7)) {
                    ChessComponent chess = first;
                    ChessboardPoint chessboardPoint = chess.getChessboardPoint();
                    int x = chessboardPoint.getX();
                    int y = chessboardPoint.getY();
                    Point location = chessboard.calculatePoint(chess.getChessboardPoint().getX(), chess.getChessboardPoint().getY());
                    chessboard.remove(chess);
                    ChessComponent newPawn = null;
                    if (chess.getChessColor().equals(ChessColor.WHITE)) {
                        if (chess.getChessboardPoint().getX() == 0) {
                            String[] options = {"Queen", "Rook", "Knight", "Bishop"};
                            int n = JOptionPane.showOptionDialog(null, "你想将兵升变为？", "兵的底线升变", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            if (n == 0) {
                                newPawn = new QueenChessComponent(chessboardPoint, location, ChessColor.WHITE, this, chessboard.getCHESS_SIZE());
                                chessboard.add(newPawn);
                                chessboard.getChessComponents()[x][y] = newPawn;
                            } else if (n == 1) {
                                newPawn = new RookChessComponent(chessboardPoint, location, ChessColor.WHITE, this, chessboard.getCHESS_SIZE());
                                chessboard.add(newPawn);
                                chessboard.getChessComponents()[x][y] = newPawn;
                            } else if (n == 2) {
                                newPawn = new KnightChessComponent(chessboardPoint, location, ChessColor.WHITE, this, chessboard.getCHESS_SIZE());
                                chessboard.add(newPawn);
                                chessboard.getChessComponents()[x][y] = newPawn;
                            } else {
                                newPawn = new BishopChessComponent(chessboardPoint, location, ChessColor.WHITE, this, chessboard.getCHESS_SIZE());
                                chessboard.add(newPawn);
                                chessboard.getChessComponents()[x][y] = newPawn;
                            }
                        }
                    } else {
                        if (first.getChessboardPoint().getX() == 7) {
                            String[] options = {"Queen", "Rook", "Knight", "Bishop"};
                            int n = JOptionPane.showOptionDialog(null, "你想将兵升变为？", "兵的底线升变", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            if (n == 0) {
                                newPawn = new QueenChessComponent(chessboardPoint, location, ChessColor.BLACK, this, chessboard.getCHESS_SIZE());
                                chessboard.add(newPawn);
                                chessboard.getChessComponents()[x][y] = newPawn;
                            } else if (n == 1) {
                                newPawn = new RookChessComponent(chessboardPoint, location, ChessColor.BLACK, this, chessboard.getCHESS_SIZE());
                                chessboard.add(newPawn);
                                chessboard.getChessComponents()[x][y] = newPawn;
                            } else if (n == 2) {
                                newPawn = new KnightChessComponent(chessboardPoint, location, ChessColor.BLACK, this, chessboard.getCHESS_SIZE());
                                chessboard.add(newPawn);
                                chessboard.getChessComponents()[x][y] = newPawn;
                            } else {
                                newPawn = new BishopChessComponent(chessboardPoint, location, ChessColor.BLACK, this, chessboard.getCHESS_SIZE());
                                chessboard.add(newPawn);
                                chessboard.getChessComponents()[x][y] = newPawn;
                            }
                        }
                    }
                    newPawn.setVisible(true);
                    chessboard.repaint();
                }

                first.setSelected(false);
                first = null;
                gameController.swapPlayer();


                //实现白色被将军
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if(chessboard.getChessComponents()[i][j].getChessColor()!=ChessColor.WHITE){
                            chessboard.getChessComponents()[i][j].canBeMoved(chessboard.getChessComponents());
                        }
                    }
                }
                int[] kingPointsWhite=new int[2];
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if(chessboard.getChessComponents()[i][j].getClass().getName().equals("model.KingChessComponent")&&chessboard.getChessComponents()[i][j].getChessColor()==ChessColor.WHITE){
                            if(chessboard.getChessComponents()[i][j].isCanBeMoved()){
                                kingPointsWhite[0]=i;
                                kingPointsWhite[1]=j;
                                KingChessComponent king=(KingChessComponent)chessboard.getChessComponents()[i][j];
                                king.setAttacked(true);
                            }
                        }
                    }
                }
                chessComponent.refresh(chessboard.getChessComponents());
                chessboard.getChessComponents()[kingPointsWhite[0]][kingPointsWhite[1]].repaint();

                //实现黑色被将军
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if(chessboard.getChessComponents()[i][j].getChessColor()!=ChessColor.BLACK){
                            chessboard.getChessComponents()[i][j].canBeMoved(chessboard.getChessComponents());
                        }
                    }
                }
                int[] kingPointsBalck=new int[2];
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if(chessboard.getChessComponents()[i][j].getClass().getName().equals("model.KingChessComponent")&&chessboard.getChessComponents()[i][j].getChessColor()==ChessColor.BLACK){
                            if(chessboard.getChessComponents()[i][j].isCanBeMoved()){
                                kingPointsBalck[0]=i;
                                kingPointsBalck[1]=j;
                                KingChessComponent king=(KingChessComponent)chessboard.getChessComponents()[i][j];
                                king.setAttacked(true);
                            }
                        }
                    }
                }
                chessComponent.refresh(chessboard.getChessComponents());
                chessboard.getChessComponents()[kingPointsBalck[0]][kingPointsBalck[1]].repaint();

                //实现宣布游戏结束
                chessboard.checkWinner();
                //存储当前chessboard
                recordChessboard(chessboard);

                chessboard.AI();
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */
    
    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     *         second棋子位置是不是敌方棋子
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    private void recordChessboard(ChessBoard theChessBoard){
        ChessComponent[][] chessComponents = new ChessComponent[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                chessComponents[i][j]=theChessBoard.getChessComponents()[i][j];
            }
        }
        ChessColor playerColor=theChessBoard.getCurrentColor();
        BoardAttributes theBoardAttributes=new BoardAttributes(chessComponents,playerColor);
        theChessBoard.getRecordGame().add(theBoardAttributes);
    }


}
