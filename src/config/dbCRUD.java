package config;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class dbCRUD {
    
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/perusahaan";
    private static final String username = "root";
    private static final String password = "";
    
    public dbCRUD () {}

    public dbCRUD (String url, String user, String pass) {
        try (Connection Koneksi = DriverManager.getConnection(url, user, pass)) {
            Driver mysqldriver = new com.mysql.jdbc.Driver(); 
            DriverManager.registerDriver(mysqldriver);

            System.out.println("Berhasil"); 
        } catch (SQLException error) {
            System.err.println(error.toString());
        }
    }
    
    public Connection getKoneksi () throws SQLException {
        try { 
            Driver mysqldriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);

        } catch (SQLException e) {
            System.err.println(e.toString());
        }

        return DriverManager.getConnection(this.jdbcURL, this.username, this.password);
    }
    
    // Mengecek apakah primary key sudah ada di tabel
    public boolean duplicateKey(String table, String primaryKey, String value) { 
        boolean exists = false;

        try {
            Statement sts = getKoneksi().createStatement();
            ResultSet rs = sts.executeQuery("SELECT * FROM " + table + " WHERE " + primaryKey + " ='" + value + "'");
            exists = rs.next();
            rs.close();
            sts.close();
            getKoneksi().close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
        return exists;
    }
    
    // Menyimpan data ke tabel keuangan
    public void insertKeuangan(String idKeuangan, String Faktur, String tanggalPembayaran, String jumlahBayar) {
        try {
            if (duplicateKey("keuangan", "id_keuangan", idKeuangan)) {
                JOptionPane.showMessageDialog(null, "ID Keuangan sudah Terdaftar");
            } else { 
                String SQL = "INSERT INTO keuangan (id_keuangan, faktur, tanggal_pembayaran, jumlah_bayar) VALUES (?, ?, ?, ?)";
                PreparedStatement simpan = getKoneksi().prepareStatement(SQL);
                simpan.setString(1, idKeuangan);
                simpan.setString(2, Faktur);
                simpan.setString(3, tanggalPembayaran);
                simpan.setString(4, jumlahBayar);
                simpan.executeUpdate();
                simpan.close();
                getKoneksi().close();
                System.out.println("Data Keuangan Berhasil Disimpan");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    // Menyimpan data ke tabel pelanggan
    public void insertPelanggan(String idPelanggan, String namaPelanggan, String alamat, String telpon, String email) {
        try {
            if (duplicateKey("pelanggan", "id_pelanggan", idPelanggan)) {
                JOptionPane.showMessageDialog(null, "ID Pelanggan sudah Terdaftar");
            } else { 
                String SQL = "INSERT INTO pelanggan (id_pelanggan, nama_pelanggan, alamat, telpon, email) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement simpan = getKoneksi().prepareStatement(SQL);
                simpan.setString(1, idPelanggan);
                simpan.setString(2, namaPelanggan);
                simpan.setString(3, alamat);
                simpan.setString(4, telpon);
                simpan.setString(5, email);
                simpan.executeUpdate();
                simpan.close();
                getKoneksi().close();
                System.out.println("Data Pelanggan Berhasil Disimpan");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    // Menghapus data dari tabel keuangan
    public void deleteKeuangan(String idKeuangan) {
        try {
            String SQL = "DELETE FROM keuangan WHERE id_keuangan = ?";
            PreparedStatement hapus = getKoneksi().prepareStatement(SQL);
            hapus.setString(1, idKeuangan);
            hapus.executeUpdate();
            hapus.close();
            getKoneksi().close();
            JOptionPane.showMessageDialog(null, "Data Keuangan Berhasil Dihapus");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    // Menghapus data dari tabel pelanggan
    public void deletePelanggan(String idPelanggan) {
        try {
            String SQL = "DELETE FROM pelanggan WHERE id_pelanggan = ?";
            PreparedStatement hapus = getKoneksi().prepareStatement(SQL);
            hapus.setString(1, idPelanggan);
            hapus.executeUpdate();
            hapus.close();
            getKoneksi().close();
            JOptionPane.showMessageDialog(null, "Data Pelanggan Berhasil Dihapus");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
}