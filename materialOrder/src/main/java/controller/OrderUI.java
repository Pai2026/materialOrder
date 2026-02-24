package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Buyer;
import model.Customer;
import model.Order;
import model.Supplier;
import service.BuyerService;
import service.CustomerService;
import service.InventoryService;
import service.OrderService;
import service.SupplierService;
import service.impl.BuyerServiceImpl;
import service.impl.CustomerServiceImpl;
import service.impl.InventoryServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.SupplierServiceImpl;
import util.Tool;

public class OrderUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextField PuNeedful, PuDeduct, MeshNeedful, MeshDeduct, LeatherNeedful, LeatherDeduct, NylonNeedful, NylonDeduct, CanvasNeedful, CanvasDeduct;
    private JLabel PuPrice, PuStock, PuOrder, PuTotal;
    private JLabel MeshPrice, MeshStock, MeshOrder, MeshTotal;
    private JLabel LeatherPrice, LeatherStock, LeatherOrder, LeatherTotal;
    private JLabel NylonPrice, NylonStock, NylonOrder, NylonTotal;
    private JLabel CanvasPrice, CanvasStock, CanvasOrder, CanvasTotal;

    private JComboBox<String> customerBox;
    private JComboBox<String> puSupplier, meshSupplier, leatherSupplier, nylonSupplier, canvasSupplier;

    private final BuyerService buyerService = new BuyerServiceImpl();
    private final CustomerService customerService = new CustomerServiceImpl();
    private final InventoryService inventoryService = new InventoryServiceImpl();
    private final SupplierService supplierService = new SupplierServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();

    private Buyer buyer;
    private List<Customer> customerList;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    model.Buyer testMember = new model.Buyer(1, "admin", "123", "測試員", "TW");
                    OrderUI frame = new OrderUI(testMember);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public OrderUI(Buyer buyer) {
        this.buyer = buyer;
        setTitle("採購系統");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1150, 750);
        
        contentPane = new JPanel(new BorderLayout(0, 0));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.BLACK);
        header.setPreferredSize(new Dimension(1150, 80));
        
        JLabel title = new JLabel("    採購系統");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("微軟正黑體", Font.BOLD, 26)); 
        header.add(title, BorderLayout.WEST);
        
        JLabel welcome = new JLabel("當前用戶: " + buyer.getName() + "    ");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        header.add(welcome, BorderLayout.EAST);
        contentPane.add(header, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        contentPane.add(panel, BorderLayout.CENTER);

        JLabel lblClient = new JLabel("客戶:");
        lblClient.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        lblClient.setBounds(30, 20, 50, 30);
        panel.add(lblClient);

        customerBox = new JComboBox<>();
        customerBox.addItem("請選擇客戶...");
        customerList = customerService.getAllCustomers();
        if (customerList != null) {
            for (Customer c : customerList) customerBox.addItem(c.getCustomerName());
        }
        customerBox.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        customerBox.setBounds(80, 22, 200, 30);
        panel.add(customerBox);

        setupFormHeader(panel);
        initMaterialForm(panel);
        setupActionButtons(panel);
    }

    private void setupFormHeader(JPanel p) {
        int hY = 75;
        Font f = new Font("微軟正黑體", Font.BOLD, 15);
        String[] headers = {"材料名稱", "單價", "需求量", "庫存量", "扣庫數", "下單數", "總金額", "供應商"};
        int[] xPos = {20, 120, 200, 290, 380, 470, 570, 730};
        for (int i = 0; i < headers.length; i++) {
            JLabel l = new JLabel(headers[i], SwingConstants.CENTER);
            l.setFont(f);
            l.setBounds(xPos[i], hY, 100, 25);
            p.add(l);
        }
    }

    private void initMaterialForm(JPanel p) {
        int[] x = {20, 120, 200, 290, 380, 470, 570, 730};
        
        // PU
        int y1 = 120;
        p.add(createMaterialLabel("PU", x[0], y1));
        PuPrice = createValueLabel("10", x[1], y1); p.add(PuPrice);
        PuNeedful = createTextField(x[2], y1); p.add(PuNeedful);
        PuStock = createValueLabel(getStock("PU"), x[3], y1); p.add(PuStock);
        PuDeduct = createTextField(x[4], y1); p.add(PuDeduct);
        PuOrder = createValueLabel("0", x[5], y1); p.add(PuOrder);
        PuTotal = createValueLabel("0", x[6], y1); p.add(PuTotal);
        puSupplier = createSupplierCombo("PU", x[7], y1); p.add(puSupplier);

        // 網布
        int y2 = 170;
        p.add(createMaterialLabel("網布", x[0], y2));
        MeshPrice = createValueLabel("5", x[1], y2); p.add(MeshPrice);
        MeshNeedful = createTextField(x[2], y2); p.add(MeshNeedful);
        MeshStock = createValueLabel(getStock("Mesh"), x[3], y2); p.add(MeshStock);
        MeshDeduct = createTextField(x[4], y2); p.add(MeshDeduct);
        MeshOrder = createValueLabel("0", x[5], y2); p.add(MeshOrder);
        MeshTotal = createValueLabel("0", x[6], y2); p.add(MeshTotal);
        meshSupplier = createSupplierCombo("Mesh", x[7], y2); p.add(meshSupplier);

        // Leather
        int y3 = 220;
        p.add(createMaterialLabel("Leather", x[0], y3));
        LeatherPrice = createValueLabel("50", x[1], y3); p.add(LeatherPrice);
        LeatherNeedful = createTextField(x[2], y3); p.add(LeatherNeedful);
        LeatherStock = createValueLabel(getStock("Leather"), x[3], y3); p.add(LeatherStock);
        LeatherDeduct = createTextField(x[4], y3); p.add(LeatherDeduct);
        LeatherOrder = createValueLabel("0", x[5], y3); p.add(LeatherOrder);
        LeatherTotal = createValueLabel("0", x[6], y3); p.add(LeatherTotal);
        leatherSupplier = createSupplierCombo("Leather", x[7], y3); p.add(leatherSupplier);

        // 尼龍
        int y4 = 270;
        p.add(createMaterialLabel("尼龍", x[0], y4));
        NylonPrice = createValueLabel("9", x[1], y4); p.add(NylonPrice);
        NylonNeedful = createTextField(x[2], y4); p.add(NylonNeedful);
        NylonStock = createValueLabel(getStock("Nylon"), x[3], y4); p.add(NylonStock);
        NylonDeduct = createTextField(x[4], y4); p.add(NylonDeduct);
        NylonOrder = createValueLabel("0", x[5], y4); p.add(NylonOrder);
        NylonTotal = createValueLabel("0", x[6], y4); p.add(NylonTotal);
        nylonSupplier = createSupplierCombo("Nylon", x[7], y4); p.add(nylonSupplier);

        // 帆布
        int y5 = 320;
        p.add(createMaterialLabel("帆布", x[0], y5));
        CanvasPrice = createValueLabel("7", x[1], y5); p.add(CanvasPrice);
        CanvasNeedful = createTextField(x[2], y5); p.add(CanvasNeedful);
        CanvasStock = createValueLabel(getStock("Canvas"), x[3], y5); p.add(CanvasStock);
        CanvasDeduct = createTextField(x[4], y5); p.add(CanvasDeduct);
        CanvasOrder = createValueLabel("0", x[5], y5); p.add(CanvasOrder);
        CanvasTotal = createValueLabel("0", x[6], y5); p.add(CanvasTotal);
        canvasSupplier = createSupplierCombo("Canvas", x[7], y5); p.add(canvasSupplier);
    }

    private void setupActionButtons(JPanel p) {
        int btnY = 450;
        JButton btnReset = createStyledButton("重新輸入", 50, btnY, Color.LIGHT_GRAY, Color.BLACK);
        btnReset.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { resetForm(); }
        });
        p.add(btnReset);

        JButton btnConfirm = createStyledButton("確認試算", 200, btnY, Color.DARK_GRAY, Color.WHITE);
        btnConfirm.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { calculateAll(); }
        });
        p.add(btnConfirm);

        JButton btnCheckout = createStyledButton("確認結帳", 350, btnY, Color.BLACK, Color.WHITE);
        btnCheckout.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { handleCheckout(); }
        });
        p.add(btnCheckout);

        JButton btnQuery = createStyledButton("查詢訂單", 500, btnY, new Color(0, 102, 204), Color.WHITE);
        btnQuery.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
            OrderQueryUI queryFrame = new OrderQueryUI(buyer);
            queryFrame.setVisible(true);
            dispose(); }
        });
        p.add(btnQuery);

        JButton btnExit = createStyledButton("離開系統", 650, btnY, new Color(204, 0, 0), Color.WHITE);
        btnExit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { System.exit(0); }
        });
        p.add(btnExit);
    }

    private JButton createStyledButton(String text, int x, int y, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 140, 50);
        btn.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        return btn;
    }

    private void handleCheckout() {
        if (customerBox.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "請選擇客戶！");
            return;
        }

        // 修正：必須呼叫供應商驗證
        if (!validateSuppliers()) return;

        try {
            List<Order> orderList = new ArrayList<>();
            // 修正：傳入正確的 JComboBox 參數
            collectOrder(orderList, "PU", PuPrice, PuNeedful, PuStock, PuDeduct, puSupplier);
            collectOrder(orderList, "Mesh", MeshPrice, MeshNeedful, MeshStock, MeshDeduct, meshSupplier);
            collectOrder(orderList, "Leather", LeatherPrice, LeatherNeedful, LeatherStock, LeatherDeduct, leatherSupplier);
            collectOrder(orderList, "Nylon", NylonPrice, NylonNeedful, NylonStock, NylonDeduct, nylonSupplier);
            collectOrder(orderList, "Canvas", CanvasPrice, CanvasNeedful, CanvasStock, CanvasDeduct, canvasSupplier);

            if (orderList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "未產生任何下單數量！");
                return;
            }

            util.Tool.saveOrder(orderList); 
            JOptionPane.showMessageDialog(this, "結帳成功！共計 " + orderList.size() + " 項材料。");
            
            OrderDetailUI detailFrame = new OrderDetailUI(buyer);
            detailFrame.setVisible(true);
            dispose();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "資料傳遞發生錯誤：" + ex.getMessage());
        }
    }

    private boolean validateSuppliers() {
        Object[][] checkList = {
            {PuNeedful, puSupplier, "PU"},
            {MeshNeedful, meshSupplier, "網布"},
            {LeatherNeedful, leatherSupplier, "Leather"},
            {NylonNeedful, nylonSupplier, "尼龍"},
            {CanvasNeedful, canvasSupplier, "帆布"}
        };

        for (Object[] row : checkList) {
            JTextField needField = (JTextField) row[0];
            JComboBox<?> supCombo = (JComboBox<?>) row[1];
            String matName = (String) row[2];

            try {
                int qty = Integer.parseInt(needField.getText());
                if (qty > 0 && supCombo.getSelectedIndex() <= 0) {
                    JOptionPane.showMessageDialog(this, "請選擇材料 [" + matName + "] 的供應商！", "欄位未填", JOptionPane.ERROR_MESSAGE);
                    return false; 
                }
            } catch (NumberFormatException e) { }
        }
        return true; 
    }

    private void calculateAll() {
        calculate(PuPrice, PuNeedful, PuStock, PuDeduct, PuOrder, PuTotal);
        calculate(MeshPrice, MeshNeedful, MeshStock, MeshDeduct, MeshOrder, MeshTotal);
        calculate(LeatherPrice, LeatherNeedful, LeatherStock, LeatherDeduct, LeatherOrder, LeatherTotal);
        calculate(NylonPrice, NylonNeedful, NylonStock, NylonDeduct, NylonOrder, NylonTotal);
        calculate(CanvasPrice, CanvasNeedful, CanvasStock, CanvasDeduct, CanvasOrder, CanvasTotal);
    }

    private void calculate(JLabel pL, JTextField nF, JLabel sL, JTextField dF, JLabel oL, JLabel tL) {
        try {
            int p = Integer.parseInt(pL.getText());
            int n = nF.getText().isEmpty() ? 0 : Integer.parseInt(nF.getText());
            int s = Integer.parseInt(sL.getText());
            int d = dF.getText().isEmpty() ? 0 : Integer.parseInt(dF.getText());
            if (d > n || d > s) { d = 0; dF.setText("0"); }
            int o = Math.max(0, n - d);
            oL.setText(String.valueOf(o));
            tL.setText(String.valueOf(o * p));
        } catch (Exception e) {}
    }

    private void resetForm() {
        JTextField[] tfs = {PuNeedful, PuDeduct, MeshNeedful, MeshDeduct, LeatherNeedful, LeatherDeduct, NylonNeedful, NylonDeduct, CanvasNeedful, CanvasDeduct};
        for (JTextField f : tfs) f.setText("0");
        JLabel[] lbs = {PuOrder, PuTotal, MeshOrder, MeshTotal, LeatherOrder, LeatherTotal, NylonOrder, NylonTotal, CanvasOrder, CanvasTotal};
        for (JLabel l : lbs) l.setText("0");
    }

    private void collectOrder(List<Order> list, String name, JLabel priceL, JTextField needF, JLabel stockL, JTextField deductF, JComboBox<String> supCombo) {
        try {
            int n = Integer.parseInt(needF.getText());
            if (n > 0) {
                int customerId = customerList.get(customerBox.getSelectedIndex() - 1).getCustomerId();
                int buyerId = buyer.getBuyerId();
                int materialId = Tool.getMaterialId(name);
                
                int supIndex = supCombo.getSelectedIndex();
                int supplierId = 0; 
                List<Supplier> suppliers = supplierService.getSuppliersByMaterial(materialId);
                if (supIndex > 0) {
                    supplierId = suppliers.get(supIndex - 1).getSupplierId();
                }

                int p = Integer.parseInt(priceL.getText());
                int s = Integer.parseInt(stockL.getText());
                int d = Integer.parseInt(deductF.getText());
                int o = n - d;
                int total = p * o;

                list.add(new Order(customerId, buyerId, materialId, supplierId, name, p, n, s, d, o, total));
            }
        } catch (Exception e) {
            System.err.println("收集訂單時發生錯誤: " + name);
        }
    }

    private JLabel createMaterialLabel(String text, int x, int y) { JLabel l = new JLabel(text, SwingConstants.CENTER); l.setFont(new Font("微軟正黑體", Font.PLAIN, 14)); l.setBounds(x, y, 90, 30); return l; }
    private JLabel createValueLabel(String text, int x, int y) { JLabel l = new JLabel(text, SwingConstants.CENTER); l.setFont(new Font("微軟正黑體", Font.PLAIN, 14)); l.setBounds(x, y, 90, 30); return l; }
    private JTextField createTextField(int x, int y) { JTextField t = new JTextField("0"); t.setFont(new Font("微軟正黑體", Font.PLAIN, 14)); t.setHorizontalAlignment(SwingConstants.CENTER); t.setBounds(x, y, 80, 30); return t; }
    private JComboBox<String> createSupplierCombo(String matName, int x, int y) {
        JComboBox<String> combo = new JComboBox<>(); combo.addItem("選擇廠商");
        combo.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
        List<Supplier> suppliers = supplierService.getSuppliersByMaterial(Tool.getMaterialId(matName));
        for (Supplier s : suppliers) combo.addItem(s.getSupplierName());
        combo.setBounds(x, y, 160, 30); return combo;
    }
    private String getStock(String name) {
        try { return String.valueOf(inventoryService.getInventoryById(Tool.getMaterialId(name)).get(0).getStockQty()); } catch (Exception e) { return "0"; }
    }
}