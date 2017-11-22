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
public class m_obat {

    private koneksi con;

    public m_obat() throws SQLException {
        con = new koneksi();
    }

    public DefaultTableModel getTabel() throws SQLException {
        String judulKolom[] = {"Nomor Obat", "Nama Obat", "Deskripsi"};
        String query = "SELECT * FROM `obat`";
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

    public boolean isNomorExisted(String nomor) throws SQLException {
        String query = "select nomor_obat from obat where nomor_obat = '" + nomor + "'";
        ResultSet hasil = con.getResult(query);
        return hasil.next();
    }

    public void create(String nomor, String nama, String deskripsi) throws SQLException {
        String query = "INSERT INTO `obat`(`nomor_obat`, `nama_obat`, `deskripsi`) VALUES ('" + nomor + "','" + nama + "','" + deskripsi + "')";
        con.execute(query);
    }

    public void update(String nomor, String nama, String deskripsi, String nomorsebelumnya) throws SQLException {
        String query = "UPDATE `obat` set `nomor_obat` = '" + nomor + "',`nama_obat` = '" + nama + "',`deskripsi` = '" + deskripsi + "' where `nomor_obat` = '" + nomorsebelumnya + "'";
        con.execute(query);
    }

    public void delete(String nomor) throws SQLException {
        String query = "DELETE from `obat` where nomor_obat = '" + nomor + "'";
        con.execute(query);
    }

    public String getNamaObat(String nomorObat) throws SQLException {
        String query = "select nama_obat from obat where nomor_obat = '" + nomorObat + "'";
        ResultSet hasil = con.getResult(query);
        hasil.next();
        String nama = (hasil.getString(1));
        return nama;
    }

    public String[] getListNomorObat() throws SQLException {
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
