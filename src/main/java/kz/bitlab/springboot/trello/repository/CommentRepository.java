package kz.bitlab.springboot.trello.repository;

import kz.bitlab.springboot.trello.model.Comments;
import kz.bitlab.springboot.trello.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments,Long> {
    List<Comments> findAllByTask(Tasks task);
}
