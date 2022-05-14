# Open protocol header here and delegate its work

from numpy import byte
import sock_comm

HOST = '127.0.0.1'
PORT = 12313

class ProtocolResolver:
    def __init__(self) -> None:
        self.sck = sock_comm.GameSocket(HOST, PORT)
        self.sck.connect()

    def send(self, method, message):
        data = len(message).to_bytes(2, byteorder="big")
        data += (method + message).encode(encoding='utf-8')
        self.sck.send(data)
        self.sck.recv(4)
        self.bytes = self.sck.data()
        print(self.bytes)
        self.sck.recv(self.message_size())
        self.bytes = self.sck.data()
        print(self.bytes)

    def message_size(self) -> int:
        return int.from_bytes(self.bytes[0:2], byteorder="big")

    def method_type(self) -> str:
        return self.bytes[2:4].decode()

    def message(self) -> str:
        return self.bytes[4:].decode()

    def clear_buffer(self):
        self.bytes = bytes()
        self.sck.clear_buffer()



# On linux, test with `nc -l localhost 12312`
def test():
    protocol_resolver = ProtocolResolver()
    protocol_resolver.send("LG", "berkey|1234")
    print(protocol_resolver.message_size())
    print(protocol_resolver.method_type())
    print(protocol_resolver.message())
    
if __name__ == "__main__":
    test()


    
