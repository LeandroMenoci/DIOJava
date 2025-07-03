package br.com.dio.ui;

import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.persistence.entity.CardEntity;
import br.com.dio.service.BoardColumnQueryService;
import br.com.dio.service.BoardQueryService;
import br.com.dio.service.CardQueryService;
import br.com.dio.service.CardService;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Scanner;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class BoardMenu {
    private final BoardEntity entity;

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public void execute() {
        try {
            System.out.printf("Bem vindo ao board %s, selecione a operação desejada\n", entity.getId());
            var option = -1;
            while (option != 9) {
                System.out.println("1 - Criar um card");
                System.out.println("2 - Mover um card");
                System.out.println("3 - Bloquear um card");
                System.out.println("4 - Desbloquear um card");
                System.out.println("5 - Cancelar um card");
                System.out.println("6 - Visualizar board");
                System.out.println("7 - Visualizar colunas com cards");
                System.out.println("8 - Visualizar card");
                System.out.println("9 - Voltar para o menu anterior");
                System.out.println("10 - Sair");
                option = scanner.nextInt();

                switch (option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                    case 9 -> System.out.println("Voltando para o menu anterior");
                    case 10 -> System.exit(0);
                    default -> System.out.println("Opção inválida, informe uma opção do menu");

                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private void createCard() throws SQLException{
        var card = new CardEntity();
        System.out.println("Informe o título do card");
        card.setTitle(scanner.next());
        System.out.println("Informe a descrição do card");
        card.setDescription(scanner.next());
        card.setBoardColumn(entity.getInitialColumn());

        try(var connection = getConnection()) {
            new CardService(connection).insert(card);
            System.out.println("Card criado com sucesso");
        }

    }

    private void moveCardToNextColumn() throws SQLException {
        // Solicita ao usuário o ID do card a ser movido
        System.out.println("Informe o id do card que deseja mover para a próxima coluna");
        var cardId = scanner.nextLong();

        // Mapeia as colunas do board atual em uma lista de DTOs com id, ordem e tipo
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(
                        bc.getId(),
                        bc.getOrder(),
                        bc.getKind()
                ))
                .toList();

        // Abre conexão com o banco e executa a movimentação via serviço
        try (var connection = getConnection()) {
            new CardService(connection).moveToNextColumn(cardId, boardColumnsInfo);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
        // Solicita o ID do card ao usuário
        System.out.println("Informe o id do card que deseja cancelar");
        var cardId = scanner.nextLong();

        // Obtém a coluna de cancelamento configurada no board
        var cancelColumn = entity.getCancelColumn();

        // Constrói uma lista com as informações básicas de cada coluna do board
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(
                        bc.getId(),
                        bc.getOrder(),
                        bc.getKind()
                ))
                .toList();

        // Abre a conexão e chama o serviço responsável por cancelar (mover) o card
        try (var connection = getConnection()) {
            new CardService(connection).cancel(cardId, cancelColumn.getId(), boardColumnsInfo);
            System.out.println("Card cancelado com sucesso!");
        } catch (RuntimeException | SQLException ex) {
            // Captura e exibe erros de negócio ou banco de dados
            System.out.println(ex.getMessage());
        }
    }


    private void showBoard() throws SQLException {
        // Tenta obter conexão com o banco (fecha automaticamente com try-with-resources)
        try (var connection = getConnection()) {
            // Chama o serviço para buscar os detalhes do board atual pelo ID
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());

            // Se o board for encontrado, imprime seus detalhes
            optional.ifPresent(b -> {
                // Exibe ID e nome do board
                System.out.printf("Board [%d, %s]\n", b.id(), b.name());

                // Para cada coluna do board, mostra nome, tipo e quantidade de cards
                b.columns().forEach(c ->
                        System.out.printf("Coluna [%s] tipo: [%s] tem %d cards\n",
                                c.name(),
                                c.kind(),
                                c.cardsAmount())
                );
            });
        }
    }

    private void showColumn() throws SQLException {
        // Exibe o nome do board atual e inicia o processo de seleção de coluna
        System.out.printf("Escolha uma coluna do board %s\n", entity.getName());

        // Coleta todos os IDs de colunas associadas ao board atual
        var columnsIds = entity.getBoardColumns()
                .stream()
                .map(BoardColumnEntity::getId)
                .toList();

        var selectedColumn = -1L;

        // Enquanto o usuário não informar um ‘ID’ válido, continua a solicitar entrada
        while (!columnsIds.contains(selectedColumn)) {
            // Exibe todas as colunas disponíveis do board
            entity.getBoardColumns().forEach(c ->
                    System.out.printf("%s - %s [%s]\n", c.getId(), c.getName(), c.getKind())
            );
            selectedColumn = scanner.nextLong(); // Lê o ID da coluna escolhida
        }

        // Após escolha válida, busca detalhes da coluna no banco de dados
        try (var connection = getConnection()) {
            var column = new BoardColumnQueryService(connection).findById(selectedColumn);

            // Se a coluna existir, exibe suas informações e lista de cards
            column.ifPresent(co -> {
                System.out.printf("Coluna %s tipo %s\n", co.getName(), co.getKind());

                // Para cada card associado à coluna, imprime ID, título e descrição
                co.getCards().forEach(ca ->
                        System.out.printf("Card %s - %s\nDescrição: %s\n",
                                ca.getId(),
                                ca.getTitle(),
                                ca.getDescription())
                );
            });
        }
    }

    private void showCard() throws SQLException {
        // Solicita ao usuário o ID do card a ser visualizado
        System.out.println("Informe o id do card que deseja visualizar");
        var selectedCardId = scanner.nextLong();

        // Abre conexão com o banco de dados
        try (var connection = getConnection()) {
            // Usa um serviço de consulta para buscar o card pelo ID
            new CardQueryService(connection).findById(selectedCardId)
                    .ifPresentOrElse(
                            c -> {
                                // Exibe os dados do card encontrados
                                System.out.printf("Card %d - %s\n", c.id(), c.title());
                                System.out.printf("Descrição: %s\n", c.description());

                                // Mostra o estado de bloqueio e o motivo, se houver
                                System.out.println(c.blocked()
                                        ? "Está bloqueado. Motivo: " + c.blockReason()
                                        : "Está desbloqueado");

                                // Mostra quantas vezes foi bloqueado
                                System.out.printf("Já foi bloqueado %d vezes\n", c.blocksAmount());

                                // Mostra a coluna atual onde o card está
                                System.out.printf("No momento está na coluna %d - %s\n", c.columnId(), c.columnName());
                            },
                            () -> {
                                // Mensagem caso nenhum card seja encontrado com o ID informado
                                System.out.printf("Não existe um card com o id %d\n", selectedCardId);
                            }
                    );
        }
    }
}
