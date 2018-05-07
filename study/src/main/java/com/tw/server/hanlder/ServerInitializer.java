package com.tw.server.hanlder;

import java.util.concurrent.BlockingQueue;

import com.lmax.disruptor.RingBuffer;
import com.tw.model.Protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * 
 * @author pc
 * 
 */
public class ServerInitializer extends ChannelInitializer<Channel> {

	private final RingBuffer<Protocol> ringBuffer;

	public ServerInitializer(RingBuffer<Protocol> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new ServerHandler(ringBuffer));
	}

}
