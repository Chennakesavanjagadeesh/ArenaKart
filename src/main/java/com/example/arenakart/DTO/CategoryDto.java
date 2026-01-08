package com.example.arenakart.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;

    private String description;
    private Long parentId;
    private String parentName;
	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryDto(Long id, @NotBlank(message = "Category name is required") String name, String description,
			Long parentId, String parentName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.parentId = parentId;
		this.parentName = parentName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	@Override
	public String toString() {
		return "CategoryDto [id=" + id + ", name=" + name + ", description=" + description + ", parentId=" + parentId
				+ ", parentName=" + parentName + "]";
	}
    
}