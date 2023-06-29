package kz.bitlab.springboot.trello.service;

import kz.bitlab.springboot.trello.model.Folders;
import kz.bitlab.springboot.trello.model.TaskCategories;
import kz.bitlab.springboot.trello.model.Tasks;
import kz.bitlab.springboot.trello.repository.CommentRepository;
import kz.bitlab.springboot.trello.repository.FolderRepository;
import kz.bitlab.springboot.trello.repository.TaskCategoryRepository;
import kz.bitlab.springboot.trello.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final TaskRepository taskRepository;
    private final FolderRepository folderRepository;
    public List<Folders> findFolders() {
        return folderRepository.findAll();
    }
    public Folders findFolderById(Long folderId){
        return folderRepository.findById(folderId).orElse(null);
    }
    public void addSaveFolder(Folders folder) {
        folderRepository.save(folder);
    }

    public void deleteFolderById(Long id){
        Folders folder = folderRepository.findById(id).orElse(null);
        if(folder!=null){
            List<Tasks> tasks = taskRepository.findAllByFolder(folder);
            taskRepository.deleteAll(tasks);
            folderRepository.deleteById(id);
        }
    }
}
