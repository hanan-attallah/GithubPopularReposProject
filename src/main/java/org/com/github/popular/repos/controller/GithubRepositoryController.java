package org.com.github.popular.repos.controller;

import org.com.github.popular.repos.controller.dto.GithubRepositoryResponse;
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

@RestController
@RequestMapping("v1/github")
public class GithubRepositoryController {

    private static final Logger log = LoggerFactory.getLogger(GithubRepositoryService.class);

    @Autowired
    private GithubRepositoryService githubRepositoryService;

    /**
     * This GET API is used to return a list of GitHub repositories matching the specified criteria,
     * limited to a certain count.
     *
     * @param programmingLanguage the programming language of the repositories
     * @param createdAfter the date after which the repositories were created
     * @param topN the number of top results to return
     * @return a JSON response containing the list of repositories
     */
    @GetMapping("/repositories")
    public ResponseEntity<GithubRepositoryResponse> getGithubPopularRepositories(
            @RequestParam(value = "programming_language", required = false) String programmingLanguage,
            @RequestParam(value = "created_after", required = false) String createdAfter,
            @RequestParam(value = "top_n", defaultValue = "10", required = false) @Min(1) @Max(100) int topN) {

        log.debug("Github api has been started");
        GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
        githubRepositoryResponse.setRepos(
                githubRepositoryService.getPopularGithubRepositories(programmingLanguage, createdAfter, topN)
        );
        log.debug("Github api response with size: " + githubRepositoryResponse.getRepos().size());
        return new ResponseEntity<>(githubRepositoryResponse, HttpStatus.OK);
    }
}
