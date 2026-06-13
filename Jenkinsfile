pipeline {

    parameters {
        string(
            name: 'TestCase',
            defaultValue: '@LogOut',
            description: 'Enter Test cases tag'
        )
        choice(
    		name:'Environment',
    		choices:['QA','UAT','PRODUCTION'], 
    		description : 'Select Environment'
		)
		choice(
    		name:'Browser',
    		choices:['chrome','edge'], 
    		description : 'Select Browser'
		)
		string(
            name: 'Branch',
            defaultValue: 'main',
            description: 'Enter Branch name'
        )
       
	}

    agent any

    tools {
        maven 'M3'
    }
    
    options {
    	skipDefaultCheckout(true)
	}

    stages {
		
		stage('Checkout code') {
    		steps {
        		checkout([
            		$class: 'GitSCM',
            		branches: [[name: "*/${params.Branch}"]],
            		userRemoteConfigs: [[
                	url: 'https://github.com/dikshab0497/DemoBlazeBDD.git'
            	]]
        	])
    	}
}
        stage('Build') {
            steps {
				script{
					echo "Compiling the test case............"
					bat "mvn clean compile"
				}
                
            }
        }

        stage('Run Tests') {
			when{
				expression {
        			(params.Environment == 'QA' && params.Browser == 'chrome') && params.TestCase?.trim().length() > 0
    			}
			}
			
            steps {
				script{
					echo "Running Test case ${params.TestCase} on ${params.Environment.toLowerCase()} Environment on ${params.Browser}"
					bat "mvn test -Dcucumber.filter.tags=${params.TestCase} -Denv=${params.Environment.toLowerCase()} -Dbrowser=${params.Browser} -Dextent.report.dir=%WORKSPACE%\\%BUILD_NUMBER%\\ExtentReport"
				}
                
            }
        }

       
      }
      post {
    	always {
        	publishHTML([
            	allowMissing: false,
            	alwaysLinkToLastBuild: true,
            	keepAll: true,
            	reportDir: "${env.BUILD_NUMBER}/ExtentReport",
            	reportFiles: '*.html',
            	reportName: 'ExtentReport'
        	])
        	emailext(
            	to: 'dikshabandagale0497@gmail.com',
            	subject: "Jenkins Build ${currentBuild.currentResult}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            	body: """
            	Build Status: ${currentBuild.currentResult}
            	Build Number: ${env.BUILD_NUMBER}
            	Build URL: ${env.BUILD_URL}

            	Environment: ${params.Environment}
            	Browser: ${params.Browser}
            	Test Case Tag: ${params.TestCase}

            	Report Link: ${env.BUILD_URL}ExtentReport/
            	"""
        )

        echo "Build completed"
    }
}
}
