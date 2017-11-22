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
import model.m_stok;
import model.m_penawaran;
import model.m_permintaan;
import view.v_mengelolahPermintaan;

/**
 *
 * @author bedhu
 */
public class c_mengelolahPermintaan {

    private v_mengelolahPermintaan view;
    private m_permintaan modelP;
    private m_penawaran modelPE;
    private m_stok modelO;
    private String username;
    private int sisaKuota;
    private boolean edit1;
    private boolean edit2;

    public c_mengelolahPermintaan(String username) throws SQLException {
        edit1 = true;
        edit2 = true;
        this.username = username;
        view = new v_mengelolahPermintaan();
        view.setLocationRelativeTo(null);
        try {
            modelP = new m_permintaan();
            modelPE = new m_penawaran();
            modelO = new m_stok();
            view.setTabelPermintaan(modelP.getTabel(username));
            view.setVisible(true);
            view.setObat(modelO.getListObat(), modelO.getListNamaObat());
            view.kembaliButton().addActionListener(new kembali());
            view.tambahButton().addActionListener(new tambah());
            view.nomorObatField().addActionListener(new nomor());
            view.namaObatField().addActionListener(new nama());
            view.terimaButton().addActionListener(new terima());
            view.tabelPermintaan().getSelectionModel().addListSelectionListener(new permintaan());
            view.tabelPenawaran().getSelectionModel().addListSelectionListener(new penawaran());
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
            new c_homeSupervisor(username);
        }
    }

    private class tambah implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                modelP.create(view.getNomor(), view.getJumlah(), username);
                view.setTabelPermintaan(modelP.getTabel(username));
                reset();
            } catch (SQLException ex) {
                Logger.getLogger(c_mengelolahPermintaan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class nomor implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.nomorObatField().removeActionListener(this);
            view.setIndexNama(view.getIndexNomor());
            view.nomorObatField().addActionListener(new nomor());
        }
    }

    private class nama implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.namaObatField().removeActionListener(this);
            view.setIndexNomor(view.getIndexNama());
            view.namaObatField().addActionListener(new nama());
        }
    }

    private class permintaan implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            try {
                if (edit1 == true) {
                    view.terimaButton().setEnabled(false);
                    int idPermintaan = Integer.parseInt(view.getCellPermintaan(0));
                    view.setTabelPenawaran(modelPE.getTabel(idPermintaan));
                    sisaKuota = modelP.getKuota(idPermintaan) - modelPE.getTotal(idPermintaan);
                    view.setKuota(sisaKuota);
                }
            } catch (SQLException ex) {
                Logger.getLogger(c_mengelolahPenawaran.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class penawaran implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            if (edit2 == true) {
                try {
                    if (view.getCellPermintaan(5).equalsIgnoreCase("terpenuhi")) {
                        view.terimaButton().setEnabled(false);
                    } else {
                        view.terimaButton().setEnabled(true);
                    }
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                    view.message("Permintaan sudah terpenuhi atau belum dipilih");
                }
            }
        }
    }

    private class terima implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int ambil = 0;
                int tersedia = Integer.parseInt(view.getCellPenawaran(2));
                int idPermintaan = Integer.parseInt(view.getCellPermintaan(0));
                if (tersedia > sisaKuota) {
                    ambil = sisaKuota;
                } else {
                    ambil = tersedia;
                }
                modelPE.update(Integer.parseInt(view.getCellPenawaran(0)), ambil);
                sisaKuota = modelP.getKuota(idPermintaan) - modelPE.getTotal(idPermintaan);
                view.setKuota(sisaKuota);
                edit1 = false;
                edit2 = false;
                if (sisaKuota == 0) {
                    modelP.update(idPermintaan);
                    int row = view.tabelPermintaan().getSelectedRow();
                    view.setTabelPermintaan(modelP.getTabel(username));
                    view.tabelPermintaan().setRowSelectionInterval(row, row);
                }
                view.setTabelPenawaran(modelPE.getTabel(idPermintaan));
                view.terimaButton().setEnabled(false);
                edit1 = true;
                edit2 = true;
            } catch (SQLException ex) {
                Logger.getLogger(c_mengelolahPermintaan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void reset() {
        view.jumlahObatField().setText("");
        view.setIndexNama(-1);
        view.setIndexNomor(-1);
    }
}
