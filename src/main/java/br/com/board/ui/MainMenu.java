package br.com.board.ui;

import br.com.board.persistence.entity.BoardEntity;
import br.com.board.service.BoardService;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scan = new Scanner(System.in);
    private final BoardService boardService = new BoardService();

    public void execute() throws Exception {
        System.out.println("=== Gerenciador de Boards ===");

        while (true) {
            System.out.println("--- Menu Principal ---");
            System.out.println("1. Criar novo Board");
            System.out.println("2. Selecionar Board");
            System.out.println("3. Excluir Board");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int option = scan.nextInt();
            scan.nextLine(); 

            switch (option) {
                case 1:
                    createBoard();
                    break;
                case 2:
                    System.out.print("Informe o ID do board: ");
                    Long id = scan.nextLong();
                    scan.nextLine();
                    BoardEntity board = boardService.findById(id);
                    System.out.println(board != null ? "Board encontrado: \nID: " + board.getId() + ", Nome: " + board.getName() : "Board não encontrado.");
                    break;
                case 3:
                    System.out.print("Informe o ID do board a ser excluído: ");
                    Long idToDelete = scan.nextLong();
                    scan.nextLine();
                    boardService.delete(idToDelete);
                    System.out.println("Board excluído com sucesso.");
                    break;
                case 0:
                    System.out.println("Saindo do gerenciador de boards.");
                    scan.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void createBoard() throws Exception {
        System.out.println("Informe o nome do board: ");
        String name = scan.nextLine();

        BoardEntity newBoard = boardService.insert(name);

        System.out.println("Board criado com sucesso! \nID: " + newBoard.getId() + ", Nome: " + newBoard.getName());
    }
}