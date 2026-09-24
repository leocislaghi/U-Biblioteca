package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class autorDAO {

    public static boolean salvarAutor(String nome, String nacionalidade) {
        String sql = "INSERT INTO autores (nome, nacionalidade) VALUES (?, ?)";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, nacionalidade);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar autor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean editarAutor(int id, String nome, String nacionalidade) {
        String sql = "UPDATE autores SET nome = ?, nacionalidade = ? WHERE id = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, nacionalidade);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar autor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean excluirAutor(int id) {
        String sql = "DELETE FROM autores WHERE id = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir autor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<Object[]> listarAutores() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, nome, nacionalidade FROM autores ORDER BY id DESC";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("nacionalidade")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar autores: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
}