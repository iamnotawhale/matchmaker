# The simple multiplayer matchmaker service

This is implementation of simple matchmaker service
The matchmaker service queues players and creates groups based on waiting time, minimum latency and player skill difference.

The algorithm for selecting players by groups works as follows:
```
if queue of players is not empty
    if queue contains player with excessive latency -> create group by excessive player latency
    else -> create groups of all players by matchmaking rating based on minimum difference of latency and skill
else waiting more players connection
```
See resources/post-requests.http to send emulation of players join requests 