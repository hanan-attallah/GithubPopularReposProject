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

2. For Gradle checkstyle
   - ./gradlew checkstyleMain
   - ./gradlew checkstyleTest
   
3. For Gradle build and run
   - gradle build
   - gradle bootRun

4. Access the application at:
    http://localhost:8080/v1/github/repositories

### API Documentation 
- To access your generated API documentation and UI:
  OpenAPI JSON: http://localhost:8080/v3/api-docs).
