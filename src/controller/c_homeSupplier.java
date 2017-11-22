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
import view.v_homeSupplier;

/**
 *
 * @author bedhu
 */
public class c_homeSupplier {
    
    private v_homeSupplier view;
    private String username;
    
    public c_homeSupplier(String username) {
        this.username = username;
        view = new v_homeSupplier();
        view.setLocationRelativeTo(null);
        try {
            view.setVisible(true);
            view.penawaranButton().addActionListener(new penawaran());
            view.logoutButton().addActionListener(new logout());
        } catch (Exception ex) {
            view.message("Koneksi ke database gagal");
            System.exit(0);
        }
    }
    
    private class penawaran implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.dispose();
                new c_mengelolahPenawaran(username);
            } catch (SQLException ex) {
                Logger.getLogger(c_homeSupplier.class.getName()).log(Level.SEVERE, null, ex);
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
