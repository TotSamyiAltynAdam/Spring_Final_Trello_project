package kz.bitlab.springboot.trello.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="comments")
@Data
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="comment")
    private String comment;

    @ManyToOne
    private Tasks task;
}
