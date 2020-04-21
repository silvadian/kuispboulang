package kuispbo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

class DataBuku extends JFrame {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/kuis";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;  
    
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"Kode Buku", "Nama Buku", "Nama Pengarang", "Nama Penerbit", "Tahun Terbit"};    
    
    JLabel lTitle = new JLabel("DATA BUKU");
    JLabel lKode = new JLabel("Kode Buku ");
    JTextField tfKode = new JTextField();
    JLabel lJudul= new JLabel("Judul Buku");
    JTextField tfJudul = new JTextField();    
    JLabel lPengarang = new JLabel("Pengarang");
    JTextField tfPengarang = new JTextField();
    JLabel lPenerbit= new JLabel("Penerbit");
    JTextField tfPenerbit = new JTextField(); 
    JLabel lTahun= new JLabel("Tahun Terbit");
    JTextField tfTahun = new JTextField(); 
    
    JTextField tfCari = new JTextField();
    JButton btnSimpanPanel = new JButton("Simpan");
    JButton btnHapusPanel = new JButton("Hapus");
    JButton btnKeluarPanel = new JButton("Kembali");
    JButton btnRefreshPanel = new JButton("Refresh");
    JButton btnCariPanel = new JButton("Cari");
    
    public DataBuku(){
        try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }        

        setTitle("DATA BUKU");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(660, 585);
        setLocation(630, 300);

        tableModel = new DefaultTableModel(namaKolom,0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);        
        
        add(scrollPane);
        scrollPane.setBounds(20, 205, 600, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        add(lTitle);
        lTitle.setBounds(280, 5, 300, 50);
        lTitle.setFont(new Font("Segoe Script",Font.BOLD, 25));
        lTitle.setForeground(Color.red);
        
        add(lKode);
        add(tfKode);
        add(lJudul);
        add(tfJudul);
        add(lPengarang);
        add(tfPengarang);
        add(lPenerbit);
        add(tfPenerbit);
        add(lTahun);
        add(tfTahun);
        
        lKode.setBounds(20, 55, 120, 20);       
        tfKode.setBounds(140, 55, 150, 20);        
        lJudul.setBounds(20, 85, 120, 20);        
        tfJudul.setBounds(140, 85, 150, 20);        
        lPengarang.setBounds(20, 115, 120, 20);        
        tfPengarang.setBounds(140, 115, 150, 20);        
        lPenerbit.setBounds(20, 145, 120, 20);        
        tfPenerbit.setBounds(140, 145, 150, 20);        
        lTahun.setBounds(20, 175, 120, 20);        
        tfTahun.setBounds(140, 175, 150, 20);

        add(btnSimpanPanel);
        add(btnHapusPanel);
        add(btnCariPanel);
        add(btnKeluarPanel);
        add(btnRefreshPanel);
        add(tfCari);
        
        tfCari.setBounds(375, 175, 150, 20);
        btnSimpanPanel.setBounds(517, 80, 100, 20);        
        btnHapusPanel.setBounds(375, 110, 100, 20);
        btnCariPanel.setBounds(537, 175, 80, 20);
        btnKeluarPanel.setBounds(517, 110, 100, 20);        
        btnRefreshPanel.setBounds(375, 80, 100, 20);

    btnSimpanPanel.addActionListener((ActionEvent e) -> {
            if (tfKode.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String kode = tfKode.getText();
                String judul = tfJudul.getText();
                String pengarang = tfPengarang.getText();
                String penerbit = tfPenerbit.getText();
                String tahun = tfTahun.getText();
    
                this.simpanBuku(kode, judul, pengarang, penerbit, tahun);
  
                String dataBuku[][] = this.readBuku();
                table.setModel(new JTable(dataBuku,namaKolom).getModel());
            }
        });
    
    tfCari.getText();
        tfCari.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            String getSearch= tfCari.getText();
            String dataBuku[][] = searchBuku(getSearch);
                table.setModel(new JTable (dataBuku, namaKolom).getModel());
            }
    });

    if (this.getBanyakData() != 0) {  
            String dataBuku[][] = this.readBuku();  
            table.setModel((new JTable(dataBuku, namaKolom)).getModel());
             
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
    }        
 
    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){ 
            int baris = table.getSelectedRow();
            int kolom = table.getSelectedColumn(); 
            String dataterpilih = table.getValueAt(baris, 0).toString();
            
            btnHapusPanel.addActionListener((ActionEvent f) -> {
                hapusBuku(dataterpilih);
                String dataBuku[][] = readBuku();
                table.setModel(new JTable(dataBuku,namaKolom).getModel());
            }); 
        }
    });

    btnKeluarPanel.addActionListener((ActionEvent e) -> {
          Menu menu = new Menu();
           dispose();
    });
    
    btnRefreshPanel.addActionListener((ActionEvent e) -> {
          tfKode.setText("");
          tfJudul.setText("");
          tfPengarang.setText("");
          tfPenerbit.setText("");
          tfTahun.setText("");
    });
    }

    public void simpanBuku(String kode, String judul, String pengarang, String penerbit, String tahun) {
        try{
            String query = "INSERT INTO `buku`(`Kode_buku`,`Judul`,`Pengarang`,`Penerbit`,`Tahun`) VALUES ('"+kode+"','"+judul+"','"+pengarang+"', '"+penerbit+"','"+tahun+"')";
        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        System.out.println("Berhasil Ditambahkan");
        JOptionPane.showMessageDialog(null,"Berhasil menambahkan "+judul);
        }catch(Exception sql){
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
    }

    String[][] readBuku() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][5];
            String query = "Select * from `buku`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("Kode_buku");
                data[jmlData][1] = resultSet.getString("Judul");
                data[jmlData][2] = resultSet.getString("Pengarang");
                data[jmlData][3] = resultSet.getString("Penerbit");
                data[jmlData][4] = resultSet.getString("Tahun");
                
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

    int getBanyakData() {
        int jmlData = 0;
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `buku`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }
    int getBanyakDataSearch(String getSearch){
        int jmlData=0;
        try{
            statement = koneksi.createStatement();
            String query = "Select * from `buku` where `Judul` like '%"+getSearch+"%'";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
            jmlData++;
            } return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL ERROR");
             return 0;
        }
    }
    String[][] searchBuku(String getSearch){
        try{
            int jmlData = 0;
            String data[][] = new String[getBanyakDataSearch(getSearch)][5];
            String query = "Select * from `buku` where `Judul` like '%"+getSearch+"%'";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("Kode_buku");
                data[jmlData][1] = resultSet.getString("Judul");
                data[jmlData][2] = resultSet.getString("Pengarang");
                data[jmlData][3] = resultSet.getString("Penerbit");
                data[jmlData][4] = resultSet.getString("Tahun");
            jmlData++;
            } return data;
        } catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL ERROR");
            return null;
        }
    }
  
    void hapusBuku(String kode) {
        try{
            String query = "DELETE FROM `buku` WHERE `Kode_buku` = '"+kode+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil menghapus kode "+kode );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }
}

  
