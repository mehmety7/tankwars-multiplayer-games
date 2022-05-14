## PROTOCOL

[2 BYTE, Size of the incoming message | 2 BYTE, Method Type | Message]

### Method Types

#### Commands For Game Setup And Information
* LO -> Login       	, Message must contain the username and the password. Username and password must be separated by delimiter(|)
* SU -> Sign Up     	, Message must contain the username and the password.
* CG -> Create Game 	, Message must contain the username, tour number, map type and shooting speed parameters.
* SG -> Start Game  	, This method tells that player `username` starts the game.(This method should sent when lobby leader presses the start button)
* JG -> Join Game   	, This method tells that player `username` joins the game.
* LG -> Leave Game  	, This method tells that player `username` left the game.
* KP -> Kick Player		, This method makes the founder of the room be able to kick a player in the room. Founder cannot kick themself and a player who is not in their room. 
* UG -> Update Game 	, This method sends the current status of the player during the game. Position of Tank etc.
* GS -> Get Statistics	, This method makes a request to the server in order to get the statistics of the room. In order for this command to work, game in the room must be finished.
* GL -> Get Leaderships	, This method makes a request to the server in order to get the leadership table of the entire game.
#### Commands For Game Controls
* TL -> Turn Left		, Client requesting it's tank to turn left 90 degrees. Message must contain the id of the client.
* TR -> Turn Right		, Client requesting it's tank to turn right 90 degrees. Message must contain the id of the client.
* GF -> Go Forward		, Client requesting it's tank to go forward. Message must contain the id of the client.
* SH -> Shoot			, Client requesting it's tank to shoot forward. Message must contain the id of the client.
####Server Commands For Client
* TI -> Tick        	, Client received information about other players, update frame.
### Responses
* OK -> Success
* FL -> Fail

#### <ins>Example</ins>

Message
```
            SHORT   STRING       STRING   
Hex     -> [ 00 0B | 4C 47 | 42 45 52 4b 45 59 7c 31 32 33 34] 
Decimal -> [  11   |  LG   |           BERKEY|1234           ]
                                         11 BYTE       
```
Response
```
            SHORT   STRING 
Hex     -> [ 00 00 | 4f 4b ] 
Decimal -> [   0   |  OK   ]
```
