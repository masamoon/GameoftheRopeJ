echo "Sweeping up the files from the machines..."

for i in {1..7}
do
	sshpass -p fortykk3ks ssh sd0402@l040101-ws0$i.ua.pt 'rm -r sd02'
done

echo "Clean up completed."