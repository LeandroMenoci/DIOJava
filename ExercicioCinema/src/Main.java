public class Main {
    public static void main(String[] args) {

        Cinema ingressoPadrao = new Cinema(30.0, "Senhor dos Aneis", false);
        HalfPrice meiaEntrada = new HalfPrice(30.0, "Senhor dos Aneis", false);
        Family ingressoFamilia = new Family(30.0, "Senhor dos aneis", false, 4);

        System.out.println("Ingresso padrão R$" + ingressoPadrao.getAmountReal());
        System.out.println("Meia entrada R$" + meiaEntrada.getAmountReal());
        System.out.println("Ingresso familia para 4 pessoas R$" + ingressoFamilia.getAmountReal());
    }
}