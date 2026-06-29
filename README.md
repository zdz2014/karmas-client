# Karmas Client

**A lightweight cosmetic Fabric mod for Minecraft Java 1.21.1**

> Zero gameplay advantages. Zero cheats. Pure visual polish.

---

## Features

| Feature | Description |
|---|---|
| **Tab List Logo** | A clean "Ka" logotype in brand colour appears centred in the tab-list header every time you press TAB. |
| **GUI Title** | "Karmas Client" is rendered above every vanilla inventory/crafting screen in `#B0C4DE`. |
| **Nametag Prefix** | Every visible player gets a `Ka` prefix (brand colour) before their username. The original name is untouched. Works in third person and spectator mode. |

## What it does NOT do

- No cheats, no Xray, no auto-anything
- No zoom, no fullbright, no coordinates HUD
- No FPS counter, no ping display, no CPS counter
- No commands
- No configuration screen
- No gameplay modifications whatsoever

---

## Build

### Prerequisites
- JDK 21
- Internet connection (Gradle downloads Fabric mappings on first run)

### Steps

```bash
# Clone / extract the project
cd karmas-client

# Generate the Gradle wrapper (run once)
gradle wrapper --gradle-version 8.8

# Build the mod
./gradlew build
```

The output JAR will be at:
```
build/libs/karmas-client-1.0.0.jar
```

Drop it into your `.minecraft/mods/` folder alongside **Fabric Loader** and **Fabric API**.

---

## Compatibility

| Dependency | Version |
|---|---|
| Minecraft | 1.21.1 |
| Fabric Loader | ≥ 0.16.5 |
| Fabric API | 0.102.0+1.21.1 |
| Java | 21 |

---

## Colour Reference

All custom text uses **`#B0C4DE`** (Light Steel Blue / CSS "lightsteelblue").

---

## Project Structure

```
src/main/java/me/karmas/client/
├── KarmasClient.java                  — mod entrypoint & constants
├── render/
│   └── LogoRenderer.java              — "Ka" logo drawing utility
└── mixin/
    ├── PlayerListHudMixin.java        — tab list logo injection
    ├── HandledScreenMixin.java        — GUI title injection
    └── PlayerEntityRendererMixin.java — nametag prefix injection
```

---

## License

MIT — do whatever you like with this.
