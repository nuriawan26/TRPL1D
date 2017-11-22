package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import model.m_akun;
import model.m_histori;
import view.v_melihatHistori;

public class c_melihatHistori {

    private v_melihatHistori view;
    private m_histori modelT;
    private m_akun modelA;
    private String username;
    private String homesebelumnya;

    public c_melihatHistori(String username, String homesebelumnya) throws SQLException {
        this.username = username;
        this.homesebelumnya = homesebelumnya;
        this.modelA = new m_akun();
        this.view = new v_melihatHistori();
        if (homesebelumnya == "supervisor") {
            view.setBackground("/gambar/supervisiorTransaksi.png");
        }
        view.setLocationRelativeTo(null);
        try {
            modelT = new m_histori();
            if (homesebelumnya == "supervisor") {
                view.setTabel(modelT.getTabel(username));
            } else {
                view.setTabel(modelT.getTabel(modelA.getAtasan(username)));
            }
            view.setVisible(true);
            view.kembaliButton().addActionListener(new kembali());
        } catch (Exception ex) {
            ex.printStackTrace();
            view.message("Koneksi ke database gagal");
            System.exit(0);
        }
    }

    private class kembali implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            if (homesebelumnya == "petugas") {
                new c_homePetugas(username);
            } else if (homesebelumnya == "supervisor") {
                new c_homeSupervisor(username);
            }
        }
    }
}
