pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "dridi-khalil_student-management:latest"
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
                sh "docker build -t ${env.DOCKER_IMAGE} ."
            }
        }

        stage('Deploy Docker Compose') {
            steps {
                sh "docker-compose down || true"
                sh "docker-compose up -d --build"
            }
        }

    }

    post {
        always {
            echo "Pipeline terminé."
        }
    }
}
