package pl.michal.todoapp.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;
import pl.michal.todoapp.TaskConfigurationProperties;
import pl.michal.todoapp.model.TaskGroup;
import pl.michal.todoapp.model.TaskGroupRepository;
import pl.michal.todoapp.model.TaskRepository;
import pl.michal.todoapp.model.projection.GroupReadModel;
import pl.michal.todoapp.model.projection.GroupWriteModel;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TaskGroupService {

    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first!");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Task group with given id not found!"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
