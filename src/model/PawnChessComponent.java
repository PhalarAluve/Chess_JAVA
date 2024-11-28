package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PawnChessComponent extends ChessComponent{
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private List<ChessboardPoint> chessboardPoints = new ArrayList<>();

    private Image pawnImage;

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size){
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
        if(color==ChessColor.WHITE){this.name='p';}
        else{this.name='P';}
    }

    @Override
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();   //先把找的图片读进来，把图像存在静态变量里了
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int row = source.getX();
        int col = source.getY();
        int Dx = destination.getX();
        int Dy = destination.getY();
        int resultX = Dx - row;
        int resultY = Dy - col;
        if(resultX < 0){resultX = -resultX;}
        if(resultY < 0){resultY = -resultY;}
        ChessColor color = chessComponents[row][col].getChessColor();
        if(resultX <= 2 && resultY <=1) {
//            x++, y++ or y-- or y
            if (color == ChessColor.BLACK) {
//                    y不变
                    if (resultY == 0) {
                        if(row == 1) {
                            for (int i = 1; i <= resultX; i++) {
                                if (!(chessComponents[row + i][col] instanceof EmptySlotComponent)) {
                                    return false;
                                }
                            }
                            return true;
                        }
                        else {
                            if (resultX > 1) {
                                return false;
                            } else {
                                for (int i = 1; i <= resultX; i++) {
                                    if (!(chessComponents[row + i][col] instanceof EmptySlotComponent)) {
                                        return false;
                                    }
                                }
                                if (Dx - row < 0) {
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
//                    y变
                    else{
                        if( resultX == 1 && resultY == 1) {
                            if(Dx - row < 0 ){
                                return false;
                            }
//                       x+1 , y+1
                            if (Dy - col > 0) {
                                if (chessComponents[row + 1][col + 1].chessColor == ChessColor.WHITE) {
                                    return true;
                                }
                                return false;
                            }
//                        x+1, y-1
                            if (Dy + col > 0) {
                                if (chessComponents[row + 1][col - 1].chessColor == ChessColor.WHITE) {
                                    return true;
                                }
                                return false;
                            }
                        }
                        else{return false;}
                    }
            }
//            白棋，向上走 ， x--
            else {
                if(Dx - row > 0 ){
                    return false;
                }
//                y 不变
                if (resultY == 0) {
//                    走两格
                    if (resultX == 2) {
                        if(row == 6) {
                            for (int i = 1; i <= resultX; i++) {
                                if (!(chessComponents[row - i][col] instanceof EmptySlotComponent)) {
                                    return false;
                                }
                            }
                            return true;
                        }
                        else{return false;}
                    }
//                    走一格
                    else {
                        for (int i = 1; i <= resultX; i++) {
                            if (!(chessComponents[row - i][col] instanceof EmptySlotComponent)) {
                                return false;
                            }
                            return true;
                        }
                    }
                }
//               y变
                else {
                    if (resultX == 1 && resultY == 1) {
//                    x+1 , y+1
                        if (Dy - col > 0) {
                            if (chessComponents[row - 1][col + 1].chessColor == ChessColor.BLACK) {
                                return true;
                            }
                            return false;
                        }
//                   x+1 , y-1
                        else {
                            if (chessComponents[row - 1][col - 1].chessColor == ChessColor.BLACK) {
                                return true;
                            }
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<ChessboardPoint> canMovePoints(ChessboardPoint source, ChessComponent[][] chessComponents) {
        int x = source.getX();
        int y = source.getY();
        ChessColor color = chessComponents[x][y].getChessColor();
        if( color == ChessColor.BLACK ){
            if(x == 1){
                if( chessComponents[x+1][y].getChessColor() == ChessColor.NONE) {
                    chessboardPoints.add(new ChessboardPoint(x + 1, y));
                    if( chessComponents[x+2][y].getChessColor() == ChessColor.NONE) {
                        chessboardPoints.add(new ChessboardPoint(x + 2, y));
                    }
                }
                if( y-1 >= 0 && y-1<8) {
                    if (chessComponents[x + 1][y - 1].getChessColor() == ChessColor.WHITE) {
                        chessboardPoints.add(new ChessboardPoint(x + 1, y - 1));
                    }
                }
                if(y+1 >=0 && y+1 <8) {
                    if (chessComponents[x + 1][y + 1].getChessColor() == ChessColor.WHITE) {
                        chessboardPoints.add(new ChessboardPoint(x + 1, y + 1));
                    }
                }
            }
            else{
                if( x+1 >= 0 && x+1 < 8 ) {
                    if (chessComponents[x + 1][y].getChessColor() == ChessColor.NONE) {
                        chessboardPoints.add(new ChessboardPoint(x + 1, y));
                    }
                }
                if(x+1 >= 0 && x+1 < 8 && y-1 >= 0 && y-1 < 8 ) {
                    if (chessComponents[x + 1][y - 1].getChessColor() == ChessColor.WHITE) {
                        chessboardPoints.add(new ChessboardPoint(x + 1, y - 1));
                    }
                }
                if(x+1 >= 0 && x+1 <8 && y+1 >= 0 && y+1 < 8) {
                    if (chessComponents[x + 1][y + 1].getChessColor() == ChessColor.WHITE) {
                        chessboardPoints.add(new ChessboardPoint(x + 1, y + 1));
                    }
                }
            }
        }


        return chessboardPoints;
    }

    public void canBeMoved(ChessComponent[][] chessComponents){
        ChessboardPoint source = getChessboardPoint();
        int x = source.getX();
        int y = source.getY();
        ChessColor color = chessComponents[x][y].getChessColor();
//        黑棋
        if( color == ChessColor.BLACK ){
            if(x == 1){
                if( chessComponents[x+1][y].getChessColor() == ChessColor.NONE) {
                    chessComponents[x+1][y].setCanBeMoved(true);
//                    chessComponents[x+1][y].repaint();
                    if( chessComponents[x+2][y].getChessColor() == ChessColor.NONE) {
                        chessComponents[x+2][y].setCanBeMoved(true);
//                        chessComponents[x+2][y].repaint();
                    }
                }
                if( y-1 >= 0 && y-1<8) {
                    if (chessComponents[x + 1][y - 1].getChessColor() == ChessColor.WHITE) {
                        chessComponents[x + 1][y - 1].setCanBeMoved(true);
//                        chessComponents[x + 1][y - 1].repaint();
                    }
                }
                if(y+1 >=0 && y+1 <8) {
                    if (chessComponents[x + 1][y + 1].getChessColor() == ChessColor.WHITE) {
                        chessComponents[x + 1][y + 1].setCanBeMoved(true);
//                        chessComponents[x + 1][y + 1].repaint();
                    }
                }
            }
            else{
                if( x+1 >= 0 && x+1 < 8 ) {
                    if (chessComponents[x + 1][y].getChessColor() == ChessColor.NONE) {
                        chessComponents[x + 1][y].setCanBeMoved(true);
//                        chessComponents[x + 1][y].repaint();
                    }
                }
                if(x+1 >= 0 && x+1 < 8 && y-1 >= 0 && y-1 < 8 ) {
                    if (chessComponents[x + 1][y - 1].getChessColor() == ChessColor.WHITE) {
                        chessComponents[x + 1][y-1].setCanBeMoved(true);
//                        chessComponents[x + 1][y-1].repaint();
                    }
                }
                if(x+1 >= 0 && x+1 <8 && y+1 >= 0 && y+1 < 8) {
                    if (chessComponents[x + 1][y + 1].getChessColor() == ChessColor.WHITE) {
                        chessComponents[x + 1][y + 1].setCanBeMoved(true);
//                        chessComponents[x + 1][y + 1].repaint();
                    }
                }
            }
        }
        else{
            if(x == 6){
                if(chessComponents[x-1][y].getChessColor() == ChessColor.NONE){
                    chessComponents[x-1][y].setCanBeMoved(true);
//                    chessComponents[x-1][y].repaint();
                    if(chessComponents[x-2][y].getChessColor() == ChessColor.NONE){
                        chessComponents[x-2][y].setCanBeMoved(true);
//                        chessComponents[x-2][y].repaint();
                    }
                }
                if( y-1 >=0 && y-1<8) {
                    if (chessComponents[x - 1][y - 1].getChessColor() == ChessColor.BLACK) {
                        chessComponents[x - 1][y - 1].setCanBeMoved(true);
//                        chessComponents[x - 1][y - 1].repaint();
                    }
                }
                if( y+1 >= 0 && y+1 <8) {
                    if (chessComponents[x - 1][y + 1].getChessColor() == ChessColor.BLACK) {
                        chessComponents[x - 1][y + 1].setCanBeMoved(true);
//                        chessComponents[x - 1][y + 1].repaint();
                    }
                }
            }
            else{
                if((x-1) >= 0 && (x-1) < 8){
                    if(chessComponents[x-1][y].getChessColor() == ChessColor.NONE){
                        chessComponents[x - 1][y].setCanBeMoved(true);
//                        chessComponents[x - 1][y].repaint();
                    }
                    if( y-1 >=0 && y-1 <8) {
                        if (chessComponents[x - 1][y - 1].getChessColor() == ChessColor.BLACK) {
                            chessComponents[x - 1][y - 1].setCanBeMoved(true);
//                            chessComponents[x - 1][y - 1].repaint();
                        }
                    }
                    if(y+1 >= 0 && y+1 <8 ) {
                        if (chessComponents[x - 1][y + 1].getChessColor() == ChessColor.BLACK) {
                            chessComponents[x - 1][y + 1].setCanBeMoved(true);
//                            chessComponents[x - 1][y + 1].repaint();
                        }
                    }
                }
            }
        }
    }

    public void refresh(ChessComponent[][] chessComponents){
        ChessboardPoint source = getChessboardPoint();
        int row = source.getX();
        int col = source.getY();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                chessComponents[x][y].setCanBeMoved(false);
                chessComponents[x][y].repaint();;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }


}
