# Audio Editor BackEnd

This project is the backend for the Audio Editor application. It is built with **Java Spring Boot** and handles audio processing tasks using **FFmpeg**. The BackEnd exposes RESTful endpoints that the FrontEnd consumes to perform operations such as uploading, processing, and editing audio files.

## Features

- **RESTful API:** Developed with Spring Boot for robust backend services.
- **Audio Processing:** Utilizes FFmpeg to handle various audio manipulation tasks.
- **Integration:** Seamlessly works with the Audio Editor FrontEnd for a complete editing solution.

## Team

- **Andi Zahiri** - 191560
- **Beqir Fazli** - 191045
- **Venhar Ademi** - 201501

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven (or use the provided Maven wrapper)
- [FFmpeg](https://ffmpeg.org/) installed and available in your system PATH

### Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Fazlibeqir/Audio-Editor-BackEnd.git
   ```
   Open the project through **pom.xml** so you don't have any errors/problems.

2. **After you navigate to the Project Directory:**
   **Build the Project:**
   ```bash
   mvn clean install
   ```
  This command will compile the project and run all tests.
## Running the Application
   Start the Spring Boot application with:
   ```bash
   mvn spring-boot:run
   ```
   The backend service will be available at http://localhost:8080 (or on the configured port in application.properties).

### API Endpoints
- POST /audio/uploadAudio

  This request gets the audio file from frontend to through backend to firebase and return the download url in frontend.
- POST /audio/mergeTracks
   
  Processes the audio files using FFmpeg and we merge all audio files in one audio file save it in firebase and return the download url in frontend.


## Configuration
- FFmpeg: Ensure FFmpeg is installed and its executable is available in the system PATH.
- Application Properties: Modify src/main/resources/application.properties to adjust settings such as server port, file storage paths, etc.

## Technologies Used
- Java Spring Boot
- Firebase Storage
- FFmpeg
- Maven
