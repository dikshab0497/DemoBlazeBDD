pipeline {

    // Jenkins parameters
    parameters {
        string(
            name: 'TestCase',
            defaultValue: '',
            description: 'Enter Cucumber tags separated by comma (e.g.,LoginWithValidCred,SignUp)'
        )
        choice(
            name: 'Environment',
            choices: ['DEV','QA','UAT'],
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

        stage('Prepare Report Folder') {
            steps {
                script {
                    // Base report folder for this build
                    env.REPORT_BASE_DIR = "${env.WORKSPACE}\\reports_${env.BUILD_NUMBER}"
                    bat "rmdir /s /q \"${env.REPORT_BASE_DIR}\" || exit 0"
                    bat "mkdir \"${env.REPORT_BASE_DIR}\""
                }
            }
        }

        stage('Run Tests in Parallel') {
            steps {
                script {
                    def mvnHome = tool 'M3'
                    def tags = params.TestCase.split(",")
                    def branches = [:]

                    for (int i = 0; i < tags.size(); i++) {
                        def tag = tags[i].trim()
                        branches["Run ${tag}"] = {
                            node {
                                stage("Execute ${tag}") {
                                    def reportDir = "${env.REPORT_BASE_DIR}\\${tag}"
                                    bat "mkdir \"${reportDir}\""

                                    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                                        bat """
                                            ${mvnHome}\\bin\\mvn.cmd clean test \
                                            -Dcucumber.filter.tags=@${tag} \
                                            -Denv=${params.Environment} \
                                            -Dextent.report.dir=${reportDir}
                                        """
                                    }
                                }
                            }
                        }
                    }

                    parallel branches
                }
            }
        }

        stage('Publish Extent Reports') {
            steps {
                script {
                    catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                        def reportFolders = findFiles(glob: "${env.REPORT_BASE_DIR}/*/")
                        reportFolders.each { folder ->
                            publishHTML(target: [
                                reportDir: folder.path,
                                reportFiles: '*.html',
                                reportName: "Extent Report - ${folder.name}",
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
        always {
            echo "Build Completed!"
        }
        success {
            echo "All Tests Passed!"
            emailext(
                subject: "Jenkins Build Success",
                body: "Build SUCCESS for ${env.JOB_NAME} #${env.BUILD_NUMBER}. Check console output: ${env.BUILD_URL}console",
                to: "${params.EmailTo ?: 'team@example.com'}"
            )
        }
        failure {
            echo "Some Tests Failed!"
            emailext(
                subject: "Jenkins Build Failed",
                body: "Build FAILED for ${env.JOB_NAME} #${env.BUILD_NUMBER}. Check console output: ${env.BUILD_URL}console",
                to: "${params.EmailTo ?: 'team@example.com'}"
            )
        }
    }
}
