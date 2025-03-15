def call() {
    stage('Clean Workspace') {
        steps {
            cleanWs()
        }
    }

    stage('Clone Repository') {
        steps {
            git url: 'https://github.com/OT-MICROSERVICES/employee-api.git', branch: 'main', credentialsId: 'aman-git'
        }
    }

    stage('Compile') {
        steps {
            sh "go build"
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
                    to: "aman.raj@mygurukulam.co"
                )
            }
        }
    }
}
