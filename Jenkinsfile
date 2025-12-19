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
            SONAR_TOKEN = credentials('sonarqube-token')
          }
          steps {
            // utilisation de quotes simples pour éviter l'interpolation Groovy des secrets
            sh 'mvn sonar:sonar -Dsonar.projectKey=student-management -Dsonar.host.url=http://localhost:9000 -Dsonar.token=$SONAR_TOKEN -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml'
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

        stage('Deploy to Kubernetes') {
            steps {
                // utilise dockerhub-creds pour récupérer DOCKER_USER (le compte qui a poussé l'image)
                // NOTE: ce stage suppose que `kubectl` est installé et configuré sur l'agent Jenkins
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds',
                                                  usernameVariable: 'DOCKER_USER',
                                                  passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                      echo "Applying k8s manifests (k8s/)..."
                      kubectl apply -f k8s/ --recursive || true

                      echo "Attempt to update deployment image (if deployment exists)..."
                      kubectl -n devops set image deployment/student-app student-app=${DOCKER_USER}/${IMAGE_NAME}:latest --record || true

                      echo "Waiting for rollout (student-app)..."
                      kubectl -n devops rollout status deployment/student-app --timeout=120s || true

                      echo "Pods status:"
                      kubectl -n devops get pods -o wide

                      echo "Service list:"
                      kubectl -n devops get svc
                    '''
                }
            }
        }

    }

    post {
        always {
            echo "Pipeline terminé."
        }
    }
}
