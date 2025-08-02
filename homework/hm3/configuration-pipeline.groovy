@Library("my-shared-lib")_
// https://github.com/socheatha/shared-jenkin-lib-devop9

pipeline {
    agent any

    stages{ 
        stage("Clone ReactJs Code") {
            steps {
                    // A public repo without Dockerfile and docker-compose.yaml
                    git 'https://github.com/socheatha/reactjs-devop8-template'
            }
        }

        stage("Build"){
            steps{
                // Inject docker dependencies which load from ressource and build the project
                script {
                    def dockerfileContent = libraryResource('docker/Dockerfile')
                    writeFile file: 'Dockerfile', text: dockerfileContent

                    def composeContent = libraryResource('docker/docker-compose.yaml')
                    writeFile file: 'docker-compose.yml', text: composeContent
                }

                sh """
                    docker build -t socheatha/hm-jenkins-react-sonarqube-pipeline:${env.BUILD_NUMBER} . 
                """
            }
        }

        stage("Deploy"){
            // Up the container with expose port 3000
            steps{
                sh"""
                docker stop hm-reactjs-cont || true 
                docker rm hm-reactjs-cont || true 

                docker run -dp 3000:80 \
                    --name hm-reactjs-cont \
                    socheatha/hm-jenkins-react-sonarqube-pipeline:${env.BUILD_NUMBER}
                """
            }
        }
    }
}
