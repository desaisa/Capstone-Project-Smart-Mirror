from newsapi import NewsApiClient
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout
from PyQt5.QtGui import QFont
# Init
import requests
newsapi = NewsApiClient(api_key='af9519f2d49c48309f79f8ecb5ad4266')

# /v2/top-headlines
top_headlines = newsapi.get_top_headlines(country='ca',
                                          language='en')
app = QApplication([])
window = QWidget()
layout = QVBoxLayout()
label1 = [QLabel(),QLabel(),QLabel(),QLabel(),QLabel(),QLabel(),QLabel(),QLabel(),QLabel()]
count = 0

for x in top_headlines['articles'][0:9]:

  temp = str(x['title'])
  label1[count].setText(temp)
  label1[count].setFont(QFont('Arial', 10))
  layout.addWidget(label1[count])
  layout.addWidget(QLabel("-------------------------------------------------"))
  count = count+1

  
window.setLayout(layout)
window.setFixedWidth(500)
window.setFixedHeight(500)
window.move (0,0)
window.show()
app.exec_()