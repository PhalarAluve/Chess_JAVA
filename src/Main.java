import view.Bgm;
import view.ChessGameFrame;
import view.EntranceFrame;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {
    public static void main(String[] args) {
        String filepath="bgm/bgm.WAV";
        Bgm bgmObject = new Bgm();
        bgmObject.playMusic(filepath);

        SwingUtilities.invokeLater(() -> {
            EntranceFrame entranceFrame = new EntranceFrame(1000,760);
            entranceFrame.setVisible(true);
        });
    }
}
