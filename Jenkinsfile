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

        stage('Prepare Report Folder') {
            steps {
                script {
                    // Build-specific base report folder
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

            		for (int i = 0; i < tags.size(); i++) {
                	def tag = tags[i].trim()
                	branches["Run ${tag}"] = {
                    	node {
                        	stage("Execute ${tag}") {
                            	withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                                // Use Windows-friendly backslash
                                def reportDir = "${env.REPORT_DIR}\\${tag}"
                                bat "mkdir \"${reportDir}\""

                                // Run Maven test with automatic '@'
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
                // Use relative workspace path
                def reportFolders = findFiles(glob: "reports_${env.BUILD_NUMBER}/*/Test-Report.html")
                reportFolders.each { reportFile ->
                    // Extract folder name for report name
                    def folderName = reportFile.path.split(/[\\\/]/)[1]  // gets the tag folder name
                    publishHTML(target: [
                        reportDir: reportFile.path.replace('\\Test-Report.html',''),
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
