package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.m_akun;
import view.v_mengelolahAkun;

public class c_mengelolahAkun {

    private v_mengelolahAkun view;
    private m_akun model;
    private String username;

    public c_mengelolahAkun(String username) throws SQLException {
        this.username = username;
        view = new v_mengelolahAkun();
        view.setLocationRelativeTo(null);
        try {
            model = new m_akun();
            view.setTabel(model.getTabel());
            view.setVisible(true);
            view.jabatanField().addActionListener(new jabatan());
            view.tambahButton().addActionListener(new tambah());
            view.ubahButton().addActionListener(new ubah());
            view.hapusButton().addActionListener(new hapus());
            view.simpanButton().addActionListener(new simpan());
            view.batalButton().addActionListener(new batal());
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
            new c_homeAdmin(username);
        }
    }

    private class tambah implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.usernameField().setEnabled(true);
            view.passwordField().setEnabled(true);
            view.jabatanField().setEnabled(true);
            view.nomorTeleponField().setEnabled(true);

            view.ubahButton().setEnabled(false);
            view.hapusButton().setEnabled(false);

            view.simpanButton().setEnabled(true);
            view.batalButton().setEnabled(true);
        }
    }

    private class ubah implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.tabel().getSelectedRow() != -1) {
                view.usernameField().setText(view.getCell(0));
                view.passwordField().setText(view.getCell(1));
                for (int i = 0; i < view.jabatanField().getItemCount(); i++) {
                    if (view.getCell(2).equalsIgnoreCase(view.jabatanField().getItemAt(i).toString())) {
                        view.jabatanField().setSelectedIndex(i);
                    }
                }
                view.nomorTeleponField().setText(view.getCell(3));
                view.alamatField().setText(view.getCell(6));

                view.tambahButton().setEnabled(false);
                view.hapusButton().setEnabled(false);

                view.usernameField().setEnabled(true);
                view.passwordField().setEnabled(true);
                view.jabatanField().setEnabled(true);
                view.nomorTeleponField().setEnabled(true);
                if (view.jabatanField().getSelectedIndex() == 0) {
                    view.alamatField().setEnabled(true);
                }

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
                        model.delete(view.getCell(0));
                        view.setTabel(model.getTabel());
                        reset();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(c_mengelolahAkun.class.getName()).log(Level.SEVERE, null, ex);
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
                try {
                    if (!view.getUsername().isEmpty() && !view.getPassword().isEmpty() && !view.getNomorTelepon().isEmpty()) {
                        if (!model.isUsernameExisted(view.getUsername())) {
                            switch (view.jabatanField().getSelectedIndex()) {
                                case -1:
                                    view.message("Mohon pilih jabatan");
                                    break;
                                default:
                                    if (noSpace(view.getPassword().toCharArray())) {
                                        if (view.jabatanField().getSelectedIndex() == 0 && view.getAlamat().isEmpty()) {
                                            view.message("Alamat apotek tidak boleh kosong");
                                        } else {
                                            model.create(view.getUsername(), view.getPassword(), view.getJabatan(), view.getNomorTelepon(), view.getAlamat());
                                            view.setTabel(model.getTabel());
                                            reset();
                                        }
                                    } else {
                                        view.message("Password tidak boleh mengandung spasi");
                                    }
                                    break;
                            }
                        } else {
                            view.message("Username telah digunakan");
                        }
                    } else {
                        view.message("Data ada yang kosong");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(c_mengelolahAkun.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (view.ubahButton().isEnabled()) {
                try {
                    if (!view.getUsername().isEmpty() && !view.getPassword().isEmpty() && !view.getNomorTelepon().isEmpty()) {
                        switch (view.jabatanField().getSelectedIndex()) {
                            case -1:
                                view.message("Mohon pilih jabatan");
                                break;
                            default:
                                if (noSpace(view.getPassword().toCharArray())) {
                                    if (view.jabatanField().getSelectedIndex() == 0 && view.getAlamat().isEmpty()) {
                                        view.message("Alamat apotek tidak boleh kosong");
                                    } else {
                                        model.update(view.getUsername(), view.getPassword(), view.getJabatan(), view.getNomorTelepon(), view.getAlamat(), view.getCell(0));
                                        view.setTabel(model.getTabel());
                                        reset();
                                    }
                                } else {
                                    view.message("Password tidak boleh mengandung spasi");
                                }
                                break;
                        }
                    } else {
                        view.message("Data ada yang kosong kosong");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class jabatan implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (view.jabatanField().getSelectedIndex()) {
                case 0:
                    view.alamatField().setEnabled(true);
                    break;
                default:
                    view.alamatField().setEnabled(false);
                    view.alamatField().setText("");
                    break;
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

        view.usernameField().setEnabled(false);
        view.passwordField().setEnabled(false);
        view.jabatanField().setEnabled(false);
        view.nomorTeleponField().setEnabled(false);
        view.alamatField().setEnabled(false);

        view.usernameField().setText("");
        view.passwordField().setText("");
        view.jabatanField().setSelectedIndex(-1);
        view.nomorTeleponField().setText("");
        view.alamatField().setText("");
    }

    private boolean noSpace(char data[]) {
        boolean valid = true;
        char no = ' ';
        int i = 0;
        do {
            if (data[i] == no) {
                valid = false;
            }
            i++;
        } while (valid == true && i < data.length);
        return valid;
    }
}
