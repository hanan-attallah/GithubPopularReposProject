package org.com.github.popular.repos.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.com.github.popular.repos.service.entity.ServiceEntity;
import java.util.ArrayList;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GithubRepositoryResponse {
  
    List<ServiceEntity> repositories;

    // Method to add a repository to the list
    public void addRepository(ServiceEntity repository) {
        if (this.repositories == null) {
            this.repositories = new ArrayList<>();
        }
        this.repositories.add(repository);
    }

}

