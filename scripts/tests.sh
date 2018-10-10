#!/bin/bash -v
echo "run integration tests"
# curl -X GET http://green.csplab.local/caseserv/api/v1/customers/1
curl -X GET http://172.16.40.133:30429/caseserv/api/v1/customers/1
