package ru.rayumov.market.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
//
//    @Column(name = "title")
//    private String title;
//
//    @Column(name = "price")
//    private int price;

    @OneToOne
    private Product product;


}
