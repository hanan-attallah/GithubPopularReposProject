package org.com.github.popular.repos.controller;

import org.com.github.popular.repos.service.GithubRepositoryService;
import org.com.github.popular.repos.service.entity.GithubRepositoryServiceEntity;
import org.com.github.popular.repos.service.entity.ServiceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GithubRepositoryController.class)
public class GithubRepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubRepositoryService githubRepositoryService;

    @BeforeEach
    public void setUp() {
        // Set up mock behavior here
        ServiceEntity mockEntity = new GithubRepositoryServiceEntity();
        // Populate mockEntity with test data as necessary

        given(githubRepositoryService.getPopularGithubRepositories("Java", "2020-01-01", 10))
                .willReturn(Collections.singletonList(mockEntity));
    }

    /*@Test
    public void getGithubPopularRepositoriesSuccess() throws Exception {
        mockMvc.perform(get("/v1/github/repositories")
                        .param("programming_language", "Java")
                        .param("created_after", "2023-01-01")
                        .param("top_n", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"repos\":[{}]}")); // Update this expected JSON based on your mockEntity's structure
    }*/
}
