package org.com.github.popular.repos.service.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GithubRepositoryServiceEntity implements ServiceEntity {
    private String name;
    private String language;
    private int stars;
    private String createdAt;
}
