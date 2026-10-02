#!/bin/sh
set -eu

ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
OUT="$ROOT/build/classes"
JAVAC=${JAVAC:-javac}
JAR=${JAR:-jar}

rm -rf "$ROOT/build/classes"
mkdir -p "$OUT/logicsim"
"$JAVAC" --release 8 -encoding UTF-8 -Xlint:none -d "$OUT" "$ROOT"/logicsim/*.java
cp -R "$ROOT/logicsim/images" "$OUT/logicsim/"

cat > "$ROOT/build/MANIFEST.MF" <<'EOF'
Manifest-Version: 1.0
Main-Class: logicsim.App
Implementation-Title: LogicSim
Implementation-Version: 2.4-modern
EOF

rm -f "$ROOT/LogicSim-modern.jar"
"$JAR" cfm "$ROOT/LogicSim-modern.jar" "$ROOT/build/MANIFEST.MF" \
  -C "$OUT" logicsim \
  -C "$ROOT" languages \
  -C "$ROOT" logicsim.cfg

echo "Created $ROOT/LogicSim-modern.jar"
