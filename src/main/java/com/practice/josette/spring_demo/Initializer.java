package com.practice.josette.spring_demo;

import com.practice.josette.spring_demo.model.Event;
import com.practice.josette.spring_demo.model.Opponent;
import com.practice.josette.spring_demo.repository.EventRepository;
import com.practice.josette.spring_demo.repository.OpponentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;

@Component
class Initializer implements CommandLineRunner {

    private final OpponentRepository opponentRepository;
    private final EventRepository eventRepository;

    public Initializer(OpponentRepository opponentRepository, EventRepository eventRepository) {
        this.opponentRepository = opponentRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... strings) {

        //Build Opponent
        Opponent opponent1 = Opponent.builder()
                .name("Cowboys")
                .build();
        opponentRepository.save(opponent1);

        Opponent opponent2 = Opponent.builder()
                .name("Falcons")
                .build();
        opponentRepository.save(opponent2);

        Opponent opponent3 = Opponent.builder()
                .name("Bengals")
                .build();
        opponentRepository.save(opponent3);

        //Build Event details tied to Opponent
        Event event1 = Event.builder()
                .eventDate(LocalDateTime.of(2018, Month.SEPTEMBER, 9, 16, 30, 0))
                .stadium("Bank of America Stadium")
                .city("Charlotte")
                .state("NC")
                .score("8 - 16")
                .winner("Panthers")
                .opponent(opponent1)
                .build();
        eventRepository.save(event1);

        Event event2 = Event.builder()
                .eventDate(LocalDateTime.of(2018, Month.SEPTEMBER, 16, 13, 0, 0))
                .stadium("Mercedes-Benz Stadium")
                .city("Atlanta")
                .state("GA")
                .score("24 - 31")
                .winner("Falcons")
                .opponent(opponent2)
                .build();
        eventRepository.save(event2);

        Event event3 = Event.builder()
                .eventDate(LocalDateTime.of(2018, Month.SEPTEMBER, 23, 13, 0, 0))
                .stadium("Bank of America Stadium")
                .city("Charlotte")
                .state("NC")
                .score("21 - 31")
                .winner("Panthers")
                .opponent(opponent3)
                .build();
        eventRepository.save(event3);

        //Check for console purposes
        //        opponentRepository.findAll().forEach(System.out::println);
        eventRepository.findAll().forEach(System.out::println);
        opponentRepository.findAll().forEach(System.out::println);
    }
}
