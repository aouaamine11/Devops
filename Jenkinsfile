pipeline {
    agent any // Use any available agent to execute the pipeline

    environment {
        // Load SonarQube token from Jenkins credentials
        SONAR_TOKEN = credentials('sonar-token')
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
        DOCKER_IMAGE = 'lassad2/lassaddev' // Replace with your image name
    }

    stages {

        stage('Main') {
            steps {
                // Simple echo to verify pipeline initiation
                echo "Echo Test of Lassad Branch"
            }
        }

        stage('Checkout') {
            steps {
                // Checkout source code from the configured Git repository
                checkout scm // Uses Jenkins SCM configuration to pull the code
            }
        }

        stage('Compile') {
            steps {
                // Compile the project with Maven (without running tests)
                sh 'mvn compile' // Runs Maven command to compile the Java project
            }
        }

        stage('Test') {
            steps {
                // Run unit tests with Maven
                sh 'mvn test' // Executes all unit tests in the project
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Perform SonarQube analysis using the SonarQube token and host URL
                sh """
                mvn sonar:sonar \
                    -Dsonar.login=${SONAR_TOKEN}
                """
            }
        }

        stage('Build') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Image') {
            steps {
                script {
                    def imageExists = sh(
                        script: "docker manifest inspect lassad2/lassaddev:1.0.0 > /dev/null 2>&1",
                        returnStatus: true
                    ) == 0

                    if (!imageExists) {
                        echo 'Building Image:'
                        sh 'docker build -t lassad2/lassaddev:1.0.0 .'
                    } else {
                        echo 'Image already exists, skipping build.'
                    }
                }
            }
        }

        stage('Dockerhub') {
            steps {
                script {
                    def imageExists = sh(
                        script: "docker manifest inspect lassad2/lassaddev:1.0.0 > /dev/null 2>&1",
                        returnStatus: true
                    ) == 0

                    if (!imageExists) {
                        echo 'Pushing Image to Docker Hub:'
                        sh 'docker login -u lassad2 -p 201Jmt3438'
                        sh 'docker push lassad2/lassaddev:tagname'
                    } else {
                        echo 'Image already exists on Docker Hub, skipping push.'
                    }
                }
            }
        }

        stage('Docker-Compose') {
            steps {
                echo 'Start Backend + DB:'
                sh 'docker compose up -d'
            }
        }


        }
    }
