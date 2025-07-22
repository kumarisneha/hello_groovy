def baseFolder = "automated-jobs"

folder(baseFolder) {
    displayName("Automated Jobs")
    description("Jobs created with Job DSL")
}

def jobs = [
    ["name": "job-daily",  "schedule": "H 2 * * *"],
    ["name": "job-hourly", "schedule": "H * * * *"]
]

jobs.each { job ->
    pipelineJob("${baseFolder}/${job.name}") {
        description("Automated job: ${job.name}")
        triggers { cron(job.schedule) }
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url("git@github.com:kumarisneha/hello_groovy.git")
                            credentials("git-ssh-creds")  // Jenkins SSH credential ID
                        }
                        branches('*/master')
                    }
                }
                scriptPath("Jenkinsfile")
            }
        }
    }
}
