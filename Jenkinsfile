pipeline {

    parameters {
        string(
            name: 'TestCase',
            defaultValue: '',
            description: 'Enter Cucumber tags separated by comma'
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
                checkout scm
            }
        }

        stage('Prepare Report Folder') {
            steps {
                script {
                    // Single consolidated report folder per build
                    env.REPORT_DIR = "ExtentReport_${env.BUILD_NUMBER}"

                    // Clean if exists
                    bat "rmdir /s /q ${env.REPORT_DIR} || exit 0"
                    bat "mkdir ${env.REPORT_DIR}"
                }
            }
        }

        stage('Run Tests in Parallel') {
            steps {
                script {
                    def mvnHome = tool 'M3'
                    def tags = params.TestCase.split(",")
                    def branches = [:]

                    // Create parallel branches per tag
                    for (String rawTag : tags) {
                        def tag = rawTag.trim()
                        branches["Run ${tag}"] = {
                            node {
                                stage("Executing @${tag}") {
                                    bat """
                                        ${mvnHome}\\bin\\mvn.cmd clean test ^
                                        -Dcucumber.filter.tags=@${tag} ^
                                        -Denv=${params.Environment} ^
                                        -Dextent.report.dir=${env.REPORT_DIR}
                                    """
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
                    // Publish the consolidated report
                    publishHTML(target: [
                        reportDir: env.REPORT_DIR,
                        reportFiles: 'ExtentReport.html',
                        reportName: 'Extent Report',
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "Build Completed!"
        }
        success {
            emailext(
                subject: "Jenkins Build Success",
                body: "Build SUCCESS for ${env.JOB_NAME} #${env.BUILD_NUMBER}.",
                to: "${params.EmailTo}"
            )
        }
        failure {
            emailext(
                subject: "Jenkins Build Failed",
                body: "Build FAILED for ${env.JOB_NAME} #${env.BUILD_NUMBER}.",
                to: "${params.EmailTo}"
            )
        }
    }
}
