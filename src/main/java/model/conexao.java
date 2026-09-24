package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class conexao {

    // Alterado o nome do arquivo para forçar a criação do banco atualizado
    private static final String URL = "jdbc:sqlite:BibliotecaBanco_v2.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBanco() {
        String sqlAlunos = "CREATE TABLE IF NOT EXISTS alunos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "matricula TEXT NOT NULL UNIQUE, "
                + "nome TEXT NOT NULL, "
                + "email TEXT, "
                + "telefone TEXT"
                + ");";

        String sqlAutores = "CREATE TABLE IF NOT EXISTS autores ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "nacionalidade TEXT"
                + ");";

        String sqlLivros = "CREATE TABLE IF NOT EXISTS livros ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "titulo TEXT NOT NULL, "
                + "isbn TEXT, "
                + "ano INTEGER, "
                + "quantidade INTEGER DEFAULT 1, "
                + "autor_id INTEGER, "
                + "FOREIGN KEY (autor_id) REFERENCES autores(id)"
                + ");";

        String sqlEmprestimos = "CREATE TABLE IF NOT EXISTS emprestimos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "aluno_id INTEGER NOT NULL, "
                + "livro_id INTEGER NOT NULL, "
                + "data_emprestimo TEXT NOT NULL, "
                + "data_devolucao TEXT NOT NULL, "
                + "status TEXT DEFAULT 'ATIVO', "
                + "FOREIGN KEY (aluno_id) REFERENCES alunos(id), "
                + "FOREIGN KEY (livro_id) REFERENCES livros(id)"
                + ");";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlAlunos);
            stmt.execute(sqlAutores);
            stmt.execute(sqlLivros);
            stmt.execute(sqlEmprestimos);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inicializar o banco de dados: " + e.getMessage(), "Erro no Banco", JOptionPane.ERROR_MESSAGE);
        }
    }
}