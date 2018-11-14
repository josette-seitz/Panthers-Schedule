package com.practice.josette.spring_demo.controllers;

import com.practice.josette.spring_demo.model.Event;
import com.practice.josette.spring_demo.model.EventAdd;
import com.practice.josette.spring_demo.model.Opponent;
import com.practice.josette.spring_demo.repository.EventRepository;
import com.practice.josette.spring_demo.repository.OpponentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
class EventController {

    private final Logger log = LoggerFactory.getLogger(EventController.class);
    private OpponentRepository opponentRepository;
    private EventRepository eventRepository;

    public EventController(OpponentRepository opponentRepository, EventRepository eventRepository) {
        this.opponentRepository = opponentRepository;
        this.eventRepository = eventRepository;
    }

    //Get List of all Events
    @GetMapping("/events")
    List<Event> events() {
        return eventRepository.findAll();
    }

    //Get Opponent by ID
    @GetMapping("/opponent/{id}")
    ResponseEntity<?> getOpponent(@PathVariable Long id) {
        Optional<Opponent> opponent = opponentRepository.findById(id);
        return opponent.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Create new Event
    @PostMapping("/event")
    ResponseEntity<Event> createEvent(@Valid @RequestBody EventAdd eventAdd) throws URISyntaxException {
        log.info("POST HERE");
        Opponent oEntity = Opponent.builder()
                .name(eventAdd.getName())
                .build();
        Opponent opponent = opponentRepository.save(oEntity);

        Event eEntity = Event.builder()
                .stadium(eventAdd.getStadium())
                .city(eventAdd.getCity())
                .eventDate(formatDate(eventAdd.getEventDate()))
                .opponent(opponent)
                .state(eventAdd.getState())
                .build();
        Event addResult = eventRepository.save(eEntity);
        log.info("Request to create event: {} " + addResult);
        //        return ResponseEntity.created(new URI("/api/event/" + addResult.getId()))
        //                .body(addResult);
        return ResponseEntity.ok().body(addResult);
    }

    //Update Event by ID
    @PutMapping("/event/{id}")
    ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody Event event) {
        log.info("PUT HERE");
        event.setId(id);
        log.info("Request to update event: {}", event);
        Event result = eventRepository.save(event);
        return ResponseEntity.ok().body(result);
    }

    //Delete Event by ID
    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteOpponent(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        eventRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private LocalDateTime formatDate(String eventDate) {
        return (eventDate.equals("")) ? null : LocalDateTime.parse(eventDate);
    }
}