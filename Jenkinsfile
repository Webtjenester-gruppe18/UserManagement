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
     }
   }

 // @author Emil s175107