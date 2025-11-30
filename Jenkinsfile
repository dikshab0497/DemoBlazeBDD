stage('Run Tests in Parallel') {
    steps {
        script {

            def mvnHome = tool 'M3'

            // Split tags passed from Jenkins UI
            def tags = params.TestCase.split(",")

            // Create parallel branches
            def branches = [:]

            for (int i = 0; i < tags.size(); i++) {
                def tag = tags[i].trim()

                branches["Run ${tag}"] = {
                    node {
                        stage("Execute ${tag}") {

                            withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {

                                // Clear reports folder for each branch
                                bat "mkdir reports_${tag}"

                                bat """
                                   ${mvnHome}\\bin\\mvn.cmd clean test \
                                   -Dcucumber.filter.tags=${tag} \
                                   -Denv=${params.Environment}
                                """

                                // Move report for each tag
                                bat "copy target\\*.html reports_${tag} || exit 0"
                            }
                        }
                    }
                }
            }

            parallel branches
        }
    }
}
