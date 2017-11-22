package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import model.m_stok;
import view.v_melihatStok;

public class c_melihatStok {

    private v_melihatStok view;
    private m_stok modelO;
    private String username;

    public c_melihatStok(String username) throws SQLException {
        this.username = username;
        view = new v_melihatStok();
        view.setLocationRelativeTo(null);
        try {
            modelO = new m_stok();
            view.setTabel(modelO.getTabel(username));
            view.setVisible(true);
            view.kembaliButton().addActionListener(new kembali());
        } catch (Exception ex) {
            view.message("Koneksi ke database gagal");
            System.exit(0);
        }
    }

    private class kembali implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            new c_homeSupervisor(username);
        }
    }

}
