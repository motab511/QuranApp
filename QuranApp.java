package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import javax.sound.sampled.*;

public class QuranApp extends JFrame {
    private Connection co;
    private Statement st;
    private ResultSet re;
    private Clip currentClip;
    
    public QuranApp() {
        connectToDatabase();
        setupUI();
    }
    
    private void connectToDatabase() {
        try {
            String URL = "jdbc:mysql://localhost:3306/quran";
            String username = "root";
            String password = "";
            
            co = DriverManager.getConnection(URL, username, password);
            st = co.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "خطأ في الاتصال بقاعدة البيانات: " + ex.getMessage());
            System.exit(1);
        }
    }
    
    private void setupUI() {
        setTitle("جزء عم");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        showMainScreen();
    }
    
    private void showMainScreen() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("سور جزء عم", JLabel.CENTER);
        titleLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 70, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel buttonsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        
        try {
            re = st.executeQuery("SELECT surah_id, name_surah FROM juz_amma_surahs ORDER BY surah_id");
            
            while (re.next()) {
                int surahId = re.getInt("surah_id");
                String surahName = re.getString("name_surah");
                JButton surahButton = createSurahButton(surahName, surahId);
                buttonsPanel.add(surahButton);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,ex );
            ex.printStackTrace();
        }
        
        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton exitButton = new JButton("خروج");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.addActionListener(e -> {
            stopCurrentAudio();
            System.exit(0);
        });
        mainPanel.add(exitButton, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JButton createSurahButton(String surahName, int surahId) {
        JButton button = new JButton(surahName);
        button.setFont(new Font("Traditional Arabic", Font.PLAIN, 18));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 70, 0)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        button.addActionListener(e -> {
            showSurahContent(surahName);
            playSurahAudio(surahId);
        });
        
        return button;
    }
    
    private void playSurahAudio(int surahId) {
    stopCurrentAudio();
    
    try {
        String audioFileName = "C:\\Users\\motab\\Downloads\\programming2\\project\\src\\project\\" + surahId + ".wav";
        File audioFile = new File(audioFileName);
        
        
        if (!audioFile.exists()) {
            JOptionPane.showMessageDialog(this, "not exists" + audioFileName);
            return;
        }
        
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        currentClip = AudioSystem.getClip();
        currentClip.open(audioStream);
        currentClip.start();
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "خطأ في تشغيل الصوت: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
    private void stopCurrentAudio() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }
    
    private void showSurahContent(String surahName) {
        try {
            String query = "SELECT ayat FROM juz_amma_surahs WHERE name_surah = ?";
            PreparedStatement pstmt = co.prepareStatement(query);
            pstmt.setString(1, surahName);
            re = pstmt.executeQuery();
            
            if (re.next()) {
                JFrame surahFrame = new JFrame(surahName);
                surahFrame.setSize(900, 700);
                surahFrame.setLocationRelativeTo(this);
                
                JTextArea textArea = new JTextArea();
                textArea.setFont(new Font("Traditional Arabic", Font.PLAIN, 20));
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setText(re.getString("ayat"));
                
                JToolBar toolBar = new JToolBar();
                toolBar.setFloatable(false);
                
                JButton closeButton = new JButton("إغلاق");
                closeButton.addActionListener(e -> {
                    stopCurrentAudio();
                    this.dispose();
                });
                
                JButton printButton = new JButton("طباعة");
                printButton.addActionListener(e -> {
                    try {
                        textArea.print();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(surahFrame, "خطأ في الطباعة");
                    }
                });
                
                JButton playButton = new JButton("تشغيل");
                playButton.addActionListener(e -> {
                    try {
                        int surahId = re.getInt("surah_id");
                        playSurahAudio(surahId);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(surahFrame,ex);
                    }
                });
                
                JButton stopButton = new JButton("إيقاف");
                stopButton.addActionListener(e -> stopCurrentAudio());
                
                toolBar.add(playButton);
                toolBar.add(stopButton);
                toolBar.add(printButton);
                toolBar.add(closeButton);
                
                surahFrame.setLayout(new BorderLayout());
                surahFrame.add(new JScrollPane(textArea), BorderLayout.CENTER);
                surahFrame.add(toolBar, BorderLayout.SOUTH);
                
                surahFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        stopCurrentAudio();
                    }
                });
                
                surahFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "تحقق من قاعدة بيانات يا ابني");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
            ex.printStackTrace();
        }
    }
    
    @Override
    public void dispose() {
        stopCurrentAudio();
        try {
            if (re != null) re.close();
            if (st != null) st.close();
            if (co != null) co.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        super.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuranApp app = new QuranApp();
            app.setVisible(true);
        });
    }
}