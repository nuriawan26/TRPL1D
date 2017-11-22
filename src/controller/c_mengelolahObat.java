package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import model.m_obat;
import view.v_mengelolahObat;

public class c_mengelolahObat {

    private v_mengelolahObat view;
    private m_obat modelO;
    private String username;

    public c_mengelolahObat(String username) throws SQLException {
        this.username = username;
        view = new v_mengelolahObat();
        view.setLocationRelativeTo(null);
        try {
            modelO = new m_obat();
            view.setTabel(modelO.getTabel());
            view.setVisible(true);
            view.kembaliButton().addActionListener(new kembali());
            view.tambahButton().addActionListener(new tambah());
            view.ubahButton().addActionListener(new ubah());
            view.hapusButton().addActionListener(new hapus());
            view.simpanButton().addActionListener(new simpan());
            view.batalButton().addActionListener(new batal());
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
            new c_homeAdmin(username);
        }
    }

    private class tambah implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.ubahButton().setEnabled(false);
            view.hapusButton().setEnabled(false);

            view.nomorField().setEnabled(true);
            view.namaField().setEnabled(true);
            view.deskripsiField().setEnabled(true);

            view.simpanButton().setEnabled(true);
            view.batalButton().setEnabled(true);
        }
    }

    private class ubah implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.tabel().getSelectedRow() != -1) {
                view.nomorField().setText(view.getCell(0));
                view.namaField().setText(view.getCell(1));
                view.deskripsiField().setText(view.getCell(2));

                view.nomorField().setEnabled(true);
                view.namaField().setEnabled(true);
                view.deskripsiField().setEnabled(true);

                view.tambahButton().setEnabled(false);
                view.hapusButton().setEnabled(false);

                view.simpanButton().setEnabled(true);
                view.batalButton().setEnabled(true);
            } else {
                view.message("Pilih baris yang ingin diubah");
            }
        }
    }

    private class hapus implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.tabel().getSelectedRow() != -1) {
                try {
                    if (view.messageYesNo("Data yang telah dihapus tidak dapat dikembalikan.\nHapus Data?") == 0) {
                        modelO.delete(view.getCell(0));
                        view.setTabel(modelO.getTabel());
                        reset();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                view.message("Pilih baris yang ingin dihapus");
            }
        }
    }

    private class simpan implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.tambahButton().isEnabled()) {
                if (!view.getNomor().isEmpty() && !view.getNama().isEmpty() && !view.getDeskripsi().isEmpty()) {
                    try {
                        if (!modelO.isNomorExisted(view.getNomor())) {
                            modelO.create(view.getNomor(), view.getNama(), view.getDeskripsi());
                            view.setTabel(modelO.getTabel());
                            reset();
                        } else {
                            view.message("Nomor Obat telah digunakan");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    view.message("Data ada yang kosong");
                }
            } else if (view.ubahButton().isEnabled()) {
                if (!view.getNomor().isEmpty() && !view.getNama().isEmpty() && !view.getDeskripsi().isEmpty()) {
                    try {
                        modelO.update(view.getNomor(), view.getNama(), view.getDeskripsi(), view.getCell(0));
                        view.setTabel(modelO.getTabel());
                        reset();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    view.message("Data ada yang kosong");
                }
            }
        }
    }

    private class batal implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
        }
    }

    private void reset() {
        view.tambahButton().setEnabled(true);
        view.ubahButton().setEnabled(true);
        view.hapusButton().setEnabled(true);
        view.simpanButton().setEnabled(false);
        view.batalButton().setEnabled(false);
        view.nomorField().setEnabled(false);
        view.namaField().setEnabled(false);
        view.deskripsiField().setEnabled(false);
        view.nomorField().setText("");
        view.namaField().setText("");
        view.deskripsiField().setText("");
    }

}
