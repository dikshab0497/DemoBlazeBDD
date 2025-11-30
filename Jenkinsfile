pipeline {

    // Parameters from Jenkins UI
    parameters {
        string(
            name: 'TestCase',
            defaultValue: '',
            description: 'Enter Cucumber tags separated by comma (e.g., @LoginWithValidCred,@LoginWithInValidCred)'
        )
        choice(
            name:'Environment',
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
        maven 'M3'   // Maven tool configured in Jenkins
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "Pulling latest code..."
                checkout scm
            }
        }

        stage('Run Tests in Parallel') {
            steps {
                script {
                    def mvnHome = tool 'M3'

                    // Split tags passed from Jenkins UI
                    def tags = params.TestCase.split(",")

                    def branches = [:]

                    for (int i = 0; i < tags.size(); i++) {
                        def tag = tags[i].trim()
                        branches["Run ${tag}"] = {
                            node {
                                stage("Execute ${tag}") {
                                    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {

                                        // Create temp folder for each branch
                                        def tempReportDir = "reports_${tag}"
                                        bat "rmdir /s /q ${tempReportDir} || exit 0"
                                        bat "mkdir ${tempReportDir}"

                                        // Run Maven tests for the tag
                                        bat """
                                           ${mvnHome}\\bin\\mvn.cmd clean test \
                                           -Dcucumber.filter.tags=${tag} \
                                           -Denv=${params.Environment}
                                        """

                                        // Copy generated HTML reports to temp folder
                                        bat "copy target\\*.html ${tempReportDir} || exit 0"

                                        // Merge temp reports into main reports folder
                                        bat "mkdir reports || exit 0"
                                        bat "copy ${tempReportDir}\\*.html reports || exit 0"
                                    }
                                }
                            }
                        }
                    }

                    parallel branches
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                script {
                    // Original stage unchanged
                    def files = findFiles(glob: 'reports/*.html')

                    if (files.size() == 0) {
                        echo "❗ No Extent HTML report found in reports/ folder!"
                    } else {
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
