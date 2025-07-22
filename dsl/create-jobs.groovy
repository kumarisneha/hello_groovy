def baseFolder = "my-root-automation-folder/automated-jobs"

folder(baseFolder) {
    displayName("Automated Jobs")
    description("All cron-based automated pipeline jobs")
}

def change_requests_properties = [
    ["name": "job-daily",     "schedule": "H 2 * * *"],
    ["name": "job-hourly",    "schedule": "H * * * *"],
]

def regions = ['wdc', 'osa', 'mad']

regions.each { region ->

    folder("${baseFolder}/${region}") {
        displayName("${region}")
        description("All cron-based automated pipeline jobs for ${region}")
    }

    change_requests_properties.each { job ->
        pipelineJob("${baseFolder}/${region}/scheduled-python-job-${job.name}") {
            description("This pipeline job clones a repo via SSH and runs a Python script on schedule")

            triggers {
                cron(job.schedule)
            }

            definition {
                cpsScm {
                    scm {
                        git {
                            remote {
                                url("git@github.com:your-org/main-repo.git")
                                credentials("git-ssh-creds")
                            }
                            branches("*/main")
                        }
                    }
                    scriptPath("Jenkinsfile")
                }
            }
        }
    }
}
