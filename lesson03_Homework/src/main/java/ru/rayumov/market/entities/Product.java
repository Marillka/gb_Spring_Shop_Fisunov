package ru.rayumov.market.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at")
    @CreationTimestamp
    // CreationTimestamp - фишка Hibernate (Jpa так не умеет). При первом создании объекта Hibernate будет подставлять временную отметку
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    /*
    Как избежать закольцовывания и SOF
    можно просто выключить сериализация для Category (JsonIgnore).
    А что если вы хотите на фронте эту категорию показать?
    Как тогда рзаорвать кольцо?
    Для этого собственно придумали DTO.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;


}
