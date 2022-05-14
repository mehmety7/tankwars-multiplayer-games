# Architecture (Do not hesitate to make changes)

-> Client
    has a -> Game
    has a -> GameMenu
    has a -> Communication(Socket)

Client should have states. e.g.:

* in_game
* in_menu
* in_lobby

Depending on this states, client should communicate with server.
For example, if we are in a in_game state, we supposed to receive game status x times in a second and update our frame
For example, if we are in a in_lobby state, we supposed to receive updates when new players joins in lobby or we supposed to receive the lobby status x times in a second and update our frame 