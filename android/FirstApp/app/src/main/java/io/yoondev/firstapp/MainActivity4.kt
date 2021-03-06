package io.yoondev.firstapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.yoondev.firstapp.databinding.MainActivity3Binding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit
//  : OKHttpClient를 이용할 때 보일러플레이트를 효과적으로 제거할 수 있습니다.
//   1) Request 생성
//   2) ResponseBody -> JSON -> gson -> User
//      : converter
//   3) runOnUiThread
//      : Callback<T> - onResponse / onFailure가 "main thread"에서 동작합니다.


// 1. API Interface 정의
// https://api.github.com/users/JakeWharton
// https://api.github.com/search/users?q=hello&per_page=5&page=3

/*
{
    "total_count": 89257,
    "incomplete_results": false,
    "items": []
}
*/

data class User(
    val login: String,
    val id: Int,
    // @field:SerializedName("avatar_url") val avatarUrl: String,
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val email: String?,

    val location: String?
)


data class UserSearchResult(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<User>,
)

/*
interface GithubApi {

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Call<User>

    @GET("search/users")
    fun searchUser(
        @Query("q") q: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 5
    ): Call<UserSearchResult>
}


//-----
// 2. OKHttpClient 객체 생성
private val httpClient = OkHttpClient.Builder().apply {

    addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    })

}.build()

// 3. Retrofit 객체 생성
private val retrofit: Retrofit = Retrofit.Builder().apply {

    baseUrl("https://api.github.com/")
    client(httpClient)

    val gson = GsonBuilder().apply {
        setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    }.create()

    addConverterFactory(GsonConverterFactory.create(gson))

}.build()

// 4. GithubApi 객체를 생성합니다.
val githubApi: GithubApi = retrofit.create(GithubApi::class.java)
*/

//------

class MainActivity4 : AppCompatActivity() {
    private val binding: MainActivity3Binding by viewBinding()

    companion object {
        private const val TAG1 = "MainActivity4"
    }

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener {

            val call = githubApi.getUser("JakeWharton")
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful.not())
                        return

                    val user = response.body() ?: return toast("Empty Body")
                    binding.loginTextView.text = user.login
                    binding.nameTextView.text = user.name
                    binding.avatarImageView.load(user.avatarUrl) {
                        crossfade(3000)
                        transformations(
                            CircleCropTransformation(),
                            GrayscaleTransformation(),
                        )
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    toast("Network Error - ${t.localizedMessage}")
                }

            })


        }
    }
    */


    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener {


            githubApi.searchUser("hello", per_page = 100)
                .enqueue(
                    onResponse = onResponse@{ response ->
                        if (response.isSuccessful.not())
                            return@onResponse

                        val user = response.body()
                            ?.items
                            ?.shuffled()
                            ?.firstOrNull()
                            ?: return@onResponse toast("Empty!")

                        updateUi(user)
                    },
                    onFailure = { t ->
                        toast("Error - ${t.localizedMessage}")
                    }
                )


            /*
            githubApi.searchUser("hello", per_page = 100)
                .enqueue(object : Callback<UserSearchResult> {
                    override fun onResponse(
                        call: Call<UserSearchResult>,
                        response: Response<UserSearchResult>
                    ) {
                        if (response.isSuccessful.not())
                            return

                        val user = response.body()
                            ?.items
                            ?.shuffled()
                            ?.firstOrNull()
                            ?: return toast("Empty!")

                        updateUi(user)
                    }

                    override fun onFailure(call: Call<UserSearchResult>, t: Throwable) =
                        toast("Error - ${t.localizedMessage}")

                })
            */
        }
    }
    */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener {


            // searchUser -> getUser -> location
            // 비동기 호출: 흐름 제어 어렵다

            // 동기
            /*
                a()
                b()
                c()
            */

            // 비동기 - Callback Hell
            //  => Reactive eXtension => Rx
            /*
                a {
                  b {
                    c {

                    }
                  }
                }
            */


            githubApi.searchUser("hello", per_page = 100)
                .enqueue(
                    onResponse = onResponse@{ response ->
                        if (response.isSuccessful.not())
                            return@onResponse

                        val login =
                            response.body()?.items?.shuffled()?.firstOrNull()?.login
                                ?: return@onResponse toast(
                                    "Empty result"
                                )

                        githubApi.getUser(login)
                            .enqueue(
                                onResponse = onResponseInner@{ responseUser ->

                                    if (responseUser.isSuccessful.not())
                                        return@onResponseInner

                                    val user = responseUser.body()
                                        ?: return@onResponseInner toast("Empty User")
                                    updateUi(user)
                                    toast("Location: ${user.location ?: "Unknown"}")

                                }, onFailure = { t ->
                                    toast("Error - ${t.localizedMessage}")
                                }
                            )
                    },
                    onFailure = { t ->
                        toast("Error - ${t.localizedMessage}")
                    }
                )
        }
    }


    fun updateUi(user: User) {
        binding.loginTextView.text = user.login
        binding.nameTextView.text = user.name
        binding.avatarImageView.load(user.avatarUrl) {
            crossfade(enable = true)
            transformations(
                CircleCropTransformation(),
                GrayscaleTransformation(),
            )
        }
    }
}

inline fun <T> Call<T>.enqueue(
    crossinline onResponse: (response: Response<T>) -> Unit,
    crossinline onFailure: (t: Throwable) -> Unit,
) = enqueue(object : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) = onResponse(response)
    override fun onFailure(call: Call<T>, t: Throwable) = onFailure(t)
})














