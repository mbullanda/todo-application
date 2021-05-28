package pl.michal.todoapp.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.michal.todoapp.TaskConfigurationProperties;
import pl.michal.todoapp.model.ProjectRepository;
import pl.michal.todoapp.model.TaskGroupRepository;
import pl.michal.todoapp.model.TaskRepository;

@Configuration
public class LogicConfiguration {
    @Bean
    ProjectService projectService(final ProjectRepository repository, final TaskGroupRepository taskGroupRepository, final TaskConfigurationProperties config){
        return new ProjectService(repository, taskGroupRepository, config);
    }
    @Bean
    TaskGroupService taskGroupService(final TaskGroupRepository taskGroupRepository, final TaskRepository taskRepository){
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }
}
