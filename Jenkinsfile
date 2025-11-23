pipeline {
    // Parameter to pass Cucumber tag from Jenkins UI
    parameters {
        string(name: 'TestCase', defaultValue: '', description: 'Enter Cucumber tag to run (e.g., @LoginWithValidCred)')
    }

    agent any

    tools {
        maven 'M3'  // Must match Maven installation name in Jenkins
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
                    def mvnHome = tool 'M3'
                    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                        // Clean old reports before running tests
                        bat "rmdir /s /q reports || exit 0"
                        bat "mkdir reports"

                        // Run tests with Maven, pass Cucumber tag from Jenkins UI
                        def tagParam = params.TestCase.trim()
                        def cucumberTagOption = tagParam ? "-Dcucumber.filter.tags=${tagParam}" : ""
                        bat "${mvnHome}\\bin\\mvn.cmd clean test ${cucumberTagOption}"
                    }
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                script {
                    // Update this path according to where Extent report generates
                    def reportDir = 'reports'  // folder containing index.html + assets
                    def reportFile = 'index.html'

                    echo "Publishing Extent Report from folder: ${reportDir}"

                    publishHTML(target: [
                        reportDir: reportDir,
                        reportFiles: reportFile,
                        reportName: 'ExtentReport',
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: false
                    ])
                }
            }
        }
    }

    post {
        success {
            echo "Build & Tests Completed Successfully!"
        }
        failure {
            echo "Build or Tests Failed!"
        }
    }
}
