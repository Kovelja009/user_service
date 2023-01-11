package com.komponente.user_service.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(indexes = {@Index(columnList = "name", unique = true)})
public class Rank_discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private int minDays;
    private int maxDays;
    private int discount;

}
