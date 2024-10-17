package com.api.budget_bridge.rest.dto;

import com.api.budget_bridge.model.Project;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Budget is mandatory")
    private BigDecimal budget;

    @NotNull(message = "Category is mandatory")
    private CategoryDTO category;

    @NotNull(message = "Cost is mandatory")
    private BigDecimal cost;

    @NotNull(message = "Services is mandatory")
    @JsonManagedReference
    private List<ServiceDTO> services;

    public Project toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Project.class);
    }
}
