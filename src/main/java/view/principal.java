package view;

import com.formdev.flatlaf.FlatLightLaf;
import model.alunoDAO;
import model.autorDAO;
import model.livroDAO;
import model.emprestimoDAO;
import model.conexao;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class principal extends javax.swing.JFrame {

    // --- CAMPOS ABA ALUNOS ---
    private JTextField txtMatricula = new JTextField();
    private JTextField txtNome = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelefone = new JTextField();
    private DefaultTableModel modelTabelaAlunos;
    private JTable tabelaAlunos;
    private int idAlunoSelecionado = -1;

    // --- CAMPOS ABA AUTORES ---
    private JTextField txtNomeAutor = new JTextField();
    private JTextField txtNacionalidadeAutor = new JTextField();
    private DefaultTableModel modelTabelaAutores;
    private JTable tabelaAutores;
    private int idAutorSelecionado = -1;

    // --- CAMPOS ABA LIVROS ---
    private JTextField txtTituloLivro = new JTextField();
    private JTextField txtIsbnLivro = new JTextField();
    private JTextField txtAnoLivro = new JTextField();
    private JTextField txtQtdEstoque = new JTextField();
    private JComboBox<String> cbAutorLivro = new JComboBox<>();
    private DefaultTableModel modelTabelaLivros;
    private JTable tabelaLivros;
    private int idLivroSelecionado = -1;

    // --- CAMPOS ABA EMPRÉSTIMOS ---
    private JComboBox<String> cbAlunoEmprestimo = new JComboBox<>();
    private JComboBox<String> cbLivroEmprestimo = new JComboBox<>();
    private JTextField txtDataEmprestimo = new JTextField("23/09/2026");
    private JTextField txtDataDevolucao = new JTextField("07/10/2026");
    private DefaultTableModel modelTabelaEmprestimos;
    private JTable tabelaEmprestimos;

    public principal() {
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

        // 3. Sistema de Abas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setBackground(Color.decode("#F1F5F9"));
        
        tabbedPane.addTab("   Alunos   ", criarPainelAlunos());
        tabbedPane.addTab("   Autores   ", criarPainelAutores());
        tabbedPane.addTab("   Livros   ", criarPainelLivros());
        tabbedPane.addTab("   Empréstimos   ", criarPainelEmprestimos());

        // Recarrega dropdowns e tabelas ao alternar as abas
        tabbedPane.addChangeListener(e -> {
            int aba = tabbedPane.getSelectedIndex();
            if (aba == 2) { // Livros
                carregarAutoresNoComboBox();
                atualizarTabelaLivros();
            } else if (aba == 3) { // Empréstimos
                carregarAlunosELivrosEmprestimo();
                atualizarTabelaEmprestimos();
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ==========================================
    // 1. ABA ALUNOS
    // ==========================================
    private JPanel criarPainelAlunos() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formCard = criarCardTranslucido();
        formCard.setPreferredSize(new Dimension(360, 0));
        GridBagConstraints gbc = criarGBCBase();

        adicionarTituloECorpo(formCard, "Dados do aluno", "Preencha os dados para incluir ou editar um aluno.", gbc);
        adicionarCampoEstilizado(formCard, "Matrícula:", txtMatricula, gbc, 2);
        adicionarCampoEstilizado(formCard, "Nome Completo:", txtNome, gbc, 4);
        adicionarCampoEstilizado(formCard, "E-mail:", txtEmail, gbc, 6);
        adicionarCampoEstilizado(formCard, "Telefone:", txtTelefone, gbc, 8);

        JButton btnSalvar = estilarBotao(new JButton("Salvar"), Color.decode("#2563EB"), Color.WHITE);
        JButton btnEditar = estilarBotao(new JButton("Editar"), Color.decode("#F8FAFC"), Color.decode("#334155"));
        JButton btnExcluir = estilarBotao(new JButton("Excluir"), Color.decode("#FEF2F2"), Color.decode("#DC2626"));
        JButton btnLimpar = estilarBotao(new JButton("Limpar"), Color.decode("#F8FAFC"), Color.decode("#334155"));

        // AÇÃO SALVAR
        btnSalvar.addActionListener(e -> {
            String matricula = txtMatricula.getText().trim();
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();

            if (matricula.isEmpty() || nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Matrícula e Nome)!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (alunoDAO.salvarAluno(matricula, nome, email, telefone)) {
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCamposAluno();
                atualizarTabelaAlunos();
                carregarAlunosELivrosEmprestimo();
            }
        });

        // AÇÃO EDITAR
        btnEditar.addActionListener(e -> {
            if (idAlunoSelecionado == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String matricula = txtMatricula.getText().trim();
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();

            if (alunoDAO.editarAluno(idAlunoSelecionado, matricula, nome, email, telefone)) {
                JOptionPane.showMessageDialog(this, "Aluno atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCamposAluno();
                atualizarTabelaAlunos();
                carregarAlunosELivrosEmprestimo();
            }
        });

        // AÇÃO EXCLUIR
        btnExcluir.addActionListener(e -> {
            if (idAlunoSelecionado == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este aluno?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (alunoDAO.excluirAluno(idAlunoSelecionado)) {
                    JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCamposAluno();
                    atualizarTabelaAlunos();
                    carregarAlunosELivrosEmprestimo();
                }
            }
        });

        btnLimpar.addActionListener(e -> limparCamposAluno());

        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 6, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnSalvar);
        btnPanel.add(btnEditar);
        btnPanel.add(btnExcluir);
        btnPanel.add(btnLimpar);

        gbc.gridy = 10; gbc.insets = new Insets(24, 0, 0, 0);
        formCard.add(btnPanel, gbc);

        // Tabela Card
        JPanel tableCard = criarCardTranslucido();
        tableCard.setLayout(new BorderLayout(16, 16));

        JLabel lblTableTitle = new JLabel("Alunos cadastrados");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTableTitle.setForeground(Color.decode("#0F172A"));
        tableCard.add(lblTableTitle, BorderLayout.NORTH);

        String[] colunas = {"ID", "MATRÍCULA", "NOME", "E-MAIL", "TELEFONE"};
        modelTabelaAlunos = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaAlunos = criarTabelaEstilizadaComModel(modelTabelaAlunos);
        tabelaAlunos.getColumnModel().getColumn(0).setPreferredWidth(45);
        tabelaAlunos.getColumnModel().getColumn(1).setPreferredWidth(95);
        tabelaAlunos.getColumnModel().getColumn(2).setPreferredWidth(160);
        tabelaAlunos.getColumnModel().getColumn(3).setPreferredWidth(190);
        tabelaAlunos.getColumnModel().getColumn(4).setPreferredWidth(110);

        // EVENTO DE SELEÇÃO NA TABELA DE ALUNOS
        tabelaAlunos.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabelaAlunos.getSelectedRow();
            if (linha != -1) {
                idAlunoSelecionado = Integer.parseInt(tabelaAlunos.getValueAt(linha, 0).toString());
                txtMatricula.setText(tabelaAlunos.getValueAt(linha, 1).toString());
                txtNome.setText(tabelaAlunos.getValueAt(linha, 2).toString());
                txtEmail.setText(tabelaAlunos.getValueAt(linha, 3) != null ? tabelaAlunos.getValueAt(linha, 3).toString() : "");
                txtTelefone.setText(tabelaAlunos.getValueAt(linha, 4) != null ? tabelaAlunos.getValueAt(linha, 4).toString() : "");
            }
        });

        JScrollPane scroll = new JScrollPane(tabelaAlunos);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        mainPanel.add(formCard, BorderLayout.WEST);
        mainPanel.add(tableCard, BorderLayout.CENTER);

        atualizarTabelaAlunos();
        return mainPanel;
    }

    // ==========================================
    // 2. ABA AUTORES
    // ==========================================
    private JPanel criarPainelAutores() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(Color.decode("#F1F5F9"));

        JPanel formCard = criarCardTranslucido();
        formCard.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = criarGBCBase();

        adicionarTituloECorpo(formCard, "Dados do autor", "Gerencie o catálogo de autoria.", gbc);
        adicionarCampoEstilizado(formCard, "Nome do Autor", txtNomeAutor, gbc, 2);
        adicionarCampoEstilizado(formCard, "Nacionalidade", txtNacionalidadeAutor, gbc, 4);

        JButton btnSalvar = estilarBotao(new JButton("Salvar"), Color.decode("#2563EB"), Color.WHITE);
        btnSalvar.addActionListener(e -> {
            String nome = txtNomeAutor.getText().trim();
            String nacionalidade = txtNacionalidadeAutor.getText().trim();
            if (!nome.isEmpty() && autorDAO.salvarAutor(nome, nacionalidade)) {
                JOptionPane.showMessageDialog(this, "Autor cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtNomeAutor.setText(""); txtNacionalidadeAutor.setText("");
                atualizarTabelaAutores();
                carregarAutoresNoComboBox();
            }
        });

        JPanel btnPanel = new JPanel(new GridLayout(1, 1, 6, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnSalvar);

        gbc.gridy = 6; gbc.insets = new Insets(24, 0, 0, 0);
        formCard.add(btnPanel, gbc);

        JPanel tableCard = criarCardTranslucido();
        tableCard.setLayout(new BorderLayout(16, 16));

        String[] colunas = {"ID", "NOME DO AUTOR", "NACIONALIDADE"};
        modelTabelaAutores = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaAutores = criarTabelaEstilizadaComModel(modelTabelaAutores);

        JScrollPane scroll = new JScrollPane(tabelaAutores);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        content.add(formCard, BorderLayout.WEST);
        content.add(tableCard, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);
        atualizarTabelaAutores();
        return mainPanel;
    }

    // ==========================================
    // 3. ABA LIVROS
    // ==========================================
    private JPanel criarPainelLivros() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(Color.decode("#F1F5F9"));

        JPanel formCard = criarCardTranslucido();
        formCard.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = criarGBCBase();

        adicionarTituloECorpo(formCard, "Dados do livro", "", gbc);
        adicionarCampoEstilizado(formCard, "Título do Livro", txtTituloLivro, gbc, 2);
        adicionarCampoEstilizado(formCard, "ISBN", txtIsbnLivro, gbc, 4);
        adicionarCampoEstilizado(formCard, "Ano de Publicação", txtAnoLivro, gbc, 6);
        adicionarCampoEstilizado(formCard, "Quantidade em Estoque", txtQtdEstoque, gbc, 8);

        cbAutorLivro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbAutorLivro.setPreferredSize(new Dimension(0, 36));
        cbAutorLivro.setBackground(Color.WHITE);
        adicionarComponenteEstilizado(formCard, "Autor", cbAutorLivro, gbc, 10);

        JButton btnSalvar = estilarBotao(new JButton("Salvar"), Color.decode("#2563EB"), Color.WHITE);
        btnSalvar.addActionListener(e -> {
            if (txtTituloLivro.getText().trim().isEmpty() || cbAutorLivro.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Preencha o Título e selecione um Autor!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String selecaoAutor = (String) cbAutorLivro.getSelectedItem();
                int autorId = Integer.parseInt(selecaoAutor.split(" - ")[0]);
                int ano = txtAnoLivro.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtAnoLivro.getText().trim());
                int estoque = txtQtdEstoque.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtQtdEstoque.getText().trim());

                if (livroDAO.salvarLivro(txtTituloLivro.getText().trim(), txtIsbnLivro.getText().trim(), ano, estoque, autorId)) {
                    JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCamposLivro();
                    atualizarTabelaLivros();
                    carregarAlunosELivrosEmprestimo();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ano e Estoque devem ser números inteiros!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel btnPanel = new JPanel(new GridLayout(1, 1, 6, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnSalvar);

        gbc.gridy = 12; gbc.insets = new Insets(24, 0, 0, 0);
        formCard.add(btnPanel, gbc);

        JPanel tableCard = criarCardTranslucido();
        tableCard.setLayout(new BorderLayout(16, 16));

        String[] colunas = {"ID", "TÍTULO", "ISBN", "ANO", "ESTOQUE", "AUTOR"};
        modelTabelaLivros = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaLivros = criarTabelaEstilizadaComModel(modelTabelaLivros);
        JScrollPane scroll = new JScrollPane(tabelaLivros);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        content.add(formCard, BorderLayout.WEST);
        content.add(tableCard, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);
        carregarAutoresNoComboBox();
        atualizarTabelaLivros();
        return mainPanel;
    }

    // ==========================================
    // 4. ABA EMPRÉSTIMOS
    // ==========================================
    private JPanel criarPainelEmprestimos() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.decode("#F1F5F9"));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(Color.decode("#F1F5F9"));

        JPanel formCard = criarCardTranslucido();
        formCard.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = criarGBCBase();

        adicionarTituloECorpo(formCard, "Novo empréstimo", "", gbc);

        cbAlunoEmprestimo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbAlunoEmprestimo.setPreferredSize(new Dimension(0, 36));
        cbAlunoEmprestimo.setBackground(Color.WHITE);
        adicionarComponenteEstilizado(formCard, "Aluno", cbAlunoEmprestimo, gbc, 2);

        cbLivroEmprestimo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbLivroEmprestimo.setPreferredSize(new Dimension(0, 36));
        cbLivroEmprestimo.setBackground(Color.WHITE);
        adicionarComponenteEstilizado(formCard, "Livro", cbLivroEmprestimo, gbc, 4);

        adicionarCampoEstilizado(formCard, "Data de Empréstimo", txtDataEmprestimo, gbc, 6);
        adicionarCampoEstilizado(formCard, "Data de Devolução Prevista", txtDataDevolucao, gbc, 8);

        JButton btnRegistrar = estilarBotao(new JButton("Registrar Empréstimo"), Color.decode("#2563EB"), Color.WHITE);
        
        btnRegistrar.addActionListener(e -> {
            if (cbAlunoEmprestimo.getSelectedItem() == null || cbLivroEmprestimo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um Aluno e um Livro válidos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String selecaoAluno = (String) cbAlunoEmprestimo.getSelectedItem();
                int alunoId = Integer.parseInt(selecaoAluno.split(" - ")[0]);

                String selecaoLivro = (String) cbLivroEmprestimo.getSelectedItem();
                int livroId = Integer.parseInt(selecaoLivro.split(" - ")[0]);

                String dataEmp = txtDataEmprestimo.getText().trim();
                String dataDev = txtDataDevolucao.getText().trim();

                if (emprestimoDAO.salvarEmprestimo(alunoId, livroId, dataEmp, dataDev)) {
                    JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    atualizarTabelaEmprestimos();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao processar empréstimo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 10; gbc.insets = new Insets(20, 0, 0, 0);
        formCard.add(btnRegistrar, gbc);

        JPanel tableCard = criarCardTranslucido();
        tableCard.setLayout(new BorderLayout(16, 16));

        String[] colunas = {"ID", "ALUNO", "LIVRO", "EMPRÉSTIMO", "PREVISÃO", "STATUS"};
        modelTabelaEmprestimos = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaEmprestimos = criarTabelaEstilizadaComModel(modelTabelaEmprestimos);
        JScrollPane scroll = new JScrollPane(tabelaEmprestimos);
        scroll.setBorder(new LineBorder(Color.decode("#E2E8F0"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        content.add(formCard, BorderLayout.WEST);
        content.add(tableCard, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);
        
        carregarAlunosELivrosEmprestimo();
        atualizarTabelaEmprestimos();
        return mainPanel;
    }

    // ==========================================
    // MÉTODOS AUXILIARES E CONEXÕES BANCO
    // ==========================================
    private void carregarAlunosELivrosEmprestimo() {
        cbAlunoEmprestimo.removeAllItems();
        List<Object[]> alunos = alunoDAO.listarAlunos();
        for (Object[] aluno : alunos) {
            cbAlunoEmprestimo.addItem(aluno[0] + " - " + aluno[2] + " (" + aluno[1] + ")");
        }

        cbLivroEmprestimo.removeAllItems();
        List<Object[]> livros = livroDAO.listarLivros();
        for (Object[] livro : livros) {
            cbLivroEmprestimo.addItem(livro[0] + " - " + livro[1]);
        }
    }

    private void carregarAutoresNoComboBox() {
        cbAutorLivro.removeAllItems();
        List<Object[]> autores = autorDAO.listarAutores();
        for (Object[] autor : autores) {
            cbAutorLivro.addItem(autor[0] + " - " + autor[1]);
        }
    }

    private void atualizarTabelaAlunos() {
        if (modelTabelaAlunos != null) {
            modelTabelaAlunos.setRowCount(0);
            List<Object[]> alunos = alunoDAO.listarAlunos();
            for (Object[] linha : alunos) {
                modelTabelaAlunos.addRow(linha);
            }
        }
    }

    private void atualizarTabelaAutores() {
        if (modelTabelaAutores != null) {
            modelTabelaAutores.setRowCount(0);
            List<Object[]> autores = autorDAO.listarAutores();
            for (Object[] linha : autores) {
                modelTabelaAutores.addRow(linha);
            }
        }
    }

    private void atualizarTabelaLivros() {
        if (modelTabelaLivros != null) {
            modelTabelaLivros.setRowCount(0);
            List<Object[]> livros = livroDAO.listarLivros();
            for (Object[] linha : livros) {
                modelTabelaLivros.addRow(linha);
            }
        }
    }

    private void atualizarTabelaEmprestimos() {
        if (modelTabelaEmprestimos != null) {
            modelTabelaEmprestimos.setRowCount(0);
            List<Object[]> lista = emprestimoDAO.listarEmprestimos();
            for (Object[] linha : lista) {
                modelTabelaEmprestimos.addRow(linha);
            }
        }
    }

    private void limparCamposAluno() {
        idAlunoSelecionado = -1;
        txtMatricula.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        if (tabelaAlunos != null) tabelaAlunos.clearSelection();
    }

    private void limparCamposLivro() {
        idLivroSelecionado = -1;
        txtTituloLivro.setText("");
        txtIsbnLivro.setText("");
        txtAnoLivro.setText("");
        txtQtdEstoque.setText("");
        if (tabelaLivros != null) tabelaLivros.clearSelection();
    }

    private JPanel criarCardTranslucido() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(Color.decode("#E2E8F0"), 1, true), new EmptyBorder(18, 18, 18, 18)));
        return card;
    }

    private GridBagConstraints criarGBCBase() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
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
        gbc.gridy = y; gbc.gridx = 0; gbc.gridwidth = 2;
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
            ((JTextField) comp).setBorder(new CompoundBorder(new LineBorder(Color.decode("#CBD5E1"), 1, true), new EmptyBorder(4, 8, 4, 8)));
        }
        p.add(comp, gbc);
    }

    private JTable criarTabelaEstilizadaComModel(DefaultTableModel model) {
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
        try { FlatLightLaf.setup(); } catch (Exception e) { e.printStackTrace(); }
        conexao.inicializarBanco();
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
