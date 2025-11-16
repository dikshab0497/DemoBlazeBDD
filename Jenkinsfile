pipeline {
    agent any

    tools {
        maven 'M3' // This must match the Maven name you configured in Jenkins
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

                        // Run tests with Maven
                        bat "${mvnHome}\\bin\\mvn.cmd clean test"
                    }
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                script {
                    // Use findFiles (sandbox-safe) to get all HTML reports
                    def files = findFiles(glob: 'reports/*.html')

                    if (files.size() == 0) {
                        echo "No report files found!"
                    } else {
                        // Get the latest report based on lastModified
                        def latestReport = files.sort { it.lastModified }[-1].name
                        echo "Publishing latest report: ${latestReport}"

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
