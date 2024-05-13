package ru.vsu.cs.zmaev.carservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "car_config")
public class CarConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Column(name = "engine_id")
    private Long engineId;

    @Column(name = "transmission_id")
    private Long transmissionId;
}
