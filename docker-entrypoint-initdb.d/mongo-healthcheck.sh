#!/bin/bash
mongo --username "$MONGO_USER" --password "$MONGO_PASS" --eval "db.adminCommand('ping')" > /dev/null 2>&1
