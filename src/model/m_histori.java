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
public class m_histori {

    private koneksi con;

    public m_histori() throws SQLException {
        con = new koneksi();
    }

    public DefaultTableModel getTabel(String supervisor) throws SQLException {
        String judulKolom[] = {"Tanggal", "Nomor Obat", "Nama Obat", "Petugas", "Status", "Jumlah"};
        String query = "SELECT `tanggal`, h.`nomor_obat`, `nama_obat`,  `petugas`, `status`, `jumlah`  FROM `histori` h JOIN `obat` o ON (h.`nomor_obat`=o.`nomor_obat`) JOIN `akun` a ON (h.`petugas`=a.`username`) WHERE `atasan`= '" + supervisor + "' ORDER BY `tanggal`";
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

    public void create(String nomorObat, String usernamePetugas, String status, int jumlah) throws SQLException {
        String query = "INSERT INTO `histori`(`nomor_obat`, `petugas`, `status`, `jumlah`) VALUES ('" + nomorObat + "','" + usernamePetugas + "','" + status + "','" + jumlah + "')";
        con.execute(query);
    }

    public int[] getPermintaanBulanan(String nomorObat, String supervisor) throws SQLException {
        String query = "SELECT `nomor_obat`, TIMESTAMPDIFF(month, `tanggal`, now()) AS selisihBulan, year(`tanggal`) AS `tahun`, sum(`jumlah`) AS total FROM `histori` h JOIN `akun` a ON (h.`petugas`=a.`username`) WHERE `status` = 'pengurangan' and TIMESTAMPDIFF(month, `tanggal`, now()) <= 3 and `nomor_obat` = '" + nomorObat + "' AND `atasan` = '" + supervisor + "' group by `nomor_obat`, month(`tanggal`), year(`tanggal`) order BY TIMESTAMPDIFF(month, `tanggal`, now())";
        int list[];
        ResultSet hasil = con.getResult(query);
        int count = 0;
        while (hasil.next()) {
            count++;
        }
        hasil.beforeFirst();
        list = new int[count];
        int i = 0;
        while (hasil.next()) {
            list[i] = -hasil.getInt(4);
            i++;
        }
        return list;
    }
}
