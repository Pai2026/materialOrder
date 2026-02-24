package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser; // 需匯入 JCalendar jar

import model.Buyer;
import model.Order;
import service.OrderService;
import service.impl.OrderServiceImpl;

public class OrderQueryUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtKeyword;
    private JDateChooser dateChooser; 
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblPageInfo;

    private final OrderService orderService = new OrderServiceImpl();
    private Buyer buyer;

    private List<Order> filteredList = new ArrayList<>();
    private int currentPage = 1;
    private final int ROWS_PER_PAGE = 15; 

    public OrderQueryUI(Buyer buyer) {
        this.buyer = buyer;
        setTitle("採購系統 - 歷史訂單查詢");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1150, 750);
        
        contentPane = new JPanel(new BorderLayout(0, 0));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.BLACK);
        header.setPreferredSize(new Dimension(1150, 70));
        JLabel title = new JLabel("    訂單歷史紀錄查詢", SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("微軟正黑體", Font.BOLD, 26));
        header.add(title, BorderLayout.CENTER);
        contentPane.add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // UI Components
        JLabel lblKeyword = new JLabel("關鍵字 (單號/材料):");
        lblKeyword.setFont(new Font("微軟正黑體", Font.BOLD, 15));
        lblKeyword.setBounds(30, 20, 150, 30);
        mainPanel.add(lblKeyword);

        txtKeyword = new JTextField();
        txtKeyword.setBounds(170, 20, 150, 30);
        mainPanel.add(txtKeyword);

        JLabel lblDate = new JLabel("下單日期:");
        lblDate.setFont(new Font("微軟正黑體", Font.BOLD, 15));
        lblDate.setBounds(350, 20, 80, 30);
        mainPanel.add(lblDate);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(430, 20, 160, 30);
        mainPanel.add(dateChooser);

        JButton btnSearch = createStyledButton("開始搜尋", 620, 18, Color.BLACK, Color.WHITE);
        btnSearch.addActionListener(e -> performSearch());
        mainPanel.add(btnSearch);
        
        JButton btnExport = createStyledButton("匯出報表", 860, 18, new Color(34, 139, 34), Color.WHITE); // 森林綠代表 Excel
        btnExport.addActionListener(e -> exportToCSV());
        mainPanel.add(btnExport);

        JButton btnReset = createStyledButton("清除重設", 740, 18, Color.GRAY, Color.WHITE);
        btnReset.addActionListener(e -> {
            txtKeyword.setText("");
            dateChooser.setDate(null);
            loadAllData();
        });
        mainPanel.add(btnReset);

        // Table
        String[] columnNames = {"訂單ID", "材料名稱", "單價", "下單量", "扣庫數", "總額", "下單日期", "供應商"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 70, 1080, 480);
        mainPanel.add(scrollPane);

        // Bottom Controls
        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setPreferredSize(new Dimension(1150, 80));
        bottomPanel.setBackground(Color.WHITE);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        JButton btnBack = createStyledButton("回上一頁", 30, 15, Color.DARK_GRAY, Color.WHITE);
        btnBack.addActionListener(e -> {
            new OrderUI(buyer).setVisible(true);
            dispose();
        });
        bottomPanel.add(btnBack);

        JButton btnPrev = createStyledButton("上一頁", 420, 15, Color.BLACK, Color.WHITE);
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableDisplay();
            }
        });
        bottomPanel.add(btnPrev);

        lblPageInfo = new JLabel("第 1 / 1 頁", SwingConstants.CENTER);
        lblPageInfo.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblPageInfo.setBounds(540, 15, 100, 40);
        bottomPanel.add(lblPageInfo);

        JButton btnNext = createStyledButton("下一頁", 650, 15, Color.BLACK, Color.WHITE);
        btnNext.addActionListener(e -> {
            if (currentPage < getTotalPages()) {
                currentPage++;
                updateTableDisplay();
            }
        });
        bottomPanel.add(btnNext);

        JButton btnExit = createStyledButton("離開系統", 980, 15, new Color(180, 0, 0), Color.WHITE);
        btnExit.addActionListener(e -> System.exit(0));
        bottomPanel.add(btnExit);

        loadAllData();
    } 

    private void loadAllData() {
        filteredList = orderService.allOrder();
        currentPage = 1;
        updateTableDisplay();
    }

    private void performSearch() {
        String keyword = txtKeyword.getText().toLowerCase();
        Date selectedDate = dateChooser.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String targetDateStr = (selectedDate != null) ? sdf.format(selectedDate) : "";

        List<Order> all = orderService.allOrder();
        filteredList = new ArrayList<>();

        for (Order o : all) {
           
            boolean matchKey = keyword.isEmpty() || 
                               (o.getOrderNo() != null && o.getOrderNo().toLowerCase().contains(keyword)) ||
                               o.getMaterialName().toLowerCase().contains(keyword);
            
            String dbDateStr = (o.getCreateDate() != null) ? sdf.format(o.getCreateDate()) : "";
            boolean matchDate = targetDateStr.isEmpty() || dbDateStr.equals(targetDateStr);

            if (matchKey && matchDate) {
                filteredList.add(o);
            }
        }
        currentPage = 1;
        updateTableDisplay();
    }

    private void updateTableDisplay() {
        tableModel.setRowCount(0);
        if (filteredList.isEmpty()) {
            lblPageInfo.setText("第 1 / 1 頁");
            return;
        }

        int start = (currentPage - 1) * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, filteredList.size());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (int i = start; i < end; i++) {
            Order o = filteredList.get(i);
            Vector<Object> row = new Vector<>();
            
            row.add(o.getOrderNo());        
            row.add(o.getMaterialName());   
            row.add(o.getUnitPrice());       
            row.add(o.getOrderQty());       
            row.add(o.getDeductQty());       
            row.add(o.getTotal());          
            
            if (o.getCreateDate() != null) {
                row.add(sdf.format(o.getCreateDate()));
            } else {
                row.add("無日期資料");
            }
            
            row.add(o.getSupplierId());   
            
            tableModel.addRow(row);
        }
        lblPageInfo.setText("第 " + currentPage + " / " + getTotalPages() + " 頁");
    }
    
    private int getTotalPages() {
        if (filteredList.isEmpty()) return 1;
        return (int) Math.ceil((double) filteredList.size() / ROWS_PER_PAGE);
    }

    private JButton createStyledButton(String text, int x, int y, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 110, 40);
        btn.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }
    
    private void exportToCSV() {
        if (filteredList.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "目前沒有資料可以匯出！");
            return;
        }
        // 彈出檔案儲存對話框
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("儲存為 Excel (CSV) 檔");
        fileChooser.setSelectedFile(new java.io.File("採購訂單報表_" + System.currentTimeMillis() + ".csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            
            // 確保副檔名是 .csv
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                fileToSave = new java.io.File(filePath + ".csv");
            }

            try (java.io.BufferedWriter bw = new java.io.BufferedWriter(
                    new java.io.OutputStreamWriter(new java.io.FileOutputStream(fileToSave), "UTF-8"))) {
                
                // 寫入 UTF-8 BOM 防止 Excel 開啟中文亂碼 (重要！)
                bw.write('\ufeff');

                // 1. 寫入標題列
                String[] headers = {"訂單單號", "材料名稱", "單價", "下單量", "扣庫數", "總額", "下單日期", "供應商ID"};
                bw.write(String.join(",", headers));
                bw.newLine();

                // 2. 寫入所有篩選後的資料 (不限於當前分頁)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                for (Order o : filteredList) {
                    String dateStr = (o.getCreateDate() != null) ? sdf.format(o.getCreateDate()) : "無日期";
                    
                    StringBuilder sb = new StringBuilder();
                    sb.append(o.getOrderNo()).append("\t,")
                      .append(o.getMaterialName()).append(",")
                      .append(o.getUnitPrice()).append(",")
                      .append(o.getOrderQty()).append(",")
                      .append(o.getDeductQty()).append(",")
                      .append(o.getTotal()).append(",")
                      .append(dateStr).append(",")
                      .append(o.getSupplierId());
                    
                    bw.write(sb.toString());
                    bw.newLine();
                }

                javax.swing.JOptionPane.showMessageDialog(this, "報表匯出成功！\n路徑: " + fileToSave.getAbsolutePath());

            } catch (java.io.IOException ex) {
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this, "匯出失敗: " + ex.getMessage(), "錯誤", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}