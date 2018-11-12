package com.practice.josette.spring_demo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
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
}