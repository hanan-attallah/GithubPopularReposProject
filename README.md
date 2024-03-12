# GithubPopularReposProject

## Project Overview
This project aims to facilitate the discovery of popular GitHub repositories. It leverages GitHub's public search API to fetch repositories based on various criteria, including star count, creation date, and programming language.

## Features
- **Repository Ranking**: List the most starred repositories.
- **Flexible Viewing Options**: Ability to view the top 10, 50, or 100 repositories.
- **Date Filtering**: Fetch repositories created on or after a specified date.
- **Language Filtering**: Option to filter repositories by programming language.

## Getting Started

### Prerequisites
- Java 17
- Maven or Gradle
- An IDE such as IntelliJ IDEA or Eclipse

### Installation
1. Clone the repository:
   ```shell
   git clone git@github.com:hanan-attallah/GithubPopularReposProject.git
   cd GithubPopularReposProject

2. For code linting: checkstyle
   - ./gradlew checkstyleMain
   - ./gradlew checkstyleTest
   
3. For Gradle build and run
   - gradle build
   - gradle bootRun

4. Access the application at:
    http://localhost:8080/v1/github/repositories

### API Documentation 
- To access your generated API documentation and UI:
  OpenAPI JSON using: http://localhost:8080/v3/api-docs.

### Git pre-commit
- Add GitHub action to check that application is working through health check and unit tests are passing
- Git hooks: create pre commit hook, in pre-commit: run unit test cases.
  #### Setting Up Git Pre-commit Hook
         To set up the pre-commit hook in your local repository, follow these steps:
         1. Make the script executable:
         ```bash
         chmod +x git-hooks/pre-commit
         ln -s -f ../../git-hooks/pre-commit .git/hooks/pre-commit

### Testing
- service unit testing 
- controller unit testing
- Performance Test: https://k6.io/docs/examples/single-request/
  1. Ensure Your Application Is Running:
  2. run ```./gradlew runSingleRequest
      > Task :runSingleRequest
      Total: 0.268428s



