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
public class m_permintaan {

    private koneksi con;

    public m_permintaan() throws SQLException {
        con = new koneksi();
    }

    public DefaultTableModel getTabel() throws SQLException {
        String judulKolom[] = {"ID Permintaan", "Nomor Obat", "Jumlah", "Supervisor","Nomor Telepon","Alamat Apotek", "Tanggal Permintaan", "Status"};
        String query = "SELECT `id_permintaan`, `nomor_obat`, `jumlah_permintaan`, `supervisor`, `nomor_telepon`, `alamat_apotek`, `tanggal_permintaan`, `status` FROM `permintaan` p JOIN `akun` a ON (p.`supervisor`=a.`username`)";
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

    public DefaultTableModel getTabel(String supervisor) throws SQLException {
        String judulKolom[] = {"ID Permintaan", "Nomor Obat", "Jumlah", "Supervisor", "Tanggal Permintaan", "Status"};
        String query = "SELECT * FROM `permintaan` where `supervisor`='" + supervisor + "'";
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

    public int getKuota(int idPermintaan) throws SQLException {
        String query = "select jumlah_permintaan from permintaan where id_permintaan = " + idPermintaan;
        ResultSet hasil = con.getResult(query);
        hasil.next();
        int kuota = hasil.getInt(1);
        return kuota;
    }

    public void create(String nomor, int jumlah, String supervisor) throws SQLException {
        String query = "INSERT INTO `permintaan`(`nomor_obat`, `jumlah_permintaan`, `supervisor`) VALUES ('" + nomor + "','" + jumlah + "','" + supervisor + "')";
        con.execute(query);
    }

    public void update(int idPermintaan) throws SQLException {
        String query = "UPDATE `permintaan` SET `status`='terpenuhi' WHERE `id_permintaan`=" + idPermintaan;
        con.execute(query);
    }

}
