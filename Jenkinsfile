pipeline {
    agent any // Utiliser n'importe quel agent disponible pour exécuter le pipeline

    environment {
        // Définir la variable d'environnement pour SonarQube si nécessaire
        SONARQUBE_SERVER = 'SonarQubeServer' // Nom de l'installation SonarQube configurée dans Jenkins
    }

    stages {
        
        stage('Main') {
            steps {
                // Étape de test ou d'affichage de message
                echo "Echo Test of Lassad Branch" // Message simple pour vérifier que le pipeline démarre bien
            }
        }

        stage('Compile') {
            steps {
                // Extraire le code source depuis le dépôt Git configuré
                checkout scm // Cette commande utilise la configuration SCM par défaut de Jenkins pour récupérer le code source

                // Compiler le projet Maven (sans exécuter de tests)
                sh 'mvn compile' // Exécute la commande Maven pour compiler le projet Java
            }
        }

        stage('Test') {
            steps {
                // Exécuter les tests avec Maven
                sh 'mvn test' // Cette commande exécute tous les tests unitaires de votre projet
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Scanner le code avec SonarQube
                withSonarQubeEnv('SonarQubeServer') { // Utilise l'environnement SonarQube configuré
                    sh 'mvn sonar:sonar' // Exécute l'analyse SonarQube
                }
            }
        }
        
    }

    post {
        // Définir les actions à effectuer après l'exécution du pipeline
        always {
            // Publier les résultats des tests, quel que soit l'état du pipeline
            junit '**/target/surefire-reports/*.xml' // Fichier de rapport JUnit généré par Maven
        }

        success {
            echo 'Pipeline terminé avec succès !'
        }

        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}
