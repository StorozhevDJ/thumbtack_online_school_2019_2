#!/bin/bash

##############################################################################
echo "            Task1";
echo "Сгенерировать набор файлов вида";
echo "YYYY-mm-dd.csv";
echo "по каждому дню за год с содержимым такого вида (числовые поля заполнить случайными  значениями):";
echo "cite        ; country ; date       ; views ; clicks";
echo "www.abc.com ; USA     ; 2017-01-01 ; 1000  ; 3"
echo "www.cba.com ; France  ; 2017-01-01 ; 750   ; 0";

mkdir "files3" -p

for d in {0..364};
do 
	mydate=$(date -d "2019-01-01 + $d days" +'%Y.%m.%d');
	echo "cite;country;date;views;clicks
www.abc.com;USA;$mydate;$RANDOM;$RANDOM
www.cba.com;France;$mydate;$RANDOM;$RANDOM" > "files3/${mydate}.csv";
done

echo "End task 1";


