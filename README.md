The appication consists of two API's CustomerAPI and RemoteAPI
CustomerAPI is a SpringBoot Application to to view / Add/ Delete and Update customer information
We have to login to the application to view customer Informations using Login Controller
The application cummunicate with RestController present in RemoteAPI to update customerinformation in CustomerAPI

Customer API uses port : 8080
RemoteAPI uses port : 9080

Run as java application for both API's

We can also run these apps by creating a eurekaserver and enabling @EnableDiscoveryClient in both API's for managing multiple applications at the same time
