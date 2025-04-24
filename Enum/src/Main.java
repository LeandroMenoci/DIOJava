import enumeration.OperationEnum;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção");

        while (true){
            System.out.println("1 - Soma");
            System.out.println("2 - Subtracao");
            System.out.println("3 - Multiplicacao");
            System.out.println("4 - Divisao");
            System.out.println("5 - Sair");
            var option = scanner.nextInt();

            if (option > 5 || option < 1) {
                System.out.println("Selecione uma opcao válida");
                continue;
            } else if (option == 5) {
                System.out.println("Você saiu.");
                break;
            }

            var selectedOption = OperationEnum.values()[option - 1];

            System.out.println("Informe o Primeiro Valor");
            var value1 = scanner.nextInt();
            System.out.println("Informe o Segundo Valor");
            var value2 = scanner.nextInt();

            var result = selectedOption.getCalculate().apply(value1, value2);

            System.out.printf("O resultado é: %s %s %s = %s\n", value1,
                    selectedOption.getSymbol(), value2, result);
            System.out.println("========");
        }
    }
}