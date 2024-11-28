package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnightChessComponent extends ChessComponent {
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private Image knightImage;
    private List<ChessboardPoint> chessboardPoints = new ArrayList<>();

    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {   //构造的时候就会用这个方法，那每构造一个就会读一次，就没必要
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();   //先把找的图片读进来，把图像存在静态变量里了
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
        if(color==ChessColor.WHITE){this.name='n';}
        else{this.name='N';}
    }

    public void canBeMoved(ChessComponent[][] chessComponents){
        ChessboardPoint source = getChessboardPoint();
        int row = source.getX();
        int col = source.getY();
        int[][] points=new int[8][2];
        points[0]=new int[]{row+2, col+1};
        points[1]=new int[]{row+1, col+2};
        points[2]=new int[]{row-1, col+2};
        points[3]=new int[]{row-2, col+1};
        points[4]=new int[]{row-2, col-1};
        points[5]=new int[]{row-1, col-2};
        points[6]=new int[]{row+1, col-2};
        points[7]=new int[]{row+2, col-1};

        for(int i=0;i<8;i++){
            if(points[i][0]>=0&&points[i][0]<8&&points[i][1]>=0&&points[i][1]<8&&chessComponents[points[i][0]][points[i][1]].getChessColor()!=this.chessColor){
                chessComponents[points[i][0]][points[i][1]].setCanBeMoved(true);
                //currentCanBeMoved
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
//      x+2 , y+1
        if( (x+2) >= 0 && (x+2) < 8 && (y+1) >= 0 && (y+1) < 8){
            if(chessColor != chessComponents [x+2][y+1].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x + 2, y + 1));
            }
        }
//      x+2 , y-1
        if( (x+2) >= 0 && (x+2) < 8 && (y-1) >= 0 && (y-1) < 8){
            if(chessColor != chessComponents [x+2][y-1].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x + 2, y - 1));
            }
        }
//      x-2 , y+1
        if( (x-2) >= 0 && (x-2) < 8 && (y+1) >= 0 && (y+1) < 8){
            if(chessColor != chessComponents [x-2][y+1].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x - 2, y + 1));
            }
        }
//      x-2 , y-1
        if( (x-2) >= 0 && (x-2) < 8 && (y-1) >= 0 && (y-1) < 8){
            if(chessColor != chessComponents [x-2][y-1].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x - 2, y - 1));
            }
        }
//      x+1 , y+2
        if( (x+1) >= 0 && (x+1) < 8 && (y+2) >= 0 && (y+2) < 8){
            if(chessColor != chessComponents [x+1][y+2].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x + 1, y + 2));
            }
        }
//      x+1 , y-2
        if( (x+1) >= 0 && (x+1) < 8 && (y-2) >= 0 && (y-2) < 8){
            if(chessColor != chessComponents [x+1][y-2].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x + 1, y - 2));
            }
        }
//      x-1 , y+2
        if( (x-1) >= 0 && (x-1) < 8 && (y+2) >= 0 && (y+2) < 8){
            if(chessColor != chessComponents [x-1][y+2].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x - 1, y + 2));
            }
        }
//      x-1 , y-2
        if( (x-1) >= 0 && (x-1) < 8 && (y-2) >= 0 && (y-2) < 8){
            if(chessColor != chessComponents [x-1][y-2].getChessColor()) {
                chessboardPoints.add(new ChessboardPoint(x - 1, y - 2));
            }
        }


        return chessboardPoints;
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
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
