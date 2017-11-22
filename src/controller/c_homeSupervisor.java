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
import view.v_homeSupervisor;

/**
 *
 * @author bedhu
 */
public class c_homeSupervisor {

    private v_homeSupervisor view;
    private String username;

    public c_homeSupervisor(String username) {
        this.username = username;
        view = new v_homeSupervisor();
        view.setLocationRelativeTo(null);
        try {
            view.setVisible(true);
            view.akunButton().addActionListener(new akun());
            view.stokObatButton().addActionListener(new stokObat());
            view.transaksiButton().addActionListener(new transaksi());
            view.prediksiButton().addActionListener(new prediksi());
            view.permintaanButton().addActionListener(new permintaan());
            view.logoutButton().addActionListener(new logout());
        } catch (Exception ex) {
            view.message("Koneksi ke database gagal");
            System.exit(0);
        }
    }

    private class akun implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.dispose();
                new c_mengelolahAkunPetugas(username);
            } catch (SQLException ex) {
                Logger.getLogger(c_homePetugas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class stokObat implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.dispose();
                new c_melihatStok(username);
            } catch (SQLException ex) {
                Logger.getLogger(c_homePetugas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class transaksi implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.dispose();
                new c_melihatHistori(username, "supervisor");
            } catch (SQLException ex) {
                Logger.getLogger(c_homePetugas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class prediksi implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.dispose();
                new c_prediksi(username);
            } catch (SQLException ex) {
                Logger.getLogger(c_homePetugas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class permintaan implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.dispose();
                new c_mengelolahPermintaan(username);
            } catch (SQLException ex) {
                Logger.getLogger(c_homePetugas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class logout implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.messageYesNo("Apakah anda yakin ingin logout?") == 0) {
                view.dispose();
                new c_login();
            }
        }
    }

}
