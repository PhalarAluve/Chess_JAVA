package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


import controller.ClickController;
import controller.GameController;
import model.ChessColor;

public class PvEGameFrameHard extends JFrame {

    private final int WIDTH;
    private final int HEIGHT;
    public int ChessBoardSize;
    private ChessBoard chessBoard;
    private ChessColor CurrentColor;
    private GameController gameController;
    private StatusPanel statusPanel;
    private ClickController clickController;
    private boolean flag;
    private String PlayerW;
    private String PlayerB;
    private EntranceFrame entranceFrame;


    //    游戏窗口
    public PvEGameFrameHard(int width, int height, boolean flag, String PlayerW, String PlayerB, EntranceFrame entranceFrame) {
        setTitle("GAME");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ChessBoardSize = HEIGHT * 4/5;
        this.flag = flag;
        this.PlayerW = PlayerW;
        this.PlayerB = PlayerB;
        this.entranceFrame = entranceFrame;


        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        addBackBoard();


        //设置chessBoard


        //放一个game controller来实现game controller里的方法


        statusPanel=new StatusPanel(WIDTH,HEIGHT/2,PlayerW,PlayerB);
        statusPanel.setLocation(0,HEIGHT/3);
        statusPanel.setBackground(null);
        statusPanel.setOpaque(false);
        if(!flag){statusPanel.setlablecolor();}
        statusPanel.setVisible(true);
        this.getLayeredPane().add(statusPanel, new Integer(0));

        gameController=new GameController(chessBoard,statusPanel,PlayerW,PlayerB);
        clickController = new ClickController(chessBoard,statusPanel,gameController);
        addChessboard();

        chessBoard=(ChessBoard)getContentPane().getComponent(0);
        gameController.setStatusPanel(statusPanel);
        gameController.setChessboard(chessBoard);
        this.add(chessBoard);

        addButton_Change();
        addLable_Black();
        addLable_White();
        addButton_White();
        addButton_NewGame();
        addButton_Save();
        addBackBoard();
        addButton_Back();


    }


    //    棋盘
    private void addChessboard(){
        ChessBoard chessBoard = new ChessBoard(ChessBoardSize,ChessBoardSize,gameController,this, PlayerW, PlayerB);
        chessBoard.setLocation(WIDTH*  3/20,HEIGHT/10);
        this.getLayeredPane().add(chessBoard, new Integer(Integer.MAX_VALUE));
        add(chessBoard);
    }
    //    标签（黑方）
    private void addLable_Black(){
        if(flag) {
            JLabel labelBlcak = new JLabel("<html><body>"+"BLACK: "+"<br>"+ PlayerB+"<body></html>");
            labelBlcak.setLocation(WIDTH * 3 / 100, HEIGHT / 10);
            labelBlcak.setSize(WIDTH / 10, HEIGHT * 3 / 38);
            labelBlcak.setFont(new Font("黑体", Font.BOLD, WIDTH / 50));
            add(labelBlcak);
        }
        else{
            JLabel labelBlcak = new JLabel("<html><body>"+"BLACK: "+"<br>"+ PlayerB+"<body></html>");
            labelBlcak.setLocation(WIDTH * 3 / 100, HEIGHT / 10);
            labelBlcak.setSize(WIDTH / 10, HEIGHT * 3 / 38);
            labelBlcak.setFont(new Font("黑体", Font.BOLD, WIDTH / 50));
            labelBlcak.setForeground(Color.WHITE);
            add(labelBlcak);
        }
    }
    //    标签（白方）
    private void addLable_White(){
        if(flag) {
            JLabel labelWhite = new JLabel("<html><body>"+"WHITE: "+"<br>"+ PlayerW+"<body></html>");
            labelWhite.setLocation(WIDTH * 87 / 100, HEIGHT * 4 / 5);
            labelWhite.setSize(WIDTH / 10, HEIGHT * 3 / 38);
            labelWhite.setFont(new Font("黑体", Font.BOLD, WIDTH / 50));
            add(labelWhite);
        }
        else{
            JLabel labelWhite = new JLabel("<html><body>"+"WHITE: "+"<br>"+ PlayerW+"<body></html>");
            labelWhite.setLocation(WIDTH * 87 / 100, HEIGHT * 4 / 5);
            labelWhite.setSize(WIDTH / 10, HEIGHT * 3 / 38);
            labelWhite.setFont(new Font("黑体", Font.BOLD, WIDTH / 50));
            labelWhite.setForeground(Color.WHITE);
            add(labelWhite);
        }
    }

