package ru.vsu.cs.zmaev.carservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "car_engine")
public class CarEngine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_id")
    private Long carId;

    @Column(name = "engine_id")
    private Long engineId;
}

//@Data
//@Entity
//@Table(name = "car_engine")
//public class CarEngine {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "car_id", referencedColumnName = "id")
//    private Car car;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "engine_id", referencedColumnName = "id")
//    private Engine engine;
//}
