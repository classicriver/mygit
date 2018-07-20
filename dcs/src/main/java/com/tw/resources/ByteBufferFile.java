package com.tw.resources;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ByteBufferFile extends FileResources {

	private String fileName;
	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}

	public ByteBufferFile(String fileName){
		this.fileName = fileName;
	}
	
	public ByteBuffer getFileByteBuffer() {
		FileChannel file = null;
		ByteBuffer byteBuf = null;
		try {
			file = stream.getChannel();
			int size = (int) file.size();
			byteBuf = ByteBuffer.allocate(size);
			file.read(byteBuf);
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				super.close();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return byteBuf;
	}

}
