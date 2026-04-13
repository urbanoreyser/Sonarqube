pipeline {
    agent any
    tools {
  maven 'mavenjenkins'
}
parameters {
	     string(name: 'BRANCH', defaultValue: '', description: 'Branch to build')
    }


    stages {
        stage(' clone the repositroy ') {
            steps {
               git branch: "${params.BRANCH}", credentialsId: 'github-cred', url: 'https://github.com/urbanoreyser/Sonarqube.git' 
            }
        }
        
        
        stage('Build the application') {
            steps {
                sh 'mvn clean package'
            }
        }

	    stage('Static code analysis') {
            steps {
        withSonarQubeEnv('sonarqube-token') {
                    sh  "mvn sonar:sonar"
                }
                }
                
            }
        
        
    }
}
