package model;

public class Aluno {

    private int id;
    private String matricula;
    private String nome;
    private String email;
    private String telefone;

    // Construtor vazio
    public Aluno() {
    }

    // Construtor SEM ID (usado no cadastro/salvar)
    public Aluno(String matricula, String nome, String email, String telefone) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // CONSTRUTOR COM ID (Faltava este para a edição!)
    public Aluno(int id, String matricula, String nome, String email, String telefone) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}