package com.zejyej.base.bus

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

/**
 * @desc
 * @author zejyej
 * @date 2018/5/4
 */
class RxBus private constructor() : IBus {

    private var bus: FlowableProcessor<Any> = PublishProcessor.create<Any>().toSerialized()

    override fun register(any : Any) {

    }

    override fun unregister(any: Any) {

    }

    override fun post(event: IBus.IEvent) {
        bus.onNext(event)

    }

    override fun postSticky(event: IBus.IEvent) {

    }

    fun <T : IBus.IEvent> toFlowable(eventType: Class<T>): Flowable<T> {
        return bus.ofType(eventType).onBackpressureBuffer()
    }

    private object Holder {
        private val instance = RxBus()
    }

    companion object {

        fun get(): RxBus {
            return RxBus()
        }
    }
}
