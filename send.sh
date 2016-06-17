echo "Compressing source files into a package..."

rm src.zip

zip -r src.zip ./src/

echo "Sending the package to the machines and uncompressing them..."

for i in {1..8}
do
	sshpass -p fortykk3ks scp src.zip sd0402@l040101-ws0$i.ua.pt:
	sshpass -p fortykk3ks ssh sd0402@l040101-ws0$i.ua.pt 'unzip -o src.zip'

done

echo "Finished sending files."