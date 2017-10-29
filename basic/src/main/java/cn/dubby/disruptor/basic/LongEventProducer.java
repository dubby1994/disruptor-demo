package cn.dubby.disruptor.basic;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 本来可以用这个类来往ringbuffer里填充数据（包括了获取序列号，填充数据，提交）
 * 但是这些步骤如果交给使用者自己来保证，有一定的风险，所以后面退出了translator来自己完成这些操作
 *
 * Created by teeyoung on 17/10/27.
 */
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.set(bb.getLong(0));  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
