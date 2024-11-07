pipeline {
    agent any // Use any available agent to execute the pipeline

    environment {
        // Load SonarQube token from Jenkins credentials
        SONAR_TOKEN = credentials('sonar-token')
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


        stage('Deploy to Nexus') {
                    steps {
                        sh 'mvn clean deploy -DskipTests'
                    }
                }
    }


}
