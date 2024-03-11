package org.com.github.popular.repos.service.entity;

import lombok.Data;
import org.com.github.popular.repos.model.GithubRepository;
import java.util.List;

@Data
public class GeneralGithubAPIServiceEntity {
    private int totalCount; // API total-count result
    private boolean incompleteResults;
    private List<GithubRepositoryServiceEntity> items; // GithubRepository is another class you've defined to capture repository details
}

