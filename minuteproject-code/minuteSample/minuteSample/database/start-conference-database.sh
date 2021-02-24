#!/bin/sh
# -----------------------------------------------------------------------------
# Start Hypersonic database
#
# -----------------------------------------------------------------------------

java -classpath hsql/hsqldb-1.7.3.jar org.hsqldb.Server -database.0 hsql/conference -dbname.0 conference -port 9001 -silent true -trace false
