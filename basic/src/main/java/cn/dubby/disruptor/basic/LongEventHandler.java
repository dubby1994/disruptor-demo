package cn.dubby.disruptor.basic;

import com.lmax.disruptor.EventHandler;

/**
 * Created by teeyoung on 17/10/27.
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event: " + event.getValue() + " ThreadName: " + Thread.currentThread().getName());
    }
}
