package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import model.m_akun;
import model.m_stok;
import model.m_histori;
import model.m_obat;
import view.v_mengelolahStok;

public class c_mengelolahStok {

    private v_mengelolahStok view;
    private m_histori modelH;
    private m_stok modelS;
    private m_obat modelO;
    private m_akun modelA;
    private String username;

    public c_mengelolahStok(String username) throws SQLException {
        this.username = username;
        view = new v_mengelolahStok();
        view.setLocationRelativeTo(null);
        try {
            modelS = new m_stok();
            modelO = new m_obat();
            modelA = new m_akun();
            modelH = new m_histori();
            view.setObat(modelO.getListNomorObat(), modelO.getListNamaObat());
            view.setTabel(modelS.getTabel(modelA.getAtasan(username)));
            view.setVisible(true);
            view.nomorObatField().addActionListener(new nomorObat());
            view.namaObatField().addActionListener(new namaObat());
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

    private class nomorObat implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.namaObatField().setSelectedIndex(view.nomorObatField().getSelectedIndex());
        }
    }

    private class namaObat implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.nomorObatField().setSelectedIndex(view.namaObatField().getSelectedIndex());
        }
    }

    private class kembali implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            new c_homePetugas(username);
        }
    }

    private class tambah implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.ubahButton().setEnabled(false);
            view.hapusButton().setEnabled(false);

            view.nomorObatField().setEnabled(true);
            view.namaObatField().setEnabled(true);
            view.jumlahStokField().setEnabled(true);

            view.simpanButton().setEnabled(true);
            view.batalButton().setEnabled(true);
        }
    }

    private class ubah implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.tabel().getSelectedRow() != -1) {
                for (int i = 0; i < view.nomorObatField().getItemCount(); i++) {
                    if (view.getCell(0).equalsIgnoreCase(view.nomorObatField().getItemAt(i).toString())) {
                        view.nomorObatField().setSelectedIndex(i);
                        view.namaObatField().setSelectedIndex(i);
                    }
                }
                view.statusField().setSelectedIndex(0);
                view.jumlahStokField().setText("0");

                view.jumlahStokField().setEnabled(true);
                view.statusField().setEnabled(true);

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
                        modelS.delete(view.getCell(0), modelA.getAtasan(username));
                        System.out.println("");
                        modelH.create(view.getCell(0), username, "data dihapus", -Integer.parseInt(view.getCell(3)));
                        view.setTabel(modelS.getTabel(modelA.getAtasan(username)));
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
                if (view.nomorObatField().getSelectedIndex() != -1 && view.namaObatField().getSelectedIndex() != -1) {
                    try {
                        if (!modelS.isNomorExisted(view.getNomorObat(), modelA.getAtasan(username))) {
                            view.getJumlahStok();
                            modelS.create(view.getNomorObat(), view.getJumlahStok(), modelA.getAtasan(username));
                            modelH.create(view.getNomorObat(), username, "data baru", view.getJumlahStok());
                            view.setTabel(modelS.getTabel(modelA.getAtasan(username)));
                            reset();
                        } else {
                            view.message("Tidak bisa membuat obat yang sama");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.message("Jumlah stok harus diisi angka");
                    }
                } else {
                    view.message("Nomor atau nama tidak dipilih");
                }
            } else if (view.ubahButton().isEnabled()) {
                if (view.nomorObatField().getSelectedIndex() != -1 && view.namaObatField().getSelectedIndex() != -1) {
                    try {
                        view.getJumlahStok();
                        if (view.statusField().getSelectedIndex() == 0) {
                            modelS.update(view.getNomorObat(), Integer.parseInt(view.getCell(3)) + view.getJumlahStok(), modelA.getAtasan(username));
                            modelH.create(view.getNomorObat(), username, "penambahan", +view.getJumlahStok());
                        } else {
                            modelS.update(view.getNomorObat(), Integer.parseInt(view.getCell(3)) - view.getJumlahStok(), modelA.getAtasan(username));
                            modelH.create(view.getNomorObat(), username, "pengurangan", -view.getJumlahStok());
                        }
                        view.setTabel(modelS.getTabel(modelA.getAtasan(username)));
                        reset();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.message("Jumlah stok harus diisi angka");
                    }
                } else {
                    view.message("Nomor atau nama tidak dipilih");
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
        view.nomorObatField().setEnabled(false);
        view.namaObatField().setEnabled(false);
        view.jumlahStokField().setEnabled(false);
        view.statusField().setEnabled(false);
        view.nomorObatField().setSelectedIndex(-1);
        view.namaObatField().setSelectedIndex(-1);
        view.statusField().setSelectedIndex(-1);
        view.jumlahStokField().setText("");
    }

}
