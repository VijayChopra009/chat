
package chatting_app;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.border.EmptyBorder;

public class Server  implements ActionListener{ 
    JTextField text;
    JPanel textarea;
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    static Box vertical= Box.createVerticalBox();
        public Server(){
            f.setLayout(null);
            JPanel p1= new JPanel();
            p1.setBackground(new Color(7,94,84));
            p1.setBounds(0,0,400,70);
            p1.setLayout(null);
            f.add(p1);
            
            //exit arrow image
            ImageIcon img1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
            Image img2= img1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
            ImageIcon img3 =new ImageIcon(img2);
            JLabel back= new JLabel(img3);
            back.setBounds(5,20,22,22);
            p1.add(back);
            back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }});
            
            ImageIcon img4= new ImageIcon(ClassLoader.getSystemResource("icons/tonystrak.jpg"));
            Image img5= img4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
            ImageIcon img6 =new ImageIcon(img5);
            JLabel profile= new JLabel(img6);
            profile.setBounds(35,10,50,50);
            p1.add(profile);
            
             ImageIcon img7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
            Image img8= img7.getImage().getScaledInstance(50,35,Image.SCALE_DEFAULT);
            ImageIcon img9 =new ImageIcon(img8);
            JLabel video= new JLabel(img9);
            video.setBounds(270,20,28,28);
            p1.add(video);
            
            ImageIcon img10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
            Image img11= img10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
            ImageIcon img12 =new ImageIcon(img11);
            JLabel phone= new JLabel(img12);
            phone.setBounds(320,22,30,27);
            p1.add(phone);
            
            ImageIcon img13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
            Image img14= img13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
            ImageIcon img15 =new ImageIcon(img14);
            JLabel morevert= new JLabel(img15);
            morevert.setBounds(380,22,10,25);
            p1.add(morevert);
            
            
            JLabel name= new JLabel("Tony Stark");
            name.setBounds(110,15,100,18);
            name.setForeground(Color.WHITE);
            name.setFont(new Font("SAN_SERIF",Font.BOLD,16));
            p1.add(name);
            
            JLabel status= new JLabel("Active Now");
            status.setBounds(110,35,425,18);
            status.setForeground(Color.WHITE);
            status.setFont(new Font("SAN_SERIF",Font.BOLD,12));
            p1.add(status);
            
                
            textarea= new JPanel();
            textarea.setBounds(5,75,390,500);
            f.add(textarea);
            
            text= new JTextField();
            text.setBounds(5,580,280,40);
            text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
            f.add(text);
            
            
            JButton send=new JButton("Send");
            send.setBounds(290,580,100,40);
            send.setBackground(new Color(7,94,84));
            send.setForeground(Color.WHITE);
            send.addActionListener(this);
            send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
            f.add(send);
            
            f.setUndecorated(true);
            f.setSize(400,620);
            f.setLocation(150,40);
            f.getContentPane().setBackground(Color.white);
            
            f.setVisible(true);
        }
        
        public void actionPerformed(ActionEvent ae){
            try{
           String out= text.getText();
           JPanel p2 = formaLabel(out);
    
            textarea.setLayout(new BorderLayout());
            JPanel right= new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            textarea.add(vertical,BorderLayout.PAGE_START);
            dout.writeUTF(out);
            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
            
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        public static JPanel formaLabel(String out){
            JPanel panel= new JPanel();
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
            JLabel output= new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
            output.setFont(new Font("Tahoma",Font.PLAIN,16));
            output.setBackground(new Color(37,211,102));
            output.setOpaque(true);
            output.setBorder(new EmptyBorder(12,15,12,15));
            panel.add(output);
            
            Calendar cal= Calendar.getInstance();
            SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
            JLabel time= new JLabel();
            time.setText(sdf.format(cal.getTime()));
            panel.add(time);
            
            return panel;
        }
            
      public static void main(String [] args){
          new Server();
          
          try {
              ServerSocket skt= new  ServerSocket(6001);
              while(true){
                  Socket s=skt.accept();
                  DataInputStream din= new DataInputStream(s.getInputStream());
                  dout= new DataOutputStream(s.getOutputStream());
                  
                  while(true){
                      String msg =din.readUTF();
                      JPanel panel=formaLabel(msg);
                      
                      JPanel left= new JPanel(new BorderLayout());
                      left.add(panel,BorderLayout.LINE_START);
                      vertical.add(left);
                      
                      f.validate();
                      
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
}
