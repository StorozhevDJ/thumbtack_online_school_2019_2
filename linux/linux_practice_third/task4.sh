#!/bin/bash

##############################################################################
echo "               Task4"
echo "Вывести в отдельный файл содержимое всех файлов *.java из своего репозитория (локальный проект заочной школы или любой другой, где есть java классы), в которых встречается ArrayList."

grep --include=\*.java -rnwlI '/server/Denis/Projects/Java/Git/' -e "ArrayList"  | xargs cat  > ArrayList.java

echo "End task 4";

