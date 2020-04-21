package kuispbo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Menu {
    public static void main(String[] args) {
        Menuu menu = new Menuu();
    }
}

    class Menuu extends JFrame {
         
        JLabel lTitle = new JLabel ("Menu Utama");
        JButton btnDataBuku = new JButton("Data Buku");
        JButton btnAnggota = new JButton("Data Anggota");
        JButton btnKaryawan = new JButton("Data Karyawan");
        JButton btnPeminjaman = new JButton("Data Peminjaman");
        
        public Menuu(){
        setTitle("PERPUSTAKAAN");
	setDefaultCloseOperation(3);
        setSize(485,320);
        setLocation(700,400);
        setLayout(null);
        
        add(lTitle);
        lTitle.setBounds(155, 20, 500, 50);
        lTitle.setFont(new Font("Segoe Script",Font.BOLD, 30));
        lTitle.setForeground(Color.red);
        add(btnAnggota);
        btnAnggota.setBounds(30, 80, 180, 60);
        add(btnDataBuku);
        btnDataBuku.setBounds(250, 80, 180, 60);
        add(btnKaryawan);
        btnKaryawan.setBounds(30, 170, 180, 60);
        add(btnPeminjaman);
        btnPeminjaman.setBounds(250, 170, 180, 60);
               
        setVisible(true);
        
        
        btnAnggota.addActionListener((ActionEvent e) -> {
           DataAnggota anggota = new DataAnggota();
           dispose();
        });
        
        btnDataBuku.addActionListener((ActionEvent e) -> {
           DataBuku daftarbuku = new DataBuku();
           dispose();
        });
       
        btnKaryawan.addActionListener((ActionEvent e) -> {
           DataKaryawan karyawan = new DataKaryawan();
           dispose();
        });
        
        btnPeminjaman.addActionListener((ActionEvent e) -> {
           DataPeminjaman peminjaman = new DataPeminjaman();
           dispose();
        });
    }    
    

}

