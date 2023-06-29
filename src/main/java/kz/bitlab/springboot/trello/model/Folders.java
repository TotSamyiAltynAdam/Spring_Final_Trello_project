package kz.bitlab.springboot.trello.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="folders")
@Data
public class Folders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    private List<TaskCategories> categoriesList;
}
