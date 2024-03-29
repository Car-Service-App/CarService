package ru.vsu.cs.zmaev.carservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "engine_type")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "engine_name")
    private String name;

    @Column(name = "engine_capacity")
    private String capacity;

    @ManyToMany(mappedBy = "engines")
    private List<Car> cars;

}
