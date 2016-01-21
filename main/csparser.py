'''
Created on 16 de dez de 2015

@author: fvj
'''
from bs4 import BeautifulSoup
import urllib2

CS_WEBSITE = "http://www.hltv.org/matches/"
BROWSER_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"


def get_games():
    request = urllib2.Request(CS_WEBSITE, headers={'User-Agent' : BROWSER_USER_AGENT}) 
    html_doc = urllib2.urlopen(request).read()
    soup = BeautifulSoup(html_doc, "html.parser")
     
    games_div = soup.find("div", class_="centerFade").find_all("div", class_="matchListBox")
        
    games = [get_game_info(games_div[i])
             for i in range(len(games_div))]
    
    return games

def get_game_info(game_div):
    game_info = {'matchTime':get_match_time(game_div),
                 'team1':get_team1(game_div),
                 'team2':get_team2(game_div),
                 'score':get_score(game_div),
                 'link':get_link(game_div),
                 }
    return game_info

def get_match_time(game_div):
    return game_div.find("div", class_="matchTimeCell").string.encode("UTF-8")

def get_team1(game_div):
    if game_div.find("div", class_="matchTeam1Cell") == None:
        return "None"
    return game_div.find("div", class_="matchTeam1Cell").a.contents[0].strip().encode("UTF-8")

def get_team2(game_div):
    if game_div.find("div", class_="matchTeam2Cell") == None:
        return "None"
    return game_div.find("div", class_="matchTeam2Cell").a.contents[-1].strip().encode("UTF-8")

def get_score(game_div):
    if game_div.find("div", class_="matchScoreCell") == None:
        return "None"
    spans = game_div.find("div", class_="matchScoreCell").find_all("span")
    if (len(spans) < 2):
        return "None"
    team1score = spans[0].string.strip()
    team2score = spans[1].string.strip()
    return "{0}-{1}".format(team1score, team2score) 

def get_link(game_div):
    return "http://www.hltv.org{0}".format(game_div.find("div", class_="matchActionCell").a.get('href'))