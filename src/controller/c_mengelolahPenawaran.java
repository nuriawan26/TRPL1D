/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.m_penawaran;
import model.m_permintaan;
import view.v_mengelolahPenawaran;

/**
 *
 * @author bedhu
 */
public class c_mengelolahPenawaran {

    private v_mengelolahPenawaran view;
    private m_permintaan modelP;
    private m_penawaran modelPE;
    private String username;

    public c_mengelolahPenawaran(String username) throws SQLException {
        this.username = username;
        view = new v_mengelolahPenawaran();
        view.setLocationRelativeTo(null);
        try {
            modelP = new m_permintaan();
            modelPE = new m_penawaran();
            view.setTabelPermintaan(modelP.getTabel());
            view.setVisible(true);
            view.kembaliButton().addActionListener(new kembali());
            view.tambahButton().addActionListener(new tambah());
            view.tabelPermintaan().getSelectionModel().addListSelectionListener(new permintaan());
        } catch (Exception ex) {
            ex.printStackTrace();
            view.message("Koneksi ke database gagal");
            System.exit(0);
        }
    }

    private class permintaan implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            try {
                view.setTabelPenawaran(modelPE.getTabel(Integer.parseInt(view.getCell(0)), username));
                view.tambahButton().setEnabled(true);
                view.jumlahObatField().setEnabled(true);
                view.hargaObatField().setEnabled(true);
            } catch (SQLException ex) {
                Logger.getLogger(c_mengelolahPenawaran.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class kembali implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            new c_homeSupplier(username);
        }
    }

    private class tambah implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int idPermintaan = Integer.parseInt(view.getCell(0));
                modelPE.create(idPermintaan, username, view.getJumlah(), view.getHarga());
                view.setTabelPenawaran(modelPE.getTabel(idPermintaan, username));
                reset();
            } catch (SQLException ex) {
                Logger.getLogger(c_mengelolahPenawaran.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void reset() {
        view.jumlahObatField().setText("");
        view.hargaObatField().setText("");
    }
}
