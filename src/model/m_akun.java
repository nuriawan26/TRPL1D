/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bedhu
 */
public class m_akun {

    protected koneksi con;

    public m_akun() throws SQLException {
        con = new koneksi();
    }

    public DefaultTableModel getTabel() throws SQLException {
        String judulKolom[] = {"Username", "Password", "Jabatan", "Nomor Telepon", "Tanggal Dibuat", "Atasan", "Alamat Apotek"};
        String query = "SELECT * FROM `akun` order by `tanggal_dibuat`";
        DefaultTableModel modelTabel = new DefaultTableModel(null, judulKolom);
        ResultSet hasil = con.getResult(query);
        while (hasil.next()) {
            String kolom[] = new String[judulKolom.length];
            for (int i = 0; i < kolom.length; i++) {
                kolom[i] = hasil.getString(i + 1);
            }
            modelTabel.addRow(kolom);
        }
        return modelTabel;
    }

    public DefaultTableModel getTabelPetugas(String supervisor) throws SQLException {
        String judulKolom[] = {"Username", "Password", "Jabatan", "Nomor Telepon", "Tanggal Dibuat", "Alamat Apotek"};
        String query = "SELECT `username`, `password`, `jabatan`, `nomor_telepon`, `tanggal_dibuat`, `alamat_apotek` FROM `akun` WHERE `username`='" + supervisor + "' or `atasan`='" + supervisor + "'";
        DefaultTableModel modelTabel = new DefaultTableModel(null, judulKolom);
        ResultSet hasil = con.getResult(query);
        while (hasil.next()) {
            String kolom[] = new String[judulKolom.length];
            for (int i = 0; i < kolom.length; i++) {
                kolom[i] = hasil.getString(i + 1);
            }
            modelTabel.addRow(kolom);
        }
        return modelTabel;
    }

    public boolean isUsernameExisted(String username) throws SQLException {
        String query = "select username from akun where username = '" + username + "'";
        ResultSet hasil = con.getResult(query);
        return hasil.next();
    }

    public boolean isPasswordValid(String username, String password) throws SQLException {
        String query = "select password from akun where username = '" + username + "'";
        ResultSet hasil = con.getResult(query);
        String dbPassword = null;
        hasil.next();
        dbPassword = hasil.getString(1);
        return dbPassword.equals(password);
    }

    public String getJabatan(String username) throws SQLException {
        String query = "select jabatan from akun where username = '" + username + "'";
        ResultSet hasil = con.getResult(query);
        hasil.next();
        String jabatan = (hasil.getString(1));
        return jabatan;
    }

    public String getAtasan(String username) throws SQLException {
        String query = "select atasan from akun where username = '" + username + "'";
        ResultSet hasil = con.getResult(query);
        hasil.next();
        String jabatan = (hasil.getString(1));
        return jabatan;
    }

    public void create(String username, String password, String jabatan, String nomorTelepon, String alamat) throws SQLException {
        String query = "INSERT INTO `akun`(`username`, `password`, `jabatan`, `nomor_telepon`, `tanggal_dibuat`, `alamat_apotek`) VALUES ('" + username + "','" + password + "','" + jabatan + "','" + nomorTelepon + "',CURRENT_TIMESTAMP,'" + alamat + "')";
        con.execute(query);
    }

    public void createPetugas(String username, String password, String nomorTelepon, String atasan) throws SQLException {
        String query = "INSERT INTO `akun`(`username`, `password`, `jabatan`, `nomor_telepon`, `tanggal_dibuat`, `atasan`) VALUES ('" + username + "','" + password + "','petugas gudang','" + nomorTelepon + "',CURRENT_TIMESTAMP,'" + atasan + "')";
        con.execute(query);
    }

    public void update(String username, String password, String jabatan, String nomorTelepon, String alamat, String usernamesebelumnya) throws SQLException {
        String query = "UPDATE `akun` set `username` = '" + username + "',`password` = '" + password + "',`jabatan` = '" + jabatan + "',`nomor_telepon` = '" + nomorTelepon + "',`alamat_apotek` = '" + alamat + "' where `username`  = '" + usernamesebelumnya + "'";
        con.execute(query);
    }

    public void updatePetugas(String username, String password, String nomorTelepon, String atasan, String usernamesebelumnya) throws SQLException {
        String query = "UPDATE `akun` set `username` = '" + username + "',`password` = '" + password + "',`jabatan` = 'petugas gudang',`nomor_telepon` = '" + nomorTelepon + "',`atasan` = '" + atasan + "' where `username`  = '" + usernamesebelumnya + "'";
        con.execute(query);
    }

    public void delete(String username) throws SQLException {
        String query = "DELETE from `akun` where `username` = '" + username + "'";
        con.execute(query);
    }
}
