#!/bin/bash

##############################################################################
echo "                Task3"
echo "Сформировать набор файлов monday.csv, tuesday.csv и т.д. куда вывести содержимое всех исходных файлов отдельно по каждому дню недели. Строку заголовка вывести один раз. Исходные файлы удалить."

mkdir "files3-3" -p

for d in {0..6};
do 
	myweek=$(LANG=en_US date -d "2020-01-01 + $d days" +%A);
	#echo ${myweek};
	echo "date;cite;country;views;clicks" > "files3-3/${myweek}.csv";
done

for filename in files3/*
do
	curdate=$(basename $filename .csv)
	myweek=$(LANG=en_US date -d $(date -d $(sed "s/\./\//g" <<< $curdate) +%Y-%m-%d) +%A);
	sed -e '1d' $filename >> "files3-3/$myweek.csv" && rm $filename
done


echo "End task 3";
