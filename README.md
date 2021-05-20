# Azure Digital Twins connector
The aim of this project is to :
 - list files from Azure Storage
 - Download Files

## Properties to overwrite :
Here is the list of properties that should be changed (in ```META-INF/microprofile-config.properties``` file):
- **azure.storage.connection.string**
- **azure.storage.container.blob.prefix**
- **csm.fetch.absolute.path**

If you want to overwrite these properties, you can write your own property values in the ```META-INF/microprofile-config.properties``` file, or set a property's system, or an environment variable named :
- **AZURE_STORAGE_CONNECTION_STRING** : the Azure Storage Connection String (can be found under the Azure Storage overview screen)
- **AZURE_STORAGE_CONTAINER_BLOB_PREFIX** : The path to the files to read in the form: container/path. Every files under this path will be fetch
- **CSM_FETCH_ABSOLUTE_PATH** : the absolute path to export all csv files (don't forget the / at the end)

## Log level
Default log level defined is "info".
We use the logging API [log4j2](https://logging.apache.org/log4j/2.x/manual/index.html).
You can change the log threshold by setting an environment variable named: **LOG_THRESHOLD_LEVEL**.
Log levels used for identifying the severity of an event. Log levels are organized from most specific to least:
- OFF (most specific, no logging)
- FATAL (most specific, little data)
- ERROR
- WARN
- INFO
- DEBUG
- TRACE (least specific, a lot of data)
- ALL (least specific, all data)


##Change the default container registry

Modify the pom.xml or set options directly in jib:build command
```
See [Jib project Configuration]("https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin#configuration") to set correctly your container registry (GCR, ECR, ACR, Docker Hub Registry)

Build your container image with:

```shell
mvn compile jib:build -Djib.allowInsecureRegistries=true
```

Subsequent builds are much faster than the initial build.

##Build to GitHub Registry
This project defines a GitHub workflow which build to ghcr.io. Example:
``` shell
mvn compile jib:build \
  -Djib.to.image="ghcr.io/cosmo-tech/azure-storage-simulator-connector:latest" \
  -Djib.to.auth.username="${{ github.actor }}" \
  -Djib.to.auth.password="${GITHUB_TOKEN}"
```

#### Build to Docker daemon

Jib can also build your image directly to a Docker daemon. This uses the `docker` command line tool and requires that you have `docker` available on your `PATH`.

```shell
mvn compile jib:dockerBuild
```

For more information, see [Jib project Build]("https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin#build-your-image")

### Watch build
To watch for file modifications and do continuous build run
```shell
mvn fizzed-watcher:run
```

#### How to run your image locally 

```
docker run \ 
-v <<local_export_dir_path>>:/tmp \
-e CSM_FETCH_ABSOLUTE_PATH=/tmp/ \
-e AZURE_STORAGE_CONNECTION_STRING= XXXXXXXXX \
-e AZURE_STORAGE_CONTAINER_BLOB_PREFIX= container/path \
<your_container_registry>/azure-storage-simulator-connector
```

You can find all export files under the directory "local_export_dir_path" specified above


## How-to

```
    <dependency>
      <groupId>com.github.Cosmo-Tech</groupId>
      <artifactId>azure-storage-simulator-connector</artifactId>
      <version>VERSION</version>
    </dependency>
```
