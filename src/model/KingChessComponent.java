package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KingChessComponent extends ChessComponent{
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;
    private boolean isAttacked;
    private List<ChessboardPoint> chessboardPoints = new ArrayList<>();

    @Override
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {   //构造的时候就会用这个方法，那每构造一个就会读一次，就没必要
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();   //先把找的图片读进来，把图像存在静态变量里了
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController clickController, int size) {
        super(chessboardPoint, location, color, clickController, size);
        initiateKingImage(color);
        this.isAttacked=false;
        if(color==ChessColor.WHITE){this.name='k';}
        else{this.name='K';}
    }

    public void canBeMoved(ChessComponent[][] chessComponents){
        ChessboardPoint source = getChessboardPoint();
        int row = source.getX();
        int col = source.getY();
        int[][] points=new int[8][2];
        points[0]=new int[]{row+1, col+1};
        points[1]=new int[]{row+1, col};
        points[2]=new int[]{row+1, col-1};
        points[3]=new int[]{row-1, col+1};
        points[4]=new int[]{row-1, col-1};
        points[5]=new int[]{row-1, col};
        points[6]=new int[]{row, col+1};
        points[7]=new int[]{row, col-1};

        for(int i=0;i<8;i++){
            if(points[i][0]>=0&&points[i][0]<8&&points[i][0]>=0&&points[i][0]<8&&points[i][1]>=0&&points[i][1]<8&&chessComponents[points[i][0]][points[i][1]].getChessColor()!=this.getChessColor()){
                chessComponents[points[i][0]][points[i][1]].setCanBeMoved(true);
            }
        }
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        return chessComponents[destination.getX()][destination.getY()].isCanBeMoved();
    }

    @Override
    public List<ChessboardPoint> canMovePoints(ChessboardPoint source, ChessComponent[][] chessComponents) {
        int x = source.getX();
        int y = source.getY();
        int i = 1;
        ChessColor color = chessComponents[x][y].getChessColor();
//      x, y++
        if (y+i < 8 && y+i >=0){
            if(color != chessComponents [x][y+i].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x, y + i));
            }
        }
//      x, y--
        if (y-i < 8 && y-i >=0){
            if(color != chessComponents [x][y-i].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x, y - i));
            }
        }
//      x++, y
        if(x+i >= 0 && x+i <8){
            if(color != chessComponents [x+i][y].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x+i, y ));
            }
        }
//      x-- , y
        if(x-i >= 0 && x-i <8){
            if(color != chessComponents [x-i][y].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x-i, y ));
            }
        }
//      x++ , y++
        if((x + i)<8 && (y + i)<8 && (x+i) >= 0){
            if(color != chessComponents [x+i][y+i].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x+i, y+i ));
            }
        }
//      x++ , y--
        if((x+i) < 8 && (x+i) >= 0 && (y-i) >= 0 && (y-i) <8){
            if(color != chessComponents [x+i][y-i].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x+i, y-i ));
            }
        }
//      x--, y++
        if((x - i)<8 && (y + i)<8 && (x-i) >= 0 && (y+i) >= 0){
            if(color != chessComponents [x-i][y+i].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x-i, y+i ));
            }
        }
//      x--, y--
        if((x-i) < 8 && (x-i) >= 0 && (y-i) >= 0 && (y-i) <8){
            if(color != chessComponents [x-i][y-i].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x-i, y-i ));
            }
        }
        for (int k = 0; i < chessboardPoints.size() -1; i++) {
            for (int j = i+1; j <chessboardPoints.size() ; j++) {
                if( chessboardPoints.get(k).getX() == chessboardPoints.get(j).getX() &&  chessboardPoints.get(i).getY() == chessboardPoints.get(j).getY()){
                    chessboardPoints.remove(j);
                }
            }
        }
        return chessboardPoints;
    }

    public void refresh(ChessComponent[][] chessComponents){
        this.setAttacked(false);
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                chessComponents[i][j].setCanBeMoved(false);
                chessComponents[i][j].repaint();
//                if(chessComponents[i][j]instanceof KingChessComponent){
//                    ((KingChessComponent) chessComponents[i][j]).setAttacked(false);
//                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        if(isAttacked()){
            g.setColor(Color.RED);
            g.drawLine(0, 0, getWidth() , getHeight());
            g.drawLine(getWidth(), 0, 0 , getHeight());
        }
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    public boolean isAttacked() {
        return isAttacked;
    }
}
