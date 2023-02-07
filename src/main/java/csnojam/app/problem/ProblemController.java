package csnojam.app.problem;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/problems")
@RestController
@AllArgsConstructor
public class ProblemController {
    private ProblemService problemService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody ProblemDto.CreationReq dto){
        problemService.create(dto);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Problem> getProblems(){
        return new ArrayList<>(problemService.getProblems());
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Problem getProblem(@PathVariable Long id){
        return problemService.getProblem(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProblem(@PathVariable Long id){
        problemService.deleteProblem(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProblem(@PathVariable Long id, @RequestBody ProblemDto.UpdateReq dto){
        problemService.updateProblem(id, dto);
    }
}
