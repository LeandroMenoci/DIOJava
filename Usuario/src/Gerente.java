public class Gerente extends User{
    public Gerente(String nome, String email, String senha) {
        super(nome, email, senha, true);
    }

    public void gerarRelatorioFinanceiro() {
        System.out.println("Gerando relatório financeiro....");
    }

    public void consultarVendas() {
        System.out.println("Consultando todas as vendas do sistema...");
    }
}
