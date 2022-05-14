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


# On linux, test with `nc -l localhost 12312``
class GameSocket:
    def __init__(self, host, port: int) -> None:
        self.host = host
        self.port = port
        self.sck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.buffer = None

    def connect(self):
        self.sck.connect((self.host, self.port))

    def send(self, binaryData):
        self.sck.sendall(binaryData)

    def recv(self, bufferSize: int): 
        self.buffer = self.sck.recv(bufferSize)

    def data(self):
        return len(self.buffer), self.buffer

        

def test():
    s = GameSocket("127.0.0.1", 12313)
    s.connect()
    s.send(b"Hello")
    s.recv(1)

    print(s.buffer)
    s.recv(2)
    print(s.buffer)
    print(s.data())

if __name__ == "__main__":
    test()