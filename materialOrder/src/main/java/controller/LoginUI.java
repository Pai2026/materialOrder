package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Buyer;
import service.BuyerService;
import service.impl.BuyerServiceImpl;

public class LoginUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField username;
    private JPasswordField password; 
    

    private final Font TITLE_FONT = new Font("微軟正黑體", Font.BOLD, 24);
    private final Font LABEL_FONT = new Font("微軟正黑體", Font.BOLD, 15);
    private final Font BTN_FONT = new Font("微軟正黑體", Font.BOLD, 14);
    
    private final BuyerService buyerService = new BuyerServiceImpl();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginUI frame = new LoginUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginUI() {
        setTitle("系統登入");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 350); // 微調尺寸讓內容更舒展
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.BLACK);
        header.setBounds(0, 0, 480, 60);
        JLabel lblTitle = new JLabel("採購系統登入", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(TITLE_FONT);
        header.add(lblTitle);
        contentPane.add(header);
        
        // --- 主面板內容 ---
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(30, 80, 400, 220);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblUser = new JLabel("帳號：");
        lblUser.setBounds(60, 20, 60, 30);
        lblUser.setFont(LABEL_FONT);
        panel.add(lblUser);
        
        username = new JTextField();
        username.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        username.setBounds(130, 22, 180, 30);
        panel.add(username);
        
        JLabel lblPass = new JLabel("密碼：");
        lblPass.setBounds(60, 75, 60, 30);
        lblPass.setFont(LABEL_FONT);
        panel.add(lblPass);
        
        password = new JPasswordField();
        password.setBounds(130, 77, 180, 30);
        panel.add(password);
        
        // --- 按鈕區 ---
        
        // 登入按鈕 (黑底白字)
        JButton btnLogin = createStyledButton("登入", 130, 135, Color.BLACK, Color.WHITE);
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String user = username.getText();
                String pass = new String(password.getPassword());
                
                Buyer b = buyerService.find_buyer_by_username_and_password(user, pass);
                
                if(b != null) {
                    OrderUI frame = new OrderUI(b);
                    frame.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "帳號或密碼錯誤！", "登入失敗", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(btnLogin);
        
        // 註冊按鈕 (灰底)
        JButton btnAdd = createStyledButton("註冊帳號", 10, 135, Color.DARK_GRAY, Color.WHITE);
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddBuyerUI frame = new AddBuyerUI();
                frame.setVisible(true);
                dispose();
            }
        });
        panel.add(btnAdd);
        
        // 離開按鈕 (紅底)
        JButton btnExit = createStyledButton("離開", 250, 135, new Color(204, 0, 0), Color.WHITE);
        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        panel.add(btnExit);
    }

    private JButton createStyledButton(String text, int x, int y, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 110, 45); 
        btn.setFont(BTN_FONT);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        return btn;
    }
}