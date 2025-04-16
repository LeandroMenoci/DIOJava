public class Atendente extends User {

    private double valorCaixa;

    public Atendente(String nome, String email, String senha) {
        super(nome, email, senha, false);
        this.valorCaixa = 0.0;
    }

    public double getValorCaixa() {
        return valorCaixa;
    }

    public void receberPagamento(double valor) {
        if(valor > 0) {
            valorCaixa += valor;
            System.out.println("Pagamento recebido: R$ " + valor + ". Total no caixa: R$" + valorCaixa);
        } else {
            System.out.println("Valor inválido");
        }
    }

    public void fecharCaixa() {
        System.out.println("Fechando o caixa. Total: R$" + valorCaixa);
        valorCaixa = 0.0;
    }
}
