def call() {
    script {
        stage('Clean Workspace') {
            cleanWs()
        }

        stage('Clone Repository') {
            git url: 'https://github.com/OT-MICROSERVICES/employee-api.git', branch: 'main', credentialsId: 'aman-git'
        }

        stage('Compile') {
            sh "go build"
        }
    }
}
