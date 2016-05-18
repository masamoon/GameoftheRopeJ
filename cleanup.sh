echo "Sweeping up the files from the machines..."

sshpass -p fortykk3ks ssh sd0402@l040101-ws01.ua.pt 'rm -r sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws02.ua.pt 'rm -r sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws03.ua.pt 'rm -r sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws04.ua.pt 'rm -r sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws05.ua.pt 'rm -r sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws06.ua.pt 'rm -r sd02'
sshpass -p fortykk3ks ssh sd0402@l040101-ws07.ua.pt 'rm -r sd02'

echo "Clean up completed."