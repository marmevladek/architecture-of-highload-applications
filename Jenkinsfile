pipeline {
    agent any

    environment {
        // Название сети из docker-compose, если нужна (у тебя main_bridge упомянута)
        DOCKER_NETWORK = "main_bridge"
    }

    stages {
        stage('Preparation') {
            steps {
                cleanWs()
                checkout scm
                script {
                    sh "git log -n 1 --format=%H > currentVersion"
                }
                stash name: 'source', includes: '**'
            }
        }

        stage('Build Docker Images') {
            steps {
                unstash 'source'
                script {
                    def services = ['profile-service', 'swipe-service', 'deck-service', 'notification-service']
                    for (svc in services) {
                        echo "Building image for ${svc}"
                        sh "docker build -t ${svc}:latest -f ${svc}/Dockerfile ${svc}"
                    }
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Пример запуска тестов для profile-service
                    // Нужно, чтобы тесты были внутри образа или запускались отдельно
                    sh """
                    docker run --rm -v \$(pwd)/profile-service:/app -w /app profile-service:latest ./gradlew test
                    """
                }
            }
        }

        stage('Publish Test Reports') {
            steps {
                junit '**/profile-service/build/test-results/test/*.xml'
                // Добавить остальные по необходимости
            }
        }

        stage('Deploy Services') {
            steps {
                sh """
                docker-compose down
                docker-compose up -d --build
                """
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}