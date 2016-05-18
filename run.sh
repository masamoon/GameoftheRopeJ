echo "Running the simulation:"
echo "Starting the Servers..."

sshpass -p fortykk3ks ssh sd0402@l040101-ws04.ua.pt 'java -jar sd02/BenchServer_jar/BenchServer.jar' > logs/bench.log & 
echo "Bench Server running."

sshpass -p fortykk3ks ssh sd0402@l040101-ws05.ua.pt 'java -jar sd02/PlaygroundServer_jar/PlaygroundServer.jar' > logs/playground.log &
echo "Playground Server running."

sshpass -p fortykk3ks ssh sd0402@l040101-ws06.ua.pt 'java -jar sd02/RefereeSite_jar/RefereeSite.jar' > logs/refereesite.log &
echo "RefereeSite Server running."

sshpass -p fortykk3ks ssh sd0402@l040101-ws07.ua.pt 'java -jar sd02/GlobalServer_jar/GlobalServer.jar' > logs/global.log &
echo "Global (aka General Repository) Server running."
logReady=$!

echo "Starting the Clients..."

sshpass -p fortykk3ks ssh sd0402@l040101-ws01.ua.pt 'java -jar sd02/RefereeClient_jar/RefereeClient.jar'> logs/referee.log &
echo "Referee Client running."

sshpass -p fortykk3ks ssh sd0402@l040101-ws02.ua.pt 'java -jar sd02/ContestantClient_jar/ContestantClient.jar' > logs/contestants.log &
echo "Contestant Client running."

sshpass -p fortykk3ks ssh sd0402@l040101-ws03.ua.pt 'java -jar sd02/CoachClient_jar/CoachClient.jar' > logs/coaches.log &
echo "Coach Client running."

echo "Waiting for the simulation to finish..."

wait $logReady
echo "Simulation Finished! Fetching the log file to the local directory and displaying on this terminal:"
sleep 3

sshpass -p fortykk3ks scp sd0402@l040101-ws07.ua.pt:log.txt .
cat log.txt
