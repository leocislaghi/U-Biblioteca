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
import model.conexao;
/**
 *
 * @author leoci
 */
public class principal extends javax.swing.JFrame {

    public principal() {
        initComponents();
        
        // 1. Configurações Globais da Janela
        setTitle("Biblioteca Central - SISTEMA DE GESTÃO");
        setSize(1200, 780);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Header Superior (Barra Escura)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.decode("#0F172A"));
        header.setPreferredSize(new Dimension(1200, 52));
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
        tabbedPane.addTab("   Autores   ", criarPainelAutores());
        tabbedPane.addTab("   Livros   ", criarPainelLivros());
        tabbedPane.addTab("   Empréstimos   ", criarPainelEmprestimos());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ==========================================
    // 1. ABA ALUNOS
    // ==========================================
    private JPanel criarPainelAlunos() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Formulário
        JPanel formCard = criarCardTranslucido();
        formCard.setPreferredSize(new Dimension(360, 0));

        GridBagConstraints gbc = criarGBCBase();
        
        adicionarTituloECorpo(formCard, "Dados do aluno", "Preencha os dados para incluir ou editar um aluno.", gbc);
        adicionarCampoEstilizado(formCard, "Matrícula:", new JTextField(), gbc, 2);
        adicionarCampoEstilizado(formCard, "Nome Completo:", new JTextField(), gbc, 4);
        adicionarCampoEstilizado(formCard, "E-mail:", new JTextField(), gbc, 6);
        adicionarCampoEstilizado(formCard, "Telefone:", new JTextField(), gbc, 8);

        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 6, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(estilarBotao(new JButton("Salvar"), Color.decode("#2563EB"), Color.WHITE));
        btnPanel.add(estilarBotao(new JButton("Editar"), Color.decode("#F8FAFC"), Color.decode("#334155")));
        btnPanel.add(estilarBotao(new JButton("Excluir"), Color.decode("#FEF2F2"), Color.decode("#DC2626")));
        btnPanel.add(estilarBotao(new JButton("Limpar"), Color.decode("#F8FAFC"), Color.decode("#334155")));

        gbc.gridy = 10; gbc.insets = new Insets(24, 0, 0, 0);
        formCard.add(btnPanel, gbc);

        // Tabela
        JPanel tableCard = criarCardTranslucido();
        tableCard.setLayout(new BorderLayout(16, 16));

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

        JTable tabela = criarTabelaEstilizada(dados, colunas);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(95);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(160);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(190);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(110);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        mainPanel.add(formCard, BorderLayout.WEST);
        mainPanel.add(tableCard, BorderLayout.CENTER);

