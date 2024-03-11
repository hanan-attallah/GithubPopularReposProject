package org.com.github.popular.repos.controller;

import org.com.github.popular.repos.controller.dto.GithubRepositoryResponse;
import org.com.github.popular.repos.service.GithubRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Controller for handling GitHub repository related requests.
 * Provides endpoints for fetching popular repositories based on various criteria.
 */
@Validated
@RestController
@RequestMapping("/v1/github")
public class GithubRepositoryController {

    private static final Logger log = LoggerFactory.getLogger(GithubRepositoryController.class);

    @Autowired
    private GithubRepositoryService githubRepositoryService;

    /**
     * Return a list of popular GitHub repositories based on the specified filter criteria
     *
     * @param programmingLanguage Programming language filter for the repositories.
     * @param createdAfter        Filter for repositories created after this date.
     * @param topN                Limits the number of repositories (response size) returned. The value must be between 1 and 100.
     * @return ResponseEntity containing the list of repositories.
     */
    @GetMapping("/repositories")
    public ResponseEntity<GithubRepositoryResponse> getGithubPopularRepositories(
            @RequestParam(value = "programming_language", required = false) String programmingLanguage,
            @RequestParam(value = "created_after", required = false) String createdAfter,
            @RequestParam(value = "top_n", defaultValue = "10", required = true) @Min(1) @Max(100) int topN) {

        log.debug("Received request to fetch popular GitHub repositories for language: {}, created after: {}, topN: {}", programmingLanguage, createdAfter, topN);
        GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
        githubRepositoryResponse.setRepositories(
                githubRepositoryService.getPopularGithubRepositories(programmingLanguage, createdAfter, topN)
        );
        log.debug("Responding with {} repositories.", githubRepositoryResponse.getRepositories().size());

        return new ResponseEntity<>(githubRepositoryResponse, HttpStatus.OK);
    }
}
