package csnojam.app.problem.service;

import csnojam.app.problem.repository.ProblemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProblemService {
    private ProblemRepository problemRepository;

//    public void create(ProblemDto.CreationReq dto){
//        problemRepository.create(dto);
//    }
//
//    public List<Problem> getProblems(){
//        return problemRepository.findAll();
//    }
//
//    public Problem getProblem(Long id){
//        return problemRepository.findById(id);
//    }
//
//    public void deleteProblem(Long id){
//       problemRepository.remove(id);
//    }
//
//    public void updateProblem(Long id, ProblemDto.UpdateReq dto){
//        problemRepository.update(id, dto);
//    }
}