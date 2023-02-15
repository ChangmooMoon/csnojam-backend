package csnojam.app.problem.repository;

import csnojam.app.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProblemRepository extends JpaRepository<Problem, UUID> {

}

