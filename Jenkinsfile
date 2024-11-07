pipeline {
    agent any // Utiliser n'importe quel agent disponible pour exécuter le pipeline

    environment {
        SONAR_TOKEN = credentials('jenkins-sonar')
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
            // Inject SonarQube token and configure SonarQube environment
            sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
        }
       }
   }

        
   }

    
}
