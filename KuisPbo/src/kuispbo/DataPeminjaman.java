package kuispbo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

class DataPeminjaman extends JFrame {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/kuis";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;    

    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"No Pinjam", "NIS", "Nama Siswa", "Kelas", "Kode", "Judul Buku", "Penerbit", "Tgl Pinjam", "Tgl Kembali", "Lama Pinjam", "Denda", "Petugas", "NIK"};    
    
    JLabel lTitle = new JLabel("DATA PENGEMBALIAN BUKU");    
    JLabel lno = new JLabel("Nomor Pinjam");
    JTextField tfno = new JTextField();
    JLabel lNIS= new JLabel("NIS");
    JTextField tfNIS = new JTextField();    
    JLabel lNama = new JLabel("Nama Siswa");
    JLabel tfNama = new JLabel();
    JLabel lkelas = new JLabel("Kelas");
    JLabel tfkelas = new JLabel();
    JLabel lKode = new JLabel("Kode Buku");
    JTextField tfKode = new JTextField();
    JLabel lJudul = new JLabel("Judul Buku");
    JLabel tfJudul = new JLabel("");
    JLabel lPenerbit = new JLabel("Penerbit");
    JLabel tfPenerbit = new JLabel("");
    JLabel ltglpinjam = new JLabel("Tgl Pinjam");
    JTextField tftglpinjam = new JTextField();
    JLabel ltglkembali = new JLabel("Tgl Kembali");
    JTextField tftglkembali = new JTextField();
    JLabel lLama = new JLabel("Lama Pinjam ");
    JLabel tfLama = new JLabel("");
    
    JLabel lDenda = new JLabel("Denda");
    JLabel tfDenda = new JLabel("");
    JLabel lNIK = new JLabel("NIK");
    JTextField tfNIK = new JTextField();
    JLabel lPetugas = new JLabel("Petugas");
    JLabel tfPetugas = new JLabel("");
    
    JTextField tfCari = new JTextField();
    JButton btnSimpanPanel = new JButton("Simpan");
    JButton btnRefreshPanel = new JButton("Refresh");
    JButton btnCariPanel = new JButton("Cari");
    JButton btnHapusPanel = new JButton("Hapus");
    JButton btnPrintPanel = new JButton("Print");
    JButton btnKeluarPanel = new JButton("Kembali");

    public DataPeminjaman(){
        try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }  
   
        setTitle("DATA PENGENMBALIAN BUKU");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(1260, 730);
        setLocation(310, 170);   
        
        tableModel = new DefaultTableModel(namaKolom,0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);  
        
        add(scrollPane);
        scrollPane.setBounds(20, 350, 1200, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        add(lTitle);
        lTitle.setBounds(490, 5, 450, 50);
        lTitle.setFont(new Font("Segoe Script",Font.BOLD, 19));
        lTitle.setForeground(Color.red);
        
        add(lno);
        add(tfno);
        add(lNIS);
        add(tfNIS);
        add(lNama);
        add(tfNama);
        add(lkelas);
        add(tfkelas);
        add(lKode);
        add(tfKode);
        add(lJudul);
        add(tfJudul);
        add(lPenerbit);
        add(tfPenerbit);
        add(ltglpinjam);
        add(tftglpinjam);
        add(ltglkembali);
        add(tftglkembali);
        add(lLama);
        add(tfLama);
        
        lno.setBounds(20, 50, 200, 20);        
        tfno.setBounds(150, 50, 200, 20);        
        lNIS.setBounds(20, 80, 200, 20);        
        tfNIS.setBounds(150, 80, 200, 20);        
        lNama.setBounds(20, 110, 200, 20);        
        tfNama.setBounds(150, 110, 200, 20);        
        lkelas.setBounds(20, 140, 200, 20);        
        tfkelas.setBounds(150, 140, 200, 20);       
        lKode.setBounds(20, 170, 200, 20);        
        tfKode.setBounds(150, 170, 200, 20);        
        lJudul.setBounds(20, 200, 200, 20);        
        tfJudul.setBounds(150, 200, 200, 20);        
        lPenerbit.setBounds(20, 230, 200, 20);        
        tfPenerbit.setBounds(150, 230, 200, 20);        
        ltglpinjam.setBounds(20, 260, 200, 20);        
        tftglpinjam.setBounds(150, 260, 200, 20);        
        ltglkembali.setBounds(20, 290, 200, 20);        
        tftglkembali.setBounds(150, 290, 200, 20);        
        lLama.setBounds(20, 320, 200, 20);        
        tfLama.setBounds(150, 320, 200, 20);

        add(lDenda);
        add(tfDenda);
        add(lNIK); 
        add(tfNIK);
        add(lPetugas);
        add(tfPetugas);
        
        
        lDenda.setBounds(530, 80, 200, 20);        
        tfDenda.setBounds(660, 80, 200, 20);       
        lNIK.setBounds(530, 110, 200, 20);        
        tfNIK.setBounds(660, 110, 200, 20);        
        lPetugas.setBounds(530, 140, 200, 20);        
        tfPetugas.setBounds(660, 140, 200, 20);
        
        add(btnSimpanPanel);
        add(btnRefreshPanel);
        add(btnCariPanel);
        add(btnHapusPanel);
        add(btnPrintPanel);
        add(btnKeluarPanel);
        add(tfCari);
        
        tfCari.setBounds(930, 320, 160, 20);
        btnSimpanPanel.setBounds(1100,110, 100, 25);        
        btnRefreshPanel.setBounds(1100, 80, 100, 25);
        btnCariPanel.setBounds(1100, 320, 100, 20);        
        btnHapusPanel.setBounds(1100, 140, 100, 25);        
        btnPrintPanel.setBounds(1100, 200, 100, 25);        
        btnKeluarPanel.setBounds(1100, 170, 100, 25);
        
    btnSimpanPanel.addActionListener((ActionEvent e) -> {
            if (tfKode.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong!");
            } else {
                String no = tfno.getText();
                String nis = tfNIS.getText();
                String kode = tfKode.getText();
                String tglpinjam = tftglpinjam.getText();
                String tglkembali = tftglkembali.getText();
                
                Date tglawal = (Date) Date.valueOf(tglpinjam);
                Date tglbatas = (Date)Date.valueOf(tglkembali);
                long lamadenda = Math.abs(tglbatas.getTime() - tglawal.getTime());
                long denda = lamadenda * 1000; 
                
                String nik = tfNIK.getText();
                this.simpanBuku(no, nis, kode, tglpinjam, tglkembali, lamadenda, denda, nik);
  
                String dataPinjam[][] = this.readBuku();
                table.setModel(new JTable(dataPinjam,namaKolom).getModel());
            }
        });
    
    if (this.getBanyakData() != 0) { 
            String dataPinjam[][] = this.readBuku(); 
            table.setModel((new JTable(dataPinjam, namaKolom)).getModel());
          
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
                  hapusBuku(dataterpilih);
                  String dataPinjam[][] = readBuku();
                table.setModel(new JTable(dataPinjam,namaKolom).getModel());
                });
            }
        });

    btnKeluarPanel.addActionListener((ActionEvent e) -> {
          Menu menu = new Menu();
           dispose();
        });
    
    btnRefreshPanel.addActionListener((ActionEvent e) -> {
          tfno.setText("");
          tfNIS.setText("");
          tfKode.setText("");
          tftglpinjam.setText("");
          tftglkembali.setText("");
          tfNIK.setText("");
    });
}

    public void simpanBuku(String no, String nis, String kode, String tglpinjam, String tglkembali, long lamadenda, long denda, String nik) {
        try{
            String query = "INSERT INTO `peminjaman`(`Nopinjam`,`NISP`,`KodebukuP`,`tglpinjam`,`tglkembali`,`LamaDenda`,`Denda`,`NIKP`) VALUES ('"+no+"','"+nis+"','"+kode+"', '"+tglpinjam+"','"+tglkembali+"','"+lamadenda+"','"+denda+"','"+nik+"')";
            statement = (Statement) koneksi.createStatement();
            statement.executeUpdate(query);
            System.out.println("Berhasil Ditambahkan");
            JOptionPane.showMessageDialog(null,"Berhasil menambahkan "+nis);
        }catch(Exception sql){
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
    }

    int getBanyakData() {
        int jmlData = 0;
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `peminjaman`";
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

    String[][] readBuku() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][13];
            String query = "Select `Nopinjam`,`NISP`,`Nama`,`kelas`,`KodebukuP`,`Judul`,`Penerbit`,`tglpinjam`,`tglkembali`,`LamaDenda`,`Denda`,`NamaKrw`,`NIKP` from `peminjaman` JOIN `buku` ON `KodebukuP`=`Kode_buku` JOIN `karyawan` ON `NIKP`=`NIK` JOIN `anggota` ON `NISP`=`NIS`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("Nopinjam");
                data[jmlData][1] = resultSet.getString("NISP");
                data[jmlData][2] = resultSet.getString("Nama");
                data[jmlData][3] = resultSet.getString("kelas");
                data[jmlData][4] = resultSet.getString("KodebukuP");
                data[jmlData][5] = resultSet.getString("Judul");
                data[jmlData][6] = resultSet.getString("Penerbit");
                data[jmlData][7] = resultSet.getString("tglpinjam");
                data[jmlData][8] = resultSet.getString("tglkembali");
                data[jmlData][9] = resultSet.getString("LamaDenda");
                data[jmlData][10] = resultSet.getString("Denda");
                data[jmlData][11] = resultSet.getString("NamaKrw");
                data[jmlData][12] = resultSet.getString("NIKP");
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

    void hapusBuku(String no) {
        try{
            String query = "DELETE FROM `peminjaman` WHERE `Nopinjam` = '"+no+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil menghapus "+no );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }


}
