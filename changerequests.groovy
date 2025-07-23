def baseFolder = "automated-jobs"

folder(baseFolder) {
    displayName("Automated Jobs")
    description("All cron-based automated pipeline jobs")
}

def change_requests_properties = [
    ["name": "job-daily",     "schedule": "H/5 * * * *"], // every day at 2AM
    ["name": "job-hourly",    "schedule": "H * * * *"], // every hour
]

def regions = [
    'wdc',
    'osa',
    'mad'
]

regions.each { region ->
    
    change_requests_properties.each { job ->

        folder("${baseFolder}/${region}") {
            displayName("${region}")
            description("All cron-based automated pipeline jobs for ${region}")
        }
    
        pipelineJob("${baseFolder}/${region}/scheduled-python-job-'${job.name}'") {
            
            description("This pipeline job clones a repo via SSH, sets up cron trigger, and runs a Python script")
            
            triggers {
                cron(job.schedule)
            }
            
            definition {
                cpsScm {
                    scm {
                        git {
                            remote {
                                url("git@github.com:kumarisneha/hello_groovy.git")
                                credentials("git-ssh-creds")
                            }
                            branches("*/master")
                        }
                    }
                    scriptPath("Jenkinsfile")
                }
            }
        }
    }
}
