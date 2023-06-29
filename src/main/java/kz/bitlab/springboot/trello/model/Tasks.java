package kz.bitlab.springboot.trello.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="tasks")
@Data
public class Tasks {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name="id") //column name
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description; // TEXT

    @Column(name="status")
    private int status; // 0 - todo, 1 - intest, 2 - done, 3 - failed

    @ManyToOne
    private Folders folder;
}
