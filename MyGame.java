package tictactoe;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener{
    
    JLabel heading,clockLabel;
    Font font=new Font("",Font.PLAIN,40);
    JPanel mainPanel;
    JButton []btns=new JButton[9];
    
    //game instance variable
    int gameChances[]={2,2,2,2,2,2,2,2,2};
    int activePlayer=0;
    int wps[][]={{0,1,2},
                 {3,4,5},
                 {6,7,8},
                 {0,3,6},
                 {1,4,7},
                 {2,5,8},
                 {0,4,8},
                 {2,4,6}
                };  
    int winner=2;
    boolean gameOver=false;
    
    MyGame(){
      System.out.println("Creating instance");
      setTitle("Tic Tac Toe Game...");
      setSize(850,700);
      ImageIcon icon=new ImageIcon("src/img/ticTac.jpeg");
      setIconImage(icon.getImage());
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      createGUI();
      setVisible(true);
    }
    
    private void createGUI()
    {
        //FOR TITLE
        this.getContentPane().setBackground(Color.BLUE);
        this.setLayout(new BorderLayout());
        heading=new JLabel("Tic Tac Toe");
        //heading.setIcon(new ImageIcon("src/img/ticTac/jpeg"));
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.WHITE);
        this.add(heading,BorderLayout.NORTH);
        
        //FOR CLOCK
        clockLabel=new JLabel("Clock");
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setForeground(Color.WHITE);
        this.add(clockLabel,BorderLayout.SOUTH);
        
        //LIVE CLOCK USING THREAD
        Thread t=new Thread()
        {
            public void run()
            {
                try
                {
                    while(true)
                    {
                        String datetime=new Date().toLocaleString();
                        clockLabel.setText(datetime);
                        Thread.sleep(1000);
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        
        //CREATING PANEL...
        
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));
        for(int i=1; i<=9; i++)
        {
            JButton btn=new JButton();
            btn.setFont(font);
            mainPanel.add(btn);
            btns[i-1]=btn;
            btn.addActionListener(this);
            btn.setName(i-1+"");
        }
        
        this.add(mainPanel,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton=(JButton)e.getSource();
        String nameStr=currentButton.getName();
        int name=Integer.parseInt(nameStr.trim());
        
        if(gameOver)
        {
            JOptionPane.showMessageDialog(this,"Game Already Over...");
            return;
        }
        
        if(gameChances[name]==2)
        {
            if(activePlayer==1)
            {
                currentButton.setIcon(new ImageIcon("src/img/1.png"));
                gameChances[name]=activePlayer;
                activePlayer=0;
            }
            else
            {
                currentButton.setIcon(new ImageIcon("src/img/0.png"));
                gameChances[name]=activePlayer;
                activePlayer=1;
            }
            for(int []temp : wps)
            {
                if((gameChances[temp[0]]==gameChances[temp[1]])&&(gameChances[temp[1]]==gameChances[temp[2]])&&(gameChances[temp[2]]!=2))
                {
                    winner=gameChances[temp[0]];
                    JOptionPane.showMessageDialog(null,"Player "+winner+" has won");
                    int i=JOptionPane.showConfirmDialog(this, "do you want to play more?");
                    if(i==0)
                    {
                        this.setVisible(false);
                        new MyGame();
                    }
                    else if(i==1)
                    {
                        System.exit(123);
                    }
                    else
                    {
                        
                    }
                    System.out.println(i);
                    break;
                }
            }
            
            //DRAW LOGIC
            int c=0;
            for(int x:gameChances)
            {
                if(x==2)
                {
                    c++;
                    break;
                }
            }
            if(c==0&&gameOver==false)
            {
                JOptionPane.showMessageDialog(null, "Its draw...");
                int i=JOptionPane.showConfirmDialog(this,"Play more?");
                if(i==0)
                {
                    this.setVisible(false);
                    new MyGame();
                }
                else if(i==1)
                    System.exit(123);
                else
                {
                    
                }
                gameOver=true;
            }
                
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Position already occupied...");
        }
        //System.out.println(nameStr);
    }
}
