package com.api.budget_bridge.model;

import com.api.budget_bridge.rest.dto.ProjectDTO;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal budget;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(nullable = false)
    private BigDecimal cost;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Service> services = new ArrayList<>();

    public ProjectDTO toDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProjectDTO.class);
    }

}
