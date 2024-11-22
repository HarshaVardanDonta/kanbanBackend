package com.kanban.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kanban.Stage.StageRepo;
import com.kanban.User.User;
import com.kanban.User.UserRepository;

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

    @Autowired
    private UserRepository userRepo;

    @PostMapping
    public Card createCard(@RequestBody Card card, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepo.findByUsername(username); // Fetch the logged-in user
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        card.setUser(user); // Assign the user to the card
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

    @PutMapping("/updateCard")
    public Card updateCardDetails(@RequestBody Card exCard, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepo.findByUsername(username); // Fetch the logged-in user

        Card card = cardRepo.findById(exCard.getId()).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        if (card != null) {
            card.setTitle(exCard.getTitle());
            card.setDesctiption(exCard.getDescription());
            card.setUser(user);
            return cardRepo.save(card);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

}