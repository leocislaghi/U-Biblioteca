package model;

import dao.AlunoDAO;
import model.Aluno;
import model.conexao;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class AlunoDAOTest {

    @BeforeAll
    public static void setup() {
        // Inicializa o banco de dados antes de executar os testes
        conexao.inicializarBanco();
    }

    // --- TESTES POSITIVOS ---

    @Test
    @DisplayName("[Positivo] Deve salvar um aluno com dados válidos")
    public void testSalvarAlunoSucesso() {
        // Descrição: Instancia um objeto Aluno com dados válidos e verifica se a inserção no banco retorna true.
        String matricula = "MAT_" + System.currentTimeMillis();
        Aluno aluno = new Aluno(matricula, "Carlos Silva", "carlos@email.com", "51988887777");

        boolean salvo = AlunoDAO.salvarAluno(aluno);
        assertTrue(salvo, "O aluno válido deve ser salvo com sucesso.");
    }

    @Test
    @DisplayName("[Positivo] Deve listar os alunos salvos no banco")
    public void testListarAlunos() {
        // Descrição: Consulta o banco de dados e confirma que o retorno da lista não é nulo.
        List<Object[]> lista = AlunoDAO.listarAlunos();
        assertNotNull(lista, "A consulta não deve retornar uma lista nula.");
    }

    // --- TESTES NEGATIVOS ---

    @Test
    @DisplayName("[Negativo] Não deve salvar um objeto Aluno nulo")
    public void testSalvarAlunoNulo() {
        // Descrição: Tenta enviar null para o método de salvamento e valida se o DAO recusa a operação (retorna false).
        boolean salvo = AlunoDAO.salvarAluno(null);
        assertFalse(salvo, "O sistema não deve aceitar o salvamento de um objeto Aluno nulo.");
    }

    @Test
    @DisplayName("[Negativo] Não deve salvar aluno com matrícula vazia")
    public void testSalvarAlunoMatriculaVazia() {
        // Descrição: Cria um aluno com matrícula em branco e valida se a regra de negócio impede a inserção.
        Aluno aluno = new Aluno("", "Aluno Sem Matricula", "email@teste.com", "51900000000");

        boolean salvo = AlunoDAO.salvarAluno(aluno);
        assertFalse(salvo, "O sistema deve rejeitar cadastros sem matrícula preenchida.");
    }
}