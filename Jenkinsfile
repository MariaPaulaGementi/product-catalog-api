pipeline {
    agent any

    environment {
        GITHUB_REPO     = 'MariaPaulaGementi/product-catalog-api'
        AWS_REGION      = 'us-east-1'
        AWS_ACCOUNT_ID  = '921821545221'
        DOCKER_IMAGE    = 'catalogo-produtos'
        DOCKER_REGISTRY = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
    }

    tools {
        jdk 'JDK_21'
        maven 'Maven_3.8'
    }

    stages {
        stage('Checkout GitHub') {
            steps {
                withCredentials([string(credentialsId: 'github-credentials', variable: 'GITHUB_TOKEN')]) {
                    git branch: 'main',
                        url: "https://${GITHUB_TOKEN}@github.com/${GITHUB_REPO}.git"
                }
            }
        }

        stage('Build Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER} ."
            }
        }

        stage('Push to ECR') {
            steps {
                withAWS(credentials: 'aws-credentials', region: "${AWS_REGION}") {
                    sh """
                        aws ecr describe-repositories --repository-names ${DOCKER_IMAGE} || \
                        aws ecr create-repository --repository-name ${DOCKER_IMAGE}

                        aws ecr get-login-password --region ${AWS_REGION} | \
                        docker login --username AWS --password-stdin ${DOCKER_REGISTRY}

                        docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}
                        docker tag ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest
                        docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest
                    """
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withAWS(credentials: 'aws-credentials', region: "${AWS_REGION}") {
                    sh """
                        sed -i 's|image: .*|image: ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}|g' k8s/deployment.yaml
                        kubectl apply -f k8s/deployment.yaml
                    """
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline concluído com sucesso!'
        }
        failure {
            echo '❌ Pipeline falhou!'
        }
    }
}
