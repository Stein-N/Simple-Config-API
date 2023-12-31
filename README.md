# Simple Config API

A Simple Config API for Fabric, Quilt and Neo/-Forge which is based on the [NightConfig Library](https://github.com/TheElectronWill/night-config).
It is downloadable on [Curseforge](https://www.curseforge.com/minecraft/mc-mods/simple-config-api) and [Modrinth](https://modrinth.com/mod/simple-config-api).

### Features

- Save Integer, Double String and Boolean Values
  - save them in Categories
  - set comments for Categories or Values
  - Values can be defined in range
- Designed for Multi-Loader Projects, but can also be used for single Loader Projects.
- Auto Correction of invalid or corrupt Config Entries

### Planned Features
- FileWatcher to dynamically load changed Values 
- Config Sync with Server

## For Developers

#### Latest Version: 0.2.4

### Adding Simple Config API to your project
<details>

##### Main Repository
````groovy
    repositories {
        maven {
          name = "xStopho Maven"
          url = "https://raw.githubusercontent.com/Stein-N/resources/main/maven"
        }
    }
````
<details>
<summary>Common</summary>

````groovy
    dependencies {
        implementation 'xstopho.simpleconfigapi:simpleconfigapi-common:<mod_version>'
    }
````

</details>
<details>
<summary>Fabric</summary>

````groovy
    dependencies {
        implementation 'xstopho.simpleconfigapi:simpleconfigapi-fabric:<mod_version>'
    }
````

</details>
<details>
<summary>Forge</summary>

````groovy
    dependencies {
        implementation 'xstopho.simpleconfigapi:simpleconfigapi-forge:<mod_version>'
    }
````

</details>
<details>
<summary>Neoforge</summary>

````groovy
    dependencies {
        implementation 'xstopho.simpleconfigapi:simpleconfigapi-neoforge:<mod_version>'
    }
````

</details>
</details>

### Create a Config
<details>

Before defining Config Values you have to create a SimpleConfigBuilder.
````java
    SimpleConfigBuilder builder = new SimpleConfigBuilder();
````
All Config Values are saved as a Supplier.
````java
    Supplier<Integer> value_0 = builder.define(<key>, <IntegerValue>);
    Supplier<Double> value_1 = builder.define(<key>, <DoubleValue>);
    Supplier<String> value_2 = builder.define(<key>, <StringValue>);
    Supplier<Boolean> value_3 = builder.define(<key>, <BooleanValue>);
````
Registering / Creating the Config file
````java
    SimpleConfigRegitry.INSTANCE.register(<mod_id>, <SimpleConfigBuilder>);
    SimpleConfigRegitry.INSTANCE.register(<mod_id>, <fileName>, <SimpleConfigBuilder>);
````
For more information about defining Config Values or working with the Config in general watch the [Wiki](https://github.com/Stein-N/Simple-Config-API/wiki/How-to-use-the-API)
</details>