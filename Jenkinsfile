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
                jobDsl targets: 'dsl/create-jobs.groovy'
            }
        }
    }
}
