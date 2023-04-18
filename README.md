# OSGi

This sample was inspired by https://www.baeldung.com/osgi. It uses latest versions of karaf, Java 17 and a cleaner
separation between API and implementation.

## Modules

There are 3 different modules: An API, a service implementing that API and a client for the API.

### OSGi API

This project defines a simple Greeter API.

### OSGi Service

This implements the Greeter API and provides it as a bundle.

### OSGi Client

This client gets notified when the service gets registered and calls its greet method.

## Usage

### Prepare Karaf

Go to the [download page](https://karaf.apache.org/download) of Apache Karaf and get the latest OSGi Runtime version. At
the time of writing this it was [4.4.3](https://www.apache.org/dyn/closer.lua/karaf/4.4.3/apache-karaf-4.4.3.tar.gz).

Unpack the archive and open a terminal in the bin folder of the extracted folder. The start Karaf:

```shell
cd $KARAF_HOME
./bin/karaf start
```

This will prompt with the Karaf CLI.

### Build the Apps

Then install the apps into your local maven repo:

```shell
./mvnw clean install
```

### Install the bundles

```
karaf@root()> bundle:install mvn:dev.mbo/osgi-service/1.0-SNAPSHOT
Bundle ID: 70
karaf@root()> bundle:install mvn:dev.mbo/osgi-client/1.0-SNAPSHOT
Bundle ID: 71
```

The `install` with `mvn:` prefix looks up the given artifact in maven and installs it. The returned IDs identify the
installation and vary depending on your machine. So make Sure to replace the numbers (64,65) with the results from your
commands.

### Start the bundles

You start the bundles with the use of their `Bundle ID`:

```
karaf@root()> start 71
client: started
karaf@root()> start 70
service: start
client::notification: service started
Hello mbo
```

First the client is started. It gets notified when the service is available as well and calls the Greeting service.

### Stop/Uninstall bunldes

#### Stop

Parallel to starting you can stop a bundle:

```
karaf@root()> stop 70
service: stop
client::notification: service stopped
karaf@root()> stop 71
client: stopped
```

You can easily start them when stopped.

#### Uninstall

Removing a bundle is equivalent:

```
karaf@root()> uninstall 70
karaf@root()> uninstall 71
```

You don't need to stop a bundle before uninstalling it.

NOTE: You can also give multiple `Bundle ID`s at once like `uninstall 70 71`.