package org.com.github.popular.repos.service;

import org.com.github.popular.repos.exception.GitHubApiException;
import org.com.github.popular.repos.model.GithubRepository;
import org.com.github.popular.repos.service.entity.GeneralGithubAPIServiceEntity;
import org.com.github.popular.repos.service.entity.GithubRepositoryServiceEntity;
import org.com.github.popular.repos.service.entity.ServiceEntity;
import org.com.github.popular.repos.service.entity.mapper.EntityToServiceEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GithubRepositoryServiceTest {

    @Mock
    private EntityToServiceEntityMapper entityToServiceEntityMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubRepositoryService githubRepositoryService;

    /**
     *  This test case checks whether the method works correctly when the API call is successful.
     *  It mocks the required dependencies and ensures that the expected service entity is returned.
     */
    /*@Test
    void getPopularRepositoriesSuccess() {
        String programmingLanguage = "Java";
        String createdAfter = "2020-01-01";
        int topN = 10;
        GeneralGithubAPIServiceEntity mockResponse = new GeneralGithubAPIServiceEntity();
        // Populate mockResponse with necessary data
        mockResponse.setTotalCount(20); // Example value
        List<GithubRepository> items = IntStream.range(0, topN)
                .mapToObj(i -> new GithubRepository()) // Assuming GithubRepository can be instantiated like this
                .collect(Collectors.toList());
        mockResponse.setItems(items); // Make sure the items list is not empty

        GithubRepositoryServiceEntity mockServiceEntity = mock(GithubRepositoryServiceEntity.class);

        when(restTemplate.getForEntity(anyString(), eq(GeneralGithubAPIServiceEntity.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        when(entityToServiceEntityMapper.mapAsList(eq(GithubRepositoryServiceEntity.class), anyList()))
                .thenReturn(Collections.singletonList(mockServiceEntity));

        List<ServiceEntity> result = githubRepositoryService.getPopularGithubRepositories(programmingLanguage, createdAfter, topN);

        assertEquals(1, result.size());
    }*/


    /**
     *  This test case verifies the behavior when the API call fails with a RestClientException.
     *  It ensures that the method throws a GitHubApiException as expected.
     */
    @Test
    void getPopularRepositoriesWhenApiCallFails() {
        // Setup
        String programmingLanguage = "Java";
        String createdAfter = "2020-01-01";
        int topN = 10;

        when(restTemplate.getForEntity(anyString(), eq(GeneralGithubAPIServiceEntity.class)))
                .thenThrow(new RestClientException("Service Unavailable"));

        // Act & Assert
        assertThrows(GitHubApiException.class, () -> githubRepositoryService.getPopularGithubRepositories(programmingLanguage, createdAfter, topN));
    }
}
