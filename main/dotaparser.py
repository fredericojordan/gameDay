'''
Created on 16 de dez de 2015

@author: fvj
'''
from bs4 import BeautifulSoup
import urllib2

DOTA_WEBSITE = "http://www.joindota.com/"

def get_games():
    html_doc = urllib2.urlopen(DOTA_WEBSITE).read()
    soup = BeautifulSoup(html_doc, "html.parser")
    
    games = soup.prettify()
    
    return games