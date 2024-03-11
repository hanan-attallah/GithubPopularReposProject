package org.com.github.popular.repos.service;

import org.com.github.popular.repos.config.CacheConfig;
import org.com.github.popular.repos.exception.GitHubApiException;
import org.com.github.popular.repos.service.entity.GeneralGithubAPIServiceEntity;
import org.com.github.popular.repos.service.entity.GithubRepositoryServiceEntity;
import org.com.github.popular.repos.service.entity.ServiceEntity;
import org.com.github.popular.repos.service.entity.mapper.EntityToServiceEntityMapper;
import org.com.github.popular.repos.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.util.List;
import java.util.stream.Collectors;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
public class GithubRepositoryService {

    private static final Logger log = LoggerFactory.getLogger(GithubRepositoryService.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EntityToServiceEntityMapper entityToServiceEntityMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${github.api.url}")
    private String apiUrl;

    /**
     * This method is responsible for requesting the GitHub URL to return the top N repository details based on search criteria.
     *
     * @param programmingLanguage String representing the programming language
     * @param createdAfter        String representing the date after which the repo was created
     * @param topN                int representing the number of repositories to be returned
     * @return List of service entities
     */
    @Cacheable(value = CacheConfig.POPULAR_REPOSITORIES_CACHE)
    public List<ServiceEntity> getPopularGithubRepositories(String programmingLanguage, String createdAfter, int topN) {
        String query = buildQuery(programmingLanguage, createdAfter);
        String url = String.format(Constants.BASE_GITHUB_API_URL_PLACEHOLDER, apiUrl, query);

        try {
            ResponseEntity<GeneralGithubAPIServiceEntity> responseEntity = restTemplate.getForEntity(url, GeneralGithubAPIServiceEntity.class);
            if (responseEntity.getBody() != null && responseEntity.getBody().getItems() != null) {
                return (List<ServiceEntity>) (List<?>) entityToServiceEntityMapper.mapAsList(GithubRepositoryServiceEntity.class,
                        responseEntity.getBody().getItems().stream()
                                .limit(topN)
                                .collect(Collectors.toList()));
            }
        } catch (RestClientException e) {
            log.error("Failed to fetch repositories from GitHub api: {}", kv("error", e.getMessage()));
            throw new GitHubApiException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Failed to fetch repositories from GitHub", e);
        } catch (Exception e) {
            log.error("Error occurred: {}", kv("error", e.getMessage()));
            throw new GitHubApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred " + e.getMessage(), e);
        }
        return List.of();
    }

    /**
     * This private function is used to build the GitHub API search query.
     *
     * @param programmingLanguage the repo programming language to filter on
     * @param createdAfter        the date after which the repo was created to filter on
     * @return the filter query
     */
    private String buildQuery(String programmingLanguage, String createdAfter) {
        String query = "created:>";
        query += (createdAfter != null) ? createdAfter : Constants.DEFAULT_GITHUB_REPO_CREATED_AFTER;
        if (programmingLanguage != null && !programmingLanguage.isEmpty()) {
            query += "+language:" + programmingLanguage;
        }
        return query;
    }
}

