from enum import Enum

class ProtocolMethodEnum(Enum):
    LO = 1
    SU = 2
    CG = 3
    SG = 4
    JG = 5
    LG = 6
    UG = 7

ProtocolMethod = {ProtocolMethodEnum.LO: "LO",
                  ProtocolMethodEnum.SU: "SU",
                  ProtocolMethodEnum.CG: "CG",
                  ProtocolMethodEnum.SG: "SG",
                  ProtocolMethodEnum.JG: "JG",
                  ProtocolMethodEnum.LG: "LG",
                  ProtocolMethodEnum.UG: "UG"
}
