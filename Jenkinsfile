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
                    env.REPORT_BASE_DIR = "reports_${env.BUILD_NUMBER}"

                    bat "rmdir /s /q ${env.REPORT_BASE_DIR} || exit 0"
                    bat "mkdir ${env.REPORT_BASE_DIR}"
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

                        branches["Run ${tag}"] = {
                            node {

                                stage("Executing @${tag}") {

                                    def reportDir = "${env.REPORT_BASE_DIR}\\${tag}"
                                    bat "mkdir \"${reportDir}\""

                                    bat """
                                        ${mvnHome}\\bin\\mvn.cmd clean test ^
                                        -Dcucumber.filter.tags=@${tag} ^
                                        -Denv=${params.Environment} ^
                                        -Dextent.report.dir=\"${reportDir}\"
                                    """
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

                    def reports = findFiles(glob: "reports_${env.BUILD_NUMBER}/**/Test-Report.html")

                    if (reports.size() == 0) {
                        echo "❌ No Test-Report.html found!"
                    }

                    reports.each { file ->
                        def path = file.path.replace('\\Test-Report.html', '')
                        def folderName = path.tokenize('/\\')[-1]

                        publishHTML(target: [
                            reportDir: path,
                            reportFiles: 'Test-Report.html',
                            reportName: "Extent Report - ${folderName}",
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
