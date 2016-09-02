package com.arangodb.internal.net.velocystream;

/**
 * @author Mark - mark at arangodb.com
 *
 */
public class Chunk {

	private final long messageId;
	private final long messageLength;
	private final int chunkX;
	private final int contentOffset;
	private final int contentLength;

	public Chunk(final long messageId, final int chunkX, final long messageLength, final int contentOffset,
		final int contentLength) {
		this.messageId = messageId;
		this.chunkX = chunkX;
		this.messageLength = messageLength;
		this.contentOffset = contentOffset;
		this.contentLength = contentLength;
	}

	public Chunk(final long messageId, final int chunkIndex, final int numberOfChunks, final long messageLength,
		final int contentOffset, final int contentLength) {
		this(messageId, chunkX(chunkIndex, numberOfChunks), messageLength, contentOffset, contentLength);
	}

	private static int chunkX(final int chunkIndex, final int numberOfChunks) {
		int chunkX;
		if (numberOfChunks == 1) {
			chunkX = 3;// last byte: 0000 0011
		} else if (chunkIndex == 0) {
			chunkX = (numberOfChunks << 1) + 1;
		} else {
			chunkX = chunkIndex << 1;
		}
		return chunkX;
	}

	public long getMessageId() {
		return messageId;
	}

	public long getMessageLength() {
		return messageLength;
	}

	public boolean isFirstChunk() {
		return 1 == (chunkX & 0x1);
	}

	public int getChunk() {
		return chunkX >> 1;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getContentOffset() {
		return contentOffset;
	}

	public int getContentLength() {
		return contentLength;
	}

}
