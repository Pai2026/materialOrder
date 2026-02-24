package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Buyer;
import model.Order;
import service.OrderService;
import service.impl.OrderServiceImpl;
import util.Tool;

public class OrderDetailUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea output;
    private JLabel lblSum;
    private Buyer buyer;

    public OrderDetailUI(Buyer buyer) {
        this.buyer = buyer;
        setTitle("採購訂單明細確認");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 700);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("採購訂單明細");
        label.setFont(new Font("微軟正黑體", Font.BOLD, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(10, 10, 510, 40);
        contentPane.add(label);

        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));
        output.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(30, 60, 470, 400);
        contentPane.add(scrollPane);

        lblSum = new JLabel("總金額: $0");
        lblSum.setFont(new Font("微軟正黑體", Font.BOLD, 22));
        lblSum.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSum.setBounds(230, 470, 270, 30);
        contentPane.add(lblSum);

        // 按鈕邏輯 (回上一頁、下訂單、離開) 同前...
        setupButtons();

        showOrder();
    }

 
    private void showOrder() {
        List<Order> list = Tool.readOrder();
        if (list == null || list.isEmpty()) {
            output.setText("\n\t[ 無暫存訂單資料 ]");
            return;
        }

        StringBuilder sb = new StringBuilder();
        int sum = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        sb.append("====================================================\n");
        sb.append("\t\t採 購 報 表\n");
        sb.append("====================================================\n");
        sb.append(" 列印時間: " + sdf.format(new Date()) + "\n");
        sb.append(" 採購人員: " + buyer.getName() + "\n");
        sb.append("----------------------------------------------------\n");
        
        // 增加「供應商」欄位
        sb.append(String.format("%-10s %-8s %-6s %-6s %-8s\n", "材料名", "供應商ID", "單價", "下單", "小計"));
        sb.append("----------------------------------------------------\n");

        for (Order o : list) {
            // 從 Order 物件取出 supplierId 並顯示
            sb.append(String.format("%-10s %-10d %-8d %-8d %-8d\n", 
                o.getMaterialName(), 
                o.getSupplierId(),   // [新增]
                o.getUnitPrice(), 
                o.getOrderQty(), 
                o.getTotal()));
            
            sum += o.getTotal();
        }

        sb.append("----------------------------------------------------\n");
        sb.append(String.format("\n 總計金額: $%d 元整\n", sum));
        sb.append("====================================================\n");

        output.setText(sb.toString());
        lblSum.setText("總金額: $" + sum);
    }

    private void setupButtons() {
        JButton btnBack = new JButton("回上一頁");
        btnBack.addActionListener(e -> {
            new OrderUI(buyer).setVisible(true);
            dispose();
        });
        btnBack.setBounds(40, 550, 110, 40);
        contentPane.add(btnBack);

        JButton btnPrint = new JButton("確認結帳");
        btnPrint.addActionListener(e -> {
            OrderService service = new OrderServiceImpl();
            String result = service.checkout(Tool.readOrder());
            if ("empty".equals(result)) {
                JOptionPane.showMessageDialog(null, "資料夾內無訂單檔案");
            } else if ("error".equals(result)) {
                JOptionPane.showMessageDialog(null, "資料庫寫入失敗");
            } else {
                try {
                    output.print();
                    JOptionPane.showMessageDialog(null, "訂單成立！編號：" + result);
                    dispose();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnPrint.setBounds(180, 550, 120, 40);
        contentPane.add(btnPrint);

        JButton btnExit = new JButton("離開");
        btnExit.addActionListener(e -> System.exit(0));
        btnExit.setBounds(330, 550, 110, 40);
        contentPane.add(btnExit);
    }
}