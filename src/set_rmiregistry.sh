echo "port: "
echo $1
rmiregistry -J-Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/classes"\
            -J-Djava.rmi.server.useCodebaseOnly=true $1
