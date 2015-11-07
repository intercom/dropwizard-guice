# dropwizard-guice
 

## Description

Use [Guice](https://github.com/google/guice) with [Dropwizard](https://github.com/dropwizard/dropwizard) by extending a `GuiceApplication` class. Here's an example -


```java
public class MyApp extends GuiceApplication<MyAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new MyApp().run(args);
    }

    public MyApp(String... packages) {
        super(packages);
    }

    @Override
    protected List<Module> addModules(MyAppConfiguration configuration) {
        return Lists.newArrayList(new MyAppModule());
    }
}
```


## Features

- Guice injection with (incomplete) Jersey2 support.
- Automatic class configuration.
- MetricRegistry access.
- Named string bindings from maps or JSON files.

### Guice injection

`GuiceApplication` will accept a list of Guice modules to be injected via the `addModules` method
 with no need to setup an `Injector`. Guice wiring is done at run time to make the app's  configuration available to modules, for example -
  
```java
    @Override
    protected List<Module> addModules(MyConfiguration configuration) {
        return Lists.newArrayList(new MyModule(configuration));
    }
``` 
 
Guice integration with Jersey is incomplete. For example, method based access to classes like 
`Request` works - 

```java
@Path("/finder")
public class MyResource {

    @Inject
    private TheDependency theDependency;

    @GET
    public Response ok(@Context Request request) {
        return Response.ok("ok").build();
    }
}
```


But field level injection of JAX-RS does not -
 
```java
@Path("/finder")
public class MyResource {
    
    // this causes a provisioning error
    @Inject
    private Request request;

    @GET
    public Response ok() {
        return Response.ok("ok").build();
    }
}
```


There's a bit more said about this limitation in the [background](#background) section.

 
### Automatic class configuration

The classpath is scanned for Resource, Provider, Task, Healthcheck and Managed objects if a list of
 packages names to scan is supplied -
 
```java 
new MyApp("org.example.myapp.foo", "com.example.theirs.bar").run(args);
```

If you prefer to wire up the environment by hand, you can start your app with no package names to
 disable scanning and configure the environment via the `applicationAtRun` method.
  
```java   
new MyApp().run(args);
```

### MetricRegistry access

`MetricRegistry` is available for injection -

```java
public class MyService {

    private final Meter meter;

    @Inject
    public MyService(MetricRegistry metrics) {
        meter = metrics.meter(MetricRegistry.name(MyService.class,"meter"));
    }
}
```

### Named string bindings

The `NamedMapBinderModule` binds `Named` strings from one or maps. For example if you had a list of 
strings in configuration.yaml like this -

```yaml
namedStrings: 
  - foo: "Foo"
  - bar: "Bar"
```

and a `MyConfiguration` class that exposed them as a `Map<String, String>` -


```java
public class MyConfiguration extends Configuration {

    @JsonProperty
    private Map<String, String> namedStrings;
    
    public Map<String, String> getNamedStrings() {
        return namedStrings;
    }

    public void setNamedStrings(Map<String, String> namedStrings) {
        this.namedStrings = namedStrings;
    }
}
```

Then the map can be be suppled to `NamedMapBinderModule` - 

```java
    @Override
    protected List<Module> addModules(MyConfiguration configuration) {
        return Lists.newArrayList( 
            new NamedMapBinderModule(configuration.getNamedStrings())
        );
    }
```

And the keys from the configuration file become available to inject -

```java
public class MyService {

    private final String foo;

    @Inject
    public MyService(@Named("foo") String foo) {
        this.foo = foo;
    }
}
```

### Named string JSON bindings

The `NamedJsonBinderModule` binds `Named` strings from one or more JSON files. For example if you 
had a list of strings in `configuration.json` located at `"conf/configuration.json"` in your 
classpath, like this -
 
```json
  {
    "a1": "a"
    ,"b1": "b"
  }
```

Then the file can be configured by prefixing it with `classpath:` - 

```java
@Override
protected List<Module> addModules(MyConfiguration configuration) {
    return Lists.newArrayList( 
        new NamedJsonBinderModule("classpath:conf/configuration.json")
    );
}
```

And the keys from the configuration file become available to inject -

```java
public class MyService {

    private final String a1;
    private final String b1;

    @Inject
    public MyService(@Named("a1") String a1, @Named("b1") String b1) {
        this.a1 = a1;
        this.b1 = b1;
    }
}
```

You can also supply absolute file paths to `NamedJsonBinderModule` - 

```java
@Override
protected List<Module> addModules(MyConfiguration configuration) {
    return Lists.newArrayList( 
        new NamedJsonBinderModule("/etc/my-service/conf/configuration.json")
    );
}
```


## Background

Dropwizard-guice derives from the excellent <a href="https://github
.com/jaredstehler/dropwizard-guice">Fiestacabin dropwizard-guice</a> and  <a 
href="dropwizard-guice">HubSpot dropwizard-guice</a> projects, to focus on three things - 
  
- Optional package scanning for Dropwizard configurable objects. Ideally to get going you declare 
some modules to wire up and don't need to set up the environment or create the Injector. On the 
other hand if you want to configure the environment manually, or complete control of Guice, it  
should be easy to avoid automatic scanning and configure Guice directly.

- Allow modules to access Dropwizard configuration. Wiring up Guice at runtime lets modules 
access configuration objects and minimizes the need to create Providers to make configuration 
data available.

- Obtain some level of HK2 injection support for Jersey2. Method level `@Context` injections are 
available but field based injection is not because of the complexity of bridging Jersey with 
Guice in recent 0.8.x versions of Dropwizard. 

A full bridge of HK2 with Guice looks challenging and is possibly a maintenance headache, as there 
is potential to break each time Dropwizard upgrades its Jersey version. HK2/Jersey2 doesn't have 
a structured way to integrate with Guice the  way Jersey 1 did (see [JERSEY-1950](https://java.net/jira/browse/JERSEY-1950)). The best attempt to date 
seems to be [jersey2-guice](https://github.com/Squarespace/jersey2-guice) from the team at Squarespace.

 
  

  
 
