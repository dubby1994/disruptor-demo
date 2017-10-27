package cn.dubby.disruptor.basic;

import com.lmax.disruptor.EventFactory;

/**
 * Created by teeyoung on 17/10/27.
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
