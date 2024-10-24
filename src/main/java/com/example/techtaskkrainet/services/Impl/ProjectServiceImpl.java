package com.example.techtaskkrainet.services.Impl;

import com.example.techtaskkrainet.exceptions.ProjectNotFoundException;
import com.example.techtaskkrainet.models.Project;
import com.example.techtaskkrainet.repositories.ProjectRepository;
import com.example.techtaskkrainet.services.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления проектами.
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    // Репозиторий для работы с сущностью Project в базе данных
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Проект с id %d не найден", id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Проверяем, существует ли проект с данным id
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException(String.format("Проект с id %d не найден. Удаление невозможно", id));
        }
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Project update(Project project) {
        // Проверяем, существует ли проект с данным id
        if (!projectRepository.existsById(project.getId())) {
            throw new ProjectNotFoundException(String.format("Проект с id %d не найден. Обновление невозможно", project.getId()));
        }
        return projectRepository.save(project);
    }
}
