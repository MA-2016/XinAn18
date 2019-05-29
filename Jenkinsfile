pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // TODO: handle exception
                echo 'Building ...'
                sh label: '', script: 'cd backend/monitor && mvn package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                echo 'No Test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying ...'
                sh label: '', script: 'cd backend/monitor && cp target/SecurityAnalyse.war /usr/tomcat9/webapps/'
            }
        }
    }
}
