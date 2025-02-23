import com.example.chatapptest.database.model.UserData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FireStoreUserDao {
    fun getusercollectiion(): CollectionReference {
        return Firebase.firestore.collection("users")
    }

    suspend fun createuser(user: UserData) {
        val docRef = getusercollectiion().document(user.id ?: "")
        docRef.set(user).await()
    }

    suspend fun getuserbyid(uid: String?): DocumentSnapshot? {
        return getusercollectiion().document(uid ?: "").get().await()
    }
}
