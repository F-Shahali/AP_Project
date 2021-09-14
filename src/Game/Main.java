package Game;
import java.awt.event.*;
import javax.swing.*;

import Client_Server.*;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;


public class Main {
	public static JFrame frame;
	static String IP, Port, name;
	static boolean IsServer = false, IsClient = false, BuildServerOrClient = false;
	static Clip clip;
	
	public static void main(String[] args) {
		BaseMenu m = new BaseMenu();
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setVisible(true);
		frame.pack();
		frame.setContentPane(m);
		BufferedImage img = null;
		try {
			 img = ImageIO.read(new File("file/cursor1.png"));
		} catch (IOException e) {}
		frame.setIconImage(img);
		try {
			music("file/menu.wav");
		} catch (Exception e) {}
		frame.repaint();
	}
	public static void music(String music) throws  Exception {
        clip = AudioSystem.getClip();
        // getAudioInputStream() also accepts a File or InputStream
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(music));
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
            	if (event.getType().equals(LineEvent.Type.STOP))
                {
                    Line soundClip = event.getLine();
                    soundClip.close();
                }
            }
        });
	}
	

}
