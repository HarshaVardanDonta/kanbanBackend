package com.kanban.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kanban.Card.CardRepo;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
@CrossOrigin(origins = "http://localhost:3000") // Ensure CORS for your frontend
public class StageController {

    @Autowired
    private StageRepo stageRepository;

    @Autowired
    private CardRepo cardRepo;

    @GetMapping
    public List<Stage> getAllStagesWithCards() {
        return stageRepository.findAll(); // This will include cards if the relationship is set properly
    }

    @PostMapping
    public Stage createStage(@RequestBody Stage stage) {
        return stageRepository.save(stage);
    }

    @DeleteMapping("/{id}")
    public void deleteStage(@PathVariable Long id) {
        stageRepository.deleteById(id);
    }
}
