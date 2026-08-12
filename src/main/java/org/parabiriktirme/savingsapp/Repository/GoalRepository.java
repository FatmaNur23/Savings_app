package org.parabiriktirme.savingsapp.Repository;

import org.parabiriktirme.savingsapp.Entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

}
