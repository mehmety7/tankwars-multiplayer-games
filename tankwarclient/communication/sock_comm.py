import socket

"""
    ====================
    |       Game       |
    ====================
    |       Proxy      | -> Delegates the work using the filled Protocol class
    ====================
    | ProtocolResolver | -> Fills class Protocol
    ====================
    |    SocketComm    | -> Reads and writes to tcp sock
    ====================
"""


class GameSocket:
    def __init__(self, host, port: int) -> None:
        self.host = host
        self.port = port
        self.sck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.buffer = bytes()

    def connect(self):
        self.sck.connect((self.host, self.port))

    def send(self, data):
        self.sck.sendall(data)

    def recv(self, bufferSize: int): 
        self.buffer += self.sck.recv(bufferSize)

    def data(self):
        return self.buffer

    def clear_buffer(self):
        self.buffer = bytes()
