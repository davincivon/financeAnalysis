import urllib.request
import re
import csv
import os
import time

#get data
headers = {
    'user-agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36'
}

req = urllib.request.Request(url='https://hq.sinajs.cn/rn=1596245500098list=fx_seurcny',headers=headers,method='GET')
response = urllib.request.urlopen(req)
html = response.read().decode('gb2312')
strlist = html.split(',')

# print(strlist)
# print('\n')
# print(strlist[len(strlist)-9] + ':'+strlist[1] + strlist[len(strlist)-1][0:10]+strlist[5]+strlist[6]+strlist[7]+strlist[8])

#write to csv
with open('datas.csv','a',newline='') as f:
     row = [strlist[len(strlist)-9],strlist[1],strlist[len(strlist)-1][0:10],strlist[5],strlist[6],strlist[7],strlist[8]]
     write = csv.writer(f)
     write.writerow(row)
     f.close()


