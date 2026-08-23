
# Build Automation with Jenkins (CI)

This project focuses on the use of Jenkins to automatically trigger workflow that tests maven project codebase, build application artifact (JAR file and docker image) and push artifact to repository (nexus/docker private repository).


## Get Repo Locally

Clone the project

```bash
  git clone https://github.com/OyedunOye/jenkins-demo.git
```

Go to the project directory

```bash
  cd jenkins-demo
```

## Tech Stack

**Server:** Hetzner Cloud Server

**Tools:** Jenkins, Nexus, Docker, Git, GitHub, DockerHub


## Documentation

### Jenkins Installation

Jenkins can be installed directly on the OS of hosting server or could be run as a docker container. The latter was done in this project. Jenkins is hosted on an hetzner server. The docker installation command ran from the hosting server to download Jenkins image and run its container with precreated volumes attached is:

```bash
  docker run -d -p 50000:50000 -p 8080:8080 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock Jenkins/Jenkins:lts
```

My hosted Jenkins is available at [Jenkins URL](http://178.105.179.238:8080) 

### Tools Installation in Jenkins

Jenkins needs access to tools that it perform build steps on, on behalf of the DevOps engineer and these tools are accessed in Jenkins via plugins. Plugins are installed either:
- directly in the container running Jenkins within the hosting server, or 
- using plugins UI in Jenkins manage Jenkins to install them.

#### Plugins Installed and Configured for This Project

- maven
- stage view
- docker
- credentials binding
- github
- ignore committer strategy

#### Global Credentials Configured in Jenkins for This Project

- GitHub login credentials (username and personal access token)
- Docker login credentials (username and password)
- Nexus login credentials (username and password)
- GitHub webhook credentials (username and personal access token with right permissions that enable auto build trigger based on events in github repo)

### Features explored and Jenkinsfile for each

The project has 5 branches asides the master branch. Each branch showcases different Jenkins job type and build properties documented below:
- jenkins-simple-workflow: This branch has JenkinsFile configured with groovy with build steps just for this git branch. This is an improvement to freestyle job that was explored and configured for a single build step in jenkins UI. It is also more practical than a chain of freestyle jobs.
- jenkinsfile-using-groovy-script:
This branch explores multi-branch build workflow, simplifies the Jenkins file and made it neater and easier to read by extracting all workflow logic each as a function into an external groovy file named script.groovy. This file was then imported into Jenkins file where steps are replaced with function calls. This also explores using available jenkins environment variables like BRANCH_NAME and conditional statements to decide the build stages performed or skipped for each branch in the concerned git repo.
- jenkins-shared-lib: configures JenkinsFile to call some build steps from central repo ([shared library](https://github.com/OyedunOye/jenkins-shared-library.git)). This explores set-up that promote reusability for common steps shared among different pipelines across different projects.
- jenkins-shared-lib-with-parameters: Code reusability, the main aim of shared lib, is defeated if variables are hard-coded into the shared library. It removes flexibility and reusability with other projects. This branch called functions in the shared library with arguments provided to suit this project. This can also be replicated in other projects with variable suitable for them as well.
- webhook-test-and-dynamic-inc-image-version: This eliminates manual triggering of workflow and enables us to set conditions that once met, Jenkins will automatically run the build steps. Also, configured a step to dynamically increase JAR artifact and docker image version number. Finally, an additional step was configured to commit updated pom.xml reflecting incremented version of JAR artifact to github directly from jenkins upon successful completion of all the preceeding build steps.
## Screenshots

![Jenkins Job List](https://res.cloudinary.com/dpav6x91z/image/upload/v1787519429/my-jenkins-job-list_suwj8i.png)
![Jenkins Multibranch Pipeline](https://res.cloudinary.com/dpav6x91z/image/upload/v1787519426/my-multijob-pipeline_whpeea.png)
![Configured Jenkins Credentials](https://res.cloudinary.com/dpav6x91z/image/upload/v1787519422/configured-jenkins-credentials_bitdpf.png)
