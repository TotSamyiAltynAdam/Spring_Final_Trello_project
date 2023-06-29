package kz.bitlab.springboot.trello.service;

import kz.bitlab.springboot.trello.model.Folders;
import kz.bitlab.springboot.trello.model.TaskCategories;
import kz.bitlab.springboot.trello.repository.CommentRepository;
import kz.bitlab.springboot.trello.repository.FolderRepository;
import kz.bitlab.springboot.trello.repository.TaskCategoryRepository;
import kz.bitlab.springboot.trello.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCategoryService {
    private final TaskCategoryRepository taskCategoriesRepository;
    public List<TaskCategories> findAllCategories() {
        return taskCategoriesRepository.findAll();
    }

    public TaskCategories findCategoryById(Long categoryId) {
        return taskCategoriesRepository.findById(categoryId).orElseThrow();
    }

    public void addSaveTaskCategory(TaskCategories taskCategory) {
        taskCategoriesRepository.save(taskCategory);
    }

    public void deleteTaskCategoryById(Long categoryId) {
        taskCategoriesRepository.deleteById(categoryId);
    }
}
