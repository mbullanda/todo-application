package pl.michal.todoapp.logic;

import pl.michal.todoapp.TaskConfigurationProperties;
import pl.michal.todoapp.model.*;
import pl.michal.todoapp.model.projection.GroupReadModel;
import pl.michal.todoapp.model.projection.GroupTaskWriteModel;
import pl.michal.todoapp.model.projection.GroupWriteModel;
import pl.michal.todoapp.model.projection.ProjectWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService service;

    public ProjectService(final ProjectRepository repository, final TaskGroupRepository taskGroupRepository, final TaskConfigurationProperties config, TaskGroupService service) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
        this.service = service;
    }

    public List<Project> readAll(){
        return repository.findAll();
    }

    public Project save(final ProjectWriteModel toSave){
        return repository.save(toSave.toProject());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if (!config.getTemplate().isAllowMultipleTask() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }

        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(project.getSteps().stream()
                            .map(step -> {
                                var task = new GroupTaskWriteModel();
                                task.setDescription(step.getDescription());
                                task.setDeadline(deadline.plusDays(step.getDaysToDeadline()));
                                return task;
                                }

                            ).collect(Collectors.toList())
                    );
                    return service.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found!"));
    }
}
