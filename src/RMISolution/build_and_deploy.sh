javac Common/EntityStates/*java Interfaces/*.java Common/*.java Registry/*.java ClientSide/Coach/*.java ClientSide/Contestant/*.java ClientSide/Referee/*.java ServerSide/Bench/*.java ServerSide/Global/*.java ServerSide/Playground/*.java ServerSide/RefereeSite/*.java 

cp Common/EntityStates/*.class dir_ClientSide/Common/EntityStates/
cp Common/EntityStates/*.class dir_ServerSide/Common/EntityStates/
cp Common/EntityStates/*.class dir_Registry/Common/EntityStates/

cp Common/*.class dir_ClientSide/Common/
cp Common/*.class dir_ServerSide/Common/
cp Common/*.class dir_Registry/Common/

cp Interfaces/*.class dir_ClientSide/Interfaces/
cp Interfaces/*.class dir_ServerSide/Interfaces/
cp Interfaces/*.class dir_Registry/Interfaces/

cp Registry/*.class dir_Registry/Registry
cp ServerSide/Bench/*.class dir_ServerSide/ServerSide/Bench/
cp ServerSide/RefereeSite/*.class dir_ServerSide/ServerSide/RefereeSite/
cp ServerSide/Playground/*.class dir_ServerSide/ServerSide/Playground/
cp ServerSide/Global/*.class dir_ServerSide/ServerSide/Global/

cp ClientSide/Coach/*.class dir_ClientSide/ClientSide/Coach/
cp ClientSide/Contestant/*.class dir_ClientSide/ClientSide/Contestant/
cp ClientSide/Referee/*.class dir_ClientSide/ClientSide/Referee/

mkdir -p /home/sd0402/Public/classes
mkdir -p /home/sd0402/Public/classes/Interfaces
mkdir -p /home/sd0402/Public/classes/ClientSide/Coach
mkdir -p /home/sd0402/Public/classes/ClientSide/Contestant
mkdir -p /home/sd0402/Public/classes/ClientSide/Referee

cp Interfaces/*.class /home/sd0402/Public/classes/Interfaces
cp ClientSide/Coach/*.class /home/sd0402/Public/classes/ClientSide/Coach
cp ClientSide/Contestant/*.class /home/sd0402/Public/classes/ClientSide/Contestant
cp ClientSide/Referee/*.class /home/sd0402/Public/classes/ClientSide/Referee