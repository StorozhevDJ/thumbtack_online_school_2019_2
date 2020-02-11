#!/bin/bash

##############################################################################
echo "             Task2";
echo "В условиях предыдущей задачи ";
echo "Проверить, что все файлы за год присутствуют и нет лишних.";
echo "Во всех исходных файлах переставить дату на первое место и привести к виду dd/mm/YYYY:";
echo "date       ; cite        ; country ; views ; clicks";
echo "01/01/2017 ; www.abc.com ; usa     ; 1000  ; 3";
echo "01/01/2017 ; www.cba.com ; France  ; 750   ; 0";
echo "";

fcntdefault=365;
fcnt=$(($(ls -f files3/ | wc -l)-2));

if [ "$fcnt" -ne "$fcntdefault" ]
then
	echo "Files count $fcnt is NOT correct";
fi

for filename in files3/*
do
	echo "date;cite;country;views;clicks" > /tmp/tempfile
	sed '1d' $filename | sed 's/\([^;]*\);\([^;]*\);\([^.]*\).\([^.]*\).\([^;]*\);\([^;]*\)/\5\/\4\/\3;\1;\2;\6/'  >> /tmp/tempfile
	mv /tmp/tempfile $filename
done

echo "End task 2";
