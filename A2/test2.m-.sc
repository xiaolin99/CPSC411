  READ x
  rPUSH x
  cPUSH 1
  OP2 -
  cJUMP L1
  rPUSH x
  cPUSH 2
  OP2 -
  cJUMP L3
  cPUSH 0
  LOAD z
  JUMP L4
L3:
  cPUSH 2
  LOAD z
L4:
  JUMP L2
L1:
  cPUSH 1
  LOAD z
L2:
  rPUSH z
  PRINT
