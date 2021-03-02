package com.egen.titanic.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item implements Serializable  {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "name")
        private String name;

        @Column(name = "price")
        private Double price;

        @Column(name = "tax")
        private Double tax;

}
