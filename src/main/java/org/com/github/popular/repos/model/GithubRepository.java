package org.com.github.popular.repos.model;

import lombok.Data;

@Data
public class GithubRepository {
    private String name;
    private String language;
    private int stars;
    private String createdAt;
}
