echo "Sending the Jar files to the machines..."

sshpass -p fortykk3ks ssh sd0402@l040101-ws01.ua.pt 'mkdir sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws02.ua.pt 'mkdir sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws03.ua.pt 'mkdir sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws04.ua.pt 'mkdir sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws05.ua.pt 'mkdir sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws06.ua.pt 'mkdir sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws07.ua.pt 'mkdir sd02'

sshpass -p fortykk3ks scp -r ./out/artifacts/* sd0402@l040101-ws01.ua.pt:sd02/
sshpass -p fortykk3ks scp -r ./out/artifacts/* sd0402@l040101-ws02.ua.pt:sd02/
sshpass -p fortykk3ks scp -r ./out/artifacts/* sd0402@l040101-ws03.ua.pt:sd02/
sshpass -p fortykk3ks scp -r ./out/artifacts/* sd0402@l040101-ws04.ua.pt:sd02/
sshpass -p fortykk3ks scp -r ./out/artifacts/* sd0402@l040101-ws05.ua.pt:sd02/
sshpass -p fortykk3ks scp -r ./out/artifacts/* sd0402@l040101-ws06.ua.pt:sd02/
sshpass -p fortykk3ks scp -r ./out/artifacts/* sd0402@l040101-ws07.ua.pt:sd02/

echo "Finished sending files."