package com.dm.dmnetworking.api_client.base.model.progress;

public final class FileProgress {

    private long bytesWritten;

    private long totalSize;

    private int percent;

    private String percentString;

    public FileProgress(final long bytesWritten, final long totalSize, final int percent, final String percentString) {
        this.bytesWritten = bytesWritten;
        this.totalSize = totalSize;
        this.percent = percent;
        this.percentString = percentString;
    }

    public long getBytesWritten() {
        return bytesWritten;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public int getPercent() {
        return percent;
    }

    public String getPercentString() {
        return percentString;
    }
}
