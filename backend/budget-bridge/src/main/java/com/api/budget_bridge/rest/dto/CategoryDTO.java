package com.api.budget_bridge.rest.dto;

import com.api.budget_bridge.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String name;

    public Category toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Category.class);
    }
}
