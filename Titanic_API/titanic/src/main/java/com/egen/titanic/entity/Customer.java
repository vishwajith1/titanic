package com.egen.titanic.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @Column(name = "id ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(name = "name")
    private String name;

    @Column(name = "extra_info")
    private String extraInfo;

    @Column(name = "age")
    private Integer age;


}



