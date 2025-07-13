@Library('my-shared-lib')_

pipeline {
    agent any

    stages {
        stage('Clone ReachJs Code') {
            steps {
                git 'https://github.com/socheatha/reactjs-devop8-template.git'

            }
        } 

        stage("Check Code Quality in Sonarqube "){
            
            environment {
                scannerHome= tool 'tool_sonarqube_scanner' 
            }

            steps{
                script {
                    def projectKey = 'reactjs-devops8-template' 
                    def projectName = 'ReactjsDevOps8template'
                    def projectVersion = '1.0.0' 
                    scanReactSonarQu("${projectKey}", "${projectName}", "${projectVersion}")
                }
            }
        }

        stage("Wait for Quality Gate "){
            steps{
                script{
                   def qg = waitForQualityGate()
                    if ( qg.status != 'OK'){
                        sh """
                        echo " No need to build since you QG is failed "
                        """
                        currentBuild.result='FAILURE'
                        return 
                    }else {
                        echo "Quality of code is okay!! "
                        currentBuild.result='SUCCESS'
                    }
                }

            }
        }

        stage("Build"){
            when {
                expression { 
                    currentBuild.result == 'SUCCESS'
                }
            }
            steps{
                echo "Building the docker image "
            }
        }

        stage("Push"){
            when {
                expression { 
                    currentBuild.result == 'SUCCESS'
                }
            }
            steps{
                echo "Pushing the docker image to registry "
            }
        }
    }
}