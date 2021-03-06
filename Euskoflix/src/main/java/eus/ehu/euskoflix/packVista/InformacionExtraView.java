package eus.ehu.euskoflix.packVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author kaecius
 */
public class InformacionExtraView extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblDirector;
    private javax.swing.JLabel lblDirectorContent;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloContent;
    private javax.swing.JScrollPane scrollSinopsis;
    private javax.swing.JTextArea txtSinopsis;

    public InformacionExtraView() {
    }


    public void fillComponents(Object[] info) {
        lblTituloContent.setText((String) info[0]);
        lblDirectorContent.setText((String) info[1]);
        lblImagen.setText("");
        lblImagen.setIcon(new ImageIcon((Image) info[3]));
        txtSinopsis.setText((String) info[2]);
    }

    public void initComponents(String[][] pDatosTags, String[] pCabeceraTags, String[][] pDatosRatings, String[] pCabeceraRatings) {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblTituloContent = new javax.swing.JLabel();
        lblDirector = new javax.swing.JLabel();
        lblDirectorContent = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        scrollSinopsis = new javax.swing.JScrollPane();
        txtSinopsis = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        // setAlwaysOnTop(true);
        getContentPane().setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblTitulo.setText("Titulo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel2.add(lblTitulo, gridBagConstraints);

        lblTituloContent.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTituloContent.setText("lblTituloContent");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 1, 0, 0);
        jPanel2.add(lblTituloContent, gridBagConstraints);

        lblDirector.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblDirector.setText("Director:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(lblDirector, gridBagConstraints);

        lblDirectorContent.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDirectorContent.setText("DirectorContent");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(lblDirectorContent, gridBagConstraints);

        jPanel7.setLayout(new java.awt.BorderLayout());

        lblImagen.setBackground(new java.awt.Color(255, 3, 0));
        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setText("Imagen");
        lblImagen.setToolTipText("");
        jPanel7.add(lblImagen, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel2.add(jPanel7, gridBagConstraints);

        scrollSinopsis.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sinopsis", 0, 3, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        scrollSinopsis.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtSinopsis.setWrapStyleWord(true);
        txtSinopsis.setEditable(false);
        txtSinopsis.setColumns(20);
        txtSinopsis.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtSinopsis.setLineWrap(true);
        txtSinopsis.setRows(5);
        txtSinopsis.setFocusable(false);
        txtSinopsis.setRequestFocusEnabled(false);
        scrollSinopsis.setViewportView(txtSinopsis);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.6;
        jPanel2.add(scrollSinopsis, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tags", 0, 0, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTable1.setRowSelectionAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(pDatosTags, pCabeceraTags) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jTable1.setFocusable(false);
        jScrollPane1.setViewportView(jTable1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ratings", 0, 0, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N
        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jTable2.setRowSelectionAllowed(false);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(pDatosRatings, pCabeceraRatings) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jTable2.setFocusable(false);
        jScrollPane2.setViewportView(jTable2);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
        flowLayout1.setAlignOnBaseline(true);
        jPanel6.setLayout(flowLayout1);

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton1.setText("Cerrar");

        jPanel6.add(jButton1);


        jButton2 = new javax.swing.JButton();

        jButton2.setText("Ver Trailer");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel2.add(jButton2, gridBagConstraints);

        getContentPane().add(jPanel6, java.awt.BorderLayout.SOUTH);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
    }

    public void addCerrarListener(ActionListener pListenForCerrar) {
        this.jButton1.addActionListener(pListenForCerrar);
    }

    public void desactivarTrailer() {
        this.jButton2.setVisible(false);
    }

    public void addReproductorListener(ActionListener reproductorListener) {
        this.jButton2.addActionListener(reproductorListener);
    }
}

