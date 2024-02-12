# Fitiz - Challenge Service

This project aims to enhance user engagement and competitiveness by implementing location-based challenges and real-time leaderboards, encouraging users to participate and track their progress within these challenges actively.

![fitiz-app - Page 1 (2)](https://github.com/fitiz/challenge-service/assets/23321849/b2c2e151-6d3e-400c-9a3f-2b2e82cf8d20)

#### 
  - Publishes step-count topic to Kafka (by simulator just for now, there will be a separate endpoint for the producer)
  - Uses Flyway + jOOQ
  - Create challenge:
     - ```POST /api/challenges```
  - Participate in challenge:
     - ```POST /api/challenges/{challengeId}/participants/{userId}```
  - Claim challenge reward:
     - ```POST /api/challenges/{challengeId}/participants/{userId}/rewards```

_Please see the [ChallengeManager](https://github.com/fitiz/challenge-service/blob/main/src/main/java/com/fitiz/challenge/service/ChallengeManager.java) , [LeaderboardRedisProvider](https://github.com/fitiz/challenge-service/blob/main/src/main/java/com/fitiz/challenge/provider/LeaderboardRedisProvider.java) detailed implementation_

*This service was later separated into challenge-service and leaderboard-websocket-service. Some codes are still here even though they were used in the leaderboard-websocket-service and other consumer services.

#### UI

- Live Mode, users can initiate a WebSocket (STOMP) connection and subscribe to the challenge-specific channel

<img width="458" alt="Screenshot 2024-02-12 at 5 13 33 PM" src="https://github.com/fitiz/challenge-service/assets/23321849/e82216e4-e8cf-453d-8163-8ee5bc064bc7">

## Getting Started

### Prerequisites

Make sure you have the following software installed:

- [Java](https://www.oracle.com/java/) 17 or higher
- [Gradle](https://gradle.org/) 7.x or higher

### Installation

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/fitiz/challenge-service.git

2. Clone this repository to your local machine:

   ```shell   
   cd challenge-service

3. Clone this repository to your local machine:

   ```shell
   ./gradlew bootRun

### Usage

Once the project is successfully running, you can visit the following URLs in your browser:

- Application: http://localhost:8080
- Actuator Endpoints: http://localhost:8080/actuator

### Contributing

1. Create a new feature branch:

   ```shell
    git checkout -b new-feature

2. Commit your changes:
   ```shell
    git commit -am 'Added a new feature'

3. Push to the branch:
   ```shell
   git push origin new-feature

4. Submit a pull request.

### Formatter

Please use google-java-format to formats Java source code to with Google Java Style:

https://github.com/google/google-java-format/tree/master
