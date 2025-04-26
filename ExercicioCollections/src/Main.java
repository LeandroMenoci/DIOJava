import br.com.dio.calc.Operation;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o numero da operação que deseja realizar " +
                "(1 = sum, 2 = subtraction)");
        var operationOption = scanner.nextInt();
        System.out.printf("Voce escolheu a opçao %s\n", operationOption);
        while (operationOption > 2 || operationOption < 1) {
            System.out.println("Escolha uma opção válida (1 = Sum, 2 = " +
                    "Subtraction");
            operationOption = scanner.nextInt();
        }
        var selectedOperation = Operation.values()[operationOption -1];
        System.out.println("Informe os números que serão usados separados por" +
                " virgula (ex: 1,2,3,4)");
        var numbers = scanner.next();
        var numberArray =
                Arrays.stream(numbers.split(",")).mapToLong(Long::parseLong).toArray();

        var result = selectedOperation.getOperationCallback().exec(numberArray);
        System.out.println("O resultado da operacao é: " + result);
    }
}