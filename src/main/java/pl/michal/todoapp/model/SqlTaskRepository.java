package pl.michal.todoapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlTaskRepository extends  TaskRepository, JpaRepository<Task,Integer> {
    //dzięki jpaRepository mamy dostęp do wielu użytecznych metod jak delete, save, find, count

}
