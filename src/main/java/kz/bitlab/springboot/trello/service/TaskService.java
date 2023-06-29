package kz.bitlab.springboot.trello.service;

import kz.bitlab.springboot.trello.model.Folders;
import kz.bitlab.springboot.trello.model.Tasks;
import kz.bitlab.springboot.trello.repository.CommentRepository;
import kz.bitlab.springboot.trello.repository.FolderRepository;
import kz.bitlab.springboot.trello.repository.TaskCategoryRepository;
import kz.bitlab.springboot.trello.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Tasks> findAllTasksByFolder(Folders folder){
        return taskRepository.findAllByFolder(folder);
    }
    public Tasks findTaskById(Long id){
        return taskRepository.findById(id).orElse(null);
    }
    public void addSaveTask(Tasks task){
        taskRepository.save(task);
    }
    public void deleteTaskById(Long taskId){
        taskRepository.deleteById(taskId);
    }

}
