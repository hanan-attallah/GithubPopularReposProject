package org.com.github.popular.repos.service;

import org.com.github.popular.repos.exception.ErrorCode;
import org.com.github.popular.repos.exception.GitHubApiException;
import org.com.github.popular.repos.service.entity.GitHubResponse;
import org.com.github.popular.repos.service.entity.GithubRepositoryServiceEntity;
import org.com.github.popular.repos.service.entity.ServiceEntity;
import org.com.github.popular.repos.service.entity.mapper.EntityToServiceEntityMapper;
import org.com.github.popular.repos.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubRepositoryService {

    private static final Logger log = LoggerFactory.getLogger(GithubRepositoryService.class);

    @Autowired
    private EntityToServiceEntityMapper entityToServiceEntityMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${github.api.url}")
    private String apiUrl;

    /**
     * This method is responsible for requesting github url to return top N repos details based on search criteria
     * @param programmingLanguage
     * @param createdAfter
     * @param topN
     * @return
     */
    public List<ServiceEntity> getPopularRepositories(String programmingLanguage, String createdAfter, int topN) {
        String query = buildQuery(programmingLanguage, createdAfter);
        String url = String.format(Constants.BASE_GITHUB_API_URL_PLACEHOLDER, apiUrl, query);

        try {
            ResponseEntity<GitHubResponse> responseEntity = restTemplate.getForEntity(url, GitHubResponse.class);
            if (responseEntity.getBody() != null && responseEntity.getBody().getItems() != null) {
                return (List<ServiceEntity>) (List<?>) entityToServiceEntityMapper.mapAsList(GithubRepositoryServiceEntity.class,
                        responseEntity.getBody().getItems().stream()
                        .limit(topN)
                        .collect(Collectors.toList()));
            }
        } catch (RestClientException e) {
            log.error("Failed to fetch repositories from GitHub", e);
            throw new GitHubApiException(ErrorCode.INTERNAL_SERVER_ERROR,
                    "Failed to fetch repositories from GitHub with error: " + e.getMessage(), e);
        }
        return List.of();
    }

    /**
     * This private function is used to build the github api search query.
     *
     * @param programmingLanguage
     * @param createdAfter
     * @return
     */
    private String buildQuery(String programmingLanguage, String createdAfter) {
        String query = "created:>";
        query += (createdAfter != null) ? createdAfter : Constants.DEFAULT_GITHUB_REPO_CREATED_AT;
        if (programmingLanguage != null && !programmingLanguage.isEmpty()) {
            query += "+language:" + programmingLanguage;
        }
        return query;
    }
}
