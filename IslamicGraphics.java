package project;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class IslamicGraphics extends JFrame { 
    JButton JB1 = new JButton("سجل دخول");
    Login log = new Login(); 

    public IslamicGraphics() {
        setLayout(new BorderLayout());
        
        MainPanel mainPanel = new MainPanel();
        mainPanel.setLayout(new GridBagLayout()); 
        
        JB1.setFont(new Font("Arial", Font.BOLD, 20));
        JB1.setPreferredSize(new Dimension(150, 40));
        mainPanel.add(JB1);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JB1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.setVisible(true);
               dispose();
            }
        });
        
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    
    class MainApplication extends JFrame {
        public MainApplication() {
            Project my=new Project();
            my.setVisible(true);
            
            JLabel welcomeLabel = new JLabel("مرحباً بك في برنامج الأذكار", JLabel.CENTER);
            welcomeLabel.setFont(new Font("Taditional Arabic", Font.BOLD, 36));
            add(welcomeLabel);
        }
    }
    
    class MainPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            GradientPaint gradient = new GradientPaint(0, 0, new Color(1, 50, 32), 
                getWidth(), getHeight(), new Color(0, 100, 0));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            g2d.setColor(new Color(200, 200, 200, 150));
            int[] mosqueX = {300, 400, 500, 450, 350};
            int[] mosqueY = {400, 300, 400, 450, 450};
            g2d.fillPolygon(mosqueX, mosqueY, 5);
            
            g2d.setColor(Color.WHITE);
            g2d.fill(new Ellipse2D.Double(600, 100, 80, 80));
            g2d.setColor(new Color(1, 50, 32));
            g2d.fill(new Ellipse2D.Double(610, 100, 70, 80));
            
            g2d.setColor(Color.WHITE);
            Font arabicFont = new Font("Traditional Arabic", Font.BOLD, 48);
            g2d.setFont(arabicFont);
            
            String Text = "برنامج اذكار";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(Text)) / 2;
            int y = 100;
            
            g2d.drawString(Text, x, y);
            
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(new Color(218, 165, 32));
            g2d.drawRoundRect(50, 50, getWidth() - 100, getHeight() - 100, 20, 20);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IslamicGraphics app = new IslamicGraphics();
            app.setVisible(true);
        });
    }
}