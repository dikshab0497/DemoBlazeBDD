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
        		git branch: 'main',
        		url: 'https://github.com/dikshab0497/DemoBlazeBDD.git'
    		}
        }

        stage('Run Tests') {
            steps {
				script{
					echo "Running Test case ${params.TestCase} on ${params.Environment} Environment on ${params.Browser}"
					bat "mvn clean test -Dcucumber.filter.tags=${params.TestCase} -DEnvironment=${params.Environment} -Dbrowser=${params.Browser}"
				}
                
            }
        }

       
      }
      post {
   		always { 
	  		echo "Build completed"
    	}
	}

}
