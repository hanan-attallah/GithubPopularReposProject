package org.com.github.popular.repos.service;

import org.com.github.popular.repos.service.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.com.github.popular.repos.exception.GitHubApiException;
import org.com.github.popular.repos.service.entity.GeneralGithubAPIServiceEntity;
import org.com.github.popular.repos.service.entity.GithubRepositoryServiceEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for GithubRepositoryService to fetch popular repositories based on given criteria.
 */
@ExtendWith(MockitoExtension.class)
public class GithubRepositoryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubRepositoryService githubRepositoryService;

    /**
     * Test to ensure that the service correctly processes and returns a list of gitHub repositories
     * when the GitHub API call is successful. It checks the size and properties of the returned list.
     */
    @Test
    void getPopularRepositoriesWhenApiCallSuccess() {
        String programmingLanguage = "Java";
        String createdAfter = "2024-01-01";
        int topN = 10;

        List<GithubRepositoryServiceEntity> mockItems = IntStream.range(0, topN)
                .mapToObj(i -> {
                    GithubRepositoryServiceEntity entity = new GithubRepositoryServiceEntity();
                    entity.setName("Repository " + i);
                    entity.setLanguage(programmingLanguage);
                    entity.setStars(i * 100);
                    entity.setCreatedAt("2024-01-" + String.format("%02d", i + 1));
                    return entity;
                })
                .collect(Collectors.toList());

        GeneralGithubAPIServiceEntity mockResponse = new GeneralGithubAPIServiceEntity();
        mockResponse.setTotalCount(100);
        mockResponse.setItems(mockItems);

        when(restTemplate.getForEntity(anyString(), eq(GeneralGithubAPIServiceEntity.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        List<ServiceEntity> result = githubRepositoryService.getPopularGithubRepositories(programmingLanguage, createdAfter, topN);

        assertEquals(topN, result.size());
        for (int i = 0; i < topN; i++) {
            GithubRepositoryServiceEntity repo = (GithubRepositoryServiceEntity) result.get(i);
            assertEquals("Repository " + i, repo.getName());
            assertEquals(programmingLanguage, repo.getLanguage());
            assertEquals(i * 100, repo.getStars());
            assertEquals("2024-01-" + String.format("%02d", i + 1), repo.getCreatedAt());
        }
    }

    /**
     * Test to verify the service throws a GitHubApiException when the API call fails due to a RestClientException.
     */
    @Test
    void getPopularRepositoriesWhenApiCallFails() {
        String programmingLanguage = "Java";
        String createdAfter = "2020-01-01";
        int topN = 50;

        when(restTemplate.getForEntity(anyString(), eq(GeneralGithubAPIServiceEntity.class)))
                .thenThrow(new RestClientException("Service Unavailable"));

        assertThrows(GitHubApiException.class, () ->
                githubRepositoryService.getPopularGithubRepositories(programmingLanguage, createdAfter, topN));
    }

    /**
     * Test to verify the service handles an empty API response correctly by returning an empty list of repositories.
     */
    @Test
    void getPopularRepositoriesWithEmptyApiResponse() {
        String programmingLanguage = "Java";
        String createdAfter = "2020-01-01";
        int topN = 5;

        GeneralGithubAPIServiceEntity mockResponse = new GeneralGithubAPIServiceEntity();
        mockResponse.setTotalCount(0); // Simulating an API response with no repositories
        mockResponse.setItems(Collections.emptyList());

        when(restTemplate.getForEntity(anyString(), eq(GeneralGithubAPIServiceEntity.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        List<ServiceEntity> result = githubRepositoryService.getPopularGithubRepositories(programmingLanguage, createdAfter, topN);

        assertTrue(result.isEmpty(), "Expected an empty list of repositories for an empty API response");
    }
}
