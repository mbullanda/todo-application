package pl.michal.todoapp.adapter;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.michal.todoapp.model.TaskGroup;
import pl.michal.todoapp.model.TaskGroupRepository;

import java.util.List;

@Repository
public interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {
    @Override
    @Query("from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();
}
