package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueenChessComponent extends ChessComponent{
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;
    private List<ChessboardPoint> chessboardPoints = new ArrayList<>();

    @Override
    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {   //构造的时候就会用这个方法，那每构造一个就会读一次，就没必要
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();   //先把找的图片读进来，把图像存在静态变量里了
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController clickController, int size) {
        super(chessboardPoint, location, color, clickController, size);
        initiateQueenImage(color);
        if(color==ChessColor.WHITE){this.name='q';}
        else{this.name='Q';}
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        return chessComponents[destination.getX()][destination.getY()].isCanBeMoved();
    }

    @Override
    public List<ChessboardPoint> canMovePoints(ChessboardPoint source, ChessComponent[][] chessComponents) {
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

//        x, y++
        for (int i = 1;(y + i) >=0 && (y + i) <8 ; i++) {
            if(chessComponents [x][y+i].getChessColor() == ChessColor.NONE){
                chessboardPoints.add(new ChessboardPoint(x, y + i));
            }
            else{
                if(color != chessComponents [x][y+i].getChessColor()){
                    chessboardPoints.add(new ChessboardPoint(x, y + i));
                }
                break;
            }


        }
//       x, y--
        for (int i = 1; (y - i) >=0 && (y - i) <8 ; i++) {
            if(chessComponents [x][y-i].getChessColor() == ChessColor.NONE){
                chessboardPoints.add(new ChessboardPoint(x, y - i));
            }
            else{
                if(color != chessComponents [x][y-i].getChessColor()){
                    chessboardPoints.add(new ChessboardPoint(x, y - i));
                }
                break;
            }
        }
//      x++, y
        for (int i = 1; (x + i) >= 0 && (x + i) < 8; i++) {
            if(chessComponents [x+i][y].getChessColor() == ChessColor.NONE){
                chessboardPoints.add(new ChessboardPoint(x + i, y));
            }
            else{
                if(color != chessComponents [x+i][y].getChessColor()){
                    chessboardPoints.add(new ChessboardPoint(x + i, y));
                }
                break;
            }
        }
//      x--, y
        for (int i = 1; (x - i) >= 0 && (x - i) < 8; i++) {
            if(chessComponents [x-i][y].getChessColor() == ChessColor.NONE){
                chessboardPoints.add(new ChessboardPoint(x - i, y));
            }
            else{
                if(color != chessComponents [x-i][y].getChessColor()){
                    chessboardPoints.add(new ChessboardPoint(x - i, y));
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
        for (int y = col; y < 8; y++) {
            if (((chessComponents[row][y] instanceof EmptySlotComponent) || chessComponents[row][y].chessColor != chessComponents[row][col].chessColor) && y != col) {
                chessComponents[row][y].setCanBeMoved(true);
//                chessComponents[row][y].repaint();
                if(!(chessComponents[row][y] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row][y].chessColor == chessComponents[row][col].chessColor && y != col){break;}
        }
        for (int y = col; y >= 0 ; y--) {
            if (((chessComponents[row][y] instanceof EmptySlotComponent) || chessComponents[row][y].chessColor != chessComponents[row][col].chessColor) && y != col) {
                chessComponents[row][y].setCanBeMoved(true);
//                chessComponents[row][y].repaint();
                if(!(chessComponents[row][y] instanceof EmptySlotComponent)){break;}
            }
            if( chessComponents[row][y].chessColor == chessComponents[row][col].chessColor && y != col){break;}
        }
        for (int x = row; x < 8; x++) {
            if (((chessComponents[x][col] instanceof EmptySlotComponent) || chessComponents[x][col].chessColor != chessComponents[row][col].chessColor) && x != row) {
                chessComponents[x][col].setCanBeMoved(true);
//                chessComponents[x][col].repaint();
                if(!(chessComponents[x][col] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[x][col].chessColor == chessComponents[row][col].chessColor && x != row){break;}

        }
        for (int x = row; x >= 0; x--) {
            if (((chessComponents[x][col] instanceof EmptySlotComponent) || chessComponents[x][col].chessColor != chessComponents[row][col].chessColor) && x != row) {
                chessComponents[x][col].setCanBeMoved(true);
//                chessComponents[x][col].repaint();
                if(!(chessComponents[x][col] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[x][col].chessColor == chessComponents[row][col].chessColor && x != row){break;}
        }

        //        x-- , y++
        for (int i = 0; (row - i >= 0) && (col + i < 8); i++) {
            if (((chessComponents[row - i][col + i] instanceof EmptySlotComponent) || chessComponents[row][col].chessColor != chessComponents[row - i][col + i].chessColor) && col + i != col) {
                chessComponents[row - i][col + i].setCanBeMoved(true);
//                chessComponents[row - i][col + i].repaint();
                if(!(chessComponents[row - i][col + i] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row - i][col + i].chessColor == chessComponents[row][col].chessColor && col + i != col){break;}
        }
//        x++ , y++
        for (int i = 0; (row + i) < 8 && (col + i) < 8; i++) {
            if (((chessComponents[row + i][col + i] instanceof EmptySlotComponent) || chessComponents[row + i][col + i].chessColor != chessComponents[row][col].chessColor) && row + i != row) {
                chessComponents[row + i][col + i].setCanBeMoved(true);
//                chessComponents[row + i][col + i].repaint();
                if(!(chessComponents[row + i][col + i] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row + i][col + i].chessColor == chessComponents[row][col].chessColor && row + i != row){break;}

        }
//       x-- , y--
        for (int i = 0; (row - i) >= 0 && (col - i) >= 0; i++) {
            if (((chessComponents[row - i][col - i] instanceof EmptySlotComponent) || chessComponents[row - i][col - i].chessColor != chessComponents[row][col].chessColor) && row - i != row) {
                chessComponents[row - i][col - i].setCanBeMoved(true);
//                chessComponents[row - i][col - i].repaint();
                if(!(chessComponents[row - i][col - i] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row - i][col - i].chessColor == chessComponents[row][col].chessColor && row - i != row){break;}
        }
//        x++ , y--
        for (int i = 0; (row + i) < 8 && (col - i) >= 0; i++) {
            if (((chessComponents[row + i][col - i] instanceof EmptySlotComponent) || chessComponents[row + i][col - i].chessColor != chessComponents[row][col].chessColor) && row + i != row) {
                chessComponents[row + i][col - i].setCanBeMoved(true);
//                chessComponents[row + i][col - i].repaint();
                if(!(chessComponents[row + i][col - i] instanceof EmptySlotComponent)){break;}
            }
            if(chessComponents[row + i][col - i].chessColor == chessComponents[row][col].chessColor && row + i != row){break;}

        }
    }

    public void refresh(ChessComponent[][] chessComponents){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                chessComponents[i][j].setCanBeMoved(false);
                chessComponents[i][j].repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }


}
