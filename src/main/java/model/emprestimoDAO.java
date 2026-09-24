package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class emprestimoDAO {

    public static boolean salvarEmprestimo(int alunoId, int livroId, String dataEmprestimo, String dataDevolucao) {
        String sql = "INSERT INTO emprestimos (aluno_id, livro_id, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, 'ATIVO')";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alunoId);
            stmt.setInt(2, livroId);
            stmt.setString(3, dataEmprestimo);
            stmt.setString(4, dataDevolucao);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar empréstimo no banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<Object[]> listarEmprestimos() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT e.id, a.nome AS aluno_nome, l.titulo AS livro_titulo, " +
                     "e.data_emprestimo, e.data_devolucao, e.status " +
                     "FROM emprestimos e " +
                     "INNER JOIN alunos a ON e.aluno_id = a.id " +
                     "INNER JOIN livros l ON e.livro_id = l.id " +
                     "ORDER BY e.id DESC";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = new Object[]{
                    rs.getInt("id"),
                    rs.getString("aluno_nome"),
                    rs.getString("livro_titulo"),
                    rs.getString("data_emprestimo"),
                    rs.getString("data_devolucao"),
                    rs.getString("status")
                };
                lista.add(linha);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar empréstimos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }
}