package pl.michal.todoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.michal.todoapp.logic.TaskGroupService;
import pl.michal.todoapp.logic.TaskService;
import pl.michal.todoapp.model.Task;
import pl.michal.todoapp.model.TaskGroup;
import pl.michal.todoapp.model.TaskRepository;
import pl.michal.todoapp.model.projection.GroupReadModel;
import pl.michal.todoapp.model.projection.GroupWriteModel;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/groups")
class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskRepository repository;
    private final TaskGroupService taskGroupService;

    TaskGroupController(final TaskRepository repository, final TaskGroupService taskGroupService) {
        this.repository = repository;
        this.taskGroupService = taskGroupService;
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups(){
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate){
        return ResponseEntity.created(URI.create("/")).body(taskGroupService.createGroup(toCreate));
    }

    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id){
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id){
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

}
