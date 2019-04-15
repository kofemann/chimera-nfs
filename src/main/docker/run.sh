#!/bin/sh

JAVA_ARGS=

case $1 in

  mds)
  echo "Staring MDS"
  ;;
  ds)
  JAVA_ARGS=-DPNFS_DS_ADDRESS="${LOCALADDRESS}"
  echo "Staring DS"
  ;;
  sh)
  exec /bin/sh
  ;;
  *)
  echo "Invalid option " $1
  exit 1
  ;;
esac

exec /usr/bin/java -server \
	${JAVA_OPT} ${JAVA_ARGS} \
	-cp "/pnfs/jars/*" org.dcache.nfs.Main $1
