pipeline {

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
        maven 'M3'
    }

    stages {

        stage('Checkout Code') {
            steps {
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
                            stage("Execute ${tag}") {
                                withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                                    // Create report folder for this tag
                                    bat "mkdir reports_${tag} || exit 0"

                                    // Run tests for this tag
                                    bat """
                                       ${mvnHome}\\bin\\mvn.cmd clean test \
                                       -Dcucumber.filter.tags=${tag} \
                                       -Denv=${params.Environment}
                                    """

                                    // Copy report
                                    bat "copy target\\*.html reports_${tag} || exit 0"
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
                    def files = findFiles(glob: 'reports_*/**/*.html')

                    if (files.size() == 0) {
                        echo "❗ No reports found!"
                    } else {
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
            emailext(
                subject: "Jenkins Build Success",
                body: "Build SUCCESS for ${env.JOB_NAME} #${env.BUILD_NUMBER}. Check console output: ${env.BUILD_URL}console",
                to: "${params.EmailTo ?: 'team@gmail.com'}"
            )
        }
        failure {
            emailext(
                subject: "Jenkins Build Failed",
                body: "Build FAILED for ${env.JOB_NAME} #${env.BUILD_NUMBER}. Check console output: ${env.BUILD_URL}console",
                to: "${params.EmailTo ?: 'team@gmail.com'}"
            )
        }
    }
}
