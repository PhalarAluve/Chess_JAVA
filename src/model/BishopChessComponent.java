package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BishopChessComponent extends ChessComponent{
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;
    private List<ChessboardPoint> chessboardPoints = new ArrayList<>();

    @Override
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if(BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color){
        try{
            loadResource();
            if(color == ChessColor.WHITE){
                bishopImage = BISHOP_WHITE;
            }else if(color == ChessColor.BLACK){
                bishopImage = BISHOP_BLACK;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size){
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
        if(color==ChessColor.WHITE){this.name='b';}
        else{this.name='B';}
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
        if(resultX != resultY){
            return false;
        }
        else{
            resultX = Dx - row;
            resultY = Dy - col;
            if(resultX < 0 && resultY < 0) {
                for (int i = -1; i > resultX; i--) {
                    if ( !(chessComponents[row + i][col + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            if(resultX > 0 && resultY < 0){
                for (int i = 1; i < resultX ; i++) {
                    if ( !(chessComponents[row + i][col - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            if(resultX < 0 && resultY > 0){
                for (int i = -1; i > resultX ; i--) {
                    if ( !(chessComponents[row + i][col - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            if(resultX > 0 && resultY > 0){
                for (int i = 1; i < resultX ; i++) {
                    if ( !(chessComponents[row + i][col + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @Override
    public List<ChessboardPoint> canMovePoints(ChessboardPoint source,ChessComponent[][] chessComponents) {
        int x = source.getX();
        int y = source.getY();
        ChessColor color = chessComponents[x][y].getChessColor();

        //  x++, y++
        for (int i = 1;(x + i)<8 && (y + i)<8 && (x+i) >= 0 && (y+i) >= 0; i++) {
            if(chessComponents [x+i][y+i].getChessColor() == ChessColor.NONE) {
                chessboardPoints.add(new ChessboardPoint(x + i, y + i));
            }
            else{
                if(color != chessComponents [x+i][y+i].getChessColor()){
                    chessboardPoints.add(new ChessboardPoint(x + i, y + i));
                }
                break;
            }
        }
//        x++, y--
        for (int i = 1; (x+i) < 8 && (x+i) >= 0 && (y-i) >= 0 && (y-i) <8; i++) {
            if(chessComponents [x+i][y-i].getChessColor() == ChessColor.NONE){
                chessboardPoints.add(new ChessboardPoint(x + i, y - i));
            }
            else{
                if( color != chessComponents [x+i][y-i].getChessColor()){
                    chessboardPoints.add(new ChessboardPoint(x + i, y - i));
                }
                break;
            }

        }
//       x--, y++
        for (int i = 1;(x - i)<8 && (y + i)<8 && (x-i) >= 0 && (y+i) >= 0 ; i++) {
            if(chessComponents [x-i][y+i].getChessColor() == ChessColor.NONE){
                chessboardPoints.add(new ChessboardPoint(x - i, y + i));
            }
            else{
                if( color != chessComponents [x-i][y+i].getChessColor()) {
                    chessboardPoints.add(new ChessboardPoint(x - i, y + i));
                }
                break;
            }
        }
//        x--, y--
        for (int i = 1; (x-i) < 8 && (x-i) >= 0 && (y-i) >= 0 && (y-i) <8; i++) {
            if(chessComponents [x-i][y-i].getChessColor() == ChessColor.NONE){
                chessboardPoints.add(new ChessboardPoint(x - i, y - i));
            }
            else{
                if(color != chessComponents [x-i][y-i].getChessColor()){
                    chessboardPoints.add(new ChessboardPoint(x - i, y - i));
                }
                break;
            }
        }

        for (int i = 0; i < chessboardPoints.size() -1; i++) {
            for (int j = i+1; j <chessboardPoints.size() ; j++) {
                if( chessboardPoints.get(i).getX() == chessboardPoints.get(j).getX() &&  chessboardPoints.get(i).getY() == chessboardPoints.get(j).getY()){
                    chessboardPoints.remove(j);
                }
            }
        }
        return chessboardPoints;
    }


    public void canBeMoved(ChessComponent[][] chessComponents){
        ChessboardPoint source = getChessboardPoint();
        int row = source.getX();
        int col = source.getY();
//        x-- , y++
        for (int i = 0; (row - i >= 0) && (col + i < 8); i++) {
            if (((chessComponents[row - i][col + i] instanceof EmptySlotComponent) || chessComponents[row][col].chessColor != chessComponents[row - i][col + i].chessColor) && col + i != col) {
                chessComponents[row - i][col + i].setCanBeMoved(true);
                if(!(chessComponents[row - i][col + i] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row - i][col + i].chessColor == chessComponents[row][col].chessColor && col + i != col){break;}
        }
//        x++ , y++
        for (int i = 0; (row + i) < 8 && (col + i) < 8; i++) {
            if (((chessComponents[row + i][col + i] instanceof EmptySlotComponent) || chessComponents[row + i][col + i].chessColor != chessComponents[row][col].chessColor) && row + i != row) {
                chessComponents[row + i][col + i].setCanBeMoved(true);
                if(!(chessComponents[row + i][col + i] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row + i][col + i].chessColor == chessComponents[row][col].chessColor && row + i != row){break;}

        }
//       x-- , y--
        for (int i = 0; (row - i) >= 0 && (col - i) >= 0; i++) {
            if (((chessComponents[row - i][col - i] instanceof EmptySlotComponent) || chessComponents[row - i][col - i].chessColor != chessComponents[row][col].chessColor) && row - i != row) {
                chessComponents[row - i][col - i].setCanBeMoved(true);
                if(!(chessComponents[row - i][col - i] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row - i][col - i].chessColor == chessComponents[row][col].chessColor && row - i != row){break;}
        }
//        x++ , y--
        for (int i = 0; (row + i) < 8 && (col - i) >= 0; i++) {
            if (((chessComponents[row + i][col - i] instanceof EmptySlotComponent) || chessComponents[row + i][col - i].chessColor != chessComponents[row][col].chessColor) && row + i != row) {
                chessComponents[row + i][col - i].setCanBeMoved(true);
                if(!(chessComponents[row + i][col - i] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row + i][col - i].chessColor == chessComponents[row][col].chessColor && row + i != row){break;}

        }
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





    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }



}
