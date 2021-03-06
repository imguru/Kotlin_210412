// 24_코틀린표준라이브러리.kt
package ex24

import java.io.File
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.stream.Collectors

// 1. 조건 확인 함수
//  - 특정한 조건이 참이 아닐 경우 예외를 발생시킵니다.
//  1) check     ->  IllegalStateException
//  2) require   ->  IllegalArgumentException
fun logMessage(filename: String, message: String?) {
    val f = File(filename)

    /*
    if (f.exists().not()) {
        throw IllegalStateException("file not found")
        // throw IllegalArgumentException("file not found")
    }
    */

    requireNotNull(message)
    val m: String = message

    // require(f.exists())
    check(f.exists()) {
        println("hello")
        "File not found"
    }
}

// 2. 명시적인 실행 종료 함수
//  - Unit: 결과가 존재하지 않음을 나타내는 결과
//  - Nothing: 결과가 발생하지 않는다.
fun foo(): Nothing {
    error("error message")
    // throw IllegalStateException("error!")
}

/*
fun main() {
    foo()
    // logMessage("a.txt", null)
}
*/

//  Java 8 - Stream API
//   : 자바에서 컬렉션을 대상으로 데이터를 변환하거나, 생성 등의 작업을 편리하게 할 수 있는 연산자를 제공합니다.
//   android minimum SDK - 24

// 3. Sequence API
//   : 코틀린에서 컬렉션에 대해서 다양한 연산자를 제공합니다.
//   - forEach
//   - filter / filterNotNull(T? -> T)

//   transform - 각각의 요소를 새로운 데이터로 변환한다.
//   - map: T -> U
//     mapNotNull

//   - flatMap: List<T> -> map     -> List<List<U>>
//              List<T> -> flatMap -> List<U>

fun main() {
    val cities = listOf("Seoul", "Suwon", "Daegu", "Busan")
    val result3 = cities
        // .stream()
        .parallelStream()
        .filter { e ->
            e.startsWith("S")
        }
        .map(String::toUpperCase)
//        .collect(Collectors.toList())

    // .forEach(::println)


    println(result3)
    for (e in result3) {
        println(e)
    }

    // Sequence API vs Stream API
    // 1. Sequence API: 컬렉션을 전체의 요소를 메모리에 두고, 연산을 전체적으로 수행한다.
    //  [ Seoul, Suwon, Daegu ] -> [ Seoul, Suwon ] -> [ SEOUL, SUWON]

    // 2. Stream API: 한 개의 요소 단위로 연산을 수행한다.
    //  [ Seoul, Suwon, Daegu ]
    //    Seoul -> filter -> map -> SEOUL
    //    Suwon -> filter -> map -> SUWON
    //    Daegu -> filter ->

    // 3. Suquence API - 컬렉션의 데이터가 많을 경우, 효과적으로 처리가 불가능합니다.


    /*
    cities
        .filter { e ->
            e.startsWith("S")
        }
        .map { e ->          // String -> Int
            e.length         // String::length(this: String)
        }
        .forEach(::println)
   */
    /*
    cities
        .filter { e ->
            e.startsWith("S")
        }
        .map(String::length)
        .forEach(::println)
     */


    /*
    val result2: List<String> = cities
        .map { e ->                       // String -> String?
            if (e.startsWith("S"))
                e.toUpperCase()
            else
                null
        }
        .filterNotNull()                  // String? -> String


    val result: List<String> = cities
        .mapNotNull { e ->                       // String -> String? -> filterNotNull -> String
            if (e.startsWith("S"))               //  T     ->   U?    -> filterNotNull -> U
                e.toUpperCase()
            else
                null
        }
    */

    /*
    val result = cities
        .flatMap { e ->      // List<String> -> map      -> List<List<String>>
            e.split(" ")     // List<String> -> flatMap  -> List<String>
        }

    println(result)
    */


    // groupBy
    /*
    val cities = listOf("서울 강서구", "서울 강남구", "서울 영등포구", "수원 영통구", "수원 장안구", "대구 수서구", "부산 강서구")
    val result: Map<String, List<String>> = cities.groupBy { e ->
        val city = e.split(" ")
        // city[0]
        city[1]      // equals / hashCode
    }

    println(result)
    */

    // take: 컬렉션에서 요소를 추출합니다.
    // drop: 컬렉션에서 원하지 않는 요소를 제거한 컬렉션을 반환합니다.
    // first / firstOrNull
    // last  / lastOrNull
    // distinct: 중복된 요소를 제거한 버전을 반환합니다.

    val list = listOf(1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5)
    // val list = emptyList<Int>()
    // if (list.isNotEmpty())
    //    println(list[0])

//    list.firstOrNull()?.let(::println)
//    list.lastOrNull()?.let(::println)
//    println(list.take(5))
//    println(list.drop(3))

    val result = list.takeWhile {
        println(it)
        it < 5
    }
    println(result)

    val result2 = list.distinct()
    println(result2)

    // zip
    val a = listOf("Korea", "UnitedState", "China")
    val b = listOf("KR", "US", "CN")

    a.zip(b).forEach(::println)
    a.zip(b).forEach { e ->
        println("${e.first} / ${e.second}")
    }
    a.zip(b).forEach { (city, code) ->
        println("$city / $code")
    }
}

















