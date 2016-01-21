'''
Created on 16 de dez de 2015

@author: fvj
'''
import dotaparser

PRIMARY_CALENDAR_ID = 'primary'

schedules = dotaparser.get_games()
print(schedules)

# calendar_api.create_events(PRIMARY_CALENDAR_ID, schedules)