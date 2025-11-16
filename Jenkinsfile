pipeline {
    agent any

    tools {
        maven 'M3'
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "Pulling latest code..."
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo "Running Maven tests..."
                bat 'mvn clean test'
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([
                    allowMissing: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'index.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        failure {
            echo "Build Failed!"
        }
        success {
            echo "Build Successful!"
        }
    }
}
