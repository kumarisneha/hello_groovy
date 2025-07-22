// @Library('shared-lib') _  // Refer to your shared lib ID in Jenkins config

pipeline {
    agent any
    stages {
        stage('Hello Shared Library') {
            steps {
                script {
                    sayHello('Sneha')
                }
            }
        }

        stage('Generate DSL Jobs') {
            steps {
                // jobDsl targets: 'dsl/CreateJobs.groovy'
                jobDsl targets: 'dsl/ChangeRequest.groovy'
            }
        }
    }
}
