package com.kanban.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
@CrossOrigin(origins = "http://localhost:3000") // Ensure CORS for your frontend
public class StageController {

    @Autowired
    private StageRepo stageRepository;

    @GetMapping
    public List<Stage> getStages() {
        // Fetch all stages
        List<Stage> stages = stageRepository.findAll();

        return stages; // No circular reference now, as @JsonIgnore is used
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
