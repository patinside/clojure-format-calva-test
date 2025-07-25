# GOAL:

This repository reproduces an issue I'm encountering with Calva not respecting custom `:extra-indents` defined in a `cljfmt.edn` file, even though the CLI `cljfmt fix` does apply them correctly.

The goal is to find a working configuration where Calva formatting matches the CLI behavior, especially with custom macros such as `m/sp`, `m/via`, etc.

# Currently:

Running clojure -M:cljfmt fix works as expected.

Pressing TAB or CMD+S in Calva update well default formating but not custom macros. it seems to ignore my cljfmt.edn.

This is my cljfmt.edn:

```clojure
{:remove-multiple-non-indenting-spaces? true
 :indent-line-comments? true
 :remove-trailing-whitespace? true
 :sort-ns-references? true
 :extra-indents {>defn [[:inner 0]]
                 loop-iter! [[:inner 0]]
                 sp-let [[:inner 0]]
                 missionary.core/via [[:block 1]]
                 missionary.core/sp [[:block 0]]}}
```

My deps.edn includes:
```
:cljfmt {:extra-deps {dev.weavejester/cljfmt {:mvn/version "0.13.1"}}
         :main-opts ["-m" "cljfmt.main"]}
```
Running ` clojure -M:cljfmt --config cljfmt.edn fix src/test_calva_format/core.clj` gives the correct formatting.

# What I’ve tried in VSCode:

I tried those configurations in  .vscode/settings.json:

## Specify config path, with newIndentEngine:
```
{
  "editor.formatOnSave": true,
  "calva.fmt.configPath": "cljfmt.edn",
  "calva.fmt.newIndentEngine": true
}
```

## Via `clojure-lsp`, last version:
First I created `.lsp/config.edn` with:
```
{:cljfmt-config-path "cljfmt.edn"}
```

Then tested:
```
{
  "[clojure]": {
    "editor.formatOnSave": true,
    "editor.formatOnType": true
  },
  "calva.clojureLspVersion": "nightly",
  "calva.fmt.configPath": "CLOJURE-LSP"
}
```
And:
```
{
  "editor.formatOnSave": true,
  "calva.fmt.configPath": "CLOJURE-LSP",
  "calva.enableClojureLspOnStart": "always"
}
```
I tested also without option, as Calva is supposed to detect `cljfmt.edn`:
```
{
  "editor.formatOnSave": true
}
```

cljfmt.edn is in the root.

For each config, I confirmed that CMD+S or TAB triggers Calva formatting, but it doesn't follow :extra-indents.

# Folder structure
```
test-calva-format/
├── .lsp/
│   └── config.edn
├── .vscode/
│   └── settings.json
├── cljfmt.edn
├── deps.edn
├── src/
│   └── test_calva_format/
│       └── core.clj
└── README.md
```

# What else I’ve tested
Applied same user settings than workspace ones.

Explicit absolute path for calva.fmt.configPath → no change.

Verified that Calva version is 2.0.523.

Tried enabling/disabling calva.fmt.newIndentEngine (doesn’t seem to affect).


Can you help me to find a configuration ensuring Calva uses my custom `cljfmt.edn`, and applies the same formatting as the CLI? I would prefer using `clojure-lsp` way to be iso in usage with Emacs users.

## Related discussion

This test repository was shared on [Clojurians Slack](https://clojurians.slack.com/) / [Clojureverse](https://clojureverse.org/) to get help resolving the issue.
