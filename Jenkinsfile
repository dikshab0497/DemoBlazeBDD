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
                    // Folder for all parallel runs
                    env.REPORT_DIR = "${env.WORKSPACE}\\ExtentReport_${env.BUILD_NUMBER}"
                    // Clean folder if exists
                    bat "if exist \"${env.REPORT_DIR}\" rmdir /s /q \"${env.REPORT_DIR}\""
                    // Create new folder
                    bat "mkdir \"${env.REPORT_DIR}\""
                }
            }
        }

        stage('Run Tests in Parallel') {
    steps {
        script {
            def mvnHome = tool 'M3'
            def tags = params.TestCase.split(",")
            def branches = [:]

            for (String rawTag : tags) {
                def tag = rawTag.trim()
                branches["Run @${tag}"] = {
                    node {
                        stage("Executing @${tag}") {
                            def branchReportDir = "${env.WORKSPACE}\\ExtentReport_${env.BUILD_NUMBER}\\${tag}"
                            bat "mkdir \"${branchReportDir}\""
                            bat """
                                ${mvnHome}\\bin\\mvn.cmd clean test ^
                                -Dcucumber.filter.tags=@${tag} ^
                                -Denv=${params.Environment} ^
                                -Dextent.report.dir="${branchReportDir}"
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
            // Merge individual reports if needed OR just pick one main folder
            publishHTML(target: [
                reportDir: "${env.WORKSPACE}\\ExtentReport_${env.BUILD_NUMBER}",
                reportFiles: 'Test-Report.html', // make sure this is the main report
                reportName: "Extent Report",
                keepAll: true,
                alwaysLinkToLastBuild: true
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
                subject: "Jenkins Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build SUCCESS for ${env.JOB_NAME} #${env.BUILD_NUMBER}.\nCheck Extent Report at ${env.BUILD_URL}display/redirect",
                to: "${params.EmailTo}"
            )
        }
        failure {
            emailext(
                subject: "Jenkins Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build FAILED for ${env.JOB_NAME} #${env.BUILD_NUMBER}.\nCheck Jenkins logs for details: ${env.BUILD_URL}",
                to: "${params.EmailTo}"
            )
        }
    }
}
