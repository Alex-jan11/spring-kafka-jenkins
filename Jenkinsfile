pipeline {
  agent any

  environment {
    USER_IMAGE_NAME = "user-service"
    ORDER_IMAGE_NAME = "order-service"
    IMAGE_TAG = "latest"
  }

  stages {
    stage('Build user-service') {
      steps {
        dir('user-service') {
          echo '🔧 Build Maven pentru user-service...'
          sh 'mvn clean package -DskipTests'

          echo '🐳 Build Docker image pentru user-service...'
          sh "docker build -t ${USER_IMAGE_NAME}:${IMAGE_TAG} ."
        }
      }
    }

    stage('Build order-service') {
      steps {
        dir('order-service') {
          echo '🔧 Build Maven pentru order-service...'
          sh 'mvn clean package -DskipTests'

          echo '🐳 Build Docker image pentru order-service...'
          sh "docker build -t ${ORDER_IMAGE_NAME}:${IMAGE_TAG} ."
        }
      }
    }

    stage('(Optional) Push Docker images to DockerHub') {
      when {
        expression { env.DOCKERHUB_USERNAME != null }
      }
      steps {
        withCredentials([usernamePassword(
          credentialsId: 'dockerhub-creds',
          usernameVariable: 'DOCKERHUB_USERNAME',
          passwordVariable: 'DOCKERHUB_PASSWORD'
        )]) {
          echo '🔐 Autentificare DockerHub...'
          sh 'echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin'

          echo '🚀 Push user-service image...'
          sh "docker tag ${USER_IMAGE_NAME}:${IMAGE_TAG} $DOCKERHUB_USERNAME/${USER_IMAGE_NAME}:${IMAGE_TAG}"
          sh "docker push $DOCKERHUB_USERNAME/${USER_IMAGE_NAME}:${IMAGE_TAG}"

          echo '🚀 Push order-service image...'
          sh "docker tag ${ORDER_IMAGE_NAME}:${IMAGE_TAG} $DOCKERHUB_USERNAME/${ORDER_IMAGE_NAME}:${IMAGE_TAG}"
          sh "docker push $DOCKERHUB_USERNAME/${ORDER_IMAGE_NAME}:${IMAGE_TAG}"
        }
      }
    }
  }

  post {
    success {
      echo '✅ Pipeline complet – Build + Docker OK!'
    }
    failure {
      echo '❌ Eroare în pipeline. Verifică logul.'
    }
  }
}