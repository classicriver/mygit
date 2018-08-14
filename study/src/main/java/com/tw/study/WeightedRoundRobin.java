package com.tw.study;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author xiesc
 * @TODO 加权轮询
 * @time 2018年8月8日
 * @version 1.0
 */
public class WeightedRoundRobin {
	// 权重和
	private int total = 0;
	private List<Server> servers;

	WeightedRoundRobin(List<Server> servers) {
		this.servers = servers;
		for (Server s : servers) {
			total += s.getWeight();
		}
	}

	public Server getMaxWeightServer() {
		int maxWeight = 0;
		int index = 0;
		for (int i = 0; i < servers.size(); i++) {
			Server s = servers.get(i);
			int current_weight = s.getCurrent_weight();
			if (current_weight == 0) {
				current_weight = s.getWeight();
				s.setCurrent_weight(current_weight);
			}
			if (maxWeight < current_weight) {
				maxWeight = current_weight;
				index = i;
			}
			s.setCurrent_weight(s.getCurrent_weight() + s.getWeight());
		}
		Server server = servers.get(index);
		server.setCurrent_weight(server.getCurrent_weight() - total);
		return server;
	}

	public static void main(String[] args) {
		List<Server> list = new ArrayList<WeightedRoundRobin.Server>();
		WeightedRoundRobin.Server s1 = new WeightedRoundRobin.Server("a", 4);
		WeightedRoundRobin.Server s2 = new WeightedRoundRobin.Server("b", 2);
		WeightedRoundRobin.Server s3 = new WeightedRoundRobin.Server("c", 1);
		// WeightedRoundRobin.Server s4 = new WeightedRoundRobin.Server("4", 2);
		// WeightedRoundRobin.Server s5 = new WeightedRoundRobin.Server("5", 2);
		// WeightedRoundRobin.Server s6 = new WeightedRoundRobin.Server("6", 5);
		// WeightedRoundRobin.Server s7 = new WeightedRoundRobin.Server("7", 6);
		list.add(s1);
		list.add(s2);
		list.add(s3);
		/*
		 * list.add(s4); list.add(s5); list.add(s6); list.add(s7);
		 */
		WeightedRoundRobin weightedRoundRobin = new WeightedRoundRobin(list);
		for (int i = 0; i < 30; i++) {
			Server server = weightedRoundRobin.getMaxWeightServer();
			System.out.println(server.getIp());
		}
	}

	static class Server {
		private String ip;
		private int weight;
		// 当前权重
		private int current_weight;

		public Server(String ip, int weight) {
			this.ip = ip;
			this.weight = weight;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public int getCurrent_weight() {
			return current_weight;
		}

		public void setCurrent_weight(int current_weight) {
			this.current_weight = current_weight;
		}

	}

}
