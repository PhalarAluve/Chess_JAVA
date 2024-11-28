package view;
import controller.GameController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntranceFrame extends JFrame{
    private final int WIDTH;
    private final int HEIGHT;
    private GameController gameController;

    public EntranceFrame(int width,int height){
        setTitle("Welcome to CHESS");
        this.WIDTH = width;
        this.HEIGHT = height;


        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        this.getContentPane().setBackground(Color.WHITE);

        addTitle();
        addNewGameButton();
        addLoadButton();
        addExitButton();
        addBackBoard();


        addComponentListener(new ComponentAdapter() {//拖动窗口监听
            public void componentResized(ComponentEvent e) {
                int WIDTH=getWidth();
                int HEIGHT=getHeight();
                getLayeredPane().getComponent(0).setBounds(0,0,WIDTH,HEIGHT);
                getContentPane().getComponent(0).setBounds(WIDTH*13/100,HEIGHT*5/76,WIDTH*3/4,HEIGHT*15/76);
                getContentPane().getComponent(1).setBounds(WIDTH*19/50, HEIGHT *63/ 152,WIDTH/5, HEIGHT *3/ 38);
                getContentPane().getComponent(1).setFont(new Font("Rockwell", Font.BOLD, WIDTH/50));
                getContentPane().getComponent(2).setBounds(WIDTH*19/50, HEIGHT *87/ 152,WIDTH/5, HEIGHT *3/ 38);
                getContentPane().getComponent(2).setFont(new Font("Rockwell", Font.BOLD, WIDTH/50));
                getContentPane().getComponent(3).setBounds(WIDTH*19/50, HEIGHT *111/ 152,WIDTH/5, HEIGHT *3/ 38);
                getContentPane().getComponent(3).setFont(new Font("Rockwell", Font.BOLD, WIDTH/50));
                getContentPane().getComponent(4).setBounds(0,0,WIDTH,HEIGHT);

            }

        });

    }

    private void addTitle(){
        ImageIcon icon=new ImageIcon("images/title.png");//创建背景图片
        icon.setImage(icon.getImage().getScaledInstance(WIDTH*3/4, HEIGHT*15/76,Image.SCALE_SMOOTH ));
        JLabel lb=new JLabel(icon);//创建一个标签对象
        lb.setSize(WIDTH*3/4,HEIGHT*15/76);
        lb.setLocation(WIDTH*13/100,HEIGHT*5/76);
        getContentPane().add(lb);
    }

    private void addNewGameButton(){
        JButton button = new JButton("New Game");
//        ActionListener,简写的方法
        button.addActionListener((e) -> {
            String [] options = {"人机对战","双人对战"};
            int n =  JOptionPane.showOptionDialog(null,"请选择游戏模式：","游戏模式",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,null);
            if(n == 0){
                String [] options2 = {"简单模式","困难模式"};
                int n2 =  JOptionPane.showOptionDialog(null,"请选择PvE游戏模式：","PvE模式",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options2,null);
                if(n2 == 0) {
                    String Player = JOptionPane.showInputDialog(null, "请输入 玩家 的用户名：", "用户属性", JOptionPane.WARNING_MESSAGE);
                    String AI = "Computer";
                    PvEGameFrameEasy pvEGameFrameEasy = new PvEGameFrameEasy(1000, 760, true, Player, AI, this);
                    this.setVisible(false);
                    pvEGameFrameEasy.setVisible(true);
                }
                if(n2 == 1){
                    String Player = JOptionPane.showInputDialog(null, "请输入 玩家 的用户名：", "用户属性", JOptionPane.WARNING_MESSAGE);
                    String AI = "Computer";
                    PvEGameFrameHard pvEGameFrameHard = new PvEGameFrameHard(1000, 760, true, Player, AI, this);
                    this.setVisible(false);
                    pvEGameFrameHard.setVisible(true);

                }
            }

            if(n == 1){
                String PlayerW = JOptionPane.showInputDialog(null,"请输入 白棋 玩家的用户名：","用户属性",JOptionPane.WARNING_MESSAGE);
                String PlayerB = JOptionPane.showInputDialog(null,"请输入 黑棋 玩家的用户名：","用户属性",JOptionPane.WARNING_MESSAGE);
                ChessGameFrame chessGameFrame = new ChessGameFrame(1000, 760,true,PlayerW,PlayerB,this);
                this.setVisible(false);
                chessGameFrame.setVisible(true);
            }
            ;});
        button.setLocation(WIDTH*19/50, HEIGHT *63/ 152);
        button.setSize(WIDTH/5, HEIGHT *3/ 38);
        button.setFont(new Font("Rockwell", Font.BOLD, WIDTH/50));
        getContentPane().add(button);
    }

    private void addLoadButton(){
        JFrame jf=this;
        JButton button = new JButton("Load Game");
        button.addActionListener((e) -> {
            ArrayList<String> theBoard=showFileOpenDialog(jf);
//            格式错误
//            if(theBoard.get(0).equals("无事发生")){
//                ;
//            }
            if(theBoard.size()==0){
                JOptionPane.showMessageDialog(null,"文件格式错误 \n错误编码：104","Error",JOptionPane.WARNING_MESSAGE);
            }
//            缺少行棋方
            else if(theBoard.get(theBoard.size()-1).length()!=1||(theBoard.get(theBoard.size()-1).charAt(0)!='w'&&theBoard.get(theBoard.size()-1).charAt(0)!='b')){
                JOptionPane.showMessageDialog(null,"缺少行棋方 \n错误编码：103","Error",JOptionPane.WARNING_MESSAGE);
            }
//           棋盘错误
            else if(theBoard.size()%9 != 0 ||boardError(theBoard)){
                JOptionPane.showMessageDialog(null,"棋盘错误 \n错误编码：101","Error",JOptionPane.WARNING_MESSAGE);
            }
//            棋子错误
            else if(chessError(theBoard)){
                JOptionPane.showMessageDialog(null,"棋子错误 \n错误编码：102","Error",JOptionPane.WARNING_MESSAGE);
            }
            else {
                String PlayerW = JOptionPane.showInputDialog(null, "请输入 白棋 玩家的用户名：", "用户属性", JOptionPane.WARNING_MESSAGE);
                String PlayerB = JOptionPane.showInputDialog(null, "请输入 黑棋 玩家的用户名：", "用户属性", JOptionPane.WARNING_MESSAGE);
                ChessGameFrame chessGameFrame = new ChessGameFrame(1000, 760, true, PlayerW, PlayerB,this);
                chessGameFrame.getChessBoard().loadGame(theBoard);
                this.setVisible(false);
                chessGameFrame.setVisible(true);
            }
            ;});
        button.setLocation(WIDTH*19/50, HEIGHT *87/ 152);
        button.setSize(WIDTH/5, HEIGHT *3/ 38);
        button.setFont(new Font("Rockwell", Font.BOLD, WIDTH/50));
        getContentPane().add(button);
    }

    private void addExitButton(){
        JButton button = new JButton("Exits");
        button.setLocation(WIDTH*19/50, HEIGHT *111/ 152);
        button.setSize(WIDTH/5, HEIGHT *3/ 38);
        button.setFont(new Font("Rockwell", Font.BOLD, WIDTH/50));
        button.addActionListener((e) -> System.exit(0));
        getContentPane().add(button);
    }

    private void addBackBoard(){
        ImageIcon board=new ImageIcon("images/backboard.png");
        board.setImage(board.getImage().getScaledInstance(WIDTH, HEIGHT,Image.SCALE_SMOOTH ));
        JLabel label=new JLabel(board);
        label.setSize(WIDTH, HEIGHT);
        label.setLocation(0,0);
        label.setVisible(true);
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        getContentPane().add(label);
//        getContentPane().add(imagePanel);
    }

    public static ArrayList<String> readFileByFileReader(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            ArrayList<String> readLines = new ArrayList<>();
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

    private ArrayList<String> showFileOpenDialog(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);

        int result = fileChooser.showOpenDialog(parent);
        ArrayList<String> theBoard = new ArrayList<>();
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if(file.getAbsolutePath().charAt(file.getAbsolutePath().length()-1)!='t'||file.getAbsolutePath().charAt(file.getAbsolutePath().length()-2)!='x'||file.getAbsolutePath().charAt(file.getAbsolutePath().length()-3)!='t'){
                theBoard = new ArrayList<>();
            }
            else {
                theBoard = readFileByFileReader(file.getAbsolutePath());
            }
        }
        return theBoard;
    }

    public static boolean chessError(ArrayList<String> theBoard){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                char c=theBoard.get(i).charAt(j);
                if(!(c=='K'||c=='k'||c=='Q'||c=='q'||c=='R'||c=='r'||c=='B'||c=='b'||c=='N'||c=='n'||c=='P'||c=='p'||c=='_')){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean boardError(ArrayList<String> theBoard){
        for(int i=0;i<8;i++){
            if(theBoard.get(i).length()!=8){
                return true;
            }
        }
        return false;
    }
}
