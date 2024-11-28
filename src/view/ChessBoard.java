package view;

import controller.GameController;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class ChessBoard extends JComponent {

    private static final int CHESSBOARD_SIZE = 8;
    //二维类组，储存各个棋格的情况
    private ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private int WIDTH;
    private GameController gameController  ;
    private  ClickController clickController ;
    private final int CHESS_SIZE;
    private ChessGameFrame chessGameFrame;
    private PvEGameFrameEasy pvEGameFrameEasy;
    private PvEGameFrameHard pvEGameFrameHard;
    private String PlayerW;
    private String PlayerB;
    private ArrayList<BoardAttributes> recordGame=new ArrayList<>();
    private boolean isAI = false;
    private boolean isHard = false;
    private boolean win = true;


    public ChessBoard(int width, int height, GameController gameController, ChessGameFrame chessGameFrame,String PlayerW ,String PlayerB) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        setWIDTH(width);
        this.gameController = gameController;
        this.clickController =   new ClickController(this,new StatusPanel(WIDTH,HEIGHT/2,PlayerW,PlayerB),gameController);
        this.chessGameFrame = chessGameFrame;
        this.PlayerW = PlayerW;
        this.PlayerB = PlayerB;
        isAI = false;
        isHard = false;

        initiateEmptyChessboard();  //先设成都是空棋子，之后再一个一个设

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-3,ChessColor.WHITE);

        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);

        initPawnOnBoard(1,0,ChessColor.BLACK);
        initPawnOnBoard(1,1,ChessColor.BLACK);
        initPawnOnBoard(1,2,ChessColor.BLACK);
        initPawnOnBoard(1,3,ChessColor.BLACK);
        initPawnOnBoard(1,4,ChessColor.BLACK);
        initPawnOnBoard(1,5,ChessColor.BLACK);
        initPawnOnBoard(1,6,ChessColor.BLACK);
        initPawnOnBoard(1,7,ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,0,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,1,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,2,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,3,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,4,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,5,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,6,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,7,ChessColor.WHITE);

        initKingOnBoard(0, 3, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);

        initQueenOnBoard(0, 4, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);

        initRecord();
        this.repaint();

    }

    public ChessBoard(int width, int height, GameController gameController, PvEGameFrameEasy pvEGameFrameEasy,String PlayerW ,String PlayerB) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        setWIDTH(width);
        this.gameController = gameController;
        this.clickController =   new ClickController(this,new StatusPanel(WIDTH,HEIGHT/2,PlayerW,PlayerB),gameController);
        this.pvEGameFrameEasy = pvEGameFrameEasy;
        this.PlayerW = PlayerW;
        this.PlayerB = PlayerB;
        isAI = true;
        isHard = false;

        initiateEmptyChessboard();  //先设成都是空棋子，之后再一个一个设

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-3,ChessColor.WHITE);

        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);

        initPawnOnBoard(1,0,ChessColor.BLACK);
        initPawnOnBoard(1,1,ChessColor.BLACK);
        initPawnOnBoard(1,2,ChessColor.BLACK);
        initPawnOnBoard(1,3,ChessColor.BLACK);
        initPawnOnBoard(1,4,ChessColor.BLACK);
        initPawnOnBoard(1,5,ChessColor.BLACK);
        initPawnOnBoard(1,6,ChessColor.BLACK);
        initPawnOnBoard(1,7,ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,0,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,1,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,2,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,3,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,4,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,5,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,6,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,7,ChessColor.WHITE);

        initKingOnBoard(0, 3, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);

        initQueenOnBoard(0, 4, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);

        initRecord();
        this.repaint();

    }

    public ChessBoard(int width, int height, GameController gameController, PvEGameFrameHard pvEGameFrameHard,String PlayerW ,String PlayerB) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        setWIDTH(width);
        this.gameController = gameController;
        this.clickController =   new ClickController(this,new StatusPanel(WIDTH,HEIGHT/2,PlayerW,PlayerB),gameController);
        this.pvEGameFrameHard = pvEGameFrameHard;
        this.PlayerW = PlayerW;
        this.PlayerB = PlayerB;
        isAI = true;
        isHard = true;

        initiateEmptyChessboard();  //先设成都是空棋子，之后再一个一个设

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-3,ChessColor.WHITE);

        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);

        initPawnOnBoard(1,0,ChessColor.BLACK);
        initPawnOnBoard(1,1,ChessColor.BLACK);
        initPawnOnBoard(1,2,ChessColor.BLACK);
        initPawnOnBoard(1,3,ChessColor.BLACK);
        initPawnOnBoard(1,4,ChessColor.BLACK);
        initPawnOnBoard(1,5,ChessColor.BLACK);
        initPawnOnBoard(1,6,ChessColor.BLACK);
        initPawnOnBoard(1,7,ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,0,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,1,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,2,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,3,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,4,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,5,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,6,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,7,ChessColor.WHITE);

        initKingOnBoard(0, 3, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);

        initQueenOnBoard(0, 4, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);

        initRecord();
        this.repaint();

    }

    private void initRecord(){
        ChessComponent[][] chessComponents = new ChessComponent[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                chessComponents[i][j]=this.getChessComponents()[i][j];
            }
        }
        ChessColor playerColor=this.getCurrentColor();
        BoardAttributes theBoardAttributes=new BoardAttributes(chessComponents,playerColor);
        this.recordGame.add(theBoardAttributes);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    //转换出棋方颜色
    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);    //对于所有JComponent记得set true
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row,col) , calculatePoint(row,col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);    //对于所有JComponent记得set true
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);    //对于所有JComponent记得set true
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);    //对于所有JComponent记得set true
        putChessOnBoard(chessComponent);
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void checkWinner(){
        boolean winnerExist = true;
        ChessColor winner;
        if(currentColor == ChessColor.BLACK){
            winner = ChessColor.BLACK;
        }
        else{
            winner = ChessColor.WHITE;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if( chessComponents [i][j] instanceof KingChessComponent){
                    if( chessComponents[i][j].getChessColor() != currentColor && !((KingChessComponent) chessComponents[i][j]).isAttacked()){
                        winnerExist = false;
                        win = false;
                    }
                }
            }
        }
        if( winnerExist ){
            ImageIcon imageIcon = new ImageIcon("images/dia-backboard.png");
            JOptionPane.showMessageDialog(null, winner.getName()+" WINS !", "Result", JOptionPane.WARNING_MESSAGE,imageIcon);
            if(isAI){
                if(isHard){
                    pvEGameFrameHard.dispose();
                    pvEGameFrameHard.setNewFrame();
                }
                else{
                    pvEGameFrameEasy.dispose();
                    pvEGameFrameEasy.setNewFrame();
                }

            }
            else{
                chessGameFrame.dispose();
                chessGameFrame.setNewFrame();
            }
        }
    }

    public ArrayList<BoardAttributes> getRecordGame() {
        return recordGame;
    }

    public void Retract2(){
        int lastGame = recordGame.size();
        if(lastGame < 3 ){
            JOptionPane.showMessageDialog(null,"无法完成悔棋！原因：没有上一步动作。","错误！",JOptionPane.WARNING_MESSAGE);
        }
        else{
            int retractGame = lastGame - 3;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    remove(chessComponents[i][j]);
                    ChessComponent theChess = recordGame.get(retractGame).getChessComponents()[i][j];
                    ChessColor color = theChess.getChessColor();
                    ChessboardPoint source = new ChessboardPoint(i, j);

                    if (theChess.getChessName() == 95) {
                        chessComponents[i][j] = new EmptySlotComponent(source, calculatePoint(i, j),  clickController, CHESS_SIZE);
                    } else if (theChess.getChessName() == 'R' | theChess.getChessName() == 'r') {
                        chessComponents[i][j] = new RookChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (theChess.getChessName() == 'N' | theChess.getChessName() == 'n') {
                        chessComponents[i][j] = new KnightChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (theChess.getChessName() == 'Q' | theChess.getChessName() == 'q') {
                        chessComponents[i][j] = new QueenChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (theChess.getChessName() == 'K' | theChess.getChessName() == 'k') {
                        chessComponents[i][j] = new KingChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (theChess.getChessName() == 'P' | theChess.getChessName() == 'p') {
                        chessComponents[i][j] = new PawnChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (theChess.getChessName() == 'B' | theChess.getChessName() == 'b') {
                        chessComponents[i][j] = new BishopChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    }
                    add(chessComponents[i][j]);
                }
            }
            currentColor=recordGame.get(retractGame).getCurrentColor();
            recordGame.remove(lastGame - 1);
            recordGame.remove(lastGame - 2);
        }

    }

    public boolean checkWin(){
        boolean winnerExist = true;
        ChessColor winner;
        if(currentColor == ChessColor.BLACK){
            winner = ChessColor.BLACK;
        }
        else{
            winner = ChessColor.WHITE;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if( chessComponents [i][j] instanceof KingChessComponent){
                    if( chessComponents[i][j].getChessColor() != currentColor && !((KingChessComponent) chessComponents[i][j]).isAttacked()){
                        winnerExist = false;
                        win = false;
                    }
                }
            }
        }
        return winnerExist;
    }


    //将存储的棋盘属性组化为字符串组，每一个元素是一个棋盘
    public List<String> recordLines(){
        List<String> theRecords=new ArrayList<>();
        for(int i=0;i<this.recordGame.size();i++){
            String s = "";
            for(int k=0;k<8;k++){
                for(int j=0;j<8;j++){
                    s=s.concat(String.valueOf(this.recordGame.get(i).getChessComponents()[k][j].getChessName()));
                }
                s=s.concat("\n");
            }
            if(this.recordGame.get(i).getCurrentColor()==ChessColor.WHITE){s=s.concat("w\n");}
            else{s=s.concat("b\n");}
            theRecords.add(s);
        }
        return theRecords;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    public void loadGame(ArrayList<String> chessData) {
        int count = 0;
        int much = chessData.size() / 9;

        for (int i = 0; i < 8; i++) {
            String Line = chessData.get((much - 1) * 9 + i);
            for (int j = 0; j < 8; j++) {
                remove(chessComponents[i][j]);
                ChessColor color = ChessColor.BLACK;
                ChessboardPoint source = new ChessboardPoint(i, j);
                if (Line.charAt(j) >= 97) {
                    color = ChessColor.WHITE;
                } else if (Line.charAt(j) == 95) {
                    color = ChessColor.NONE;
                }


                if (Line.charAt(j) == 95) {
                    chessComponents[i][j] = new EmptySlotComponent(source, calculatePoint(i, j), clickController, CHESS_SIZE);
                } else if (Line.charAt(j) == 'R' | Line.charAt(j) == 'r') {
                    chessComponents[i][j] = new RookChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (Line.charAt(j) == 'N' | Line.charAt(j) == 'n') {
                    chessComponents[i][j] = new KnightChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (Line.charAt(j) == 'Q' | Line.charAt(j) == 'q') {
                    chessComponents[i][j] = new QueenChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (Line.charAt(j) == 'K' | Line.charAt(j) == 'k') {
                    chessComponents[i][j] = new KingChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (Line.charAt(j) == 'P' | Line.charAt(j) == 'p') {
                    chessComponents[i][j] = new PawnChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (Line.charAt(j) == 'B' | Line.charAt(j) == 'b') {
                    chessComponents[i][j] = new BishopChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                }
                add(chessComponents[i][j]);
            }
        }

        if (chessData.get(chessData.size() - 1 ).charAt(0) == 'w') {
            currentColor = ChessColor.WHITE;
        } else {
            currentColor = ChessColor.BLACK;
            gameController.swapPlayer();
        }
        ChessColor playerColor = null;

        //遍历克隆
        count++;
        while (count != much) {
            ChessComponent[][] theChessComponents = new ChessComponent[8][8];
            for (int i = 0; i < 9; i++) {
                String Line = chessData.get(i + count*9);
                for (int j = 0; j < 8; j++) {
                    if(i == 8){
                        if ( chessData.get(i + count*9).charAt(0) == 'w') {
                            playerColor = ChessColor.WHITE;
                        } else {
                            playerColor = ChessColor.BLACK;
                        }
                        break;
                    }
                    ChessColor color = ChessColor.BLACK;
                    ChessboardPoint source = new ChessboardPoint(i, j);
                    if (Line.charAt(j) >= 97) {
                        color = ChessColor.WHITE;
                    } else if (Line.charAt(j) == 95) {
                        color = ChessColor.NONE;
                    }
                    if (Line.charAt(j) == 95) {
                        theChessComponents[i][j] = new EmptySlotComponent(source, calculatePoint(i, j), clickController, CHESS_SIZE);
                    } else if (Line.charAt(j) == 'R' | Line.charAt(j) == 'r') {
                        theChessComponents[i][j] = new RookChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (Line.charAt(j) == 'N' | Line.charAt(j) == 'n') {
                        theChessComponents[i][j] = new KnightChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (Line.charAt(j) == 'Q' | Line.charAt(j) == 'q') {
                        theChessComponents[i][j] = new QueenChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (Line.charAt(j) == 'K' | Line.charAt(j) == 'k') {
                        theChessComponents[i][j] = new KingChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (Line.charAt(j) == 'P' | Line.charAt(j) == 'p') {
                        theChessComponents[i][j] = new PawnChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    } else if (Line.charAt(j) == 'B' | Line.charAt(j) == 'b') {
                        theChessComponents[i][j] = new BishopChessComponent(source, calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    }

                }
            }

            
            //新建attribute
            BoardAttributes theBoardAttributes = new BoardAttributes(theChessComponents, playerColor);
            this.recordGame.add(theBoardAttributes);
            count++;
        }


    }

    public void AI(){
        if(isAI && !checkWin() ) {
            if (!isHard) {
                Random rand = new Random();
                int row = rand.nextInt(7);
                int col = rand.nextInt(7);
                int count = 0;
                movechess1:
                for (int i = row; i < 8; i++) {
                    for (int j = col; j < 8; j++) {
                        if (!(chessComponents[i][j] instanceof EmptySlotComponent) && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                            List<ChessboardPoint> canMovePoints = chessComponents[i][j].canMovePoints(chessComponents[i][j].getChessboardPoint(), chessComponents);
                            if (canMovePoints.size() != 0) {
                                int index = rand.nextInt(canMovePoints.size());
                                int x = canMovePoints.get(index).getX();
                                int y = canMovePoints.get(index).getY();
                                chessComponents[i][j].canBeMoved(chessComponents);
                                if (chessComponents[x][y].isCanBeMoved()) {
                                    swapChessComponents(chessComponents[i][j], chessComponents[x][y]);
                                    chessComponents[i][j].refresh(chessComponents);
                                    swapColor();
                                    gameController.swapPlayer();
                                    if(!win) {
                                        checkWinner();
                                    }
                                    repaint();
                                    recordChessboard(this);
                                    count++;
                                    break movechess1;
                                }
                            }
                        }
                    }
                }
                if (count == 0) {
                    movechess2:
                    for (int i = row; i >= 0; i--) {
                        for (int j = col; j >= 0; j--) {
                            if (!(chessComponents[i][j] instanceof EmptySlotComponent) && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                                List<ChessboardPoint> canMovePoints = chessComponents[i][j].canMovePoints(chessComponents[i][j].getChessboardPoint(), chessComponents);
                                if (canMovePoints.size() != 0) {
                                    int index = rand.nextInt(canMovePoints.size());
                                    int x = canMovePoints.get(index).getX();
                                    int y = canMovePoints.get(index).getY();
                                    chessComponents[i][j].canBeMoved(chessComponents);
                                    if (chessComponents[x][y].isCanBeMoved()) {
                                        swapChessComponents(chessComponents[i][j], chessComponents[x][y]);
                                        chessComponents[i][j].refresh(chessComponents);
                                        swapColor();
                                        gameController.swapPlayer();
                                        if(!win) {
                                            checkWinner();
                                        }
                                        repaint();
                                        recordChessboard(this);
                                        count++;
                                        break movechess2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(isHard){
                Random rand = new Random();
                int row = rand.nextInt(7);
                int col = rand.nextInt(7);
                int count = 0;
                movechess1:
                for (int i = row; i < 8; i++) {
                    for (int j = col; j < 8; j++) {
                        if (!(chessComponents[i][j] instanceof EmptySlotComponent) && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                            List<ChessboardPoint> canMovePoints = chessComponents[i][j].canMovePoints(chessComponents[i][j].getChessboardPoint(), chessComponents);
                            if (canMovePoints.size() != 0) {
                                for (int k = 0; k < canMovePoints.size(); k++) {
                                    int x = canMovePoints.get(k).getX();
                                    int y = canMovePoints.get(k).getY();
                                    if(chessComponents[x][y].getChessColor() == ChessColor.WHITE) {
                                        chessComponents[i][j].canBeMoved(chessComponents);
                                        if (chessComponents[x][y].isCanBeMoved()) {
                                            swapChessComponents(chessComponents[i][j], chessComponents[x][y]);
                                            chessComponents[i][j].refresh(chessComponents);
                                            swapColor();
                                            gameController.swapPlayer();
                                            if(!win) {
                                                checkWinner();
                                            }
                                            repaint();
                                            recordChessboard(this);
                                            count++;
                                            break movechess1;
                                        }
                                    }
                                }
                                int index = rand.nextInt(canMovePoints.size());
                                int x = canMovePoints.get(index).getX();
                                int y = canMovePoints.get(index).getY();
                                chessComponents[i][j].canBeMoved(chessComponents);
                                if (chessComponents[x][y].isCanBeMoved()) {
                                    swapChessComponents(chessComponents[i][j], chessComponents[x][y]);
                                    chessComponents[i][j].refresh(chessComponents);
                                    swapColor();
                                    gameController.swapPlayer();
                                    if(!win) {
                                        checkWinner();
                                    }
                                    repaint();
                                    recordChessboard(this);
                                    count++;
                                    break movechess1;
                                }
                            }
                        }
                    }
                }
                if (count == 0) {
                    movechess2:
                    for (int i = row; i >= 0; i--) {
                        for (int j = col; j >= 0; j--) {
                            if (!(chessComponents[i][j] instanceof EmptySlotComponent) && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                                List<ChessboardPoint> canMovePoints = chessComponents[i][j].canMovePoints(chessComponents[i][j].getChessboardPoint(), chessComponents);
                                if (canMovePoints.size() != 0) {
                                    for (int k = 0; k < canMovePoints.size(); k++) {
                                        int x = canMovePoints.get(k).getX();
                                        int y = canMovePoints.get(k).getY();
                                        if(chessComponents[x][y].getChessColor() == ChessColor.WHITE) {
                                            chessComponents[i][j].canBeMoved(chessComponents);
                                            if (chessComponents[x][y].isCanBeMoved()) {
                                                swapChessComponents(chessComponents[i][j], chessComponents[x][y]);
                                                chessComponents[i][j].refresh(chessComponents);
                                                swapColor();
                                                gameController.swapPlayer();
                                                if(!win) {
                                                    checkWinner();
                                                }
                                                repaint();
                                                recordChessboard(this);
                                                count++;
                                                break movechess2;
                                            }
                                        }
                                    }
                                    int index = rand.nextInt(canMovePoints.size());
                                    int x = canMovePoints.get(index).getX();
                                    int y = canMovePoints.get(index).getY();
                                    chessComponents[i][j].canBeMoved(chessComponents);
                                    if (chessComponents[x][y].isCanBeMoved()) {
                                        swapChessComponents(chessComponents[i][j], chessComponents[x][y]);
                                        chessComponents[i][j].refresh(chessComponents);
                                        swapColor();
                                        gameController.swapPlayer();
                                        if(!win) {
                                            checkWinner();
                                        }
                                        repaint();
                                        recordChessboard(this);
                                        count++;
                                        break movechess2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
