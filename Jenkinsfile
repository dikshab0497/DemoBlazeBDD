pipeline {

    // Parameter to pass Cucumber tag from Jenkins UI
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
            steps {
                script {
                    def mvnHome = tool 'M3'

                    // Split tags passed from Jenkins UI
                    def tags = params.TestCase.split(",")

                    def branches = [:]

                    for (int i = 0; i < tags.size(); i++) {
                        def tag = tags[i].trim()
                        if (tag) {
                            branches["Run ${tag}"] = {
                                node {
                                    stage("Execute ${tag}") {
                                        withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {

                                            // Create folder per tag to avoid overwriting
                                            def reportFolder = "reports_${tag.replaceAll('@','')}"
                                            bat "rmdir /s /q ${reportFolder} || exit 0"
                                            bat "mkdir ${reportFolder}"

                                            // Run tests
                                            bat """
                                               ${mvnHome}\\bin\\mvn.cmd clean test \
                                               -Dcucumber.filter.tags=${tag} \
                                               -Denv=${params.Environment}
                                            """

                                            // Copy HTML reports to tag folder
                                            bat "copy target\\*.html ${reportFolder} || exit 0"
                                        }
                                    }
                                }
                            }
                        }
                    }

                    parallel branches
                }
            }
        }

        stage('Publish Reports') {
            steps {
                script {
                    // Loop through each report folder
                    def tags = params.TestCase.split(",")
                    for (int i = 0; i < tags.size(); i++) {
                        def tag = tags[i].trim()
                        if (tag) {
                            def reportFolder = "reports_${tag.replaceAll('@','')}"
                            def files = findFiles(glob: "${reportFolder}/*.html")
                            if (files.size() > 0) {
                                def latestReport = files.sort { it.lastModified }[-1].name
                                echo "Publishing report for ${tag}: ${latestReport}"
                                publishHTML(target: [
                                    reportDir: reportFolder,
                                    reportFiles: latestReport,
                                    reportName: "ExtentReport_${tag.replaceAll('@','')}",
                                    keepAll: true,
                                    alwaysLinkToLastBuild: true,
                                    allowMissing: true
                                ])
                            } else {
                                echo "❗ No report found for ${tag}!"
                            }
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
