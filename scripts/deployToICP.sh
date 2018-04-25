#!/bin/bash -v
if [[ "$PWD" = */scripts ]]; then
  cd ..
fi
. ./scripts/setenv.sh
v=$(grep -o 'v\([0-9]\+.\)\{2\}\([0-9]\+\)' $servclass | head -1) 

./scripts/exp  admin docker login -u admin $k8cluster:8500
docker push $k8cluster:8500/$namespace/$progname:$v

rc=$(helm ls --all $progname --tls)
if [[ ! -z "$rc" ]]; then
  rc=$(helm del --purge $progname --tls)
  if [[ "$rc" = *deleted ]]; then
    helm install --name $progname chart/$helmchart --namespace=$namespace --tls
  fi
else
  helm install --name $progname chart/$helmchart --namespace=$namespace --tls
fi
