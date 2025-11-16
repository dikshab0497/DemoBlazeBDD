pipeline {
    agent any

    tools {
        maven 'M3'      // This must match the Maven name you configured in Jenkins
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
                script {
                    def mvnHome = tool 'M3'       // Fetch Maven tool
                    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                        // Clean old reports before running tests
                        bat "rmdir /s /q reports || exit 0"
                        bat "mkdir reports"
                        
                        // Run tests with Maven
                        bat "${mvnHome}\\bin\\mvn.cmd clean test"
                    }
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                script {
                    // Get the latest report dynamically
                    def reportDir = new File("${WORKSPACE}/reports")
                    def latestReport = reportDir.listFiles().findAll { it.name.endsWith(".html") }
                                       .max { it.lastModified() }.name

                    echo "Publishing report: ${latestReport}"

                    publishHTML(target: [
                        reportDir: 'reports',
                        reportFiles: latestReport,
                        reportName: 'ExtentReport',
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                    ])
                }
            }
        }
    }

    post {
        success {
            echo "Build Success!"
        }
        failure {
            echo "Build Failed!"
        }
    }
}
