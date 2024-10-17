package com.api.budget_bridge.model;

import com.api.budget_bridge.rest.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public CategoryDTO toDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, CategoryDTO.class);
    }
}
