算出两个人之间的共同好友

friendsdata.txt
其中冒号前面代表某个用户，冒号后面代表用户拥有的好友

A:B,C,D,F,E,O
B:A,C,E,K
C:F,A,D,I
D:A,E,F,L
E:B,C,D,M,L
F:A,B,C,D,E,O,M
G:A,C,D,E,F
H:A,C,D,E,O
I:A,O
J:B,O
K:A,C,D
L:D,E,F
M:E,F,G
O:A,H,I,J

操作：
1、将friendsdata.txt、hadoop-learn-1.0-SNAPSHOT.jar 上传到服务器
2、hadoop fs -mkdir -p /input/friends
3、hadoop fs -put friendsdata.txt /input/friends
4、hadoop jar hadoop-learn-1.0-SNAPSHOT.jar com/ucpaas/hadoop/learn/commonfriend/StepFirst /input/friends/friendsdata.txt /output/friends1
5、hadoop jar hadoop-learn-1.0-SNAPSHOT.jar com/ucpaas/hadoop/learn/commonfriend/StepSecond /output/friends1/part-r-00000 /output/friends2
6、hadoop fs -ls /output/friends1
7、hadoop fs -cat /output/friends2/*