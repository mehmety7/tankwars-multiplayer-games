import socket


# On linux, test with `nc -l localhost 12312``
class GameSocket:
    def __init__(self, host, port: int) -> None:
        self.host = host
        self.port = port
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.data = None

    def connect(self):
        self.socket.connect((self.host, self.port))

    def send(self, binaryData):
        self.socket.sendall(binaryData)

    def recv(self, bufferSize: int): 
        self.data = self.socket.recv(bufferSize)
        


s = GameSocket("127.0.0.1", 12313)
s.connect()
s.send(b"Hello")
s.recv(2048)

print(s.data)