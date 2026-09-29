package model;

import DAO.AlunoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoDAOTest {

    private AlunoDAO alunoDAO;

    @BeforeEach
    public void setUp() {
        conexao.inicializarBanco();
        alunoDAO = new AlunoDAO();
    }

    @Test
    public void testSalvarEListarAluno() {
        Aluno aluno = new Aluno("MAT" + System.currentTimeMillis(), "Aluno Teste", "teste@email.com", "51999999999");
        boolean salvou = alunoDAO.salvarAluno(aluno);
        assertTrue(salvou);
        assertNotNull(alunoDAO.listarAlunos());
    }
}