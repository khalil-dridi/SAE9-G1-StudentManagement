pipeline {
    agent any

    environment {
        IMAGE_NAME = "student-management"
        DOCKER_IMAGE = "${IMAGE_NAME}:latest"

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
                sh 'mvn -B clean package -DskipTests'
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
        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('sonar-token')
            }
            steps {
                sh "mvn sonar:sonar -Dsonar.projectKey=student-management -Dsonar.host.url=http://localhost:9000 -Dsonar.login=${SONAR_TOKEN}"
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Push DockerHub') {
            steps {
                // utilise les credentials Jenkins (id: dockerhub-creds)
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds',
                                                  usernameVariable: 'DOCKER_USER',
                                                  passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker tag ${DOCKER_IMAGE} $DOCKER_USER/${IMAGE_NAME}:latest
                        docker push $DOCKER_USER/${IMAGE_NAME}:latest
                        docker logout
                    '''
                }
            }
        }


        stage('Deploy Docker Compose') {
            steps {
                sh '''
                echo "Pulling images from registry (if docker-compose.yml references full image name)..."
                docker-compose pull || true

                echo "Stopping existing containers..."
                docker rm -f spring-student || true
                docker rm -f mysql-student || true

                echo "Starting containers via docker-compose..."
                docker-compose up -d
                '''
            }
        }
    }

    post {
        always {
            echo "Pipeline terminé."
        }
    }
}
