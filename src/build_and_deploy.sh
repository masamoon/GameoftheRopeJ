javac common/entityStates/*java interfaces/*.java common/*.java registry/*.java clientSide/coach/*.java clientSide/contestant/*.java clientSide/referee/*.java serverSide/bench/*.java serverSide/global/*.java serverSide/playground/*.java serverSide/refereeSite/*.java 

cp common/entityStates/*.class dir_clientSide/common/entityStates/
cp common/entityStates/*.class dir_serverSide/common/entityStates/
cp common/entityStates/*.class dir_registry/common/entityStates/

cp common/*.class dir_clientSide/common/
cp common/*.class dir_serverSide/common/
cp common/*.class dir_registry/common/

cp interfaces/*.class dir_clientSide/interfaces/
cp interfaces/*.class dir_serverSide/interfaces/
cp interfaces/*.class dir_registry/interfaces/

cp registry/*.class dir_registry/registry
cp serverSide/bench/*.class dir_serverSide/serverSide/bench/
cp serverSide/refereeSite/*.class dir_serverSide/serverSide/refereeSite/
cp serverSide/playground/*.class dir_serverSide/serverSide/playground/
cp serverSide/global/*.class dir_serverSide/serverSide/global/

cp clientSide/coach/*.class dir_clientSide/clientSide/coach/
cp clientSide/contestant/*.class dir_clientSide/clientSide/contestant/
cp clientSide/referee/*.class dir_clientSide/clientSide/referee/

mkdir -p /home/sd0402/Public/classes
mkdir -p /home/sd0402/Public/classes/interfaces
mkdir -p /home/sd0402/Public/classes/clientSide/coach
mkdir -p /home/sd0402/Public/classes/clientSide/contestant
mkdir -p /home/sd0402/Public/classes/clientSide/referee
mkdir -p /home/sd0402/Public/classes/common/
mkdir -p /home/sd0402/Public/classes/common/entityStates

cp interfaces/*.class /home/sd0402/Public/classes/interfaces
cp clientSide/coach/*.class /home/sd0402/Public/classes/clientSide/coach
cp clientSide/contestant/*.class /home/sd0402/Public/classes/clientSide/contestant
cp clientSide/referee/*.class /home/sd0402/Public/classes/clientSide/referee
cp common/*.class /home/sd0402/Public/classes/common/
cp common/entityStates/*.class /home/sd0402/Public/classes/common/entityStates