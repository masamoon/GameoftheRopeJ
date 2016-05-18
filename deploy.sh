echo "Sending the Jar files to the machines..."

for i in {1..7}
do
	sshpass -p fortykk3ks ssh sd0402@l040101-ws0$i.ua.pt 'mkdir sd02'
	sshpass -p fortykk3ks scp -r ./out/artifacts/* sd0402@l040101-ws0$i.ua.pt:sd02/
done

echo "Finished sending files."