#!/bin/bash
./gradlew build
docker build -t ibmcase/customerms .

prev=$(grep -o 'v\([0-9]\+.\)\{2\}\([0-9]\+\)' chart/green-customerms/values.yaml)
echo $prev
if [[ $# -gt 0 ]]; then
	v=v$1
else
	v=$prev
fi
echo $v
docker tag ibmcase/customerms greencluster.icp:8500/greencompute/customerms:$v

docker images

cd chart/green-customerms

sed -i -e s/$prev/$v/g values.yaml
sed -i -e s/$prev/$v/g Chart.yaml