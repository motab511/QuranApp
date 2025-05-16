package project;
import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import project.Login;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Project extends JFrame {
    JMenuBar bar = new JMenuBar();
    Icon pic = new ImageIcon(getClass().getResource("p.png"));
    JMenuItem Q = new JMenuItem("القران الكريم", pic);
    Icon h = new ImageIcon(getClass().getResource("hadith.png"));
    Icon m = new ImageIcon(getClass().getResource("muteb.png"));
    Icon log = new ImageIcon(getClass().getResource("loginpic.png"));
    Icon p = new ImageIcon(getClass().getResource("4.png"));
    Icon back = new ImageIcon(getClass().getResource("background.png"));
    Icon rat = new ImageIcon(getClass().getResource("rating.png"));

    JMenuItem hadith = new JMenuItem("الحديث النبوي", h);
    JMenu muteb = new JMenu("مطورالبرنامج");
    JLabel background = new JLabel(back);
    JMenuItem mu=new JMenuItem("موقع متعب العتيبي", m);
  
   

    public Project() {
        JMenu booksMenu = new JMenu("الكتب");
        Font font = new Font("Traditional Arabic", Font.PLAIN, 22);
        
        booksMenu.setFont(font);
        setJMenuBar(bar);

        Q.setFont(font);
        hadith.setFont(font);
        muteb.setFont(font);
        booksMenu.add(Q);
        booksMenu.add(hadith);
        bar.add(booksMenu);
        bar.add(muteb);
        Q.addActionListener((e) -> {QuranApp Quran=new QuranApp();
            Quran.setVisible(true);
            
         dispose();
        Toolkit kit=Toolkit.getDefaultToolkit();

    Quran.setSize(kit.getScreenSize().width, kit.getScreenSize().height);


    Quran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
        muteb.add(mu);
       mu.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt) {
try {
    try {
        Desktop.getDesktop().browse(new URI("https://mutebalotaibi.me"));
    } catch (IOException ex) {
JOptionPane.showMessageDialog(rootPane, ex);    }
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(Project.this, ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }        } 
});
        setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);
        background.setLayout(new FlowLayout());

        setTitle("تطبيق القرآن والحديث");
    }

    public static void main(String[] args) {
        IslamicGraphics app = new IslamicGraphics();
        app.setVisible(true);
        
        Project my = new Project();
        my.setVisible(false);
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        my.setSize(kit.getScreenSize().width, kit.getScreenSize().height);
        my.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void MainPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        add(panel, BorderLayout.CENTER);
    }
}