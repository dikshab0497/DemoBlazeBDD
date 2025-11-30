pipeline {

    // Parameters from Jenkins UI
    parameters {
        string(
            name: 'TestCase',
            defaultValue: '',
            description: 'Enter Cucumber tags separated by comma (e.g., @LoginWithValidCred,@SignUp)'
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
        maven 'M3'   // Maven configured in Jenkins
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

                    for (int i = 0; i < tags.size(); i++) {
                        def tag = tags[i].trim()
                        branches["Run ${tag}"] = {
                            node {
                                stage("Execute ${tag}") {
                                    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {

                                        // Temp folder for this tag
                                        def tempReportDir = "reports_${tag.replaceAll('@','')}"
                                        bat "rmdir /s /q ${tempReportDir} || exit 0"
                                        bat "mkdir ${tempReportDir}"

                                        // Run Maven tests for this tag
                                        bat """
                                           ${mvnHome}\\bin\\mvn.cmd clean test \
                                           -Dcucumber.filter.tags=${tag} \
                                           -Denv=${params.Environment}
                                        """

                                        // Copy all generated HTML reports to temp folder
                                        bat "copy /Y target\\*.html ${tempReportDir}\\ || exit 0"

                                        // Merge temp report into main reports folder
                                        bat "mkdir reports || exit 0"
                                        bat "copy /Y ${tempReportDir}\\*.html reports\\ || exit 0"
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
                        def files = findFiles(glob: 'reports/*.html')
                        if (files.size() == 0) {
                            echo "❗ No Extent HTML report found!"
                        } else {
                            publishHTML(target: [
                                reportDir: 'reports',    // folder where all HTML reports are
                                reportFiles: '*.html',   // pick all HTML files
                                reportName: 'Extent Reports',
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
