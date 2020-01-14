pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean install'
      }
    }
     stage('Build') {
          steps {
            sh 'docker build --tag usermanagerimage:latest . '
          }
        }
     stage('Deploy') {
          steps {
            sh '''docker stop usermanager || true && docker rm usermanager || true;
            docker run -d -p 7676:8080 --name usermanager usermanagerimage:latest
            '''
          }
     }
  }
}