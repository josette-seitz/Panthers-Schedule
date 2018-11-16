package com.practice.josette.spring_demo.controllers;

import com.practice.josette.spring_demo.model.Event;
import com.practice.josette.spring_demo.model.EventAdd;
import com.practice.josette.spring_demo.model.EventEdit;
import com.practice.josette.spring_demo.model.Opponent;
import com.practice.josette.spring_demo.repository.EventRepository;
import com.practice.josette.spring_demo.repository.OpponentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
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

    //Get Event by ID
    @GetMapping("/event/{id}")
    ResponseEntity<?> getEvent(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Create new Event
    @PostMapping("/event")
    ResponseEntity<Event> createEvent(@Valid @RequestBody EventAdd eventAdd) throws URISyntaxException {
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
        Event addRecord = eventRepository.save(eEntity);

        log.info("Request to create event: {} " + addRecord);
        return ResponseEntity.created(new URI("/api/event/" + addRecord.getId()))
                .body(addRecord);
        //        return ResponseEntity.ok().body(addRecord);
    }

    //Update Event by ID
    @PutMapping("/event/{id}")
    ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody EventEdit eventEdit) {
        Opponent oEntity = Opponent.builder()
                .name(eventEdit.getName())
                .build();
        Opponent opponent = opponentRepository.save(oEntity);

        Event eEntity = Event.builder()
                .stadium(eventEdit.getStadium())
                .city(eventEdit.getCity())
                .eventDate(formatDate(eventEdit.getEventDate()))
                .opponent(opponent)
                .state(eventEdit.getState())
                .winner(eventEdit.getWinner())
                .score(eventEdit.getScore())
                .build();
        eEntity.setId(id);
        Event editRecord = eventRepository.save(eEntity);

        log.info("Request to update event: {}", editRecord);
        return ResponseEntity.ok().body(editRecord);
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