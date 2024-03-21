fun sum(a: Int, b: Int) /*: Unit*/ {
    println(a)
    println(b)
}

val i: Int =10 // 변수 형 지정
val i2 = "go" // 기본 변수

const val stay = 20; //상수

val array: Array<Int?> = arrayOfNulls(5)
fun main(){
    val another = arrayOf(1,2,3)
    val nother = arrayOf(1,2,3)
    val i: Int =30
    val i2 = "let's"
   // sum(10, 20)
//    print("${i} ")
//    println(i2)
//    println(another[0])
   // printArg(i,*another)
    compareArrInt(another,nother)
}

fun printArg(vararg number: Number){
    for(a in number){
        print(a)
    }
}

fun compareArrInt(vararg array: Array<Int>){
    print(array[0].contentEquals(array[1]))
    array[0][0]=1967;
    print(array[0] contentEquals array[1])
}