Jenkins Master setup
====================

To test the MPL & examples you need a properly configured Jenkins Master, here is described how to run it using docker.

Basic installation
------------------
1. You need installed docker on your system (Mac & Linux should be fine, but Win users will suffer as usual).
2. Run the jenkins master using shell command (Linux & Mac):
    ```
    docker run --name jenkins -it --rm -p 8080:8080 -p 50000:50000 -v /var/run/docker.sock:/var/run/docker.sock -v ${HOME}/.jenkins_docker:/var/jenkins_home -v ${HOME}:/home/user jenkins/jenkins:lts-alpine
    ```
3. Jenkins master will be started and will show you a message about initial setup required with temp password for admin user, copy it.
4. Go to [http://localhost:8080/] and enter admin password.
5. Choose "Install suggested plugins" - it will take some time.
7. Now, when plugins are installed, continue as admin, leave default url and continue to the default dashboard.

Dynamic agents
--------------
I do recommend to use dynamic docker agents even if it's a local jenkins for testing to make sure you creating really portable pipelines. 

1. Allow jenkins to use the docker daemon to create agents, execute the next command in terminal:
    ```
    docker exec -it -u 0 jenkins chmod o+rw /var/run/docker.sock
    ```
2. We need an additional plugin to manage docker jenkins agents: Go to "Manage Jenkins" --> "Manage Plugins" --> tab "Available", search for "Docker" plugin and install it without restart.
3. Now we need to configure the plugin:
    1. Go to "Manage Jenkins" --> "Configure System", scroll all the way down, click on "Cloud" button and select "Docker" - it will add new cloud provider.
    2. Let's configure the added cloud: click on "Docker Cloud Details" button and set **Docker Host URI** to "unix:///var/run/docker.sock".
    3. Now click on "Test Connection" button - it should show the docker daemon version and API version.
    4. Set the "Enabled" flag.
4. Now we need to add a template to the Docker provider:
    1. Click on "Docker Agent Templates" button
    2. Click on "Add Docker Template" button
    3. Set the values:
        * **Labels:** "LS"
        * **Enabled:** True
        * **Name:** "jnlp"
        * **Docker Image:** "jenkins/jnlp-slave:alpine"
        * **Remote File System Root:** /home/jenkins
    4. Click on "Container Settings" button and setup volumes: I'm usually mount my Work directory to have access to my local git repositories:
        ```
        <HOME dir on host system>/Work:/home/user/Work
        ```

MPL library setup
-----------------
To run demos we need a configured MPL jenkins shared library.

1. Go to "Manage Jenkins" --> "Configure System" and find "Global Pipeline Libraries" section.
2. Click "Add" button to add a new one.
3. Set the values:
    * **Name:** "mpl"
    * **Default Version:** "master"
4. Select "Modern SCM" in "Retrieval method" section
5. And select "Source Code Management":
    * If you have local clone of MPL - use "Git" and set "Project Repository", for example "/home/user/Work/git/mpl"
    * If you want to use github - select "GitHub" and configure it accordingly.