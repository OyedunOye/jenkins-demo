def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
}

def testSourceCode() {
    echo 'testing the application...'
    sh 'mvn test'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh 'docker build -t oluwasade/demo-app:jma-2.1 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push oluwasade/demo-app:jma-2.1'

    }
}

def deployApp() {
    echo 'deploying the application...'
}

return this