package org.com.github.popular.repos.controller;

import org.com.github.popular.repos.controller.dto.GithubRepositoryResponse;
import org.com.github.popular.repos.service.GithubRepositoryService;
import org.com.github.popular.repos.service.entity.GithubRepositoryServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GithubRepositoryController.class)
public class GithubRepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubRepositoryService githubRepositoryService;

    @Test
    public void testAPIShouldReturnRepositories() throws Exception {
        // Setup
        String programmingLanguage = "Java";
        String createdAfter = "2024-01-01";
        int topN = 50;

        GithubRepositoryServiceEntity dummyRepo = new GithubRepositoryServiceEntity();
        dummyRepo.setName("Test Repository1");
        dummyRepo.setLanguage(programmingLanguage);
        dummyRepo.setStars(100);
        dummyRepo.setCreatedAt(createdAfter);

        GithubRepositoryResponse mockResponse = new GithubRepositoryResponse();
        mockResponse.addRepository(dummyRepo);

        // Assuming getPopularGithubRepositories returns GithubRepositoryResponse
        when(githubRepositoryService.getPopularGithubRepositories(anyString(), anyString(), anyInt()))
                .thenReturn(mockResponse.getRepositories());

        // Perform and verify the mock MVC call
        mockMvc.perform(get("/v1/github/repositories")
                        .param("programming_language", programmingLanguage)
                        .param("created_after", createdAfter)
                        .param("top_n", String.valueOf(topN))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.repositories[0].name").value("Test Repository1"));
    }


    @Test
    public void testAPIWithDefaultParametersShouldReturnRepositories() throws Exception {
        // Given a default setup for the mock response
        GithubRepositoryServiceEntity dummyRepo = new GithubRepositoryServiceEntity();
        dummyRepo.setName("Default Repository");
        dummyRepo.setStars(50);

        GithubRepositoryResponse mockResponse = new GithubRepositoryResponse();
        mockResponse.addRepository(dummyRepo);

        when(githubRepositoryService.getPopularGithubRepositories(null, null, 10))
                .thenReturn(mockResponse.getRepositories());

        // When performing a GET request without specifying parameters
        mockMvc.perform(get("/v1/github/repositories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.repositories[0].name").value("Default Repository"));
    }

    @Test
    public void testAPIWithInvalidParametersShouldReturnBadRequest() throws Exception {
        // When performing a GET request with invalid parameters
        mockMvc.perform(get("/v1/github/repositories")
                        .param("top_n", "-10") // Invalid top_n parameter
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAPIWhenServiceThrowsExceptionShouldReturnServerError() throws Exception {
        // Given the service throws an exception
        when(githubRepositoryService.getPopularGithubRepositories(anyString(), anyString(), anyInt()))
                .thenThrow(new RuntimeException("Unexpected error"));

        // When performing a GET request
        mockMvc.perform(get("/v1/github/repositories")
                        .param("programming_language", "Java")
                        .param("created_after", "2024-01-01")
                        .param("top_n", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
