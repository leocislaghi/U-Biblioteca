package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class alunoDAO {

    public static boolean salvarAluno(String matricula, String nome, String email, String telefone) {
        String sql = "INSERT INTO alunos (matricula, nome, email, telefone) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            stmt.setString(2, nome);
            stmt.setString(3, email);
            stmt.setString(4, telefone);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar no banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MÉTODO PARA LISTAR OS ALUNOS DO BANCO
    public static List<Object[]> listarAlunos() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, matricula, nome, email, telefone FROM alunos ORDER BY id DESC";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = new Object[]{
                    rs.getInt("id"),
                    rs.getString("matricula"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                };
                lista.add(linha);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar alunos do banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }
    
    // EXCLUIR ALUNO
    public static boolean excluirAluno(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // EDITAR ALUNO
    public static boolean editarAluno(int id, String matricula, String nome, String email, String telefone) {
        String sql = "UPDATE alunos SET matricula = ?, nome = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            stmt.setString(2, nome);
            stmt.setString(3, email);
            stmt.setString(4, telefone);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
}