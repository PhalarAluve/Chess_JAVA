package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示国际象棋里面的车
 */
public class RookChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    //属性： 存了两张图像，对整个类有效
    private static Image ROOK_WHITE;
    private static Image ROOK_BLACK;
    private List<ChessboardPoint> chessboardPoints = new ArrayList<>();

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    //当前这个车的实例
    private Image rookImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (ROOK_WHITE == null) {   //构造的时候就会用这个方法，那每构造一个就会读一次，就没必要
            ROOK_WHITE = ImageIO.read(new File("./images/rook-white.png"));
        }

        if (ROOK_BLACK == null) {
            ROOK_BLACK = ImageIO.read(new File("./images/rook-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();   //先把找的图片读进来，把图像存在静态变量里了
            if (color == ChessColor.WHITE) {
                rookImage = ROOK_WHITE;
            } else if (color == ChessColor.BLACK) {
                rookImage = ROOK_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RookChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
        if(color==ChessColor.WHITE){this.name='r';}
        else{this.name='R';}
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else { // Not on the same row or the same column.
            return false;
        }
        return true;
    }

    @Override
    public List<ChessboardPoint> canMovePoints(ChessboardPoint source, ChessComponent[][] chessComponents) {
        int x = source.getX();
        int y = source.getY();
        ChessColor color = chessComponents[x][y].getChessColor();
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
            if( chessColor == chessComponents [x-i][y].getChessColor()) {
                break;
            }
            else{
                if(chessComponents [x-i][y].getChessColor() == ChessColor.NONE) {
                    chessboardPoints.add(new ChessboardPoint(x - i, y));
                }
                if(chessComponents [x-i][y].getChessColor() != ChessColor.NONE &&
                        chessComponents [x][y].getChessColor() != chessComponents [x-i][y].getChessColor()){
                    chessboardPoints.add(new ChessboardPoint(x - i, y));
                    break;
                }
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

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(rookImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
