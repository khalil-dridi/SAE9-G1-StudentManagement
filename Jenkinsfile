pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo "Récupération du code depuis Git..."
                git branch: 'DridiKhalil_4SAE9_G1', url: 'https://github.com/khalil-dridi/SAE9-G1-StudentManagement.git'
            }
        }
    }
}
