#!/bin/bash
./gradlew build
docker build -t ibmcase/customerms .

if [[ $# -gt 0 ]]; then
	v=greencluster.icp:8500/greencompute/customerms:v$1
else
	v="greencluster.icp:8500/greencompute/customerms:v0.0.1"
fi
echo $v
docker tag ibmcase/customerms $v

docker images
cd chart/green-customerms
a=$(grep 'version' Chart.yaml)
sed -i -e 's/"$a"/version: "$v"/g' Chart.yaml
