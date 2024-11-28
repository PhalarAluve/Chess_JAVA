package view;

import model.ChessColor;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private JLabel presentPlayerLabel;
    private JLabel label;
    private String PlayerW;
    private String PlayerB;

    public StatusPanel(int width,int height,String PlayerW, String PlayerB){
        this.setSize(width,height);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(null);
        this.setOpaque(true);

        this.PlayerW = PlayerW;
        this.PlayerB = PlayerB;

        this.presentPlayerLabel = new JLabel();
        this.presentPlayerLabel.setLocation(width* 23/28, height/10);
        this.presentPlayerLabel.setSize((int) (width*4/25), height*1/2);
        this.presentPlayerLabel.setFont(new Font("楷体", Font.BOLD, width/37));
        this.presentPlayerLabel.setForeground(Color.decode("#586AB4"));
        this.setPresentPlayerText(PlayerW);
        add(presentPlayerLabel);

    }

    public void setPresentPlayerText(String presentPlayerText){
        this.presentPlayerLabel.setText("<html><body>"+presentPlayerText + "<br>"+"'s turn"+"<body></html>");
    }

    public void setlablecolor(){
        this.presentPlayerLabel.setForeground(Color.white);
    }

    public void setText(String player){
        this.setPresentPlayerText(player);
    }

}
