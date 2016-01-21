'''
Created on 16 de dez de 2015

@author: fvj
'''
import csparser
# import calendar_api

PRIMARY_CALENDAR_ID = 'primary'

schedules = csparser.get_games()
print(schedules)

# calendar_api.create_events(PRIMARY_CALENDAR_ID, schedules)