    //    悔棋按钮（白方）
    private void addButton_White(){
        if(flag) {
            JButton buttonWhite = new JButton("悔棋");
            buttonWhite.addActionListener((e) -> {
                if(chessBoard.getCurrentColor() == ChessColor.WHITE) {
                    chessBoard.Retract2();
                    chessBoard.repaint();
                }
            });
            buttonWhite.setLocation(WIDTH * 87 / 100, HEIGHT * 269 / 380);
            buttonWhite.setSize(WIDTH * 2 / 25, HEIGHT * 3 / 38);
            buttonWhite.setFont(new Font("楷体", Font.BOLD, WIDTH * 11 / 500));
            add(buttonWhite);
        }
        else{
            JButton buttonWhite = new JButton("悔棋");
            buttonWhite.addActionListener((e) -> {
                if(chessBoard.getCurrentColor() == ChessColor.WHITE) {
                    chessBoard.Retract2();
                    chessBoard.repaint();
                }
            });
            buttonWhite.setLocation(WIDTH * 87 / 100, HEIGHT * 269 / 380);
            buttonWhite.setSize(WIDTH * 2 / 25, HEIGHT * 3 / 38);
            buttonWhite.setFont(new Font("楷体", Font.BOLD, WIDTH * 11 / 500));
            buttonWhite.setBackground(Color.white);
            add(buttonWhite);
        }
    }
    //    新游戏按钮
    private void addButton_NewGame(){
        if(flag) {
            JButton buttonNewGame = new JButton("RESTART");
            buttonNewGame.addActionListener((e) -> {
                dispose();
                String Player = JOptionPane.showInputDialog(null, "请输入 玩家 的用户名：", "用户属性", JOptionPane.WARNING_MESSAGE);
                String AI = "Computer";
                new PvEGameFrameHard(WIDTH, HEIGHT,true,PlayerW,PlayerB,entranceFrame).setVisible(true);
            });
            buttonNewGame.setLocation(WIDTH * 21 / 25, HEIGHT * 1 / 10);
            buttonNewGame.setSize(HEIGHT * 4 / 25, HEIGHT * 3 / 38);
            buttonNewGame.setFont(new Font("Rockwell", Font.BOLD, WIDTH * 9 / 500));
            add(buttonNewGame);
        }
        else{
            JButton buttonNewGame = new JButton("RESTART");
            buttonNewGame.addActionListener((e) -> {
                dispose();
                String Player = JOptionPane.showInputDialog(null, "请输入 玩家 的用户名：", "用户属性", JOptionPane.WARNING_MESSAGE);
                String AI = "Computer";
                new PvEGameFrameHard(WIDTH, HEIGHT,true,PlayerW,PlayerB,entranceFrame).setVisible(true);
            });
            buttonNewGame.setLocation(WIDTH * 21 / 25, HEIGHT * 1 / 10);
            buttonNewGame.setSize(HEIGHT * 4 / 25, HEIGHT * 3 / 38);
            buttonNewGame.setFont(new Font("Rockwell", Font.BOLD, WIDTH * 9 / 500));
            buttonNewGame.setBackground(Color.white);
            add(buttonNewGame);
        }
    }
    //    存档按钮
    private void addButton_Save(){
        JFrame jf =this;
        if(flag) {
            JButton buttonSave = new JButton("SAVE");
            buttonSave.setLocation(WIDTH * 21 / 25, HEIGHT * 1 / 5);
            buttonSave.setSize(HEIGHT * 4 / 25, HEIGHT * 3 / 38);
            buttonSave.setFont(new Font("Rockwell", Font.BOLD, WIDTH * 9 / 500));
            buttonSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showFileSaveDialog(jf);
                }
            });
            add(buttonSave);
        }
        else{
            JButton buttonSave = new JButton("SAVE");
            buttonSave.setLocation(WIDTH * 21 / 25, HEIGHT * 1 / 5);
            buttonSave.setSize(HEIGHT * 4 / 25, HEIGHT * 3 / 38);
            buttonSave.setFont(new Font("Rockwell", Font.BOLD, WIDTH * 9 / 500));
            buttonSave.setBackground(Color.white);
            buttonSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {showFileSaveDialog(jf);}
            });
            add(buttonSave);
        }
    }
    //      背景
    private void addBackBoard(){
        if(flag) {
            ImageIcon board = new ImageIcon("images/board.png");
            board.setImage(board.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(board);
            label.setBounds(117, 1, 760, 760);
            label.setVisible(true);
            JPanel imagePanel = (JPanel) this.getContentPane();
            imagePanel.setOpaque(false);
            this.getLayeredPane().setLayout(null);
            this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        }
        else{
            ImageIcon board = new ImageIcon("images/board-2.png");
            board.setImage(board.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(board);
            label.setBounds(0, 0, 1000, 760);
            label.setVisible(true);
            JPanel imagePanel = (JPanel) this.getContentPane();
            imagePanel.setOpaque(false);
            this.getLayeredPane().setLayout(null);
            this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        }
    }
    //    切换主题皮肤
    private void addButton_Change(){
        if(flag) {
            JButton buttonSave = new JButton("换皮肤");
            buttonSave.addActionListener((e) -> {
                dispose();
                PvEGameFrameHard newFrame = new PvEGameFrameHard(WIDTH, HEIGHT,false,PlayerW,PlayerB,entranceFrame);
                newFrame.setVisible(true);
            });
            buttonSave.setLocation(WIDTH * 21 / 25, HEIGHT * 4 / 13);
            buttonSave.setSize(HEIGHT * 4 / 25, HEIGHT * 3 / 38);
            buttonSave.setFont(new Font("宋体", Font.BOLD, WIDTH * 9 / 500));
            add(buttonSave);
        }
        else{
            JButton buttonSave = new JButton("换皮肤");
            buttonSave.addActionListener((e) -> {
                dispose();
                PvEGameFrameHard newFrame = new PvEGameFrameHard(WIDTH, HEIGHT,true,PlayerW,PlayerB,entranceFrame);
                newFrame.setVisible(true);

            });
            buttonSave.setLocation(WIDTH * 21 / 25, HEIGHT * 2 / 7);
            buttonSave.setSize(HEIGHT * 4 / 25, HEIGHT * 3 / 38);
            buttonSave.setFont(new Font("宋体", Font.BOLD, WIDTH * 9 / 500));
            buttonSave.setBackground(Color.white);
            add(buttonSave);
        }
    }

    //    返回主菜单
    private void addButton_Back(){
        if(flag) {
            JButton buttonBack = new JButton("Back");
            buttonBack.addActionListener((e) -> {
                dispose();
                entranceFrame.setVisible(true);
            });
            buttonBack.setLocation(10, 3);
            buttonBack.setSize(80, 40);
            buttonBack.setFont(new Font("Rockwell", Font.BOLD, 15));
            add(buttonBack);
        }
        else{
            JButton buttonBack = new JButton("Back");
            buttonBack.addActionListener((e) -> {
                dispose();
                entranceFrame.setVisible(true);
            });
            buttonBack.setLocation(10, 3);
            buttonBack.setSize(80, 40);
            buttonBack.setFont(new Font("Rockwell", Font.BOLD, 15));
            buttonBack.setBackground(Color.white);
            add(buttonBack);
        }
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public void setStatusPanel(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;
    }

    public void setNewFrame(){
        new EntranceFrame(1000,760).setVisible(true);
    }

    public void writeFileByFileWriter(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            List<String> lines = this.chessBoard.recordLines();
            for (String line : lines
            ) {
                writer.write(line);
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showFileSaveDialog(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setSelectedFile(new File("chessboard_1.txt"));
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.writeFileByFileWriter(file.getAbsolutePath());
        }
    }

    public List<String> readFileByFileReader(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            List<String> readLines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                readLines.add(line);
            }
            reader.close();
            fileReader.close();
            return readLines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showFileOpenDialog(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.readFileByFileReader(file.getAbsolutePath());
        }
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }
}
