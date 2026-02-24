# 📦 Material Procurement System (Java 採購訂單管理系統)

這是一個基於 **Java Swing** 開發的桌面端採購訂單管理系統。本專案採用標準的 **MVC (Model-View-Controller)** 架構與 **DAO (Data Access Object)** 設計模式，並透過 **Maven** 進行依賴管理。系統底層使用 **JDBC** 與 **MySQL** 資料庫進行互動，為企業採購人員提供一個直覺、穩定且高效的訂單建立與歷史查詢平台。

---

## ✨ 核心功能 (Features)

* **🔐 帳號與權限管理**
  * 提供採購人員 (Buyer) 帳號註冊與安全登入功能。
* **🛒 智能採購下單系統**
  * 支援多種基礎材料（PU、網布、Leather、尼龍、帆布）的採購試算。
  * 動態下拉選單：自動載入資料庫中的「客戶清單」與對應的「材料供應商」。
  * 智能計算：自動核算需求量、扣除現有庫存數，並計算最終下單數量與總金額。
  * 暫存與結帳：支援訂單本地暫存（Java 序列化），確認無誤後一鍵結帳並寫入資料庫。
* **📦 庫存即時連動**
  * 結帳成功後，系統會自動扣除 MySQL 資料庫中對應材料的現有庫存。
* **📊 歷史訂單查詢與報表**
  * **複合條件搜尋**：支援透過「關鍵字 (單號/材料)」與「下單日期 (JDateChooser)」進行精準查詢。
  * **分頁顯示**：內建資料分頁功能，確保大量數據下的 UI 渲染效能。
  * **報表匯出**：支援將查詢結果一鍵匯出為 `.csv` 檔案（內建 UTF-8 BOM，完美解決 Excel 開啟中文亂碼問題）。
  * **列印預覽**：提供單據文字化報表預覽及實體列印功能。

---

## 🛠️ 技術棧 (Tech Stack)

* **程式語言**: Java (JDK 8 或以上推薦)
* **圖形介面 (GUI)**: Java Swing
* **資料庫**: MySQL 8.0+
* **專案建置工具**: Maven
* **架構模式**: MVC, DAO Pattern
* **核心依賴 (Dependencies)**: 
  * `mysql-connector-java` (8.0.33) - MySQL 資料庫驅動程式
  * `jcalendar` (1.4) - 圖形化日期選擇元件

---

## 📂 專案目錄結構 (Project Structure)

```text
Material-Procurement-System/
├── src/main/java/
│   ├── controller/       # UI 介面與事件控制層 (Java Swing UI)
│   ├── model/            # 資料實體層 (POJO: Buyer, Customer, Order, etc.)
│   ├── dao/              # 資料存取層 (DAO 介面與 JDBC 實作)
│   ├── service/          # 業務邏輯層 (Service 介面與實作)
│   └── util/             # 工具類別 (DB 連線管理、IO 序列化存取)
├── materialOrder_full_backup.sql # MySQL 資料庫建表與預設資料備份檔
├── pom.xml               # Maven 專案設定與依賴管理檔
└── README.md             # 專案說明文件
