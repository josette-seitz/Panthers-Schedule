package com.practice.josette.spring_demo.controllers;

import com.practice.josette.spring_demo.model.Opponent;
import com.practice.josette.spring_demo.repository.OpponentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
class OpponentController {

    private final Logger log = LoggerFactory.getLogger(OpponentController.class);
    private OpponentRepository opponentRepository;

    public OpponentController(OpponentRepository opponentRepository) {
        this.opponentRepository = opponentRepository;
    }

    //Get List of all Opponents
    @GetMapping("/opponents")
    List<Opponent> opponents() {
        return opponentRepository.findAll();
    }

    //Get Opponent by ID
    @GetMapping("/opponent/{id}")
    ResponseEntity<?> getOpponent(@PathVariable Long id) {
        Optional<Opponent> opponent = opponentRepository.findById(id);
        return opponent.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Create new Opponent
    @PostMapping("/opponent")
    ResponseEntity<Opponent> createOpponent(@Valid @RequestBody Opponent opponent) throws URISyntaxException {
        log.info("Request to create opponent: {}", opponent);
        Opponent result = opponentRepository.save(opponent);
        return ResponseEntity.created(new URI("/api/opponent/" + result.getId()))
                .body(result);
    }

    //Update Opponent by ID
    @PutMapping("/opponent/{id}")
    ResponseEntity<Opponent> updateOpponent(@PathVariable Long id, @Valid @RequestBody Opponent opponent) {
        opponent.setId(id);
        log.info("Request to update opponent: {}", opponent);
        Opponent result = opponentRepository.save(opponent);
        return ResponseEntity.ok().body(result);
    }

    //Delete Opponent by ID
    @DeleteMapping("/opponent/{id}")
    public ResponseEntity<?> deleteOpponent(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        opponentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}