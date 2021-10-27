<div id="top"></div>

# Builders Coffee API

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#redisson">Redisson</a>
      <ul>
        <li><a href="#redisson-connection">Connection</a></li>
        <li><a href="#redisson-topics">Creating topics</a></li>
        <li><a href="#redisson-listeners">Listeners</a></li>
        <li><a href="#redisson-packets">Packets</a></li>
      </ul>
    </li>
    <li>
      <a href="#events">Events</a>
      <ul>
        <li><a href="#events-event">Create an event</a></li>
        <li><a href="#events-callevent">Call an event</a></li>
        <li><a href="#events-listeners">Listeners</a></li>
      </ul>
    </li>
    <li>
      <a href="#configuration">Configuration files (yml)</a>
      <ul>
        <li><a href="#configuration-create">Create a config file</a></li>
        <li><a href="#configuration-read-or-create">Read or create config</a></li>
        <li><a href="#configuration-write-or-overwrite">Write or overwrite configuration</a></li>
      </ul>
    </li>
  </ol>
</details>

<div id="redisson"></div>

## Redisson

You need to know that the most important actions are in the [Redis class](https://github.com/builderscoffee/builderscoffeeapi/blob/main/src/main/java/eu/builderscoffee/api/common/redisson/Redis.java)


<div id="redisson-connection"></div>

### Connection
First of all you need to initialize a connection to the Redisson Server.

```java
RedisCredentials credentials = new RedisCredentials()
        .setClientName("Client name")
        .setIp("192.168.1.1")
        .setPassword("secretpassword")
        .setPort(2657);

Redis.Initialize("Instance name", credentials, 0, 0);
```

<div id="redisson-topics"></div>

### Creating topics

```java
RedisTopic redisTopic = new RedisTopic("name", "description");
```

<div id="redisson-listeners"></div>

### Listeners

```java
Listener listener = new Listener();

// Add listeners
Redis.subscribe(redisTopic, listener);

// Remove listeners
Redis.unsubscribe(redisTopic);
```

Listener.class
```java
public class Listener implements PacketListener {
    
    // This will listen to any packet
    @ProcessPacket
    public void onAnyNameYouWantForTheMethod(Packet packet) {
        // Do something here
    }
    
    // This will listen to any SpecificPacket and sub classes
    @ProcessPacket
    public void onAnyNameYouWantForTheMethod(SpecificPacket packet) {
        // Do something here
    }
}
```

<div id="redisson-packets"></div>

### Packets

There are 3 types of packets:
* ActionPackets: Can only send information
* RequestPackets: Request something and do an action when responded
* ResponsePackets: Responses to a request

SpecificActionPacket:
```java
public class SpecificActionPacket extends ActionPacket {
    // Some variables if wanted
}
```

SpecificRequestPacket:
```java
public class SpecificRequestPacket extends RequestPacket<SpecificResponsePacket> {
    // Some variables if wanted
}
```

SpecificResponsePacket:
```java
public class SpecificResponsePacket extends ResponsePacket {

    public PackagesResponsePacket(String packetId) {
        super(packetId);
    }

    public PackagesResponsePacket(RequestPacket requestPacket) {
        super(requestPacket);
    }

    // Some variables if wanted
}
```

How to send packets:
```java
// Send actions packets
SpecificActionPacket actionPacket = new SpecificActionPacket();
Redis.publish(redisTopic, actionPacket);

// Send request packets
SpecificRequestPacket requestPacket = new SpecificRequestPacket();
// Do action when it has a response
requestPacket.onResponse = response -> {
    // Get data from the response
};
Redis.publish(redisTopic, requestPacket);
        
// Send response packets
SpecificResponsePacket responsePacket = new SpecificResponsePacket(requestPacket);
Redis.publish(redisTopic, responsePacket);
```


<p align="right">(<a href="#top">back to top</a>)</p>

<div id="events"></div>

## Events

<div id="events-event"></div>

### Create an event

```java
public class TestEvent extends Event {
    // Some variables if wanted
}
```

```java
TestEvent event = new TestEvent();
```

<div id="events-callevent"></div>

### Call an Event

```java
EventHandler.getInstance().callEvent(event);
```

<div id="events-listeners"></div>

### Listeners

```java
Listener listener = new Listener();

// Add listeners
EventHandler.getInstance().addListener(listener);

// remove listeners
EventHandler.getInstance().removeListener(listener);
```

Listener.class
```java
public static class TestListener implements EventListener{
    // This will listen to any event
    @ProcessPacket
    public void onAnyNameYouWantForTheMethod(Event event) {
        // Do something here
    }

    // This will listen to any TestEvent and sub classes
    @ProcessPacket
    public void onAnyNameYouWantForTheMethod(TestEvent event) {
        // Do something here
    }
}
```
<p align="right">(<a href="#top">back to top</a>)</p>

<div id="configuration"></div>

## Configuration files (yml)

<div id="configuration-create"></div>

### Create a config file

```java
import eu.builderscoffee.api.common.configuration.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
public class ExampleConfig {
    String mySecretPassword = "default value";
}

@Data
@Configuration(value = "mySpecificConfigFileName")
public class ExampleConfig {
    // some variables
}
```

<div id="configuration-read-or-create"></div>

### Read or create configuration

#### Without enums

```java
// Read sigle config
ExampleConfig config = Configuration.readOrCreateConfiguration("pluginFolderName", ExampleConfig.class);
```

#### With enums
```java
public enum MyEnum {
    A_VALUE,
    OTHER_VALUE
}
```

```java
// Read sigle config from enum
ExampleConfig config = Configuration.readOrCreateConfiguration("pluginFolderName", ExampleConfig.class, MyEnum.A_VALUE);

// Read multiple configs from enum
Map<MyEnum, ExampleConfig> configs = Configuration.readOrCreateConfiguration("pluginFolderName", ExampleConfig.class, MyEnum.class);
```

<div id="configuration-write-or-overwrite"></div>

### Write or overwrite configuration

```java
// Write single config
ExampleConfig config = ...;
Configuration.writeConfiguration("pluginFolderName", config);

// Write single configs specified with enum
Map<MyEnum, ExampleConfig> configs = ...;
Configuration.writeConfiguration("pluginFolderName", configs, MyEnum.A_VALUE);

// Write multiple configs
Map<MyEnum, ExampleConfig> configs = ...;
Configuration.writeConfiguration("pluginFolderName", configs);
```

<p align="right">(<a href="#top">back to top</a>)</p>