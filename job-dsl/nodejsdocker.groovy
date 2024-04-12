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
    def gitRevision = "${GIT_REVISION}".take(9).toLowerCase()
    steps {
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
