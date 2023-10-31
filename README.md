# Dot.ai-project
Dot.ai take home test project

--WORKFLOW OF THE PROJECT ON HOW TO DEPLOY THIS PROJECT USING DOCKER AND KUBERNETES --

1. to have access to this project on your local system, you will have to fork this repo and you can clone it to your PC to enable you access the code on your local system
2. Install Docker, Git, Kubernetes and Maven to your system/PC
3. after cloning the repo, go into the dot_Ai microservice/project folder on your terminal, By doing 'ls', you can see the files/folders inside the project folder.
4. we are going to build our microservice to get the 'jar' file using Maven to build the application by using the 'mvn clean install' command
5. After the build success, you can see the target folder in the project folder. In that folder, we have our jar file.
6. we can build the image of our dot_ai.jar jar file from the dockerfile by using docker build command.
7. we can use the command 'ls' to check the Dockerfile inside the project
8. You have to push the image of our project to the docker account earlier created and Now, login to your docker account by 'docker login' command, It will ask for the username or password.
9. After the login succeeded, you can push your images to the dockerhub. You have to give the username of your docker account because if you don't give the username, you won't able to push the images because it has to understand from where the images are being pushed (from which account).
10. Now, you go to dockerhub, and you can see your images of our project in your docker repository.
11. Once you push your image, you can use command 'docker logout' from dockerhub login from your system.
12. Now if I go to the application folder, there is a folder 'Kubernetes'. It contains the YAML files for our microservice.
13. In the YAML file, we use "Deployment" and "Service" objects. Using a deployment allows you to easily keep a group of identical pods running with a common configuration. Once we have defined and deployed our deployment, Kubernetes will then work to make sure all pods managed by the deployment meet whatever requirements we have set.
14. Moreso, When using a Kubernetes service, each pod is assigned an IP address. As this address may not be directly knowable, the service provides accessibility, then automatically connects the correct pod.
15. When a service is created it publishes its own virtual address as either an environment variable to every pod or, if our cluster is using 'coredns', as a DNS entry any pod can attempt to reach. In the event of any changes to the number of available pods the service will be updated and begin directing traffic accordingly with no manual action required.
16. Now, We are going to apply the YAML files using 'kubectl apply' command.
17. Now, our Deployment and Service objects are created
18. We can see our earlier deployed miceroservice/s by using 'Kubectl get deployment' and 'kubectl get svc' commands respectively.
19. To access our microservices from the web, we will do 'minikube service dot_ai', this will give the URL to access the application
20. You can now go to your web browser and put the IP address shown after the earlier command to access the microservice from the web browser
