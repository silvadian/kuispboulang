package kuispbo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

class DataAnggota extends JFrame {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/kuis";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;
    
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"NIS", "Nama", "TTL", "Jenis Kelamin", "Agama", "Tgl Daftar", "Berlaku S/D", "Kelas"};
    
    JLabel lTitle = new JLabel("DATA ANGGOTA");
    JLabel lNIS = new JLabel("NIS ");
    JTextField tfNIS = new JTextField();
    JLabel lNama= new JLabel("Nama ");
    JTextField tfNama = new JTextField();    
    JLabel lTTL = new JLabel("TTL ");
    JTextField tfTTL = new JTextField();
    JLabel lJenkel = new JLabel("Jenis kelamin ");
        JCheckBox cbPerempuan = new JCheckBox(" Perempuan ");
        JCheckBox cbLaki = new JCheckBox(" Laki-laki");
    JLabel lAgama = new JLabel(" Agama ");
        String[] namaAgama =
            {"Islam", "Hindu", "Budha", "Kristen", "Katolik"};
    JComboBox cmbAgama = new JComboBox(namaAgama);
    JLabel ltgldaftar = new JLabel("Tanggal Daftar");
    JTextField tftgldaftar = new JTextField();
    JLabel ltglberlaku = new JLabel("Berlaku Hingga ");
    JTextField tftglberlaku = new JTextField();
    JLabel lkelas= new JLabel("Kelas ");
    JTextField tfkelas = new JTextField();
    JButton btnSimpanPanel = new JButton("Simpan");
    JButton btnRefreshPanel = new JButton("Refresh");
    JButton btnHapusPanel = new JButton("Hapus");
    JButton btnKeluarPanel = new JButton("Kembali");    
    
    public DataAnggota(){
        try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }
        
        setTitle("DATA ANGGOTA");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(860, 600);
        setLocation(520, 280);
        
        tableModel = new DefaultTableModel(namaKolom,0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);        

        add(scrollPane);
        scrollPane.setBounds(20, 270, 800, 250);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
             
        add(lTitle);
        lTitle.setBounds(330, 10, 300, 60);
        lTitle.setFont(new Font("Segoe Script",Font.BOLD, 30));
        lTitle.setForeground(Color.red);
        
        add(lNIS);
        add(tfNIS);
        add(lNama);
        add(tfNama);
        add(lTTL);
        add(tfTTL);
        add(lJenkel);
        add(cbPerempuan);
        add(cbLaki);
        add(lAgama);
        add(cmbAgama);
        add(lkelas);
        add(tfkelas);
        add(ltgldaftar);
        add(tftgldaftar);
        add(ltglberlaku);
        add(tftglberlaku);
        
        lNIS.setBounds(40, 80, 80, 20);
        tfNIS.setBounds(140, 80, 220, 20);        
        lNama.setBounds(40, 110, 80, 20);        
        tfNama.setBounds(140, 110, 220, 20);        
        lTTL.setBounds(40, 140, 80, 20);        
        tfTTL.setBounds(140, 140, 220, 20);        
        lJenkel.setBounds(40, 170, 120, 20);        
        cbPerempuan.setBounds(140, 170, 100, 20);        
        cbLaki.setBounds(280, 170, 100, 20);        
        lAgama.setBounds(480, 80, 80, 20);        
        cmbAgama.setBounds(580, 80, 220, 20);        
        lkelas.setBounds(480, 110, 80, 20);        
        tfkelas.setBounds(580, 110, 220, 20);        
        ltgldaftar.setBounds(480, 140, 120, 20);        
        tftgldaftar.setBounds(580, 140, 220, 20);        
        ltglberlaku.setBounds(480, 170, 80, 20);        
        tftglberlaku.setBounds(580, 170, 220, 20);

        add(btnSimpanPanel);
        add(btnHapusPanel);
        add(btnKeluarPanel);
        add(btnRefreshPanel);
        btnSimpanPanel.setBounds(150, 220, 80, 30);        
        btnHapusPanel.setBounds(450, 220, 80, 30);        
        btnKeluarPanel.setBounds(600, 220, 80, 30);        
        btnRefreshPanel.setBounds(300, 220, 80, 30);
        
        btnSimpanPanel.addActionListener((ActionEvent e) -> {
            if (tfNIS.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong!");
            } else {
                String nis = tfNIS.getText();
                String nama = tfNama.getText();
                String ttl = tfTTL.getText();
                String jenkel = null;
                    if (cbPerempuan.isSelected()) {
                        jenkel = "Perempuan";
                     } else if (cbLaki.isSelected() ) {
                        jenkel = "Laki-laki";
                    }
                String agama = (String) cmbAgama.getSelectedItem();
                String kelas = tfkelas.getText();
                String tgldaftar = tftgldaftar.getText();
                String tglberlaku = tftglberlaku.getText();
                
                this.simpanAnggota(nis, nama, ttl, jenkel, agama, tgldaftar, tglberlaku, kelas);
  
                String dataAnggota[][] = this.readAnggota();
                table.setModel(new JTable(dataAnggota,namaKolom).getModel());
            }
        });
        
        if (this.getBanyakData() != 0) {  
            String dataAnggota[][] = this.readAnggota();  
            table.setModel((new JTable(dataAnggota, namaKolom)).getModel());
             
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada!");
        }
        
        table.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = table.getSelectedRow();
               int kolom = table.getSelectedColumn();  
               
               String dataterpilih = table.getValueAt(baris, 0).toString();
               btnHapusPanel.addActionListener((ActionEvent f) -> {
                  hapusAnggota(dataterpilih);
                  String dataAnggota[][] = readAnggota();
                table.setModel(new JTable(dataAnggota,namaKolom).getModel());
                }); 
           }
        });

        btnKeluarPanel.addActionListener((ActionEvent e) -> {
           Menu menu = new Menu();
           dispose();
        });
        
        btnRefreshPanel.addActionListener((ActionEvent e) -> {
            tfNama.setText("");
            tfNIS.setText("");
            tfTTL.setText("");
            tftgldaftar.setText("");
            tftglberlaku.setText("");
            tfkelas.setText("");
        });
}
    
    public void simpanAnggota(String nis, String nama, String ttl, String jenkel, String agama, String tgldaftar, String tglberlaku, String kelas) {
        try{
            String query = "INSERT INTO `anggota`(`NIS`,`Nama`,`TTL`,`Jenkel`,`Agama`,`tgldaftar`,`tglberlaku`,`kelas`) VALUES ('"+nis+"','"+nama+"','"+ttl+"', '"+jenkel+"','"+agama+"','"+tgldaftar+"','"+tglberlaku+"','"+kelas+"')";
            statement = (Statement) koneksi.createStatement();
            statement.executeUpdate(query);
            System.out.println("Berhasil Ditambahkan!");
            JOptionPane.showMessageDialog(null,"Berhasil menambahkan "+nis+"!");
        }catch(Exception sql){
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }        
    }
    String[][] readAnggota() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][8];
            String query = "Select * from `anggota`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("NIS");
                data[jmlData][1] = resultSet.getString("Nama");
                data[jmlData][2] = resultSet.getString("TTL");
                data[jmlData][3] = resultSet.getString("Jenkel");
                data[jmlData][4] = resultSet.getString("Agama");
                data[jmlData][5] = resultSet.getString("tgldaftar");
                data[jmlData][6] = resultSet.getString("tglberlaku");
                data[jmlData][7] = resultSet.getString("kelas");
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
            String query = "SELECT * from `anggota`";
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

    void hapusAnggota(String nis) {
        try{
            String query = "DELETE FROM `anggota` WHERE `NIS` = '"+nis+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil menghapus "+nis+"!" );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }



}
