
def res = GQ {
  from p in [0,1,2]
  crossjoin c in "abc".toCharArray()
  select p,c
}

println(res)
