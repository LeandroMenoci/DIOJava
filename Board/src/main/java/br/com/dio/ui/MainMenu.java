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

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public void execute() throws SQLException {
        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada.");
        var option = -1;
        while (true) {
            System.out.println("1 - Criar um novo board");
            System.out.println("2 - Selecinar um board existente");
            System.out.println("3 - Excluir um board");
            System.out.println("4 - Sair");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");

            }
        }
    }

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu Board");
        entity.setName(scanner.next());

        System.out.println("Seu Board terá colunas além das 3 padrões? Se sim, informe quantas, se não digite '0'");
        var additionalColumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do Board");
        var initialColumnName = scanner.next();
        var initiallColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initiallColumn);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente do Board");
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumn(pendingColumnName, PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final do Board");
        var finalColumnName = scanner.next();
        var finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do Board");
        var cancelColumnName = scanner.next();
        var cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);

        try(var connection = getConnection()) {
            var service = new BoardService(connection);
            service.insert(entity);
        }

    }

    private void selectBoard() throws SQLException {
        System.out.println("Informe o ID do Board que deseja selecionar");
        var id = scanner.nextLong();
        try (var connection = getConnection()) {
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            optional.ifPresentOrElse(b -> new BoardMenu(b).execute(),
                    () -> System.out.printf("Não foi encontrado um Board com o ID %s\n", id));
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Informe o ID do Board que será excluido");
        var id = scanner.nextLong();
        try(var connection = getConnection()) {
            var service = new BoardService(connection);
            if(service.delete(id)){
                System.out.printf("O Board %s foi excluido \n", id);
            } else {
                System.out.printf("Não foi encontrado um Board com o ID %s\n", id);
            }
        }
    }

    private BoardColumnEntity createColumn(String name, BoardColumnKindEnum kind, int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);

        return boardColumn;
    }
}
