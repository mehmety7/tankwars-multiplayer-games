## PROTOCOL

[2 BYTE, Size of the incoming message | 2 BYTE, Method Type | Message]

### Method Types
* LO -> Login       , Message must contain the username and the password. Username and password must be separated by delimiter(|)
* SU -> Sign Up     , Message must contain the username and the password.
* CG -> Create Game , Message must contain the username
* SG -> Start Game  , This method tells that player `username` starts the game.(This method should sent when lobby leader presses the start button)
* JG -> Join Game   , This method tells that player `username` joins the game.
* LG -> Leave Game  , This method tells that player `username` left the game.
* UG -> Update Game , This method sends the current status of the player during the game. Position of Tank etc.
* TI -> Tick        , Client received information about other players, update frame

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
