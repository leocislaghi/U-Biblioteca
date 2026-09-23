/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author leoci
 */
public class login extends javax.swing.JFrame {

    public login() {
        initComponents(); // Chama o código gerado pelo NetBeans
        
        // 1. Configurações Globais da Janela
        setTitle("Biblioteca Central - SISTEMA DE GESTÃO");
        setSize(1180, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Header Superior (Barra Escura)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.decode("#0F172A"));
        header.setPreferredSize(new Dimension(1180, 52));
        header.setBorder(new EmptyBorder(0, 24, 0, 24));

        JLabel title = new JLabel("Biblioteca Central   |   SISTEMA DE GESTÃO");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(Color.WHITE);
        
        JLabel userLabel = new JLabel("MB   Marina • Bibliotecária");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(Color.decode("#94A3B8"));

        header.add(title, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // 3. Sistema de Abas Integrado
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setBackground(Color.decode("#F1F5F9"));
        
        tabbedPane.addTab("   Alunos   ", criarPainelAlunos());
        tabbedPane.addTab("   Autores   ", new JPanel());
        tabbedPane.addTab("   Livros   ", new JPanel());
        tabbedPane.addTab("   Empréstimos   ", new JPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel criarPainelAlunos() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setPreferredSize(new Dimension(360, 0));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(Color.decode("#E2E8F0"), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;

        JLabel lblTitleForm = new JLabel("Dados do aluno");
        lblTitleForm.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitleForm.setForeground(Color.decode("#0F172A"));
        formCard.add(lblTitleForm, gbc);

        gbc.gridy++;
        JLabel lblSub = new JLabel("Preencha os dados para incluir ou editar um aluno.");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.decode("#64748B"));
        gbc.insets = new Insets(0, 0, 14, 0);
        formCard.add(lblSub, gbc);

        adicionarCampoEstilizado(formCard, "Matrícula:", new JTextField(), gbc, 2);
        adicionarCampoEstilizado(formCard, "Nome Completo:", new JTextField(), gbc, 4);
        adicionarCampoEstilizado(formCard, "E-mail:", new JTextField(), gbc, 6);
        adicionarCampoEstilizado(formCard, "Telefone:", new JTextField(), gbc, 8);

        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 6, 0));
        btnPanel.setBackground(Color.WHITE);
        
        JButton btnSalvar = estilarBotao(new JButton("Salvar"), Color.decode("#2563EB"), Color.WHITE);
        JButton btnEditar = estilarBotao(new JButton("Editar"), Color.decode("#F8FAFC"), Color.decode("#334155"));
        JButton btnExcluir = estilarBotao(new JButton("Excluir"), Color.decode("#FEF2F2"), Color.decode("#DC2626"));
        JButton btnLimpar = estilarBotao(new JButton("Limpar"), Color.decode("#F8FAFC"), Color.decode("#334155"));

        btnPanel.add(btnSalvar);
        btnPanel.add(btnEditar);
        btnPanel.add(btnExcluir);
        btnPanel.add(btnLimpar);

        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 0, 0, 0);
        formCard.add(btnPanel, gbc);

        JPanel tableCard = new JPanel(new BorderLayout(16, 16));
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new CompoundBorder(
                new LineBorder(Color.decode("#E2E8F0"), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTableTitle = new JLabel("Alunos cadastrados");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTableTitle.setForeground(Color.decode("#0F172A"));
        tableCard.add(lblTableTitle, BorderLayout.NORTH);

        String[] colunas = {"ID", "MATRÍCULA", "NOME", "E-MAIL", "TELEFONE"};
        Object[][] dados = {
            {"001", "202400184", "Ana Beatriz Souza", "ana.souza@aluno.edu.br", "(11) 98764-2103"},
            {"002", "202300927", "Bruno Henrique Lima", "bruno.lima@aluno.edu.br", "(11) 99642-7810"},
            {"003", "202400356", "Camila Rodrigues Alves", "camila.alves@aluno.edu.br", "(11) 98211-4507"}
        };

        DefaultTableModel model = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setRowHeight(38);
        tabela.setGridColor(Color.decode("#F1F5F9"));
        
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabela.getTableHeader().setBackground(Color.decode("#F8FAFC"));
        tabela.getTableHeader().setForeground(Color.decode("#64748B"));
        tabela.getTableHeader().setPreferredSize(new Dimension(0, 38));
        tabela.getTableHeader().setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));

        tabela.getColumnModel().getColumn(0).setPreferredWidth(45);  
        tabela.getColumnModel().getColumn(1).setPreferredWidth(95);  
        tabela.getColumnModel().getColumn(2).setPreferredWidth(160); 
        tabela.getColumnModel().getColumn(3).setPreferredWidth(190); 
        tabela.getColumnModel().getColumn(4).setPreferredWidth(110); 

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabela.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        mainPanel.add(formCard, BorderLayout.WEST);
        mainPanel.add(tableCard, BorderLayout.CENTER);

        return mainPanel;
    }

    private void adicionarCampoEstilizado(JPanel p, String labelText, JTextField tf, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(6, 0, 4, 0);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(Color.decode("#334155"));
        p.add(label, gbc);

        gbc.gridy = y + 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setPreferredSize(new Dimension(0, 36));
        tf.setBorder(new CompoundBorder(
                new LineBorder(Color.decode("#CBD5E1"), 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        p.add(tf, gbc);
    }

    private JButton estilarBotao(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 36));
        btn.setBorder(new CompoundBorder(
                new LineBorder(bg.equals(Color.decode("#F8FAFC")) ? Color.decode("#CBD5E1") : bg, 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return btn;
    }

    // --- O MÉTODO MAIN FICA AQUI EM BAIXO ---
    public static void main(String args[]) {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new login().setVisible(true));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
