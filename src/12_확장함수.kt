// 12_확장함수
package ex12

// (Int, Int) -> Unit
fun move(x: Int, y: Int) {
    println("Global move")
}

class User {
    // fun move(this: User, x: Int, y: Int)
    fun move(x: Int, y: Int) {
        println("User move - $this")
    }
}

/*
fun main() {
    var fn: (Int, Int) -> Unit = ::move

    // fn = User::move  // ?
    val fn2: (User, Int, Int) -> Unit = User::move

    fn(10, 20)
    val user = User()
    fn = user::move   // Bound reference - this의 객체가 이미 bind 되었다.
    fn(10, 20)

    val obj = User()
    fn = obj::move
    fn(20, 30)

    // user.move(10, 20) // User::move(user, 10, 20)
    // fn2(user, 10, 20)
}
*/


// Extension Function: 매우 중요합니다.
//  => 코틀린 라이브러리의 핵심적인 설계 기술
//  => 수평 확장 vs 수직 확장

//  기존 라이브러리를 확장하는 방법
//   => 상속(수직 확장)
//    : 기반 클래스를 상속 받아서, 자식 클래스를 통해 새로운 기능을 제공한다.
//    한계: 기존 클래스가 상속을 목적으로 설계되어 있지 않다면, 확장이 불가능하다.

//   => 확장 함수(수평 확장)
//    : 사용자가 정의한 '함수'가 기존 클래스의 '메소드'처럼 보이게 하는 기술
//       C#: Extension Function
//    Swift: Extension
//     ObjC: Category

/*
fun lastChar(text: String): Char {
    return text[text.length - 1]
}
*/

// fun lastChar(text: String): Char = text[text.length - 1]
fun String.lastChar(): Char = this[length - 1]

fun main() {
    val text = "hello"

    // val result = lastChar(text)
    val result = text.lastChar()

    println(result)
}










