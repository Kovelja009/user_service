package com.komponente.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(indexes = {@Index(columnList = "company", unique = true)})
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private User user;

    @Column(unique = true)
    private String company;

    private Date startDate;
}
