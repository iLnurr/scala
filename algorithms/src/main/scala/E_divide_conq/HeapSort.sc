object Heap {
  private implicit class ArrayOps(val a: Array[Int]) extends AnyVal {
    def fromIndex(i: Int): Int = a(i - 1)
  }
  private def swapHeap(a: Array[Int], i:Int,j: Int): Array[Int] = {
    val ai = a.fromIndex(i)
    val aj = a.fromIndex(j)
    a.update(i - 1,aj)
    a.update(j - 1,ai)
    a
  }
  def parent(i: Int): Int = i >> 1
  def left(i: Int): Int = i << 1
  def right(i: Int): Int = (i << 1).toInt + 1

  def maxHeapify(a: Array[Int], i: Int): Array[Int] = {
    val heapSize = a.length
    val l = left(i)
    val r = right(i)
    val largest = {
      val ll = if (l <= heapSize && a.fromIndex(l) > a.fromIndex(i)) {
        l
      } else i

      if (r <= heapSize && a.fromIndex(r) > a.fromIndex(ll)) {
        r
      } else ll
    }

    if (largest != i) {
      maxHeapify(swapHeap(a,largest,i),largest)
    }
    a
  }
  def buildMaxHeap(a: Array[Int]) = {
    val heapSize = a.length
    ((heapSize / 2) to 1 by -1).foreach(i => maxHeapify(a,i))
    a
  }

  def heapSort(a: Array[Int]) = {
    val length = a.length
    val heap = buildMaxHeap(a)
    val res = (a.length to 2 by -1).foldLeft((length,heap, List.empty[Int])) { case ((heapSize,h,a),i) =>
      val swapped = swapHeap(h,1,i)
      val nHeap = maxHeapify(swapped.init,1)
      (heapSize - 1, nHeap, swapped.last :: a)
    }
    res._2.head :: res._3
  }

}
import Heap._

val heap0 = buildMaxHeap(Array(16,4,10,14,7,9,3,2,8,1))
assert(heap0 sameElements Array(16,14,10,8,7,9,3,2,4,1))

val heap = buildMaxHeap(Array(4, 1, 3, 2, 16, 9, 10, 14, 8, 7))
assert(heap sameElements Array(16,14,10,8,7,9,3,2,4,1))

heapSort(Array(5,6,2,3,4,7,8,33,22,3))
heapSort(Array(16,14,10,8,7,9,3,2,4,1))