package org.com.github.popular.repos.controller;

import org.com.github.popular.repos.controller.entity.GithubRepositoryResponse;
import org.com.github.popular.repos.exception.GitHubApiException;
import org.com.github.popular.repos.service.GithubRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("v1/github")
public class GithubRepositoryController {

    private static final Logger log = LoggerFactory.getLogger(GithubRepositoryService.class);

    @Autowired
    private GithubRepositoryService githubRepositoryService;

    /**
     * This GET API is used to return list of github repos with n count
     *
     * @param programmingLanguage
     * @param createdAfter
     * @param topN
     * @return
     */
    @GetMapping("/repositories")
    public ResponseEntity<GithubRepositoryResponse> getGithubPopularRepositories(
            @RequestParam(value = "programming_language", required = false) String programmingLanguage,
            @RequestParam(value = "created_after", required = false) String createdAfter,
            @RequestParam(value = "top_n", defaultValue = "10", required = false) @Min(1) @Max(100) int topN) {
        try {
            log.debug("Github api has been started");
            GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
            githubRepositoryResponse.setRepos(
                    githubRepositoryService.getPopularRepositories(programmingLanguage, createdAfter, topN)
            );
            log.debug("Github api is done");
            return new ResponseEntity<>(githubRepositoryResponse, HttpStatus.OK);
        } catch (GitHubApiException e) {
            log.error("Error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Invalid request: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request", e);
        }
    }
}