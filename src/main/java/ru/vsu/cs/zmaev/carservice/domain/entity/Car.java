package ru.vsu.cs.zmaev.carservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.vsu.cs.zmaev.carservice.domain.enums.CarType;

import java.util.List;

@Data
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "car_image_link")
    private String carImageLink;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_model_id", referencedColumnName = "id")
    private CarModel carModel;

    @Column(name = "car_generation")
    private Integer generation;

    @Column(name = "is_restyling")
    private Boolean isRestyling;

    @Column(name = "car_type")
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @OneToMany(mappedBy = "car", cascade = CascadeType.REMOVE)
    private List<CarConfig> carConfigs;

    @ManyToMany
    @JoinTable(name = "CarConfig",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "transmission_id"))
    private List<Transmission> transmissions;

    @ManyToMany
    @JoinTable(name = "CarConfig",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "engine_id"))
    private List<Engine> engines;
}
