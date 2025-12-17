pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "dridi-khalil_student-management:latest"
        DOCKER_BUILDKIT = 1  // Active BuildKit
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'DridiKhalil_4SAE9_G1',
                    url: 'https://github.com/khalil-dridi/SAE9-G1-StudentManagement.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'DOCKER_BUILDKIT=0 docker build -t dridi-khalil_student-management:latest .'
            }
        }

        stage('Deploy Docker Compose') {
            steps {
                // Toujours arrêter d'abord, puis lancer
                sh 'docker-compose down || true'
                sh 'docker-compose up -d --build'
                // Si besoin : sh 'sudo docker-compose ...'
            }
        }

    }

    post {
        always {
            echo "Pipeline terminé."
        }
    }
}
