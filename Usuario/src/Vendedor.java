public class Vendedor extends User{

    private int quantidadeDeVendas;


    public Vendedor(String nome, String email, String senha) {
        super(nome, email, senha, false);
        this.quantidadeDeVendas = 0;
    }

    public int getQuantidadeDeVendas() {
        return quantidadeDeVendas;
    }

    public void realizarVenda() {
        quantidadeDeVendas++;
        System.out.println("Venda realizada, total de vendas: " + quantidadeDeVendas);
    }

    public void consultarVendas() {
        System.out.println("Você realizou " + quantidadeDeVendas + " vendas.");
    }
}
