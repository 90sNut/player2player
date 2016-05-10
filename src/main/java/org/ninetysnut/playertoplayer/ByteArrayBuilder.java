package org.ninetysnut.playertoplayer;

import java.util.ArrayList;

public class ByteArrayBuilder {

	private ArrayList<Byte> bytes;

	public ByteArrayBuilder() {
		bytes = new ArrayList<>();
	}

	public ByteArrayBuilder append(Byte b) {
		bytes.add(b);
		return this;
	}

	public ByteArrayBuilder append(byte b) {
		append(Byte.valueOf(b));
		return this;
	}

	public ByteArrayBuilder append(Byte[] b) {
		for (Byte bt : b) {
			append(bt);
		}
		return this;
	}

	public ByteArrayBuilder append(byte[] b) {
		for (Byte bt : b) {
			append(Byte.valueOf(bt));
		}
		return this;
	}

	public Byte[] getByteArray() {
		return (Byte[]) bytes.toArray();
	}

	public byte[] getPrimitiveByteArray() {
		byte[] primitiveByteArray = new byte[bytes.size()];
		for (int i = 0; i < primitiveByteArray.length; i++) {
			primitiveByteArray[i] = bytes.get(i);
		}
		return primitiveByteArray;
	}
}
