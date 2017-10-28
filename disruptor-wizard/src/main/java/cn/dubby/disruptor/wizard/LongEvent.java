package cn.dubby.disruptor.wizard;

/**
 * Created by teeyoung on 17/10/27.
 */
public class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
