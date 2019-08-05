package org.art.java_core.stream_api;

import org.art.java_core.stream_api.entity.DispLine;

import java.nio.ByteBuffer;
import java.util.Spliterator;
import java.util.function.Consumer;

class LineSpliterator implements Spliterator<DispLine> {

    private int lo;
    private int hi;

    private ByteBuffer bb;

    private static final int DEFAULT_AVERAGE_LINE_LENGTH = 10;

    LineSpliterator(ByteBuffer bb, int lo, int hi) {
        this.bb = bb;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public boolean tryAdvance(Consumer<? super DispLine> action) {
        int index = lo;
        StringBuilder sb = new StringBuilder();
        do {
            sb.append((char) bb.get(index));
        } while (bb.get(index++) != '\n');
        action.accept(new DispLine(lo, sb.toString()));
        lo = lo + sb.length();
        return lo <= hi;
    }

    @Override
    public Spliterator<DispLine> trySplit() {
        int index = lo;
        int mid = (lo + hi) >>> 1;
        while (bb.get(index) != '\n') {
            mid++;
            index++;
        }
        LineSpliterator newSpliterator = null;
        if (mid != hi) {
            newSpliterator = new LineSpliterator(bb, lo, mid);
        }
        return newSpliterator;
    }

    @Override
    public long estimateSize() {
        return (hi - lo + 1) / DEFAULT_AVERAGE_LINE_LENGTH;
    }

    @Override
    public int characteristics() {
        return ORDERED | IMMUTABLE | NONNULL;
    }
}
