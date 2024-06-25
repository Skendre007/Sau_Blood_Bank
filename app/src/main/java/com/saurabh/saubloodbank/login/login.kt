import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

fun login(email: String, password: String, context: Context, callback: (Boolean) -> Unit) {
    val firebaseAuth = FirebaseAuth.getInstance()

    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                callback(true)
            } else {
                 val exception = task.exception
                when (exception) {
                    is FirebaseAuthInvalidUserException -> {
                         Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
                    }
                    is FirebaseAuthInvalidCredentialsException -> {
                         Toast.makeText(context, "Invalid credentials (wrong password)", Toast.LENGTH_SHORT).show()
                    }
                    is FirebaseAuthException -> {
                         Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                         Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
                callback(false)
            }
        }
}
