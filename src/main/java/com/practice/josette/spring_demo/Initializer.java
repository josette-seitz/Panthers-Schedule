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
                .stadium("Bank of America Stadium")
                .city("Charlotte")
                .state("NC")
                .build();
        opponentRepository.save(opponent1);

        Opponent opponent2 = Opponent.builder()
                .name("Falcons")
                .stadium("Mercedes-Benz Stadium")
                .city("Atlanta")
                .state("GA")
                .build();
        opponentRepository.save(opponent2);

        Opponent opponent3 = Opponent.builder()
                .name("Bengals")
                .stadium("Bank of America Stadium")
                .city("Charlotte")
                .state("NC")
                .build();
        opponentRepository.save(opponent3);

        //Find Opponent
        Opponent o1 = opponentRepository.findByName("Cowboys");
        Opponent o2 = opponentRepository.findByName("Falcons");
        Opponent o3 = opponentRepository.findByName("Bengals");

        //Build Event details tied to Opponent
        Event event1 = Event.builder()
                .date(LocalDateTime.of(2018, Month.SEPTEMBER, 9, 4, 30, 0))
                .score("8 - 16")
                .winner("Panthers")
                .opponentId(opponent1.getId())
                .build();
        o1.setEvent(event1);
        opponentRepository.save(o1);

        Event event2 = Event.builder()
                .date(LocalDateTime.of(2018, Month.SEPTEMBER, 16, 1, 0, 0))
                .score("24 - 31")
                .winner("Falcons")
                .opponentId(opponent2.getId())
                .build();
        o2.setEvent(event2);
        opponentRepository.save(o2);

        Event event3 = Event.builder()
                .date(LocalDateTime.of(2018, Month.SEPTEMBER, 23, 1, 0, 0))
                .score("21 - 31")
                .winner("Panthers")
                .opponentId(opponent3.getId())
                .build();
        o3.setEvent(event3);
        opponentRepository.save(o3);

        //Check for console purposes
        opponentRepository.findAll().forEach(System.out::println);
    }
}
