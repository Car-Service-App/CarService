package ru.vsu.cs.zmaev.carservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.vsu.cs.zmaev.carservice.domain.enums.CarType;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_type")
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "car_image_link")
    private String carImageLink;

    @Column(name = "car_generation")
    private Integer generation;

    @Column(name = "is_restyling")
    private Boolean isRestyling;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_model_id", referencedColumnName = "id")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "transmission_id", referencedColumnName = "id")
    private Transmission transmission;

    @ManyToMany
    @JoinTable(name = "CarEngine",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "engine_id"))
    private List<Engine> engines;
}
