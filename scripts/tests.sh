#!/bin/bash -v
echo "run integration tests"
curl -X GET http://green.csplab.local/caseserv/api/v1/customers/1
