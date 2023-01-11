package com.komponente.user_service.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    private Long company;

    private Date startDate;
}
