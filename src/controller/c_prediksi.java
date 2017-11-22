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
import model.m_histori;
import model.m_stok;
import model.m_permintaan;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import view.v_prediksi;

/**
 *
 * @author bedhu
 */
public class c_prediksi {

    private v_prediksi view;
    private m_histori modelH;
    private m_stok modelO;
    private m_permintaan modelP;
    private String username;
    private int jumlahObatA;
    private int jumlahObatB;
    private XYSeriesCollection dataset = new XYSeriesCollection();
    private XYSeries series1 = new XYSeries("Obat A");
    private XYSeries series2 = new XYSeries("Obat B");
    private XYSeries series3 = new XYSeries("Perkiraan Obat A");
    private XYSeries series4 = new XYSeries("Perkiraan Obat B");

    public c_prediksi(String username) throws SQLException {
        this.username = username;
        view = new v_prediksi();
        view.setLocationRelativeTo(null);
        try {
            modelH = new m_histori();
            modelO = new m_stok();
            modelP = new m_permintaan();
            view.setObat(modelO.getListObat());
            view.kembaliButton().addActionListener(new kembali());
            view.obatAField().addActionListener(new obatA());
            view.obatBField().addActionListener(new obatB());
            view.prediksikanAButton().addActionListener(new prediksikanA());
            view.prediksikanBButton().addActionListener(new prediksikanB());
            view.masukkanAButton().addActionListener(new masukkanA());
            view.masukkanBButton().addActionListener(new masukkanB());
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

    private class obatA implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.obatAField().removeActionListener(this);
                int permintaan[] = modelH.getPermintaanBulanan(view.getNomorObatA(), username);
                view.setValueObatA(permintaan);
                view.onA();
                if (permintaan.length < 3) {
                    view.message("Data permintaan obat tidak lengkap untuk melakukan prediksi, tapi anda dapat memasukkan data dummy untuk melakukan eksperimen prediksi.");
                }
                view.obatAField().addActionListener(this);
            } catch (SQLException ex) {
                Logger.getLogger(c_prediksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class obatB implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.obatAField().removeActionListener(this);
                int permintaan[] = modelH.getPermintaanBulanan(view.getNomorObatB(), username);
                view.setValueObatB(permintaan);
                view.onB();
                if (permintaan.length < 3) {
                    view.message("Data permintaan obat tidak lengkap untuk melakukan prediksi, tapi anda dapat memasukkan data dummy untuk melakukan eksperimen prediksi.");
                }
                view.obatAField().addActionListener(this);
            } catch (SQLException ex) {
                Logger.getLogger(c_prediksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class prediksikanA implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.chart(createDatasetA(view.getValueObatA()));
                view.masukkanAButton().setEnabled(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                view.message("Data input invalid");
            }
            view.reset();
        }
    }

    private class prediksikanB implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.chart(createDatasetB(view.getValueObatB()));
                view.masukkanBButton().setEnabled(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                view.message("Data input invalid");
            }
            view.reset();
        }
    }

    private class masukkanA implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (view.messageYesNo("Masukkan perdiksi berikut ke permintaan?"
                        + "\nNomor Obat : " + view.getNomorObatA()
                        + "\nNama Obat : " + modelO.getNamaObat(view.getNomorObatA())
                        + "\nJumlah Obat : " + jumlahObatA) == 0) {
                    modelP.create(view.getNomorObatA(), jumlahObatA, username);
                    view.message("Permintaan telah dibuat");
                }
            } catch (SQLException ex) {
                Logger.getLogger(c_prediksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class masukkanB implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (view.messageYesNo("Masukkan obat berikut ke penawaran?"
                        + "\nNomor Obat : " + view.getNomorObatB()
                        + "\nNama Obat : " + modelO.getNamaObat(view.getNomorObatB())
                        + "\nJumlah Obat : " + jumlahObatB) == 0) {
                    modelP.create(view.getNomorObatB(), jumlahObatB, username);
                }
            } catch (SQLException ex) {
                Logger.getLogger(c_prediksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public XYDataset createDatasetA(int valueObatA[]) {
        series1.clear();
        for (int i = -3; i < valueObatA.length - 3; i++) {
            series1.add(i, valueObatA[i + 3]);
        }

        series3.clear();
        series3.add(-1, valueObatA[2]);
        jumlahObatA = perkiraan(valueObatA);
        series3.add(0, jumlahObatA);

        dataset.removeAllSeries();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);

        return dataset;
    }

    public XYDataset createDatasetB(int valueObatB[]) {
        series2.clear();
        for (int i = -3; i < valueObatB.length - 3; i++) {
            series2.add(i, valueObatB[i + 3]);
        }

        series4.clear();
        series4.add(-1, valueObatB[2]);
        jumlahObatB = perkiraan(valueObatB);
        series4.add(0, jumlahObatB);

        dataset.removeAllSeries();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);

        return dataset;
    }

    private int perkiraan(int jumlah[]) {
        int ratarata = 0;
        for (int i = 0; i < jumlah.length; i++) {
            ratarata += jumlah[i];
        }
        ratarata = ratarata / 3;
        return ratarata;
    }
}
