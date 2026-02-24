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
import service.impl.BuyerServiceImpl;

public class AddBuyerUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField username;
    private JPasswordField password;
    private JTextField name;
    private JTextField country;

    private final Font TITLE_FONT = new Font("微軟正黑體", Font.BOLD, 26);
    private final Font LABEL_FONT = new Font("微軟正黑體", Font.BOLD, 15);
    private final Font BTN_FONT = new Font("微軟正黑體", Font.BOLD, 14);

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddBuyerUI frame = new AddBuyerUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AddBuyerUI() {
        setTitle("採購系統 - 帳號註冊");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 480); 
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // --- 標題列 ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.BLACK);
        header.setBounds(0, 0, 500, 60);
        JLabel lblTitle = new JLabel("採購員註冊", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(TITLE_FONT);
        header.add(lblTitle);
        contentPane.add(header);
        
        // --- 輸入欄位區 ---
        int startY = 100;
        int gap = 55;     
        int labelX = 40;  
        int labelWidth = 150; 
        int fieldX = 200; 
        int fieldWidth = 220; 

        // 帳號
        JLabel lblUser = new JLabel("帳號 (Username):");
        lblUser.setFont(LABEL_FONT);
        lblUser.setBounds(labelX, startY, labelWidth, 30);
        contentPane.add(lblUser);
        
        username = new JTextField();
        username.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        username.setBounds(fieldX, startY, fieldWidth, 30);
        contentPane.add(username);
        
        // 密碼 (Password)
        JLabel lblPass = new JLabel("密碼 (Password):");
        lblPass.setFont(LABEL_FONT);
        lblPass.setBounds(labelX, startY + gap, labelWidth, 30);
        contentPane.add(lblPass);
        
        password = new JPasswordField();
        password.setBounds(fieldX, startY + gap, fieldWidth, 30);
        contentPane.add(password);
        
        // 姓名
        JLabel lblName = new JLabel("姓名 (Name):");
        lblName.setFont(LABEL_FONT);
        lblName.setBounds(labelX, startY + gap * 2, labelWidth, 30);
        contentPane.add(lblName);
        
        name = new JTextField();
        name.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        name.setBounds(fieldX, startY + gap * 2, fieldWidth, 30);
        contentPane.add(name);
        
        // 國家
        JLabel lblCountry = new JLabel("國家 (Country):");
        lblCountry.setFont(LABEL_FONT);
        lblCountry.setBounds(labelX, startY + gap * 3, labelWidth, 30);
        contentPane.add(lblCountry);
        
        country = new JTextField();
        country.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        country.setBounds(fieldX, startY + gap * 3, fieldWidth, 30);
        contentPane.add(country);
        
        // --- 按鈕區 ---
        int btnY = 350;

        // 確認註冊
        JButton btnSubmit = createStyledButton("確認註冊", 40, btnY, Color.BLACK, Color.WHITE);
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleRegistration();
            }
        });
        contentPane.add(btnSubmit);
        
        // 回上一頁
        JButton btnBack = createStyledButton("回上一頁", 195, btnY, Color.DARK_GRAY, Color.WHITE);
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginUI().setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnBack);
        
        // 離開
        JButton btnExit = createStyledButton("離開系統", 350, btnY, new Color(204, 0, 0), Color.WHITE);
        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        contentPane.add(btnExit);
    }

    private void handleRegistration() {
        String u = username.getText();
        String p = new String(password.getPassword());
        String n = name.getText();
        String c = country.getText();
        
        if(u.isEmpty() || p.isEmpty() || n.isEmpty() || c.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請填寫所有欄位", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Buyer newB = new Buyer(0, u, p, n, c);
        BuyerServiceImpl service = new BuyerServiceImpl();
        boolean isSuccess = service.AddBuyer(newB);
        
        if(isSuccess) {
            JOptionPane.showMessageDialog(this, "註冊成功！請重新登入");
            new LoginUI().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "註冊失敗，帳號可能重複", "錯誤", JOptionPane.ERROR_MESSAGE);
        }
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