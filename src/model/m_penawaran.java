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
public class m_penawaran {

    private koneksi con;

    public m_penawaran() throws SQLException {
        con = new koneksi();
    }

    public DefaultTableModel getTabel(int idPermintaan) throws SQLException {
        String judulKolom[] = {"ID Penawaran", "Supplier", "Jumlah", "Harga", "Status", "Jumlah Diminta"};
        String query = "SELECT `id_penawaran`, `supplier`, `jumlah_obat`, `harga_obat`, `status`, `jumlah_diminta` FROM `penawaran` where `id_permintaan` = " + idPermintaan;
        DefaultTableModel modelTabel = new DefaultTableModel(null, judulKolom);
        ResultSet hasil = con.getResult(query);
        while (hasil.next()) {
            String kolom[] = new String[judulKolom.length];
            for (int i = 0; i < kolom.length; i++) {
                if (i == kolom.length - 2) {
                    if (hasil.getBoolean(i + 1)) {
                        kolom[i] = "Diterima";
                    } else {
                        kolom[i] = "";
                    }
                } else {
                    kolom[i] = hasil.getString(i + 1);
                }
            }
            modelTabel.addRow(kolom);
        }
        return modelTabel;
    }

    public DefaultTableModel getTabel(int idPermintaan, String supplier) throws SQLException {
        String judulKolom[] = {"ID Penawaran", "Jumlah", "Harga", "Status", "Jumlah Diminta"};
        String query = "SELECT `id_penawaran`,  `jumlah_obat`, `harga_obat`, `status`, `jumlah_diminta` FROM `penawaran` where `id_permintaan` = " + idPermintaan + " and `supplier` = '" + supplier + "'";
        DefaultTableModel modelTabel = new DefaultTableModel(null, judulKolom);
        ResultSet hasil = con.getResult(query);
        while (hasil.next()) {
            String kolom[] = new String[judulKolom.length];
            for (int i = 0; i < kolom.length; i++) {
                if (i == kolom.length - 2) {
                    if (hasil.getBoolean(i + 1)) {
                        kolom[i] = "Diterima";
                    } else {
                        kolom[i] = "";
                    }
                } else {
                    kolom[i] = hasil.getString(i + 1);
                }
            }
            modelTabel.addRow(kolom);
        }
        return modelTabel;
    }

    public DefaultTableModel getTabelNull() throws SQLException {
        String judulKolom[] = {"ID Penawaran", "Jumlah", "Harga", "Status", "Jumlah Diminta"};
        DefaultTableModel modelTabel = new DefaultTableModel(null, judulKolom);
        return modelTabel;
    }

    public int getTotal(int idPermintaan) throws SQLException {
        String query = "select jumlah_diminta from penawaran where id_permintaan = " + idPermintaan + " and status = 1";
        ResultSet hasil = con.getResult(query);
        int kuota = 0;
        while (hasil.next()) {
            kuota += (hasil.getInt(1));
        }
        return kuota;
    }

    public void create(int idPermintaan, String username, int jumlah, int harga) throws SQLException {
        String query = "INSERT INTO `penawaran`(`id_permintaan`, `supplier`, `jumlah_obat`, `harga_obat`) VALUES ('" + idPermintaan + "','" + username + "','" + jumlah + "','" + harga + "')";
        con.execute(query);
    }

    public void update(int idPermintaan, int jumlah) throws SQLException {
        String query = "UPDATE `penawaran` SET `status`= 1,`jumlah_diminta` = '" + jumlah + "' WHERE `id_penawaran` = '" + idPermintaan + "'";
        con.execute(query);
    }
}
