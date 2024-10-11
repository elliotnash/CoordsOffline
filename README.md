<!-- Title + Logo -->
<br />
<div align="center">

  # CoordsOffline

  A simple Spigot plugin to get an offline player's coordinates.

  [![Latest Release][release-shield]][latest-release-url]
  [![Build Status][actions-shield]][actions-url]
  ![Kotlin][kotlin-shield]
  <!-- [![Last Commit][last-commit-shield]][last-commit-url]
  [![License][license-shield]][license-url] -->
</div>

## Downloads



Please visit the [releases][releases-url] page to download the latest release.

<details>
<summary>Nightly Builds</summary>
<br>

> [!WARNING]  
> Nightly builds are **experimental** and **untested**.
> Builds are distributing for testing purposes only, and may not be stable.

[Nightly builds][actions-url] are available from github actions.

A permalink to the latest nightly build can be found [here][nightly-download-url].

</details>

## Usage

### `/coords`

Gets the coordinates of a player (including offline players).

#### Syntax
```
/coords <player>
```

#### Example
```
/coords Ethemoose
Ethemoose is in world world_nether with location -100.5, 100.5, 0.5
```

### `/otp`

Teleports a player to a target, allowing offline targets.

#### Syntax
```
/otp [source] <target>
```

If `source` is not specified, the player issuing the command will be teleported.

`target` can be a username or a coordinate.

#### Example
```
/otp Ethemoose -100.5, 100.5, 0.5
```

## License
This project is licensed under the [MIT License](LICENSE).

<!-- urls -->
<!-- shields -->
[release-shield]: https://img.shields.io/github/v/release/elliotnash/CoordsOffline?style=for-the-badge
[actions-shield]: https://img.shields.io/github/actions/workflow/status/elliotnash/CoordsOffline/build.yml?style=for-the-badge
[kotlin-shield]: https://img.shields.io/badge/Kotlin-%237F52FF.svg?logo=kotlin&logoColor=white&style=for-the-badge

[releases-url]: https://github.com/elliotnash/CoordsOffline/releases
[latest-release-url]: https://github.com/elliotnash/CoordsOffline/releases/latest
[actions-url]: https://github.com/elliotnash/CoordsOffline/actions

[nightly-download-url]: https://nightly.link/elliotnash/CoordsOffline/workflows/build/main/CoordsOffline.jar.zip