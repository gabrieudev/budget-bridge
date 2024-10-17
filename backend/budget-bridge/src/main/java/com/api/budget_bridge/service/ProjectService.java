package com.api.budget_bridge.service;

import com.api.budget_bridge.exception.EntityNotFoundException;
import com.api.budget_bridge.exception.InvalidUpdateException;
import com.api.budget_bridge.model.Project;
import com.api.budget_bridge.repository.ProjectRepository;
import com.api.budget_bridge.rest.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public ProjectDTO getById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        return project.toDto();
    }

    @Transactional
    public void save(ProjectDTO projectDTO) {
        projectRepository.save(projectDTO.toModel());
    }

    @Transactional
    public void update(ProjectDTO projectDTO) {
        if (!projectRepository.existsById(projectDTO.getId())) {
            throw new EntityNotFoundException("Project not found");
        }
        if (projectDTO.getBudget().compareTo(projectDTO.getCost()) < 0) {
            throw new InvalidUpdateException("Budget is less than cost");
        }
        projectRepository.save(projectDTO.toModel());
    }

    @Transactional
    public void delete(Long id) {
        Project project = getById(id).toModel();
        projectRepository.delete(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> getProjects() {
        return projectRepository.findAll().stream()
                .map(Project::toDto)
                .toList();
    }
}
