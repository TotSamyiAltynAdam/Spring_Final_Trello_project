package kz.bitlab.springboot.trello.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="task_categories")
@Data
public class TaskCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;

    @Column(name="name")
    String name;
}