        return mainPanel;
    }

    // ==========================================
    // 2. ABA AUTORES (Novo do Protótipo)
    // ==========================================
    private JPanel criarPainelAutores() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Header Info
        JPanel topInfo = new JPanel(new GridLayout(2, 1, 2, 2));
        topInfo.setBackground(Color.decode("#F1F5F9"));
        JLabel title = new JLabel("Gestão de Autores");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.decode("#0F172A"));
        JLabel sub = new JLabel("Gerencie autores e nacionalidades disponíveis no acervo.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(Color.decode("#64748B"));
        topInfo.add(title);
        topInfo.add(sub);
        mainPanel.add(topInfo, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(Color.decode("#F1F5F9"));

        // Form Autor
        JPanel formCard = criarCardTranslucido();
        formCard.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = criarGBCBase();

        adicionarTituloECorpo(formCard, "Dados do autor", "Novo autor\nMantenha o catálogo de autoria organizado.", gbc);
        adicionarCampoEstilizado(formCard, "Nome do Autor", new JTextField("Conceição Evaristo"), gbc, 2);
        adicionarCampoEstilizado(formCard, "Nacionalidade", new JTextField("Brasileira"), gbc, 4);

        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 6, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(estilarBotao(new JButton("Salvar"), Color.decode("#2563EB"), Color.WHITE));
        btnPanel.add(estilarBotao(new JButton("Editar"), Color.decode("#F8FAFC"), Color.decode("#334155")));
        btnPanel.add(estilarBotao(new JButton("Excluir"), Color.decode("#FEF2F2"), Color.decode("#DC2626")));

        gbc.gridy = 6; gbc.insets = new Insets(24, 0, 0, 0);
        formCard.add(btnPanel, gbc);

        // Tabela Autor
        JPanel tableCard = criarCardTranslucido();
        tableCard.setLayout(new BorderLayout(16, 16));

        JPanel searchHeader = new JPanel(new BorderLayout());
        searchHeader.setBackground(Color.WHITE);
        JTextField txtSearch = new JTextField("  🔍  Buscar por nome ou nacionalidade...");
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(0, 38));
        txtSearch.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1, true));
        txtSearch.setForeground(Color.decode("#94A3B8"));
        
        JLabel countLabel = new JLabel("7 registros");
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        countLabel.setForeground(Color.decode("#64748B"));
        countLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

        searchHeader.add(txtSearch, BorderLayout.CENTER);
        searchHeader.add(countLabel, BorderLayout.EAST);

        JPanel tableHeaderBox = new JPanel(new BorderLayout(0, 10));
        tableHeaderBox.setBackground(Color.WHITE);
        JLabel lblTableTitle = new JLabel("Autores cadastrados");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableHeaderBox.add(lblTableTitle, BorderLayout.NORTH);
        tableHeaderBox.add(searchHeader, BorderLayout.SOUTH);

        tableCard.add(tableHeaderBox, BorderLayout.NORTH);

        String[] colunas = {"ID", "NOME DO AUTOR", "NACIONALIDADE"};
        Object[][] dados = {
            {"101", "Clarice Lispector", "Brasileira"},
            {"102", "Machado de Assis", "Brasileira"},
            {"103", "George Orwell", "Britânica"},
            {"104", "Gabriel García Márquez", "Colombiana"},
            {"105", "Chimamanda Ngozi Adichie", "Nigeriana"},
            {"106", "José Saramago", "Portuguesa"},
            {"107", "Virginia Woolf", "Britânica"}
        };

        JTable tabela = criarTabelaEstilizada(dados, colunas);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        content.add(formCard, BorderLayout.WEST);
        content.add(tableCard, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);
        return mainPanel;
    }

    // ==========================================
    // 3. ABA LIVROS (Novo do Protótipo)
    // ==========================================
    private JPanel criarPainelLivros() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Header Info
        JPanel topInfo = new JPanel(new GridLayout(2, 1, 2, 2));
        topInfo.setBackground(Color.decode("#F1F5F9"));
        JLabel title = new JLabel("Gestão de Livros");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.decode("#0F172A"));
        JLabel sub = new JLabel("Controle títulos, autoria e disponibilidade do inventário.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(Color.decode("#64748B"));
        topInfo.add(title);
        topInfo.add(sub);
        mainPanel.add(topInfo, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(Color.decode("#F1F5F9"));

        // Form Livro
        JPanel formCard = criarCardTranslucido();
        formCard.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = criarGBCBase();

        adicionarTituloECorpo(formCard, "Dados do livro", "", gbc);
        adicionarCampoEstilizado(formCard, "Título do Livro", new JTextField("Quarto de Despejo"), gbc, 2);
        adicionarCampoEstilizado(formCard, "ISBN", new JTextField("978-85-359-2914-7"), gbc, 4);
        adicionarCampoEstilizado(formCard, "Ano de Publicação", new JTextField("1960"), gbc, 6);
        adicionarCampoEstilizado(formCard, "Quantidade em Estoque", new JTextField("4"), gbc, 8);
        
        JComboBox<String> cbAutor = new JComboBox<>(new String[]{"Carolina Maria de Jesus", "Machado de Assis", "Clarice Lispector"});
        cbAutor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbAutor.setPreferredSize(new Dimension(0, 36));
        cbAutor.setBackground(Color.WHITE);
        adicionarComponenteEstilizado(formCard, "Autor", cbAutor, gbc, 10);

        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 6, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(estilarBotao(new JButton("Salvar"), Color.decode("#2563EB"), Color.WHITE));
        btnPanel.add(estilarBotao(new JButton("Editar"), Color.decode("#F8FAFC"), Color.decode("#334155")));
        btnPanel.add(estilarBotao(new JButton("Excluir"), Color.decode("#FEF2F2"), Color.decode("#DC2626")));
        btnPanel.add(estilarBotao(new JButton("Limpar"), Color.decode("#F8FAFC"), Color.decode("#334155")));

        gbc.gridy = 12; gbc.insets = new Insets(24, 0, 0, 0);
        formCard.add(btnPanel, gbc);

        // Tabela Livros
        JPanel tableCard = criarCardTranslucido();
        tableCard.setLayout(new BorderLayout(16, 16));

        JPanel searchHeader = new JPanel(new BorderLayout());
        searchHeader.setBackground(Color.WHITE);
        JTextField txtSearch = new JTextField("  🔍  Buscar por título, ISBN ou autor...");
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(0, 38));
        txtSearch.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1, true));
        txtSearch.setForeground(Color.decode("#94A3B8"));
        
        JLabel countLabel = new JLabel("6 títulos");
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        countLabel.setForeground(Color.decode("#64748B"));
        countLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

        searchHeader.add(txtSearch, BorderLayout.CENTER);
        searchHeader.add(countLabel, BorderLayout.EAST);

        JPanel tableHeaderBox = new JPanel(new BorderLayout(0, 10));
        tableHeaderBox.setBackground(Color.WHITE);
        JLabel lblTableTitle = new JLabel("Inventário do acervo");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableHeaderBox.add(lblTableTitle, BorderLayout.NORTH);
        tableHeaderBox.add(searchHeader, BorderLayout.SOUTH);

        tableCard.add(tableHeaderBox, BorderLayout.NORTH);

        String[] colunas = {"ID", "TÍTULO", "ISBN", "ANO", "ESTOQUE", "AUTOR"};
        Object[][] dados = {
            {"301", "Dom Casmurro", "978-85-359-027...", "1899", "8", "Machado de Assis"},
            {"302", "A Hora da Estrela", "978-85-209-294...", "1977", "5", "Clarice Lispector"},
            {"303", "1984", "978-85-359-148...", "1949", "3", "George Orwell"},
            {"304", "Cem Anos de Solidão", "978-85-359-155...", "1967", "6", "Gabriel García Márquez"},
            {"305", "Ensaio sobre a Cegueira", "978-85-359-027...", "1995", "4", "José Saramago"},
            {"306", "Olhos d'Água", "978-85-7559-32...", "2014", "7", "Conceição Evaristo"}
        };

        JTable tabela = criarTabelaEstilizada(dados, colunas);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(140);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        content.add(formCard, BorderLayout.WEST);
        content.add(tableCard, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);
        return mainPanel;
    }

    // ==========================================
    // 4. ABA EMPRÉSTIMOS (Novo do Protótipo)
    // ==========================================
    private JPanel criarPainelEmprestimos() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Header Info
        JPanel topInfo = new JPanel(new GridLayout(2, 1, 2, 2));
        topInfo.setBackground(Color.decode("#F1F5F9"));
        JLabel title = new JLabel("Controle de Empréstimos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.decode("#0F172A"));
        JLabel sub = new JLabel("Registre retiradas, acompanhe prazos e processe devoluções.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(Color.decode("#64748B"));
        topInfo.add(title);
        topInfo.add(sub);
        mainPanel.add(topInfo, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(Color.decode("#F1F5F9"));

        // Form Empréstimo
        JPanel formCard = criarCardTranslucido();
        formCard.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = criarGBCBase();

        adicionarTituloECorpo(formCard, "Novo empréstimo", "", gbc);

        // Banner informativo azul claro
        JPanel infoBanner = new JPanel(new BorderLayout());
        infoBanner.setBackground(Color.decode("#EFF6FF"));
        infoBanner.setBorder(new CompoundBorder(
                new LineBorder(Color.decode("#BFDBFE"), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        JLabel infoText = new JLabel("O prazo padrão de devolução é de 14 dias.");
        infoText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoText.setForeground(Color.decode("#1E40AF"));
        infoBanner.add(infoText);

        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 10, 0);
        formCard.add(infoBanner, gbc);

        JComboBox<String> cbAluno = new JComboBox<>(new String[]{"Ana Beatriz Souza • 202400184", "Bruno Henrique Lima • 202300927"});
        cbAluno.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbAluno.setPreferredSize(new Dimension(0, 36));
        cbAluno.setBackground(Color.WHITE);
        adicionarComponenteEstilizado(formCard, "Aluno", cbAluno, gbc, 2);

        JComboBox<String> cbLivro = new JComboBox<>(new String[]{"A Hora da Estrela • 5 disponíveis", "Dom Casmurro • 8 disponíveis"});
        cbLivro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbLivro.setPreferredSize(new Dimension(0, 36));
        cbLivro.setBackground(Color.WHITE);
        adicionarComponenteEstilizado(formCard, "Livro", cbLivro, gbc, 4);

        adicionarCampoEstilizado(formCard, "Data de Empréstimo", new JTextField("22/09/2026"), gbc, 6);
        adicionarCampoEstilizado(formCard, "Data de Devolução Prevista", new JTextField("06/10/2026"), gbc, 8);

        JButton btnRegistrar = estilarBotao(new JButton("Registrar Empréstimo"), Color.decode("#2563EB"), Color.WHITE);
        gbc.gridy = 10; gbc.insets = new Insets(20, 0, 0, 0);
        formCard.add(btnRegistrar, gbc);

        // Tabela Empréstimos
        JPanel tableCard = criarCardTranslucido();
        tableCard.setLayout(new BorderLayout(16, 16));

        JPanel searchHeader = new JPanel(new BorderLayout());
        searchHeader.setBackground(Color.WHITE);
        JTextField txtSearch = new JTextField("  🔍  Buscar por aluno, livro ou status...");
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(0, 38));
        txtSearch.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1, true));
        txtSearch.setForeground(Color.decode("#94A3B8"));
        
        JLabel countLabel = new JLabel("5 registros");
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        countLabel.setForeground(Color.decode("#64748B"));
        countLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

        searchHeader.add(txtSearch, BorderLayout.CENTER);
        searchHeader.add(countLabel, BorderLayout.EAST);

        JPanel tableHeaderBox = new JPanel(new BorderLayout(0, 10));
        tableHeaderBox.setBackground(Color.WHITE);
        JLabel lblTableTitle = new JLabel("Empréstimos atuais");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableHeaderBox.add(lblTableTitle, BorderLayout.NORTH);
        tableHeaderBox.add(searchHeader, BorderLayout.SOUTH);

        tableCard.add(tableHeaderBox, BorderLayout.NORTH);

        String[] colunas = {"ID", "ALUNO", "LIVRO", "EMPRÉSTIMO", "PREVISÃO", "STATUS", "AÇÃO"};
        Object[][] dados = {
            {"501", "Ana Beatriz Souza", "A Hora da Estrela", "18/09/2026", "02/10/2026", "ATIVO", "Registrar Devolução"},
            {"502", "Bruno Henrique Lima", "1984", "16/09/2026", "30/09/2026", "DEVOLVIDO", "—"},
            {"503", "Camila R. Alves", "Olhos d'Água", "20/09/2026", "04/10/2026", "ATIVO", "Registrar Devolução"},
            {"504", "Diego Martins Costa", "Dom Casmurro", "09/09/2026", "23/09/2026", "DEVOLVIDO", "—"},
            {"505", "Fernanda O. Reis", "Ensaio sobre a Cegueira", "21/09/2026", "05/10/2026", "ATIVO", "Registrar Devolução"}
        };

        JTable tabela = criarTabelaEstilizada(dados, colunas);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(140);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(85);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(85);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        content.add(formCard, BorderLayout.WEST);
        content.add(tableCard, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);
        return mainPanel;
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE ESTILIZAÇÃO
    // ==========================================
    private JPanel criarCardTranslucido() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(Color.decode("#E2E8F0"), 1, true),
                new EmptyBorder(18, 18, 18, 18)
        ));
        return card;
    }

    private GridBagConstraints criarGBCBase() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        return gbc;
    }

    private void adicionarTituloECorpo(JPanel p, String title, String subtitle, GridBagConstraints gbc) {
        gbc.gridy = 0;
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.decode("#0F172A"));
        p.add(lblTitle, gbc);

        if (!subtitle.isEmpty()) {
            gbc.gridy = 1;
            JLabel lblSub = new JLabel(subtitle);
            lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            lblSub.setForeground(Color.decode("#64748B"));
            gbc.insets = new Insets(0, 0, 10, 0);
            p.add(lblSub, gbc);
        }
    }

    private void adicionarCampoEstilizado(JPanel p, String labelText, JTextField tf, GridBagConstraints gbc, int y) {
        adicionarComponenteEstilizado(p, labelText, tf, gbc, y);
    }

    private void adicionarComponenteEstilizado(JPanel p, String labelText, JComponent comp, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 0, 2, 0);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(Color.decode("#334155"));
        p.add(label, gbc);

        gbc.gridy = y + 1;
        gbc.insets = new Insets(0, 0, 8, 0);

        if (comp instanceof JTextField) {
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            comp.setPreferredSize(new Dimension(0, 36));
            ((JTextField) comp).setBorder(new CompoundBorder(
                    new LineBorder(Color.decode("#CBD5E1"), 1, true),
                    new EmptyBorder(4, 8, 4, 8)
            ));
        }
        p.add(comp, gbc);
    }

    private JTable criarTabelaEstilizada(Object[][] dados, String[] colunas) {
        DefaultTableModel model = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabela.setRowHeight(36);
        tabela.setGridColor(Color.decode("#F1F5F9"));

        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabela.getTableHeader().setBackground(Color.decode("#0F172A"));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setPreferredSize(new Dimension(0, 36));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        return tabela;
    }

    private JButton estilarBotao(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 34));
        btn.setBorder(new CompoundBorder(
                new LineBorder(bg.equals(Color.decode("#F8FAFC")) ? Color.decode("#CBD5E1") : bg, 1, true),
                new EmptyBorder(4, 6, 4, 6)
        ));
        return btn;
    }

   public static void main(String args[]) {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializa e cria as tabelas do SQLite no banco
        conexao.inicializarBanco();

        // Abre a tela principal
        java.awt.EventQueue.invokeLater(() -> new principal().setVisible(true));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                                               

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
