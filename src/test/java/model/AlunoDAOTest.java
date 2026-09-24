package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AlunoDAOTest {

    @BeforeAll
    public static void setup() {
        conexao.inicializarBanco();
    }

    @Test
    @DisplayName("Teste de Cadastro de Aluno no Banco SQLite")
    public void testSalvarAluno() {
        String matricula = "TESTE_" + System.currentTimeMillis();
        String nome = "Aluno Teste Unitario";
        String email = "teste@exemplo.com";
        String telefone = "51999999999";

        boolean salvo = alunoDAO.salvarAluno(matricula, nome, email, telefone);
        assertTrue(salvo, "O aluno deve ser salvo com sucesso no banco de dados.");
    }

    @Test
    @DisplayName("Teste de Listagem de Alunos")
    public void testListarAlunos() {
        List<Object[]> alunos = alunoDAO.listarAlunos();
        assertNotNull(alunos, "A lista de alunos nao deve ser nula.");
    }
}