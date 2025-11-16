pipeline {
    agent any

    stages {

        stage('Checkout Code') {
            steps {
                echo "Pulling latest code from Git..."
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo "Running Maven tests..."
                sh 'mvn clean test'
            }
        }

        stage('Publish Extent Report') {
            steps {
                echo "Publishing Extent Report..."
                publishHTML(target: [
                    reportDir: 'reports',
                    reportFiles: 'ExtentReport.html',  // change this to your actual report file
                    reportName: 'Extent Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])
            }
        }
    }

    post {
        success {
            echo "Build Successful!"
        }
        failure {
            echo "Build Failed!"
        }
    }
}
