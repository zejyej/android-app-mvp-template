package com.zejyej.base.bus

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
interface IBus {

    interface IEvent {
        fun getTag():Int
    }

    fun register(any: Any)

    fun unregister(any: Any)

    fun post(event: IEvent)

    fun postSticky(event: IEvent)

}