# GithubPopularReposProject
The idea of this project is to implement a backend application for discovering popular repositories on GitHub.

# Service Specification
The service should be able to provide:
1- A list of the most popular repositories, sorted by number of stars.
2- An option to be able to view the top 10, 50, 100 repositories should be available.
3- Given a date, the most popular repositories created from this date onwards should be returned. A filter for the programming language would be a great addition to have.

# Implementation Details
GitHub provides a public search endpoint which you can use for fetching the most popular Repositories:
https://api.github.com/search/repositories?q=created:2019-01-10&sort=stars&order=desc
