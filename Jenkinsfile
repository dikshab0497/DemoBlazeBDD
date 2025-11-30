pipeline {

    // Parameters from Jenkins UI
    parameters {
        string(
            name: 'TestCase',
            defaultValue: '',
            description: 'Enter Cucumber tags separated by comma (e.g., @LoginWithValidCred,@LoginWithInValidCred)'
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
        maven 'M3'
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
                    def tags = params.TestCase.split(",")

                    def branches = [:]

                    // Create parallel branches for each tag
                    for (int i = 0; i < tags.size(); i++) {
                        def tag = tags[i].trim()
                        branches["Run ${tag}"] = {
                            node {
                                stage("Execute ${tag}") {
                                    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {

                                        // Temporary folder for this tag
                                        def tempReportDir = "reports_${tag.replaceAll('@','')}"
                                        bat "rmdir /s /q ${tempReportDir} || exit 0"
                                        bat "mkdir ${tempReportDir}"

                                        // Run Maven tests for this tag
                                        bat """
                                           ${mvnHome}\\bin\\mvn.cmd clean test \
                                           -Dcucumber.filter.tags=${tag} \
                                           -Denv=${params.Environment}
                                        """

                                        // Copy Extent report from test run into temp folder
                                        // Replace with actual path where your Extent report is generated
                                        bat "copy /Y reports\\Test-Report.html ${tempReportDir}\\Test-Report-${tag.replaceAll('@','')}.html || exit 0"

                                        // Copy temp report to main workspace for Jenkins
                                        bat "mkdir reports || exit 0"
                                        bat "copy /Y ${tempReportDir}\\*.html reports || exit 0"
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
                    catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                        def files = findFiles(glob: 'reports/*.html')
                        if (files.size() == 0) {
                            echo "❗ No Extent HTML report found!"
                        } else {
                            publishHTML(target: [
                                reportDir: 'reports',
                                reportFiles: '*.html',
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
