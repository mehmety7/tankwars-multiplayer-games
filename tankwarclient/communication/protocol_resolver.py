# Open protocol header here and delegate its work

from protocol import Protocol

class ProtocolResolver:
    def __init__(self, protocol_: Protocol) -> None:
        self.protocol = protocol_