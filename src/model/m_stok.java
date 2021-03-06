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
public class m_stok {

    private koneksi con;

    public m_stok() throws SQLException {
        con = new koneksi();
    }

    public DefaultTableModel getTabel(String supervisor) throws SQLException {
        String judulKolom[] = {"Nomor Obat", "Nama Obat", "Deskripsi", "Jumlah Stok"};
        String query = "SELECT s.`nomor_obat`, `nama_obat`, `deskripsi`, `jumlah` FROM `stok` s JOIN `obat` o ON (s.`nomor_obat`=o.`nomor_obat`) JOIN `akun` a ON (s.`supervisor`=a.`username`) WHERE `supervisor`='" + supervisor + "'";
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

    public boolean isNomorExisted(String nomor, String supervisor) throws SQLException {
        String query = "select nomor_obat from stok where `nomor_obat` = '" + nomor + "' and `supervisor` = '" + supervisor + "'";
        ResultSet hasil = con.getResult(query);
        return hasil.next();
    }

    public void create(String nomor, int jumlah, String supervisor) throws SQLException {
        String query = "INSERT INTO `stok`(`nomor_obat`, `jumlah`, `supervisor`) VALUES ('" + nomor + "','" + jumlah + "','" + supervisor + "')";
        con.execute(query);
    }

    public void update(String nomor, int jumlah, String supervisor) throws SQLException {
        String query = "UPDATE `stok` set `nomor_obat` = '" + nomor + "',`jumlah` = '" + jumlah + "',`supervisor` = '" + supervisor + "' where `nomor_obat` = '" + nomor + "' and `supervisor` = '" + supervisor + "'";
        con.execute(query);
    }

    public void delete(String nomor, String supervisor) throws SQLException {
        String query = "DELETE from `stok` where `nomor_obat` = '" + nomor + "' and `supervisor` = '" + supervisor + "'";
        con.execute(query);
    }

    public String getNamaObat(String nomorObat) throws SQLException {
        String query = "select nama_obat from obat where nomor_obat = '" + nomorObat + "'";
        ResultSet hasil = con.getResult(query);
        hasil.next();
        String nama = (hasil.getString(1));
        return nama;
    }

    public String[] getListObat() throws SQLException {
        String query = "select nomor_obat from obat";
        String list[];
        ResultSet hasil = con.getResult(query);
        int count = 0;
        while (hasil.next()) {
            count++;
        }
        hasil.beforeFirst();
        list = new String[count];
        int i = 0;
        while (hasil.next()) {
            list[i] = hasil.getString(1);
            i++;
        }
        return list;
    }

    public String[] getListNamaObat() throws SQLException {
        String query = "select nama_obat from obat";
        String list[];
        ResultSet hasil = con.getResult(query);
        int count = 0;
        while (hasil.next()) {
            count++;
        }
        hasil.beforeFirst();
        list = new String[count];
        int i = 0;
        while (hasil.next()) {
            list[i] = hasil.getString(1);
            i++;
        }
        return list;
    }
}
