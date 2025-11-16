pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t catalogo-produtos:${BUILD_NUMBER} .'
            }
        }
        stage('Push to Registry') {
            steps {
                sh 'docker push <seu-registro>/catalogo-produtos:${BUILD_NUMBER}'
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/deployment.yaml'
            }
        }
    }
}
