package com.binar.melif.utils


import com.binar.melif.data.firebase.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException




suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, onCancellation = null)
            }
        }

    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun DatabaseReference.setValueAsync(data: Any): Boolean {
    return suspendCancellableCoroutine { cont ->
        setValue(data)
            .addOnCompleteListener {
                cont.resume(true, onCancellation = null)

            }
            .addOnCanceledListener {
                cont.resume(false, onCancellation = null)

            }
            .addOnFailureListener {
                cont.resumeWithException(it)
            }

    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun DatabaseReference.setValueAppendId(mapData: (id: String) -> Any): Boolean {
    return suspendCancellableCoroutine { cont ->
        setValue(mapData(key.toString()))
            .addOnCompleteListener {
                cont.resume(true, onCancellation = null)

            }
            .addOnCanceledListener {
                cont.resume(false, onCancellation = null)

            }
            .addOnFailureListener {
                cont.resumeWithException(it)
            }

    }
}


fun FirebaseUser.toUserObject(): User {
    return User(
        displayName.orEmpty(),
        email.orEmpty(),
        photoUrl.toString()
    )
}
