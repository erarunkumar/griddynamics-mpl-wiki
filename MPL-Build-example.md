MPL Build example
=================

Make sure you setup the Jenkins Master: [Jenkins Master setup](Jenkins-Master-setup)

Now you can run build of the MPL using MPL itself:

1. Create new pipeline job with name "mpl-master"
2. In the job configuration scroll down to "Pipeline" section and set **Definition** to "Pipeline script from SCM".
3. Select "SCM" value "Git" and set the same path as for the shared library, for example: "/home/user/Work/git/mpl"
4. Now you can save the project and start the build, it will pull the jenkins agent image, will initialize properly but fail with message "No tool named Maven 3 found".
5. You need to add new tool - go to "Manage Jenkins" --> "Global Tool Configuration" and find "Maven" section.
6. Click on "Add Maven" button, set **Name** "Maven 3" and click "Save".
7. Start the new build of the project - and you will see that tool will be downloaded from the Apache server and unpacked properly. After that build will be started and all the tests will be executed.