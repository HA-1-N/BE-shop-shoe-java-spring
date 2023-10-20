package com.example.shopshoejavaspring.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "color")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "code")
    @NotNull
    private String code;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "product_color",
            joinColumns = {@JoinColumn(name = "color_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")}
    )
    private Set<Product> products = new HashSet<>();
}
