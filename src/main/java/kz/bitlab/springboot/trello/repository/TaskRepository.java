package kz.bitlab.springboot.trello.repository;

import kz.bitlab.springboot.trello.model.Folders;
import kz.bitlab.springboot.trello.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks,Long> {
    List<Tasks> findAllByFolder(Folders folder);
}
