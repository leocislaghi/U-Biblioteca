package DAO;

import model.Aluno;
import model.conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public boolean salvarAluno(Aluno aluno) {
        if (aluno == null || aluno.getMatricula() == null || aluno.getMatricula().trim().isEmpty()) {
            return false;
        }

        String sql = "INSERT INTO alunos (matricula, nome, email, telefone) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getTelefone());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarAluno(Aluno aluno) {
        if (aluno == null || aluno.getId() <= 0) {
            return false;
        }

        String sql = "UPDATE alunos SET matricula = ?, nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getTelefone());
            stmt.setInt(5, aluno.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirAluno(int id) {
        if (id <= 0) {
            return false;
        }

        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> listarAlunos() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, matricula, nome, email, telefone FROM alunos ORDER BY id DESC";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("matricula"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}