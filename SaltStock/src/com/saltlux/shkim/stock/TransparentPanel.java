package com.saltlux.shkim.stock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.Timer;
import java.util.TimerTask;
 
public class TransparentPanel extends JDialog {
    // public static void main(String[] args) throws IOException {
    public static JFrame frame = new JFrame("Hello");
    public List<JLabel> jLabelList = new ArrayList<JLabel>();

 
    public void initial() {
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setType(javax.swing.JFrame.Type.UTILITY);
        frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
        frame.setLocation(0, 0); // 1620 780
        frame.setSize(1920, 1080);
        frame.getContentPane().setLayout(new java.awt.BorderLayout(0, 0));
        frame.setBackground(new Color(0, 0, 0, 0));
    }
 
    public void drawImage(JFrame frame, String fileName, int x_coor, int y_coor, int x_size, int y_size) {
        ImageIcon myPicture0 = new ImageIcon(fileName);
        Image image0 = myPicture0.getImage();
        Image resizedImage0 = image0.getScaledInstance(x_size, y_size, Image.SCALE_DEFAULT);
        ImageIcon resizedMypic = new ImageIcon(resizedImage0);
        JLabel pitureLabel0 = new JLabel(resizedMypic);
        pitureLabel0.setBorder(BorderFactory.createEmptyBorder(y_coor, x_coor, 0, 0));
        frame.getContentPane().add(pitureLabel0, 0);
        frame.setVisible(true);
    }
 
    public void drawString(JFrame frame, String sentence, int size, int y_coord, int x_coord, float h, float s,
            float b) {
        JLabel key = new JLabel();
        key.setText(sentence);
        Font myFont = new Font("NanumBarunGothic", Font.BOLD, size);
        key.setFont(myFont);
        key.setForeground(Color.getHSBColor(h, s, b));
        key.setBorder(BorderFactory.createEmptyBorder(y_coord, x_coord, 0, 0));
        frame.getContentPane().add(key, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
 
    public static void main(String[] args) throws IOException {
        TransparentPanel ryan = new TransparentPanel();
        ryan.initial();
        
        Stock stock = new Stock();
        
        Timer timer = new Timer();
        List<String> happyLyan = new ArrayList<String>();
        List<String> sadLyan = new ArrayList<String>();
        
        happyLyan.add("res/happy1.gif");
        happyLyan.add("res/happy2.gif");
        sadLyan.add("res/sad1.gif");
        sadLyan.add("res/sad2.gif");
        
        
        TimerTask timerTask = new TimerTask() {
        	@Override
        	public void run() {
        		String result;
				try {
					result = stock.getJsonFromUrl();
					stock.updateValues(result);
	        		int contentNum = frame.getContentPane().getComponentCount();
	        		for(int i = 0; i < contentNum; i++) {
	        			frame.getContentPane().remove(0);
	        		}
	        		int subNum = Integer.parseInt(stock.startValue) - Integer.parseInt(stock.currentValue);
	        		boolean isDduckSang = false;
	        		if(subNum < 0) {
	        			isDduckSang = true;
	        		}
	        		float percent = ((float)Math.abs(subNum) / Float.parseFloat(stock.startValue)) * 100;
	        		percent = Math.round(percent * 100 / 100.0);
	        		StringBuilder stockString = new StringBuilder();
	        		if(isDduckSang) {
	        			stockString.append("¡ã ");
	        			stockString.append(String.valueOf(subNum) + " (");
	        			stockString.append(String.valueOf(percent) + "%)");
	        		} else {
	        			stockString.append("¡å ");
	        			stockString.append(String.valueOf(subNum) + " (");
	        			stockString.append(String.valueOf(percent) + "%)");
	        		}
	        		float h = 0;
	        		float s = 1;
	        		float b = 1;
	        		String ryanFile = "";
	        		int randomNum = getRandomNumber(0, 2);
	        		if(isDduckSang) {
	        			h = 0;
	        			s = 1;
	        			b = 1;
	        			ryanFile = happyLyan.get(randomNum);
	        			
	        		} else {
	        			h = (float) 0.6;
	        			s = 1;
	        			b = 1;
	        			ryanFile = sadLyan.get(randomNum);
	        		}
	        		ryan.drawImage(ryan.frame, ryanFile, 1700, 800, 200, 200);
	        		ryan.drawImage(ryan.frame, "res/SpeechBubble.png", 1300, 800, 300, 200);
	        		ryan.drawString(ryan.frame, stock.currentValue, 40, 680, 1530, h, s, b);
	        		ryan.drawString(ryan.frame, stockString.toString(), 20, 750, 1540, h, s, b);
	        		Thread.sleep(5000);
	        		contentNum = frame.getContentPane().getComponentCount();
					for(int i = 0; i < contentNum-1; i++) {
						frame.getContentPane().remove(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					int contentNum = frame.getContentPane().getComponentCount();
	        		for(int i = 0; i < contentNum; i++) {
	        			frame.getContentPane().remove(0);
	        		}
	        		ryan.drawString(ryan.frame, "ERROR!", 40, 670, 1530, 0, 1, 1);
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}
        };
        timer.schedule(timerTask, 0, 1000 * 60 * 5);	// reset 5 minutes
    }
}