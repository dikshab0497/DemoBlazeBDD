pipeline {

    // Parameters to pass from Jenkins UI
    parameters {
        string(
            name: 'TestCase',
            defaultValue: '',
            description: 'Enter Cucumber tag(s) to run, comma separated (e.g., @LoginWithValidCred,@LoginWithInvalidCred)'
        )
        choice(
            name: 'Environment',
            choices: ['DEV', 'QA', 'UAT'],
            description: 'Select Environment'
        )
        string(
            name: 'EmailTo', 
            defaultValue: 'team@example.com', 
            description: 'Email to notify'
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

        stage('Run Tests in Parallel') {
            parallel {
                script {
                    def mvnHome = tool 'M3'

                    // Split tags passed from Jenkins UI (comma separated)
                    def tags = params.TestCase.split(",")

                    // Map to hold parallel branches
                    def branches = [:]

                    for (int i = 0; i < tags.size(); i++) {
                        def tag = tags[i].trim()
                        branches["Run ${tag}"] = {
                            node {
                                stage("Execute ${tag}") {
                                    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {

                                        // Create separate report folder for this tag
                                        bat "mkdir reports_${tag} || exit 0"

                                        // Run Cucumber tests for this tag
                                        bat """
                                           ${mvnHome}\\bin\\mvn.cmd clean test \
                                           -Dcucumber.filter.tags=${tag} \
                                           -Denv=${params.Environment}
                                        """

                                        // Copy generated HTML reports to separate folder
                                        bat "copy target\\*.html reports_${tag} || exit 0"
                                    }
                                }
                            }
                        }
                    }

                    // Execute all branches in parallel
                    parallel branches
                }
            }
        }

        stage('Publish Extent Reports') {
            steps {
                script {
                    // Find all HTML files in reports folders
                    def files = findFiles(glob: 'reports_*/**/*.html')

                    if (files.size() == 0) {
                        echo "❗ No Extent HTML report found!"
                    } else {
                        // Publish each report
                        files.each { f ->
                            publishHTML(target: [
                                reportDir: f.path.replaceAll('/[^/]+$', ''),
                                reportFiles: f.name,
                                reportName: "Extent Report - ${f.name}",
                                keepAll: true,
                                alwaysLinkToLastBuild: true,
                                allowMissing: true
                            ])
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Build & Tests Completed Successfully!"
            emailext(
                subject: "Jenkins Build Success",
                body: "Build SUCCESS for ${env.JOB_NAME} #${env.BUILD_NUMBER}. Check console output: ${env.BUILD_URL}console",
                to: "${params.EmailTo ?: 'team@example.com'}"
            )
        }
        failure {
            echo "Build or Tests Failed!"
            emailext(
                subject: "Jenkins Build Failed",
                body: "Build FAILED for ${env.JOB_NAME} #${env.BUILD_NUMBER}. Check console output: ${env.BUILD_URL}console",
                to: "${params.EmailTo ?: 'team@example.com'}"
            )
        }
    }
}
