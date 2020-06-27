#! /bin/bash
call_crawler()
{
  ${DIR}/ccrawl.sh $1 > /dev/null && echo "$1, $(tail -1 ${DIR}/crawler.log)" >> ${DIR}/benchCcrawl.txt
}

DIR=$(dirname $0)

echo $1 >> ${DIR}/benchCcrawl.txt

call_crawler 2
call_crawler 4
call_crawler 8
call_crawler 16
