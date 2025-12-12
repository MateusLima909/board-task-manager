package br.com.board;

import br.com.board.persistence.entity.BoardEntity;
import br.com.board.service.BoardService;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) throws Exception {
        BoardService service = new BoardService();
        Javalin app = Javalin.create().start(8080);

        app.get("/", ctx -> ctx.result("Gerenciador de Boards rodando!"));

        app.get("/boards/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            BoardEntity entity = service.findById(id);

            if (entity == null) {
                ctx.status(404).result("Board não encontrado");
            } else {
                ctx.json(entity);
            }
        });

        app.get("/boards", ctx -> {
            BoardEntity[] boards = service.findAll();
            ctx.json(boards);
        });

        app.post("/boards", ctx -> {
            BoardEntity content = ctx.bodyAsClass(BoardEntity.class);
            BoardEntity createdBoard = service.insert(content.getName());
            ctx.status(201).json(createdBoard);
        });

        app.put("/boards/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            BoardEntity content = ctx.bodyAsClass(BoardEntity.class);
            BoardEntity existingBoard = service.findById(id);

            if (existingBoard == null) {
                ctx.status(404).result("Board não encontrado para atualização");
            } else {
                existingBoard.setName(content.getName());
                service.update(existingBoard);
                ctx.json(existingBoard);
            }
        });

        app.delete("/boards/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            boolean deleted = service.delete(id);

            if (deleted) {
                ctx.status(204);
            } else {
                ctx.status(404).result("Board não encontrado para exclusão");
            }
        });
    }
}