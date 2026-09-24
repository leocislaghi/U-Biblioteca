package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class livroDAO {

    public static boolean salvarLivro(String titulo, String isbn, int ano, int estoque, int autorId) {
        String sql = "INSERT INTO livros (titulo, isbn, ano, quantidade, autor_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, isbn);
            stmt.setInt(3, ano);
            stmt.setInt(4, estoque);
            stmt.setInt(5, autorId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean editarLivro(int id, String titulo, String isbn, int ano, int estoque, int autorId) {
        String sql = "UPDATE livros SET titulo = ?, isbn = ?, ano = ?, quantidade = ?, autor_id = ? WHERE id = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, isbn);
            stmt.setInt(3, ano);
            stmt.setInt(4, estoque);
            stmt.setInt(5, autorId);
            stmt.setInt(6, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean excluirLivro(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<Object[]> listarLivros() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT l.id, l.titulo, l.isbn, l.ano, l.quantidade, a.nome AS autor_nome " +
                     "FROM livros l " +
                     "LEFT JOIN autores a ON l.autor_id = a.id " +
                     "ORDER BY l.id DESC";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getInt("ano"),
                    rs.getInt("quantidade"),
                    rs.getString("autor_nome") != null ? rs.getString("autor_nome") : "Sem Autor"
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar livros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
}