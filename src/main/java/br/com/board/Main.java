package br.com.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.board.service.BoardQueryService;
import br.com.board.persistence.entity.BoardEntity;
import br.com.board.persistence.entity.CardEntity;
import br.com.board.service.BoardService;
import br.com.board.service.CardService;
import io.javalin.json.JavalinJackson;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) throws Exception {
        BoardQueryService boardQueryService = new BoardQueryService();
        BoardService service = new BoardService();
        CardService cardService = new CardService();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson(objectMapper));
        }).start(8080);

        app.get("/", ctx -> ctx.result("Gerenciador de Boards rodando!"));

        app.get("/boards/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id")); 
            
            var boardDetails = boardQueryService.showBoardDetails(id);
            
            if (boardDetails == null) {
                ctx.status(404).result("Board não encontrado");
            } else {
                ctx.json(boardDetails);
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

        app.post("/boards/{id}/cards", ctx -> {
            Long boardId = Long.parseLong(ctx.pathParam("id"));
            CardEntity content = ctx.bodyAsClass(CardEntity.class);

            CardEntity createdCard = cardService.insert(boardId, content.getTitle(), content.getDescription());
            ctx.status(201).json(createdCard);
        });

        app.post("/cards/{id}/move", ctx -> {
            Long cardId = Long.parseLong(ctx.pathParam("id"));
  
            cardService.moveToNextColumn(cardId);

            ctx.status(204);
        });

        app.post("/cards/{id}/block", ctx -> {
            Long cardId = Long.parseLong(ctx.pathParam("id"));
            
            var params = ctx.bodyAsClass(BlockDTO.class);

            cardService.block(cardId, params.getReason());

            ctx.status(201).result("Card bloqueado com sucesso");
        });

        app.post("/cards/{id}/unblock", ctx -> {
            Long cardId = Long.parseLong(ctx.pathParam("id"));
            
            var params = ctx.bodyAsClass(BlockDTO.class);

            cardService.unblock(cardId, params.getReason());

            ctx.status(200).result("Card desbloqueado com sucesso");
        });


    }
    
}

class BlockDTO {
    private String reason;
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}