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
import model.m_akun;
import view.v_login;

/**
 *
 * @author bedhu
 */
public class c_login {

    private v_login view;
    private m_akun model;

    public c_login() {
        view = new v_login();
        view.setLocationRelativeTo(null);
        try {
            model = new m_akun();
            view.setVisible(true);
            view.loginButton().addActionListener(new login());
            view.exitButton().addActionListener(new exit());
        } catch (Exception ex) {
            ex.printStackTrace();
            view.message("Koneksi ke database gagal");
            System.exit(0);
        }
    }

    private class login implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (model.isUsernameExisted(view.getUsername())) {
                    if (model.isPasswordValid(view.getUsername(), view.getPassword())) {
                        switch (model.getJabatan(view.getUsername())) {
                            case "supervisor":
                                new c_homeSupervisor(view.getUsername());
                                break;
                            case "petugas gudang":
                                new c_homePetugas(view.getUsername());
                                break;
                            case "supplier":
                                new c_homeSupplier(view.getUsername());
                                break;
                            case "admin":
                                new c_homeAdmin(view.getUsername());
                                break;
                            default:
                                view.message("Error login");
                                break;
                        }
                        view.dispose();
                    } else {
                        view.message("Password salah");
                    }
                } else {
                    view.message("Username tidak terdaftar");
                }
            } catch (SQLException ex) {
                view.message("Gagal login");
                Logger.getLogger(c_login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class exit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
