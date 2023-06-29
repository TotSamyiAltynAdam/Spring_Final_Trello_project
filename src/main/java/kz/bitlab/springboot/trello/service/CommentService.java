package kz.bitlab.springboot.trello.service;

import kz.bitlab.springboot.trello.model.Comments;
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
public class CommentService {
    private final CommentRepository commentRepository;
    public List<Comments> findCommentsByTask(Tasks task){
        return commentRepository.findAllByTask(task);
    }
    public void addSaveComment(Comments comment){
        commentRepository.save(comment);
    }
    public void deleteAllCommentsByTaskId(Tasks task){
        List<Comments> commentsList = commentRepository.findAllByTask(task);
        for(Comments comments: commentsList) {
            commentRepository.deleteById(comments.getId());
        }
    }
}
