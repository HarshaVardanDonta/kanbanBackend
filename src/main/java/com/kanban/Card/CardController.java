package com.kanban.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanban.Stage.StageRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/Cards")

@CrossOrigin(origins = "http://localhost:3000")

public class CardController {
    @Autowired
    private StageRepo stageRepo;
    @Autowired
    private CardRepo cardRepo;

    @PostMapping
    public Card createCard(@RequestBody Card card) {
        return cardRepo.save(card);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardRepo.deleteById(id);
    }

    @PutMapping("/{id}/{stageId}")
    public Object updateCard(@PathVariable Long id, @PathVariable Long stageId) {
        Card card = cardRepo.findById(id).orElse(null);
        if (card != null) {
            card.setStage(stageRepo.findById(stageId).orElse(null));
            cardRepo.save(card);
        }
        return card;
    }

}