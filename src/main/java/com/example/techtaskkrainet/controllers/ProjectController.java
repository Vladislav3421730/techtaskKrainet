package com.example.techtaskkrainet.controllers;

import com.example.techtaskkrainet.dto.ResponseDto;
import com.example.techtaskkrainet.models.Project;
import com.example.techtaskkrainet.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addNewProject(@RequestBody Project project){
        Project savingProject=projectService.save(project);
        return ResponseEntity.ok(new ResponseDto(String.format("Проект %s сохранён",savingProject.getName())));
    }

    @GetMapping("/get")
    public List<Project> findAllProjects(){
        return projectService.findAll();
    }

    @GetMapping("/get/{id}")
    public Project findProject(@PathVariable Long id){
        return projectService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> DeleteProject(@PathVariable Long id){
        projectService.delete(id);
        return ResponseEntity.ok(new ResponseDto(String.format("Проект с id id %d был удалён",id)));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> UpdateProject(@RequestBody Project project){
        projectService.update(project);
        return ResponseEntity.ok(new ResponseDto(String.format("Проект с id %d был обновлён",project.getId())));
    }



}
