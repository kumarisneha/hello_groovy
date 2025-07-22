def baseFolder = "sayhello-folder"

folder(baseFolder) {
    displayName("Say Hello Jobs")
    description("Folder for all Say Hello related pipeline jobs")
}

// Define list of job names and schedules
def jobs = [
    [ name: "sayhello-daily",  schedule: "H 2 * * *" ],  // Runs daily at 2 AM
    [ name: "sayhello-hourly", schedule: "H * * * *" ]   // Runs every hour
]

// Loop through each job and define a pipeline
jobs.each { jobConfig ->
    def jobName = "${baseFolder}/${jobConfig.name}"

    pipelineJob(jobName) {
        description("Pipeline job '${jobConfig.name}' that runs sayhello.groovy script")

        // Set up cron trigger
        triggers {
            cron(jobConfig.schedule)
        }

        // Define the SCM and Jenkinsfile path
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url("git@github.com:kumarisneha/hello_groovy.git")
                            credentials("git-ssh-creds")  // Make sure this credential ID exists in Jenkins
                        }
                        branches("*/master")  // Or "*/master" if your repo uses master
                    }
                }
                scriptPath("sayhello.groovy")  // This should be the pipeline script in your repo
            }
        }
    }
}
