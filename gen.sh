#!/usr/bin/env bash
set -o nounset
set -o pipefail
set -e
echo "public class T {"
echo "public static de.schildbach.pte.AbstractNetworkProvider t(String name) {"
echo "switch (name) {"
for i in $(ls public-transport-enabler-master/enabler/src/de/schildbach/pte/*Provider.java | cut -d. -f1 | rev | cut -c 9- | cut -d/ -f1 | rev | grep -vE "Abstract|Vgn|Vao|Paris|Network|Gvh|Vbn"); do
	echo 'case "'$i'": return new de.schildbach.pte.'$i'Provider();'
done
echo "default: return null;"
echo "}"
echo "}"
echo "}"
