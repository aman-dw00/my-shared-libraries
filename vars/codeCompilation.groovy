def call() {
    pipeline {
        agent any

        tools {
            go 'Golang'  
        }

        environment {
            REPORT_FILE = "compile_report.txt"
            branch_name = 'main'
            repo_url = 'https://github.com/OT-MICROSERVICES/employee-api.git'
            email_recipients = "aman.raj@mygurukulam.co"
        }

        stages {
            stage('Clean Workspace') {
                steps {
                    cleanWs()
                }
            }

            stage('Clone Repository') {
                steps {
                    git url: repo_url, branch: branch_name, credentialsId: 'aman-git'
                }
            }

            stage('Compile') {
                steps {
                    sh "go build"
                }
            }
        }

        post {
            always {
                script {
                    emailext(
                        subject: "Build Status - ${currentBuild.fullDisplayName}",
                        body: """The build status for ${currentBuild.fullDisplayName} is as follows:

                        Build Log URL: ${BUILD_URL}console

                        Regards,
                        Jenkins
                        """,
                        to: email_recipients
                    )
                }
            }
        }
    }
}
