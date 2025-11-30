pipeline {

    // Parameters to pass Cucumber tags, environment, and email
    parameters {
        string(
            name: 'TestCase',
            defaultValue: '',
            description: 'Enter Cucumber tag(s) to run, comma separated (e.g., @LoginWithValidCred,@LoginWithInvalidCred)'
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
                    def tags = params.TestCase.split(",")

                    def branches = [:]

                    for (int i = 0; i < tags.size(); i++) {
                        def tag = tags[i].trim()
                        branches["Run ${tag}"] = {
                            node {
                                stage("Execute ${tag}") {
                                    // Checkout code inside each parallel node
                                    checkout scm
                                    
                                    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                                        def reportDir = "reports_${tag}"

                                        // Clean or create report folder
                                        bat "if exist ${reportDir} rmdir /s /q ${reportDir}"
                                        bat "mkdir ${reportDir}"

                                        // Run Maven from project root
                                        bat "${mvnHome}\\bin\\mvn.cmd clean test -Dcucumber.filter.tags=${tag} -Denv=${params.Environment}"

                                        // Copy generated HTML reports to branch-specific folder
                                        bat "if exist target\\*.html copy target\\*.html ${reportDir} || exit 0"
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
                    // Collect all report folders
                    def allReports = findFiles(glob: 'reports_*/**/*.html')

                    if (allReports.size() == 0) {
                        echo "❗ No Extent HTML reports found!"
                    } else {
                        allReports.each { report ->
                            def folder = report.path.split('/')[0]
                            publishHTML(target: [
                                reportDir: folder,
                                reportFiles: report.name,
                                reportName: "ExtentReport - ${folder}",
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
