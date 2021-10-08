# wso2-hazelcast-health-check

Health checker for monitoring the hazelcast cluster status

## Build

Clone the repository and in the directory where the pom file is located, issue the following command to build the project.
```
mvn clean install
```

## Deploy

After successfully building the project, the resulting jar file can be retrieved from the target directory. (the already built jar is included in the release section) copy the resulting jar to the <IS_HOME>/repository/components/dropins/ directory.

The health check API and this health checker can be enabled by adding the following configurations in the deployment.toml.

```
[carbon_health_check]
enable= true

[[health_checker]]
name = "ComponentHealthChecker"
order="87"
```
## Testing

Start the server and send a GET request to the health check API

Sample curl request :

```
curl -k -v https://localhost:9443/api/health-check/v1.0/health
```

Response :
```
{
   "health":[
      {
         "key":"jdbc/WSO2CarbonDB.active.connection.count",
         "value":"1"
      },
      {
         "key":"component.status",
         "value":"ACTIVE"
      },
      {
         "key":"jdbc/WSO2CarbonDB.connectivity.time.ms",
         "value":"34"
      }
   ]
}
```
Incase of a component failure, the health api would return a HTTP Status Code 503 with the following error.

```
{
   "errors":[
      {
         "code":"500",
         "message":"Components have not started, A server Restart and DB check required.",
         "description":null
      }
   ]
}
```
