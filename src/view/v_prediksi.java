/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author bedhu
 */
public class v_prediksi extends javax.swing.JFrame {

    /**
     * Creates new form v_prediksi
     */
    private ChartPanel chartPanel;

    public v_prediksi() {
        initComponents();
        chart(createDatasetNull());
        panel.add(chartPanel);
        this.setVisible(true);
    }

    public void chart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("Average salary per age", "Bulan", "Permintaan", dataset, PlotOrientation.VERTICAL, true, true, false);

        chartPanel = new ChartPanel(chart);

        XYPlot plot = chart.getXYPlot();

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1));
        xAxis.setRange(-3.5, 0.5);

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setTickUnit(new NumberTickUnit(10));
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        renderer.setSeriesPaint(2, Color.MAGENTA);
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        renderer.setSeriesPaint(3, Color.DARK_GRAY);
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Grafik Permintaan", new Font("Serif", Font.BOLD, 18)));

        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);

        StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator("{2}", format, format);
        renderer.setBaseToolTipGenerator(ttg);

        chartPanel.setMouseZoomable(false);
        chartPanel.setInitialDelay(0);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDatasetNull() {
        XYSeries series1 = new XYSeries("Obat A");
        XYSeries series2 = new XYSeries("Obat B");

        XYSeries series3 = new XYSeries("Perkiraan Obat A");
        XYSeries series4 = new XYSeries("Perkiraan Obat B");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);

        return dataset;
    }

    public void reset() {
        panel.removeAll();
        panel.add(chartPanel);
        chartPanel.revalidate();
        chartPanel.repaint();
        panel.revalidate();
        panel.repaint();
    }

    public int[][] setbulan() {
        int bulan[][] = new int[2][3];
        bulan[0][0] = 1;
        bulan[0][1] = 2;
        bulan[0][2] = 3;
        bulan[1][0] = 1;
        bulan[1][1] = 2;
        bulan[1][2] = 3;
        return bulan;
    }

    public void setObat(String[] nomorObat) {
        for (int i = 0; i < nomorObat.length; i++) {
            obatA.addItem(nomorObat[i]);
            obatB.addItem(nomorObat[i]);
        }
        obatA.setSelectedIndex(-1);
        obatB.setSelectedIndex(-1);
    }

    public void setValueObatA(int value[]) {
        obatA1.setText("");
        obatA2.setText("");
        obatA3.setText("");
        if (value.length > 0) {
            obatA1.setText(value[0] + "");
        }
        if (value.length > 1) {
            obatA2.setText(value[1] + "");
        }
        if (value.length > 2) {
            obatA3.setText(value[2] + "");
        }
    }

    public void setValueObatB(int value[]) {
        obatB1.setText("");
        obatB2.setText("");
        obatB3.setText("");
        if (value.length > 0) {
            obatB1.setText(value[0] + "");
        }
        if (value.length > 1) {
            obatB2.setText(value[1] + "");
        }
        if (value.length > 2) {
            obatB3.setText(value[2] + "");
        }
    }

    public int[] getValueObatA() {
        int valueObatA[] = new int[3];
        valueObatA[0] = Integer.parseInt(obatA3.getText());
        valueObatA[1] = Integer.parseInt(obatA2.getText());
        valueObatA[2] = Integer.parseInt(obatA1.getText());
        return valueObatA;
    }

    public int[] getValueObatB() {
        int valueObatB[] = new int[3];
        valueObatB[0] = Integer.parseInt(obatB3.getText());
        valueObatB[1] = Integer.parseInt(obatB2.getText());
        valueObatB[2] = Integer.parseInt(obatB1.getText());
        return valueObatB;
    }

    public String getNomorObatA() {
        return obatA.getSelectedItem() + "";
    }

    public String getNomorObatB() {
        return obatB.getSelectedItem() + "";
    }

    public JComboBox obatAField() {
        return obatA;
    }

    public JComboBox obatBField() {
        return obatB;
    }

    public void onA() {
        obatA1.setEnabled(true);
        obatA2.setEnabled(true);
        obatA3.setEnabled(true);
        prediksikanA.setEnabled(true);
    }

    public void onB() {
        obatB1.setEnabled(true);
        obatB2.setEnabled(true);
        obatB3.setEnabled(true);
        prediksikanB.setEnabled(true);
    }

    public JButton prediksikanAButton() {
        return prediksikanA;
    }

    public JButton prediksikanBButton() {
        return prediksikanB;
    }

    public JButton masukkanAButton() {
        return masukkanA;
    }

    public JButton masukkanBButton() {
        return masukkanB;
    }

    public JButton kembaliButton() {
        return kembali;
    }

    public void message(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, null, JOptionPane.INFORMATION_MESSAGE);
    }

    public int messageYesNo(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, null, JOptionPane.YES_NO_OPTION);
        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        panel = new javax.swing.JPanel();
        obatA2 = new javax.swing.JTextField();
        obatA1 = new javax.swing.JTextField();
        obatA3 = new javax.swing.JTextField();
        obatB2 = new javax.swing.JTextField();
        obatB1 = new javax.swing.JTextField();
        obatB3 = new javax.swing.JTextField();
        obatB = new javax.swing.JComboBox<>();
        obatA = new javax.swing.JComboBox<>();
        kembali = new javax.swing.JButton();
        prediksikanB = new javax.swing.JButton();
        masukkanA = new javax.swing.JButton();
        masukkanB = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        prediksikanA = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel.setLayout(new java.awt.GridLayout(1, 0));
        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 800, 390));

        obatA2.setEnabled(false);
        getContentPane().add(obatA2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 60, -1));

        obatA1.setEnabled(false);
        getContentPane().add(obatA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 60, -1));

        obatA3.setEnabled(false);
        getContentPane().add(obatA3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 60, -1));

        obatB2.setEnabled(false);
        getContentPane().add(obatB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 60, -1));

        obatB1.setEnabled(false);
        getContentPane().add(obatB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 60, -1));

        obatB3.setEnabled(false);
        getContentPane().add(obatB3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 60, -1));

        obatB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                obatBActionPerformed(evt);
            }
        });
        getContentPane().add(obatB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 120, -1));

        getContentPane().add(obatA, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 120, -1));

        kembali.setBorderPainted(false);
        kembali.setContentAreaFilled(false);
        kembali.setDefaultCapable(false);
        getContentPane().add(kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, 50));

        prediksikanB.setText("Prediksikan");
        prediksikanB.setEnabled(false);
        getContentPane().add(prediksikanB, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, -1, -1));

        masukkanA.setText("Masukkan ke Permintaan");
        masukkanA.setEnabled(false);
        getContentPane().add(masukkanA, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, -1, -1));

        masukkanB.setText("Masukkan ke Permintaan");
        masukkanB.setEnabled(false);
        getContentPane().add(masukkanB, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, -1, -1));

        jLabel5.setText("*jumlah permintaan obat bulan lalu dapat diubah apabila ingin melakukan eksperimen terhadap prediksi");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 490, 20));

        prediksikanA.setText("Prediksikan");
        prediksikanA.setEnabled(false);
        getContentPane().add(prediksikanA, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Prediksi.png"))); // NOI18N
        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void obatBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_obatBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_obatBActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(v_prediksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(v_prediksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(v_prediksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(v_prediksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new v_prediksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton kembali;
    private javax.swing.JButton masukkanA;
    private javax.swing.JButton masukkanB;
    private javax.swing.JComboBox<String> obatA;
    private javax.swing.JTextField obatA1;
    private javax.swing.JTextField obatA2;
    private javax.swing.JTextField obatA3;
    private javax.swing.JComboBox<String> obatB;
    private javax.swing.JTextField obatB1;
    private javax.swing.JTextField obatB2;
    private javax.swing.JTextField obatB3;
    private javax.swing.JPanel panel;
    private javax.swing.JButton prediksikanA;
    private javax.swing.JButton prediksikanB;
    // End of variables declaration//GEN-END:variables
}
