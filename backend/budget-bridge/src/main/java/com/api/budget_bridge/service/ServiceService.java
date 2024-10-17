package com.api.budget_bridge.service;

import com.api.budget_bridge.exception.EntityNotFoundException;
import com.api.budget_bridge.exception.InvalidCostException;
import com.api.budget_bridge.model.Project;
import com.api.budget_bridge.repository.ServiceRepository;
import com.api.budget_bridge.rest.dto.ProjectDTO;
import com.api.budget_bridge.rest.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ProjectService projectService;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository, ProjectService projectService, ProjectService projectService1) {
        this.serviceRepository = serviceRepository;
        this.projectService = projectService1;
    }

    @Transactional(readOnly = true)
    public ServiceDTO getById(Long id) {
        com.api.budget_bridge.model.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        return service.toDto();
    }

    @Transactional
    public void save(ServiceDTO serviceDTO) {
        Project project = projectService.getById(serviceDTO.getProject().getId()).toModel();
        verifyCost(serviceDTO);
        project.setCost(project.getCost().add(serviceDTO.getCost()));
        projectService.save(project.toDto());
        serviceRepository.save(serviceDTO.toModel());
    }

    @Transactional
    public void update(ServiceDTO serviceDTO) {
        com.api.budget_bridge.model.Service service = serviceRepository.findById(serviceDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        verifyCost(serviceDTO);

        ProjectDTO project = serviceDTO.getProject();

        if (service.getCost().compareTo(serviceDTO.getCost()) != 0) {
            project.setCost(project.getCost().subtract(service.getCost()));
            project.setCost(project.getCost().add(serviceDTO.getCost()));
        }

        projectService.save(project);
        serviceRepository.save(serviceDTO.toModel());
    }

    @Transactional
    public void delete(Long id) {
        com.api.budget_bridge.model.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        ProjectDTO project = projectService.getById(service.getProject().getId());

        project.setCost(project.getCost().subtract(service.getCost()));

        project.getServices().removeIf(s -> s.getId().equals(service.getId()));

        projectService.save(project);
        serviceRepository.delete(service);
    }


    @Transactional(readOnly = true)
    public List<ServiceDTO> getServices() {
        return serviceRepository.findAll().stream()
                .map(com.api.budget_bridge.model.Service::toDto)
                .toList();
    }

    public void verifyCost(ServiceDTO serviceDTO) {
        ProjectDTO project = projectService.getById(serviceDTO.getProject().getId());

        BigDecimal costSum = project.getServices().stream()
                .map(ServiceDTO::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (serviceDTO.getCost().add(costSum).compareTo(project.getBudget()) > 0) {
            throw new InvalidCostException("Sum of cost exceeds total budget");
        }
    }
}
