package com.api.budget_bridge.rest.dto;

import com.api.budget_bridge.model.Service;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceDTO {
    private Long id;

    @JsonBackReference
    @NotNull(message = "Project is mandatory")
    private ProjectDTO project;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Cost is mandatory")
    private BigDecimal cost;

    private String description;

    public Service toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Service.class);
    }
}
