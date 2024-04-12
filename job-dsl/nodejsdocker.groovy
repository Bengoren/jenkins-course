job('NodeJS Docker example') {
    scm {
        git('https://github.com/Bengoren/docker-demo.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        script {
            def gitRevision = env.GIT_COMMIT.take(9).toLowerCase()  
        dockerBuildAndPublish {
            repositoryName('bengoren/docker-nodejs-demo')
            tag(gitRevision)
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}
