package kz.bitlab.springboot.trello.controller;

import kz.bitlab.springboot.trello.model.Comments;
import kz.bitlab.springboot.trello.model.Folders;
import kz.bitlab.springboot.trello.model.TaskCategories;
import kz.bitlab.springboot.trello.model.Tasks;
import kz.bitlab.springboot.trello.service.CommentService;
import kz.bitlab.springboot.trello.service.FolderService;
import kz.bitlab.springboot.trello.service.TaskCategoryService;
import kz.bitlab.springboot.trello.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FolderService folderService;
    private final TaskService taskService;
    private final TaskCategoryService taskCategoryService;
    private final CommentService commentService;


    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("folders", folderService.findFolders());
        return "index";
    }

    @PostMapping(value="/add-folder")
    public String addFolder(@RequestParam(name="id", required = false) Long folderId,
                            @RequestParam(name="name") String folderName) {
        Folders folder;
        if (folderId!=null) {
            folder = folderService.findFolderById(folderId);
        } else {
            folder = new Folders();
        }
        folder.setName(folderName);
        folderService.addSaveFolder(folder);
        return "redirect:/";
    }

    @PostMapping(value = "/delete-folder")
    public String deleteFolder(@RequestParam(name="id") Long id){
        folderService.deleteFolderById(id);
        return "redirect:/";
    }
    @GetMapping(value="/task/{id}")
    public String detailedFolder(@PathVariable(name="id") Long folderId, Model model){
        Folders folder = folderService.findFolderById(folderId);

        model.addAttribute("folder",folder);
        model.addAttribute("tasks",taskService.findAllTasksByFolder(folder));
        model.addAttribute("taskCategoriesList",taskCategoryService.findAllCategories());
        return "task";
    }

    @PostMapping(value = "/assign-categories")
    public String assignCategory(@RequestParam(name="folder") Long folderId,
                                 @RequestParam(name="category") Long categoryId){
        Folders folder = folderService.findFolderById(folderId);
        TaskCategories taskCategory = taskCategoryService.findCategoryById(categoryId);
        if(folder.getCategoriesList()!=null && folder.getCategoriesList().size()>0){
            folder.getCategoriesList().add(taskCategory);
        }else{
            List<TaskCategories> categoriesList = new ArrayList<>();
            categoriesList.add(taskCategory);
            folder.setCategoriesList(categoriesList);
        }
        folderService.addSaveFolder(folder);
        return "redirect:/task/"+folderId;
    }
    @PostMapping(value="/re-assign-category")
    public String reAssignCategory(@RequestParam(name="folder") Long folderId,
            @RequestParam(name="category") Long categoryId){
        Folders folder = folderService.findFolderById(folderId);
        List<TaskCategories> categoriesList = folder.getCategoriesList();
        if (categoriesList != null) {
            Iterator<TaskCategories> iterator = categoriesList.iterator();
            while (iterator.hasNext()) {
                TaskCategories category = iterator.next();
                if (category.getId().equals(categoryId)) {
                    iterator.remove();
                    break;
                }
            }
            folder.setCategoriesList(categoriesList);
            folderService.addSaveFolder(folder);
        }
        return "redirect:/task/" + folderId;
    }

    @PostMapping(value = "/add-tasks")
    public String addTask(
            @RequestParam(name="folder") Long folderId,
            @RequestParam(name="title") String title,
            @RequestParam(name="description") String description
    ){
        Tasks task = new Tasks();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(0);
        task.setFolder(folderService.findFolderById(folderId));
        taskService.addSaveTask(task);
        return "redirect:/task/" + folderId;
    }
    @GetMapping(value="/detailedTask/{id}")
    public String detailedTask(@PathVariable(name="id") Long id,
                               Model model){
        Tasks task = taskService.findTaskById(id);
        model.addAttribute("task",task);
        model.addAttribute("commentaries", commentService.findCommentsByTask(task));
        return "detailedTask";
    }

    @PostMapping(value="/save-task")
    public String saveTask(Tasks task){
        taskService.addSaveTask(task);
        return "redirect:/task/"+task.getFolder().getId();
    }
    @PostMapping(value="/delete-task")
    public String deleteTask(@RequestParam(name="id") Long taskId){
        Long folderId = taskService.findTaskById(taskId).getFolder().getId();
        commentService.deleteAllCommentsByTaskId(taskService.findTaskById(taskId));
        taskService.deleteTaskById(taskId);
        return "redirect:/task/" + folderId;
    }

    @GetMapping(value="/categories")
    public String categoriesPage(Model model){
        model.addAttribute("categories", taskCategoryService.findAllCategories());
        return "categoriesPage";
    }
    @GetMapping(value="/detailedCategory/{id}")
    public String detailedCategory(@PathVariable(name="id") Long id, Model model){
        model.addAttribute("category",taskCategoryService.findCategoryById(id));
        return "detailedCategory";
    }
    @PostMapping(value="/save-category")
    public String saveCategory(TaskCategories taskCategory){
        taskCategoryService.addSaveTaskCategory(taskCategory);
        return "redirect:/categories";
    }
    @PostMapping(value="/delete-category")
    public String deleteCategory(@RequestParam(name="id") Long categoryId){
        TaskCategories deletedCategory = taskCategoryService.findCategoryById(categoryId);

        List<Folders> folders = folderService.findFolders();
        for(Folders folder: folders){
            List<TaskCategories> categoriesList = folder.getCategoriesList();
            List<TaskCategories> categoriesToRemove = new ArrayList<>();
            for(TaskCategories taskCategory: categoriesList){
                if(taskCategory.equals(deletedCategory)){
                    categoriesToRemove.add(taskCategory);
                }
            }
            categoriesList.removeAll(categoriesToRemove);
            folder.setCategoriesList(categoriesList);
            folderService.addSaveFolder(folder);
        }
        taskCategoryService.deleteTaskCategoryById(categoryId);
        return "redirect:/categories";
    }

    @PostMapping(value = "/add-comment")
    public String addTask(
            @RequestParam(name="taskId") Long taskId,
            @RequestParam(name="commentary") String commentary
    ){
        Comments comment = new Comments();
        comment.setComment(commentary);
        comment.setTask(taskService.findTaskById(taskId));
        commentService.addSaveComment(comment);
        return "redirect:/detailedTask/" + taskId;
    }
}
