pipeline {
    agent any

    stages {
        stage("Telegram Message"){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: 'TELEGRAM_BOT', passwordVariable: 'TOKEN', usernameVariable: 'CHAT_ID')]) {
                        sh """
                            curl -s -X POST https://api.telegram.org/bot"${TOKEN}"/sendMessage -d chat_id="${CHAT_ID}" -d text="Hello from Jenkins !"
                        """
                    }
                }
            }
        }

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
                withSonarQubeEnv(credentialsId: 'SONAR_TOKEN', installationName: 'svr_sonarqube_scanner') {
                    script{
                    
                        def projectKey = 'reactjs-devops8-template' 
                        def projectName = 'ReactjsDevOps8template'
                        def projectVersion = '1.0.0' 
                        sh """
                        ${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=${projectKey} \
                        -Dsonar.projectName="${projectName}" \
                        -Dsonar.projectVersion=${projectVersion} \
                        """       
                    }
                }

            }
        }

        // wait for the quality gate 
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