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
        		git branch: "${params.Branch}",
        		url: 'https://github.com/dikshab0497/DemoBlazeBDD.git'
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
					bat "mvn test -Dcucumber.filter.tags=${params.TestCase} -Denv=${params.Environment.toLowerCase()} -Dbrowser=${params.Browser}"
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
            	reportDir: 'ExtentReport',
            	reportFiles: '*.html',
            	reportName: 'Extent Report'
        	])
        echo "Build completed"
    }
}
}
