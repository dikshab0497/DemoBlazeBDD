pipeline {

    // Parameter to pass Cucumber tag from Jenkins UI
    parameters {
        string(
            name: 'TestCase',
            defaultValue: '',
            description: 'Enter Cucumber tag to run (e.g., @LoginWithValidCred)'
        )
        choice(
			name:'Environment',
			choices: ['DEV', 'QA', 'UAT'],
			description: 'Select Environment'
		)
    }

    agent any

    tools {
        maven 'M3'   // Must match Maven installation name in Jenkins
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

                        // Read tag parameter
                        def tagParam = params.TestCase.trim()
                        def cucumberTagOption = tagParam ? "-Dcucumber.filter.tags=${tagParam}" : ""
                        
                        //Read env parameter
                        def envParam = "-Denv=${params.Environment}"

                        // Run tests
                        bat "${mvnHome}\\bin\\mvn.cmd clean test ${cucumberTagOption} ${envParam}"
                    }
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                script {

                    // Find any HTML file inside /reports/
                    def files = findFiles(glob: 'reports/*.html')

                    if (files.size() == 0) {
                        echo "❗ No Extent HTML report found in reports/ folder!"
                    } else {
                        // Get latest report
                        def latestReport = files.sort { it.lastModified }[-1].name
                        echo "Publishing Extent Report: ${latestReport}"

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
            echo " Build & Tests Completed Successfully!"
        }
        failure {
            echo " Build or Tests Failed!"
        }
    }
}
