#!/bin/bash -v
# Program to compile, build war, build the docker images,
# and modify the version number for the different element
if [[ "$PWD" = */scripts ]]; then
  cd ..
fi
. scripts/setenv.sh

echo "##########################################"
echo deploy $progname to $k8cluster $namespace

# Get current version if the version is not the first argument of this command line
prev=$(grep -o 'v\([0-9]\+.\)\{2\}\([0-9]\+\)' $servclass | head -1)

if [[ $# -gt 0 ]]; then
	v=v$1
	# Update version in java code
	sed -i -e "s/$prev/$v/g" $servclass
	rm $servclass-e
else
  v=$prev
fi
echo 'old version: ' $prev ' new version to use:' $v

# Compile Java Code
./gradlew -Dorg.gradle.daemon=false build

# Build docker
docker build -t ibmcase/$namespace-$progname:$v .


## modify helm version
cd ./chart/$helmchart
a=$(grep 'version' Chart.yaml)
sed -i -e "s/$a/version: ${v:1}/" Chart.yaml
## same for the tag in values.yaml
sed -i -e "s/tag: $prev/tag: $v/" values.yaml
sed -i -e "s/green-cluster.icp/$k8cluster/" values.yaml
rm values.yaml-e
rm Chart.yaml-e
cd ..
