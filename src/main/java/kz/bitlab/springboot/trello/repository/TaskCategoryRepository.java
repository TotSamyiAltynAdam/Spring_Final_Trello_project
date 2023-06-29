package kz.bitlab.springboot.trello.repository;

import kz.bitlab.springboot.trello.model.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategories,Long> {
}
