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
        
    }

    agent any

    tools {
        maven 'M3'
    }

    stages {

        stage('Run Tests') {
            steps {
				script{
					echo "Running Test case ${params.TestCase} on ${params.Environment} Environment"
					bat "mvn clean test -Dcucumber.filter.tags=${params.TestCase} -DEnvironment=${params.Environment}"
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
