package com.example.mvvmlogin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//Định nghĩa lớp LoginViewModel kế thừa từ ViewModel
class LoginViewModel(private val auth: FirebaseAuth) : ViewModel() {

    //Tham số constructor, nhận một instance của FirebaseAuth để tương tác với Firebase Authentication.
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = _uiState//Tạo một StateFlow chỉ đọc để cung cấp trạng thái cho UI

    //Hàm này xử lý kết quả đăng nhập bằng Google.
    fun handleGoogleSignInResult(account: GoogleSignInAccount?) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userEmail = auth.currentUser?.email
                            _uiState.value = LoginUiState.Success("Success!\n Hi  $userEmail\nWelcome to UTHSmartTasks!")
                        } else {
                            _uiState.value = LoginUiState.Failure("Google Sign-In Failed: ${task.exception?.message}")
                        }
                    }
            } else {
                _uiState.value = LoginUiState.Failure("Google Sign-In Failed\n User canceled the Google sign-in process")
            }
        }
    }
}
//Định nghĩa một sealed class để đại diện cho các trạng thái UI của quá trình đăng nhập.
sealed class LoginUiState {
    object Initial : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val message: String) : LoginUiState()
    data class Failure(val errorMessage: String) : LoginUiState()
}