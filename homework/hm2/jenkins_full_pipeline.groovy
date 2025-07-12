pipeline {
    agent any

    tools{
        nodejs "nodejs-lts"
    }

    stages {
        stage('Clone Code') {
            steps {
                git 'https://github.com/socheatha/reactjs-devop8-template'

            }
        } 

        stage("Run Test"){
            steps{
                sh """
                    npm install --force
                    npm test 
                """
            }
        }

        stage("Build"){
            steps{
                sh """
                    docker build -t socheatha/jenkins-react-pipeline:${env.BUILD_NUMBER} . 
                """
            }
        }

        stage("Push to Dockerhub"){
            steps{
                withCredentials([usernamePassword(credentialsId: 'DOCKERHUB_CRE', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                    sh """
                        echo '$PASSWORD' |  docker login -u $USERNAME --password-stdin   
                        docker push socheatha/jenkins-react-pipeline:${env.BUILD_NUMBER} 
                    """
                }
            }
        }

        stage("Deploy"){
            steps{
                sh"""
                    docker stop reactjs-cont || true 
                    docker rm reactjs-cont || true 

                    docker run -dp 3000:80 \
                        --name reactjs-cont \
                        socheatha/jenkins-react-pipeline
                """

            }
        }

        stage("Add Domain Name"){
            steps{
                sh """
                    sudo /var/jenkins_utils/autoAddSubDomain.sh homework-jenkins 3000
                """ 
            }
        }
    }
}
