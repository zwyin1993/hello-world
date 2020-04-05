package com.me.practice.struc;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

class LFUCache {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.computeIfAbsent("zhangsan", k -> 2);
        map.computeIfAbsent("zhangsan", k -> 3);
        map.forEach((k, v) -> System.out.println("key:" + k + ";value:" + v));
    }

    private int capacity;
    private HashMap<Integer, Node> cache;
    private HashMap<Integer, LinkedHashSet<Node>> freqMap; // key是频次，value是该频次对应的数据
    private int min = 0;
    private int size = 0;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.freqMap = new HashMap<>();
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (null == node) {
            return -1;
        }
        updateNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (this.capacity == 0) {
            return;
        }
        Node node = cache.get(key);
        if (null != node) {
            node.value = value; // update value
            updateNode(node); // update location
            return;
        }

        if (size == this.capacity) {
            Node deadNode = removeNode();
            cache.remove(deadNode.key);
            size--;
        }
        node = new Node(key, value);
        cache.put(key, node);
        addNode(node);
        size++;
    }

    public void updateNode(Node node) {
        // remove from pre list
        int freq = node.freq;
        LinkedHashSet<Node> set = freqMap.get(freq);
        set.remove(node);
        if (freq == min && set.isEmpty()) {
            min = freq + 1; // 之前频次的列表中节点已经移除
        }
        // add to new list
        node.freq++;
        LinkedHashSet<Node> newSet = freqMap.computeIfAbsent(freq + 1, k -> new LinkedHashSet<Node>());
        newSet.add(node);
    }

    public void addNode(Node node) {
        LinkedHashSet<Node> set = freqMap.computeIfAbsent(1, k -> new LinkedHashSet<>());
        set.add(node);
        min = 1;
    }


    public Node removeNode() {
        LinkedHashSet<Node> set = freqMap.get(min);
        Node deadNode = set.iterator().next();
        set.remove(deadNode);
        return deadNode;
    }

    public static class Node {
        int key;
        int value;
        int freq;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }
}
