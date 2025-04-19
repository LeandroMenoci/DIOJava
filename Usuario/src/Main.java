public class Main {
    public static void main(String[] args) {
        Gerente gerente = new Gerente("Alice", "alice@empresa.com", "admin123");
        Vendedor vendedor = new Vendedor("Bruno", "bruno@empresa.com", "venda123");
        Atendente atendente = new Atendente("Carla", "carla@empresa.com", "atende123");

        gerente.realizarLogin("alice@empresa.com", "admin123");

        gerente.gerarRelatorioFinanceiro();
        gerente.consultarVendas();

        vendedor.realizarLogin("bruno@empresa.com", "venda123");
        vendedor.realizarVenda();
        vendedor.consultarVendas();

        atendente.realizarLogin("carla@empresa.com", "atende123");
        atendente.receberPagamento(150.0);
        atendente.fecharCaixa();
    }
}