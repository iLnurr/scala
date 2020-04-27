
object Main {
  type Element = Int
  type Index = Int
  @scala.annotation.tailrec def binarySearchWithPredicate(
    predicate: Element => Boolean,
    lastOccurence: Boolean = false,
    prevIndex: Option[Index] = None,
    array: Array[Element],
    l: Index,
    r: Index
  ): Index = {
    val index = (l + r) / 2
    val am = array(index)
    val checkResult = predicate(am)
    val checkedIndex = if (checkResult) {Option(index)} else {prevIndex}
    if (l > r) {
      checkedIndex.getOrElse(-1)
    } else {
      if ((checkResult && lastOccurence)) {
        binarySearchWithPredicate(predicate, lastOccurence, checkedIndex, array, index + 1, r)
      } else {
        binarySearchWithPredicate(predicate, lastOccurence, checkedIndex, array, l, index - 1)
      }
    }
  }
  def lds(ar: Array[Int]): Array[Int] = ???

  def lis(array: Array[Element]): List[Index] = {
    val n = array.length

    def lisBottomUp2: (Array[Int], Array[Index]) = {
      array.zipWithIndex.foldLeft((Array.fill(n)(1), Array.fill(n)(-1))) { case ((d, prev), (ai, index)) =>
        (0 to index).foreach(j => {
          if ((array(j) < ai) && (d(j) + 1 > d(index))) {
            d(index) = d(j) + 1
            prev(index) = j
          }
        })

        (d, prev)
      }
    }

    def returnAns(d: Array[Int], prev: Array[Index]):List[Index] = {
      val max = d.max
      val (_, k) = d.zipWithIndex.maxBy(t => t._1 + t._2)

      val (lastK, acc) = (1 until max).foldLeft((k,List.empty[Index])){
        case ((k, acc), _) => (prev(k), k :: acc)
      }

      lastK :: acc
    }

    val (d,prev)= lisBottomUp2
    returnAns(d,prev)
  }
  def main(args: Array[String]): Unit = {
    scala.io.StdIn.readLine().toInt
    val array = scala.io.StdIn.readLine().split(" ").map(_.toInt)

    println(lds(array))
  }
  def test(): String = {
    def testOne(t: Array[Int], expected: Array[Int], method: Array[Int] => List[Int], methodName: String) = {
      val res = method(t).toArray
      assert(res sameElements expected, s"$methodName(${t.mkString("["," ","]")})=${res.mkString("[",",","]")} =! ${expected.mkString("Array(",",",")")}")
      println(s"Result ${res.map(t).mkString(",")}")
    }

    testOne(Array(7,2,1,3,8,4,9,1,2,6,5,9,3,8,1),Array(1,3,5,9,13), lis, "lis")
    "success"
  }
}
Main.test()