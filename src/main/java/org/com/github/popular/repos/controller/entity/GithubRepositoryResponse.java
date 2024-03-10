package org.com.github.popular.repos.controller.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.com.github.popular.repos.service.entity.ServiceEntity;
import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GithubRepositoryResponse {
    List<ServiceEntity> repos;
}
