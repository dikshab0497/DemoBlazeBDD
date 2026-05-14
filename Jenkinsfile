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

    stages {

        stage('Run Tests') {
            steps {
				script{
					echo "Running Test case ${params.TestCase} on ${params.Environment} Environment on ${params.browser}"
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
