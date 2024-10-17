package com.api.budget_bridge.model;

import com.api.budget_bridge.rest.dto.ServiceDTO;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "Service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;

    private String description;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    public ServiceDTO toDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ServiceDTO.class);
    }
}
