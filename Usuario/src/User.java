public class User {

    protected String nome;
    protected String email;
    protected String senha;
    protected boolean administrador;

    public User(String nome, String email, String senha, boolean administrador) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.administrador = administrador;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome() {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail() {
        this.email = email;
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
        System.out.println("Senha alterada com sucesso");
    }

    public void realizarLogin(String email, String senha) {
        if (this.email.equals(email) && this.senha.equals(senha)) {
            System.out.println(nome + " logado com sucesso!");
        } else {
            System.out.println("Email ou senha incorretos");
        }
    }

    public void realizarLogoff() {
        System.out.println(nome + " realizou logoff");
    }

    public void alterarDados(String novoNome, String novoEmail) {
        this.nome = novoNome;
        this.email = novoEmail;
        System.out.println("Dados alterados com sucesso");
    }
}
