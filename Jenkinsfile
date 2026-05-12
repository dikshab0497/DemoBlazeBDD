pipeline {

    parameters {
        string(
            name: 'TestCase',
            defaultValue: '@LoginWithInValidCred',
            description: 'Enter Test cases tag'
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
					echo "Running Test case ${params.TestCase}"
					bat "mvn clean test -Dcucumber.filter.tags=${params.TestCase}"
				}
                
            }
        }

       
           }


    post {
        always {
            echo "Build Completed!"
        }
        success {
            
        }
        failure {
            
        }
    }
}
