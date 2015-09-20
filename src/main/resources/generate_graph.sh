#!/bin/bash

cat <(echo digraph uja {) target/edges target/nodes <(echo }) | dot -Tsvg > target/uja.svg

# spark
# cat <(echo digraph uja {) target/edges/* target/nodes/* <(echo }) | dot -Tsvg > target/uja.svg
