/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class conexao {

    private static final String NOME_BANCO = "BibliotecaBanco.db";
    private static final String URL = "jdbc:sqlite:" + NOME_BANCO;

    private conexao() {
    }

    /**
     * Conecta ao banco de dados SQLite e exibe mensagem de status no console.
     */
    public static Connection conectar() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println(" Driver do SQLite não encontrado: " + e.getMessage());
        }

        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println(" Conexão com o banco SQLite (" + NOME_BANCO + ") estabelecida com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.err.println(" Falha ao conectar ao banco de dados SQLite: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retorna o caminho absoluto onde o arquivo .db está salvo.
     */
    public static String caminhoBanco() {
        return new File(NOME_BANCO).getAbsolutePath();
    }

    /**
     * Cria automaticamente todas as tabelas do sistema se ainda não existirem.
     */
    public static void inicializarBanco() {
        // Tabela Alunos
        String sqlAlunos = "CREATE TABLE IF NOT EXISTS alunos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "matricula TEXT UNIQUE NOT NULL, "
                + "nome TEXT NOT NULL, "
                + "email TEXT, "
                + "telefone TEXT"
                + ");";

        // Tabela Autores
        String sqlAutores = "CREATE TABLE IF NOT EXISTS autores ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "nacionalidade TEXT"
                + ");";

        // Tabela Livros
        String sqlLivros = "CREATE TABLE IF NOT EXISTS livros ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "titulo TEXT NOT NULL, "
                + "isbn TEXT, "
                + "ano INTEGER, "
                + "quantidade INTEGER DEFAULT 1, "
                + "autor_id INTEGER, "
                + "FOREIGN KEY (autor_id) REFERENCES autores(id)"
                + ");";

        // Tabela Empréstimos
        String sqlEmprestimos = "CREATE TABLE IF NOT EXISTS emprestimos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "aluno_id INTEGER NOT NULL, "
                + "livro_id INTEGER NOT NULL, "
                + "data_emprestimo TEXT NOT NULL, "
                + "data_devolucao_prevista TEXT NOT NULL, "
                + "status TEXT DEFAULT 'ATIVO', "
                + "FOREIGN KEY (aluno_id) REFERENCES alunos(id), "
                + "FOREIGN KEY (livro_id) REFERENCES livros(id)"
                + ");";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlAlunos);
            stmt.execute(sqlAutores);
            stmt.execute(sqlLivros);
            stmt.execute(sqlEmprestimos);
            System.out.println("✅ Tabelas verificadas/criadas com sucesso no SQLite!");
        } catch (SQLException e) {
            System.err.println("❌ Erro ao criar tabelas no banco de dados: " + e.getMessage());
        }
    }
}