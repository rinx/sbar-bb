# sbar-bb

Write your [SketchyBar](https://github.com/FelixKratz/SketchyBar) config in Babashka script.

## Usage

Add this to your `bb.edn`.

```edn
{:deps {io.github.rinx/sbar-bb {:git/sha "97aa02b85fa00dea557f135ad44e61551f43e8cf"}}}
```

Write your own SketchyBar config as a Babashka script. See [examples/example.clj](https://github.com/rinx/sbar-bb/blob/main/examples/example.clj).

Call your Babashka script from `sketchybarrc`.

```bash
# add path to your bb executable
PATH=$PATH:$HOME/.bin

# config script
CONFIG_FILE="$HOME/.config/sketchybar/rc/config.clj"

bb ${CONFIG_FILE}
```

## API

TBW

## Example

- [Basic example: examples/example.clj](https://github.com/rinx/sbar-bb/blob/main/examples/example.clj)
- [rinx/dotfiles: macos/sketchybar](https://github.com/rinx/dotfiles/tree/ceb45e434684b8ce93349fc6ab602190c8cd7477/macos/sketchybar)

## Similar Projects

- [FelixKratz/SbarLua](https://github.com/FelixKratz/SbarLua)
