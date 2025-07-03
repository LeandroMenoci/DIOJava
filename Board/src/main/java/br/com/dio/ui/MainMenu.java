package br.com.dio.ui;

import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardColumnKindEnum;
import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.service.BoardQueryService;
import br.com.dio.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.*;

public class MainMenu {

    // Scanner para capturar entradas do usuário via terminal
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    // Método principal de execução do menu
    public void execute() throws SQLException {
        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada.");
        var option = -1;

        // Loop principal do menu
        while (true) {
            System.out.println("1 - Criar um novo board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - Excluir um board");
            System.out.println("4 - Sair");
            option = scanner.nextInt(); // Lê a opção digitada

            switch (option) {
                case 1 -> createBoard();   // Criação de novo board
                case 2 -> selectBoard();   // Acessar um board existente
                case 3 -> deleteBoard();   // Excluir um board
                case 4 -> System.exit(0);  // Encerra o sistema
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    // Criação de um novo board com colunas padrão e adicionais
    private void createBoard() throws SQLException {
        var entity = new BoardEntity();

        // Solicita o nome do board
        System.out.println("Informe o nome do seu Board");
        entity.setName(scanner.next());

        // Pergunta se haverá colunas adicionais
        System.out.println("Seu Board terá colunas além das 3 padrões? Se sim, informe quantas, se não digite '0'");
        var additionalColumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        // Cria coluna inicial obrigatória
        System.out.println("Informe o nome da coluna inicial do Board");
        var initialColumnName = scanner.next();
        var initialColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        // Cria colunas adicionais do tipo PENDING
        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente do Board");
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumn(pendingColumnName, PENDING, i + 1);
            columns.add(pendingColumn);
        }

        // Cria coluna final obrigatória
        System.out.println("Informe o nome da coluna final do Board");
        var finalColumnName = scanner.next();
        var finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        // Cria coluna de cancelamento obrigatória
        System.out.println("Informe o nome da coluna de cancelamento do Board");
        var cancelColumnName = scanner.next();
        var cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        // Associa as colunas criadas ao board
        entity.setBoardColumns(columns);

        // Persiste o board e suas colunas no banco de dados
        try (var connection = getConnection()) {
            var service = new BoardService(connection);
            service.insert(entity);
        }
    }

    // Acesso a um board já criado pelo ID
    private void selectBoard() throws SQLException {
        System.out.println("Informe o ID do Board que deseja selecionar");
        var id = scanner.nextLong();

        // Consulta e, se encontrado, inicia o menu do board
        try (var connection = getConnection()) {
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            optional.ifPresentOrElse(
                    b -> new BoardMenu(b).execute(),
                    () -> System.out.printf("Não foi encontrado um Board com o ID %s\n", id)
            );
        }
    }

    // Exclusão de um board pelo ID
    private void deleteBoard() throws SQLException {
        System.out.println("Informe o ID do Board que será excluido");
        var id = scanner.nextLong();

        try (var connection = getConnection()) {
            var service = new BoardService(connection);
            if (service.delete(id)) {
                System.out.printf("O Board %s foi excluído \n", id);
            } else {
                System.out.printf("Não foi encontrado um Board com o ID %s\n", id);
            }
        }
    }

    // Metodo auxiliar para criação de colunas
    private BoardColumnEntity createColumn(String name, BoardColumnKindEnum kind, int order) {
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);       // Nome informado pelo usuário
        boardColumn.setKind(kind);       // Tipo da coluna (INITIAL, PENDING, FINAL, CANCEL)
        boardColumn.setOrder(order);     // Ordem usada para navegação entre colunas

        return boardColumn;
    }
}
