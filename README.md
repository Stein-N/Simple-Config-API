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

### Planned
- support more Datatypes

### For Developers

#### Latest Version: 0.3.2
#### Available for:
  - Fabric 1.20 -> latest
  - Forge 1.20 -> latest
  - NeoForge 1.20.1 -> latest

### Note: NeoForge will have full support after 1.21 release, because of the changes of some implementation or tags in the mods.toml File.

<details>
<summary>Adding Simple Config API to your project</summary>

````groovy
    repositories {
        maven {
          name = "xStopho Mods"
          url = "https://raw.githubusercontent.com/Stein-N/resources/main/maven"
        }
    }
````

````groovy
    dependencies {
        implementation "xstopho.simpleconfigapi:simpleconfigapi-common:<version>"
        implementation "xstopho.simpleconfigapi:simpleconfigapi-fabric:<version>"
        implementation "xstopho.simpleconfigapi:simpleconfigapi-forge:<version>"
        implementation "xstopho.simpleconfigapi:simpleconfigapi-neoforge:<version>"
    }
````
</details>
