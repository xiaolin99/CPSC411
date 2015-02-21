  READ x
L1:
  rPUSH x
  cJUMP L2
  rPUSH x
  cPUSH 1
  OP2 +
  cPUSH 2
  OP2 /
  rPUSH x
  cPUSH 2
  OP2 /
  OP2 -
  cJUMP L3
  rPUSH x
  PRINT
  JUMP L4
L3:
  cPUSH 0
  LOAD z
L4:
  rPUSH x
  cPUSH 1
  OP2 -
  LOAD x
  JUMP L1
L2:
