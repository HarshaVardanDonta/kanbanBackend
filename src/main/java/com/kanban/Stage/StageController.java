package com.kanban.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.kanban.Card.CardRepo;
import com.kanban.User.User;
import com.kanban.User.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
@CrossOrigin(origins = "http://localhost:3000") // Ensure CORS for your frontend
public class StageController {

    @Autowired
    private StageRepo stageRepository;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/userSpecific")
    public List<Stage> getStagesForUser(@AuthenticationPrincipal UserDetails userDetails) {
        // Assuming the user ID is stored in the JWT
        String username = userDetails.getUsername();

        // Fetch user-specific stages
        User user = userRepo.findByUsername(username);
        return stageRepository.findByUserId(user.getId());
    }

    @GetMapping
    public List<Stage> getAllStagesWithCards() {
        return stageRepository.findAll(); // This will include cards if the relationship is set properly
    }

    @PostMapping
    public Stage createStage(@RequestBody Stage stage, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepo.findByUsername(username); // Get the logged-in user
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        stage.setUser(user); // Set the user field
        return stageRepository.save(stage);
    }

    @DeleteMapping("/{id}")
    public void deleteStage(@PathVariable Long id) {
        stageRepository.deleteById(id);
    }

    @PutMapping("/updateStage")
    public Stage updateStage(@RequestBody Stage exStage, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepo.findByUsername(username); // Get the logged-in user
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        Stage stage = stageRepository.findById(exStage.getId()).orElse(null);
        if (stage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage not found");
        }

        stage.setName(exStage.getName());
        return stageRepository.save(stage);

    }
}
