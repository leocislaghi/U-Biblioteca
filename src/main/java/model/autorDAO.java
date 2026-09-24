package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class autorDAO {

    // Salva um novo autor no banco de dados
    public static boolean salvarAutor(String nome, String nacionalidade) {
        String sql = "INSERT INTO autores (nome, nacionalidade) VALUES (?, ?)";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, nacionalidade);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar autor no banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Listar todos os autores cadastrados
    public static List<Object[]> listarAutores() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, nome, nacionalidade FROM autores ORDER BY id DESC";

        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = new Object[]{
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("nacionalidade")
                };
                lista.add(linha);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar autores do banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }
}