package com.practice.josette.spring_demo.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Opponent {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    private String stadium;
    private String city;
    private String state;

    @OneToOne(cascade = CascadeType.ALL)
    private Event event;
}