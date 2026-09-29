package model;

import DAO.AlunoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlunoDAOTest {

    private AlunoDAO alunoDAO;

    @BeforeEach
    public void setUp() {
        alunoDAO = new AlunoDAO();
    }

    @Test
    public void testSalvarAluno() {
        Aluno aluno = new Aluno("12345", "João Silva", "joao@email.com", "51999999999");
        boolean resultado = alunoDAO.salvarAluno(aluno);
        assertTrue(resultado, "Deveria salvar o aluno com sucesso");
    }

    @Test
    public void testEditarAluno() {
        Aluno aluno = new Aluno(1, "12345", "João Silva Editado", "joao@email.com", "51999999999");
        boolean resultado = alunoDAO.editarAluno(aluno);
        assertTrue(resultado, "Deveria editar o aluno com sucesso");
    }

    @Test
    public void testExcluirAluno() {
        int idAluno = 1;
        boolean resultado = alunoDAO.excluirAluno(idAluno);
        assertTrue(resultado, "Deveria excluir o aluno com sucesso");
    }
}
