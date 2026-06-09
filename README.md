# WorldManager

**The most powerful multi-world management system for Nukkit / Lumi servers.** Create, delete, copy, teleport, and configure worlds — all from in-game commands and an intuitive form UI. Extend with official addons.

> **Version:** 1.2.3  
> **Author:** Natro  
> **API:** 1.0.11–1.0.15

---

## Features

- **World creation** — Generate worlds with any generator and seed.
- **World management** — Load, unload, reload, rename, copy, duplicate, and delete worlds.
- **Teleportation** — Teleport between worlds with commands or a UI.
- **World settings** — Per-world game mode, player limit, protection, gamerules, and spawn location.
- **World regeneration** — Regenerate entire worlds or change their seed.
- **Biome editing** — Change the biome of all loaded chunks in a world.
- **Entity clearing** — Kill all entities in a world (clearlag).
- **Addon system** — Extend functionality with official addons (signs, whitelist, chat, join messages, The End, etc.).
- **Default world switching** — Change the server's default world at runtime.

---

## Installation

1. Place `WorldManager.jar` into your server's `plugins/` folder.
2. Restart the server.
3. (Optional) Install addons from the in-game addon UI (`/wm addons`).

---

## Commands

Use any of these aliases: `/worldmanager`, `/wm`, `/mw`, `/levelmanager`, `/lm`, or `/mv`.

| Command | Description |
|---|---|
| `/wm teleport <world> [player]` | Teleport to a world |
| `/wm generate <world> [generator] [seed]` | Create a new world |
| `/wm delete <world>` | Delete a world |
| `/wm list` | List all worlds (loaded and unloaded) |
| `/wm load <world>` | Load a world |
| `/wm unload <world>` | Unload a world |
| `/wm reload [world]` | Reload a world |
| `/wm rename <world> <newName>` | Rename a world |
| `/wm copy <world> [copyName]` | Copy a world |
| `/wm setspawn` | Set the world spawn to your position |
| `/wm settings [world]` | Open world settings UI |
| `/wm regenerate [world]` | Regenerate a world |
| `/wm setseed <world> <seed>` | Change a world's seed |
| `/wm info [world]` | Show world information |
| `/wm setbiome <biome>` | Change biome in loaded chunks |
| `/wm gamerule [world]` | Open gamerule management UI |
| `/wm killentitys <world>` | Kill all entities in a world |
| `/wm spawn` | Teleport to world spawn |
| `/wm addons` | Open addon management UI |
| `/wm default [world]` | Show or change the default world |
| `/wm version` | Show plugin version |

Parameters in `[]` are optional.

---

## Permissions

| Permission | Description |
|---|---|
| `worldmanager.admin` | Access all WorldManager features |
| `worldmanager.<subcommand>` | Access a specific subcommand (e.g., `worldmanager.generate`) |
| `worldmanager.teleport.<world>` | Teleport to a specific world |
| `worldmanager.deny.<world>` | Prevent entry to a specific world |
| `worldmanager.teleportui` | Open the teleport UI |
| `worldmanager.generationui` | Open the generation UI |
| `worldmanager.enterfullworlds` | Bypass per-world player limits |

---

## Addons

Install from the in-game addon UI at `/wm addons`:

| Addon | Author |
|---|---|
| WorldSigns | Buddelbubi |
| WorldWhitelist | Buddelbubi |
| WorldPlayerLimits | Buddelbubi |
| WorldChat | Buddelbubi |
| WorldJoinMessage | Buddelbubi |
| TheEnd | wode |
| BetterVanillaGenerator | wode |
| PerWorldInventory | lukeeey |
| MultiNether | PetteriM1 |
| FastAsyncWorldEdit | IntellectualSites |

---

## Building

```bash
mvn clean package
```

---

## License

MIT License — see [LICENSE](LICENSE).
