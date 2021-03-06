// 22_범위지정함수2.kt
package ex22

import java.io.Closeable

// 코틀린은 Try with resources 문법을 지원하지 않습니다.
class Resource : Closeable {
    override fun close() {
        println("Resource close")
    }

    fun foo() {
        println("Resource foo")
    }
}

// use - AutoClosable / Closeable
fun main() {
    val res = Resource()
    val res2 = Resource()

    res2.use {
        res.use { e ->
            e.foo()
        }
    }
}