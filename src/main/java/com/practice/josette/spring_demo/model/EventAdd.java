package com.practice.josette.spring_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventAdd {
    //Opponent name
    private String name;
    private String eventDate;
    private String stadium;
    private String city;
    private String state;
}
