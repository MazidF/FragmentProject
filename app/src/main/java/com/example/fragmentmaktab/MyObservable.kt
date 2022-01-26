package com.example.fragmentmaktab

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class MyObservable(val lifecycle: Lifecycle) : DefaultLifecycleObserver {
    lateinit var observers: MutableList<MyObserver>
    lateinit var list: MutableList<Int>
    lateinit var thread: Thread
    var loop = true

    init {
        list = (1..100).toMutableList()
        observers = mutableListOf()
        action()
        lifecycle.addObserver(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        loop = true
        action()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        loop = false
    }

    private fun action(millisecond: Long = 3000) {
        thread = Thread {
            while (loop) {
                Thread.sleep(millisecond)
                if (observers.isEmpty()) continue
                send(list.first())
                list.removeAt(0)
            }
        }
        thread.start()
    }

    private fun send(int: Int) {
        for (observer in observers) {
            observer.receive(int)
        }
    }

    fun register(observer: MyObserver) {
        observers.add(observer)
    }
    fun unregister(observer: MyObserver) {
        observers.remove(observer)
    }
}

class MyObserver {
    val id = id()

    companion object {
        var counter = 1
        fun id() = counter++
        const val TAG = "tag_tag"
    }

    private fun log(int: Int) {
        Log.d(TAG, "Observer$id received: $int")
    }

    fun receive(int: Int) {
        log(int)
    }
}
