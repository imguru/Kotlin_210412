// 20_Nullable.kt
package ex20

// Nullable
//  : 'null'을 안전하게 다루는 방법

class User {
    fun sendMail(email: String) {
        println("User sendMail - $email")
    }
}

fun getUser(): User? {
    return User()
}

var email: String? = null

fun main() {
    /*
    val email = "hello@gmail.com"
    val user: User? = getUser()

    user?.sendMail(email)
    */


    val user = User()

    // var a: String?
    // var b: String

    // a = b   // ok!
    // b = a   // error!
    // user.sendMail(email)


    /*
    if (email != null) {
        user.sendMail(email)  // Smart Cast!
                              // 전역 프로퍼티 또는 객체의 프로퍼티가 var인 경우 스마트 캐스트가 동작하지 않습니다.
    }
    */

    /*
    val email = email
    if (email != null) {
        user.sendMail(email)
    }
    */

    // let - Safe Call
    //  => email이 null이 아니면, let 블록을 실행하겠다.
    email?.let { email ->
        user.sendMail(email)
    }

}

/*
public inline fun <T, R> T.let(block: (T) -> R): R {
    return block(this)
}
*/





