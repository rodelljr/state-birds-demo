package com.demo.usstatebirds.datamodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import java.io.IOException


@OptIn(ExperimentalStdlibApi::class)
fun getBirds(context: Context): List<Bird> {
    lateinit var jsonString: String
    try {
        jsonString = context.assets.open("statebirds.json")
            .bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        println(ioException)
    }
    val listStateBirds = object: TypeToken<List<Bird>>() {}.type
    return Gson().fromJson(jsonString, listStateBirds)
}


@SuppressLint("DiscouragedApi")
fun getDrawableFromName(state: String, context: Context, res: String ): Int {
    return context.resources.getIdentifier(state,res, context.packageName)
}


@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.userInteractionNotification(onInteracted: () -> Unit): Modifier {
    return pointerInput(onInteracted) {
        val currentContext = currentCoroutineContext()
        awaitPointerEventScope {
            while (currentContext.isActive) {
                val event = awaitPointerEvent(PointerEventPass.Initial)
                // if user taps (down) or scrolls - consider it an interaction signal
                if (
                    event.type == PointerEventType.Press || event.type == PointerEventType.Scroll
                ) {
                    onInteracted.invoke()
                }
            }
        }
    }
}