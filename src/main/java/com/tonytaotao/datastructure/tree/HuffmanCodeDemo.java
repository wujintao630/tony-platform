package com.tonytaotao.datastructure.tree;

import java.util.*;

public class HuffmanCodeDemo {

    public static void main(String[] args) {
        String content = "i like like like java do you like a java";

        byte[] contentBytes = content.getBytes();
        System.out.println("原始的字节长度：" + contentBytes.length);
        System.out.println("原始的字节数组 " + Arrays.toString(contentBytes));


        // 得到字符权值节点
        List<HCNode> nodes = getNodes(contentBytes);

        // 得到哈夫曼树
        HCNode root = createHuffmanTree(nodes);
        System.out.println("生成的哈夫曼树(前序遍历)");
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("空树");
        }

        // 得到哈夫曼编码表
        getHuffmanCodes(root);
        System.out.println("生成的哈夫曼编码表 " + huffmanCodes);

        // 得到哈夫曼编码后的字节数组
        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        System.out.println("哈夫曼编码后的字节长度:" + huffmanCodeBytes.length);
        System.out.println("哈夫曼编码后的字节 " + Arrays.toString(huffmanCodeBytes));
    }

    private static List<HCNode> getNodes(byte[] bytes) {
        List<HCNode> nodes = new ArrayList<>();

        Map<Byte, Integer> countMap = new HashMap<>();
        
        for(byte b : bytes) {
            Integer count = countMap.get(b);
            if (count == null) {
                countMap.put(b,1);
            } else {
                countMap.put(b, count + 1);
            }
        }

        for(Map.Entry<Byte, Integer> entry : countMap.entrySet()) {
            nodes.add(new HCNode(entry.getKey(),entry.getValue()));
        }

        return nodes;
    }

    public static HCNode createHuffmanTree(List<HCNode> nodes) {

        while (nodes.size() > 1) {

            // 由小到大排序
            Collections.sort(nodes);

            // 取出最小的节点
            HCNode left = nodes.get(0);
            HCNode right = nodes.get(1);

            // 形成父节点, 没有data, 只有权值
            HCNode parent = new HCNode( null ,left.weight + right.weight);
            parent.left = left;
            parent.right = right;

            // 移除已处理的节点
            nodes.remove(left);
            nodes.remove(right);

            // 形成的节点加入参与处理
            nodes.add(parent);
        }

        // 返回哈弗曼树的root节点
        return nodes.get(0);
    }

    // 格式 32->01， 97->100
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    // 拼接路径
    static StringBuilder stringBuilder = new StringBuilder();

    public static Map<Byte, String> getHuffmanCodes(HCNode root) {
        if(root == null) {
            return null;
        }
        getHuffmanCode(root.left, "0", stringBuilder);
        getHuffmanCode(root.right,"1", stringBuilder);

        return  huffmanCodes;
    }

    /**
     * 将传入的node节点的所有叶子节点的哈夫曼编码得到，并放入huffmanCodes集合
     * @param node 传入节点
     * @param code 路径： 左子节点0，右子节点1
     * @param stringBuilder 拼接路径
     */
    public static void getHuffmanCode(HCNode node, String code, StringBuilder stringBuilder) {
        StringBuilder sb = new StringBuilder(stringBuilder);
        sb.append(code);
        if (node != null) {
            // 判断当前是否是叶子节点
            if(node.data == null) { // 非叶子节点
                getHuffmanCode(node.left, "0", sb);
                getHuffmanCode(node.right, "1", sb);
            } else {
                // 是叶子节点
                huffmanCodes.put(node.data, sb.toString());
            }
        }
    }

    /**
     *
     * @param bytes 原始字符串对应的byte[]
     * @param huffmanCodes 哈夫曼编码的map
     * @return 哈夫曼编码处理后的byte[]
     */
    public static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b: bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        System.out.println("生成的哈夫曼编码串 = " + stringBuilder.toString());

        // 将哈夫曼编码串转换为byte[]
        int len;
        if (stringBuilder.length() % 8 == 0 ) {
            len = stringBuilder.length() / 8;
        } else {
            len = stringBuilder.length() / 8 + 1;
        }
        // 创建存储压缩后的byte数组
        byte[] result = new byte[len];
        int index = 0;
        for (int i = 0; i< stringBuilder.length(); i += 8) {
            String strByte;
            if ((i + 8) > stringBuilder.length()) {
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i+8);
            }

            // 将 strByte 转成 byte
            result[index] = (byte) Integer.parseInt(strByte, 2);
            index++;

        }
        return result;
    }

}

class HCNode implements Comparable<HCNode>{
    Byte data; // 数据本身 'a' => 97
    int weight; // 权值，字符出现的次数
    HCNode left;
    HCNode right;

    public HCNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(HCNode o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "HCNode{" +
                "weight=" + weight +
                ",data=" + data +
                '}';
    }
}
