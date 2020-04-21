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
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;

class DataKaryawan extends JFrame {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/kuis";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;    

    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"NIS", "Nama", "Jenis Kelamin", "Golongan", "Jabatan", "Tunjangan", "Total Gaji"};
    
    JLabel lTitle = new JLabel("DATA KARYAWAN");    
    JLabel lNIK = new JLabel("NIK");
    JTextField tfNIK = new JTextField();
    JLabel lNama= new JLabel("Nama");
    JTextField tfNama = new JTextField();    
    JLabel lJenkel = new JLabel("Jenis Kelamin");
        JCheckBox cbPerempuan = new JCheckBox("Perempuan ");
        JCheckBox cbLaki = new JCheckBox("Laki-laki");
    JLabel lGolongan = new JLabel("Golongan");
    JTextField tfGolongan = new JTextField();
    JLabel lJabatan = new JLabel("Jabatan");
    JLabel tfJabatan = new JLabel("");
    JLabel lTunjangan = new JLabel("Tunjangan ");
    JLabel tfTunjangan = new JLabel("");
    JLabel lGaji = new JLabel("Total Gaji");
    JLabel tfGaji = new JLabel("");
       
    JTextField tfCari = new JTextField();
    JButton btnSimpanPanel = new JButton("Simpan");
    JButton btnHapusPanel = new JButton("Hapus");
    JButton btnKeluarPanel = new JButton("Kembali");
    JButton btnRefreshPanel = new JButton("Refresh");
    JButton btnCariPanel = new JButton("Cari");
    
    public DataKaryawan(){
        try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }  
    
        setTitle("DATA KARYAWAN");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(1135, 365);
        setLocation(400, 350);
        
        tableModel = new DefaultTableModel(namaKolom,0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        
        add(scrollPane);
        scrollPane.setBounds(400, 70, 700, 220);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    
        add(lTitle);
        lTitle.setBounds(500, 10, 200, 50);
        lTitle.setFont(new Font("Segoe Script",Font.BOLD, 19));
        lTitle.setForeground(Color.red);
        
        add(lNIK);
        add(tfNIK);
        add(lNama);
        add(tfNama);
        add(lJenkel);
        add(cbPerempuan);
        add(cbLaki);
        add(lGolongan);
        add(tfGolongan);
        add(lJabatan);
        add(tfJabatan);
        add(lTunjangan);
        add(tfTunjangan);
        add(lGaji);
        add(tfGaji);
        
        lNIK.setBounds(20, 60, 80, 20);        
        tfNIK.setBounds(130, 60, 120, 20);        
        lNama.setBounds(20, 90, 120, 20);        
        tfNama.setBounds(130, 90, 120, 20);        
        lJenkel.setBounds(20, 120, 100, 20);        
        cbPerempuan.setBounds(125, 120, 95, 20);        
        cbLaki.setBounds(220, 120, 80, 20);
        lGolongan.setBounds(20, 150, 100, 20);        
        tfGolongan.setBounds(130, 150, 100, 20);        
        lJabatan.setBounds(20, 180,100, 20);        
        tfJabatan.setBounds(130, 180,100, 20);        
        lTunjangan.setBounds(20, 210, 100, 20);        
        tfTunjangan.setBounds(130, 210, 100, 20);        
        lGaji.setBounds(20, 240, 100, 20);        
        tfGaji.setBounds(130, 240, 100, 20);

        add(tfCari);
        add(btnSimpanPanel);
        add(btnCariPanel);
        add(btnKeluarPanel);
        add(btnHapusPanel);
        add(btnRefreshPanel);
        
        tfCari.setBounds(850, 40, 140, 20);        
        btnSimpanPanel.setBounds(300, 110, 80, 20);        
        btnHapusPanel.setBounds(300,230,80,20);        
        btnCariPanel.setBounds(1000, 40, 80, 20);        
        btnKeluarPanel.setBounds(300, 200, 80, 20);        
        btnRefreshPanel.setBounds(300, 140, 80, 20);

        btnSimpanPanel.addActionListener((ActionEvent e) -> {
            if (tfNIK.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String nik = tfNIK.getText();
                String nama = tfNama.getText();
                String jenkel = null;
                    if (cbPerempuan.isSelected()) {
                        jenkel = "Perempuan";
                    } else if (cbLaki.isSelected() ) {
                        jenkel = "Laki-laki";
                    }
                String golongan = tfGolongan.getText();
                String jabatan = tfJabatan.getText();
                String tunjangan = tfTunjangan.getText();
                String gaji = tfGaji.getText();
                
                this.simpanKaryawan(nik, nama, jenkel, golongan, jabatan, tunjangan, gaji);
                String dataKaryawan[][] = readKaryawan();
                table.setModel(new JTable(dataKaryawan,namaKolom).getModel());
            }
        });
        
        if (this.getBanyakData() != 0) { 
            String dataKaryawan[][] = this.readKaryawan();  
            table.setModel((new JTable(dataKaryawan, namaKolom)).getModel());
             
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
                  hapusKaryawan(dataterpilih);
                  String dataKaryawan[][] = readKaryawan();
                table.setModel(new JTable(dataKaryawan,namaKolom).getModel());
                });
               
            }
        });
        
        btnKeluarPanel.addActionListener((ActionEvent e) -> {
           Menu menu = new Menu();
           dispose();
        });
        
        btnRefreshPanel.addActionListener((ActionEvent e) -> {
             tfNIK.setText("");
             tfNama.setText("");
             tfGolongan.setText("");
             tfJabatan.setText("");
             tfTunjangan.setText("");
             tfGaji.setText("");
        });
        tfGolongan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String golongan = tfGolongan.getText();  
                updateFieldState(golongan);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String golongan = tfGolongan.getText();  
                updateFieldState(golongan);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String golongan = tfGolongan.getText();
                updateFieldState(golongan);
            }
            protected void updateFieldState(String golongan) {
                
                int Bayaran=0;
                int Tunjangan=0;
                String Jabatan=null;
                if (golongan == "1") {
                    Jabatan = "Manager";
                    Tunjangan = 700000;
                    Bayaran = 1000000;
                } else  if (golongan == "2") {
                    Jabatan = "Supervisor";
                    Tunjangan = 500000;
                    Bayaran = 900000;
                } else  if (golongan == "3") {
                    Jabatan = "Staff";
                    Tunjangan = 300000;
                    Bayaran = 800000;
                } else  if (golongan == "4") {
                    Jabatan = "Assisten Staff";
                    Tunjangan = 250000;
                    Bayaran = 750000; 
                } else {
                    Jabatan = "Honorer";
                    Tunjangan = 200000;
                    Bayaran = 650000;                      
                } 
    
                int Gaji = Bayaran + Tunjangan;
                
                tfJabatan.setText(Jabatan);
                tfTunjangan.setText(Integer.toString(Tunjangan));
                tfGaji.setText(Integer.toString(Gaji));
                }
        });

}

    public void simpanKaryawan(String nik, String nama, String jenkel, String golongan, String jabatan, String tunjangan, String gaji) {
        try{
            String query = "INSERT INTO `karyawan`(`NIK`,`NamaKrw`,`Jenkel`,`Golongan`,`Jabatan`,`Tunjangan`,`Gaji`) VALUES ('"+nik+"','"+nama+"','"+jenkel+"', '"+golongan+"','"+jabatan+"','"+tunjangan+"','"+gaji+"')";
        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        System.out.println("Berhasil Ditambahkan");
        JOptionPane.showMessageDialog(null,"Berhasil menambahkan "+nama);
        }catch(Exception sql){
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
        
    }
    int getBanyakData() {
        int jmlData = 0;
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `karyawan`";
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

    String[][] readKaryawan() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][7];
            String query = "Select * from `karyawan`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("NIK");
                data[jmlData][1] = resultSet.getString("Namakrw");
                data[jmlData][2] = resultSet.getString("Jenkel");
                data[jmlData][3] = resultSet.getString("Golongan");
                data[jmlData][4] = resultSet.getString("Jabatan");
                data[jmlData][5] = resultSet.getString("Tunjangan");
                data[jmlData][6] = resultSet.getString("Gaji");
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

    void hapusKaryawan(String nik) {
        try{
            String query = "DELETE FROM `karyawan` WHERE `NIK` = '"+nik+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil menghapus "+nik );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }

}


