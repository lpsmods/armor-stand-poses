# Changelog

## [mod-1.4.0] (1.21.3) - 4/8/2025

### General

- Updated for Minecraft 1.21.3

## [forge-1.3.0] (1.21.1) - 4/7/2025

### General

- Initial release.

## [neoforge-1.3.0] (1.21.1) - 4/7/2025

### General

- Initial release.

## [fabric-1.3.0] (1.21.1) - 4/7/2025

### General

- Data-Driven pose fields now use snake_case instead of CamelCase
- Changed "poses" folder to "pose" in data packs.
- Poses now have fallback text if a translation can't be found.
- Arms can now be hidden (using /data or from other mods)
- Default armor stand pose (when placed at date/time).
  - 🎃October will use zombie pose.
  - 🪖Memorial and Veterans Day will use salute pose.
- This update adds support for [Armor Poser](https://www.curseforge.com/minecraft/mc-mods/armor-poser)!

## [fabric-1.2.0] - 7/24/2024

### General

- Updated for Minecraft 1.21
- Now uses java 21
- Added ChangePoseCallback for developers.
- You can no longer change the pose of marker armor stands.
- Invisible or marker armor stands will not change with Redstone.

## [fabric-1.1.0] - 4/21/2024

### General

- Poses now use codecs.

## [fabric-1.0.1] - 1/10/2024

### Fixes

- #12 Doesn't work on servers.

## [fabric-1.0.0] - 10/4/2023

- Initial Release

## [datapack-1.5.0] - 7/18/2024

### General

- Updated for 1.21
- Improved performance of armor stands
- Added API to interact with the armor stand poses

## [datapack-1.4.0] - 6/22/2023

### General

- Updated for 1.20
- Shift clicking on the base of the armor stand should now target the correct armor stand.
- Added API to add your own poses to armor stands.
- Changing the pose via Shift-Click will now display the name of the pose above the hotbar.

## [datapack-1.3.0] - 3/16/2023

### General

- Updated for 1.19.4 !!! Incompatible with older version!
- Improved the Right Click hitbox. Shift Right-Click at the base of the armor stand to change the pose.

## [datapack-1.2.0] - 2/28/2022

### General

- Updated for 1.18.1
- Added datapack register common for all LPS datapacks.
- Added update checker URL to easily check for updates.
- Armor stands that are invisable will not have pose functions.
- You can now Shift + Right click with a open hand on the base of the armor stand to change the pose.

## [datapack-1.1.0] - 12/12/2020

- Redstone power 14 and 15 will reset the armor stand
- Updating and working on 1.16.4
- Fixed an instance of armor stand not changing pose when first Redstone in front of armor stand has been redirected. Causes all poses not to work.

## [datapack-0.1] - 5/8/2020

- Initial Release
