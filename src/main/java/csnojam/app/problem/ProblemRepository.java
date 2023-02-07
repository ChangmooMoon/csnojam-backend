package csnojam.app.problem;

import java.util.List;

public interface ProblemRepository {
    public void create(ProblemDto.CreationReq dto);

    public Problem findById(Long id);

    public List<Problem> findAll();

    public void remove(Long id);

    public void update(Long id, ProblemDto.UpdateReq dto);
}

