pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo "Récupération du code depuis Git..."
                git branch: 'DridiKhalil_4SAE9_G1', url: 'https://github.com/khalil-dridi/SAE9-G1-StudentManagement.git'
            }
        }

        stage('Build Maven') {
            steps {
                echo "Compilation du projet avec Maven..."
                sh 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                echo "Exécution des tests unitaires..."
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }
}
